/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.model.Book;
import library.model.Member;

/**
 * LibraryService = tempat semua logika bisnis perpustakaan.
 * MainApp hanya urus tampilan/menu, semua proses ada di sini.
 */
public class LibraryService {

    // Koleksi buku disimpan di ArrayList
    private ArrayList<Book> daftarBuku = new ArrayList<>();

    // Data anggota: key = id anggota, value = objek Member
    private HashMap<String, Member> daftarAnggota = new HashMap<>();

    // ===== Data untuk analisis aktivitas =====
    private HashMap<String, Integer> hitungPinjamBuku = new HashMap<>();      // judul -> jumlah dipinjam
    private HashMap<String, Integer> hitungAktivitasAnggota = new HashMap<>(); // nama -> jumlah pinjam
    private HashMap<String, Integer> hitungPinjamKategori = new HashMap<>();   // kategori -> jumlah pinjam
    private int totalPinjaman = 0;

    // =====================================================
    // 1. MANAJEMEN DATA BUKU
    // =====================================================

    public void tambahBuku(String judul, String penulis, int tahunTerbit, String kategori) {
        Book bukuBaru = new Book(judul, penulis, tahunTerbit, kategori);
        daftarBuku.add(bukuBaru);
    }

    public ArrayList<Book> getDaftarBuku() {
        return daftarBuku;
    }

    // =====================================================
    // 2. PENCARIAN & ANALISIS BUKU
    // =====================================================

    /**
     * Cari buku berdasarkan judul ATAU kategori.
     * Pakai toLowerCase() dan contains() supaya tidak peka huruf besar/kecil.
     */
    public ArrayList<Book> cariBuku(String kataKunci) {
        ArrayList<Book> hasil = new ArrayList<>();
        String kunci = kataKunci.trim().toLowerCase();

        for (Book b : daftarBuku) {
            boolean cocokJudul = b.getJudul().toLowerCase().contains(kunci);
            boolean cocokKategori = b.getKategori().toLowerCase().contains(kunci);
            if (cocokJudul || cocokKategori) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    /**
     * Hitung jumlah buku di setiap kategori menggunakan looping.
     */
    public Map<String, Integer> hitungBukuPerKategori() {
        Map<String, Integer> hasil = new LinkedHashMap<>();
        for (Book b : daftarBuku) {
            String kategori = b.getKategori().toLowerCase();
            if (hasil.containsKey(kategori)) {
                hasil.put(kategori, hasil.get(kategori) + 1);
            } else {
                hasil.put(kategori, 1);
            }
        }
        return hasil;
    }

    // Cari satu buku berdasarkan judul persis (tidak peka huruf besar/kecil)
    private Book cariBukuByJudul(String judul) throws BookNotFoundException {
        for (Book b : daftarBuku) {
            if (b.getJudul().equalsIgnoreCase(judul.trim())) {
                return b;
            }
        }
        throw new BookNotFoundException(judul);
    }

    // =====================================================
    // 3. SISTEM ANGGOTA
    // =====================================================

    /**
     * Daftarkan anggota baru. Jika id sudah ada, kembalikan anggota yang lama.
     * Validasi id: hanya boleh huruf/angka (pakai Character).
     */
    public Member daftarkanAnggota(String id, String nama) {
        for (int i = 0; i < id.length(); i++) {
            if (!Character.isLetterOrDigit(id.charAt(i))) {
                throw new IllegalArgumentException("ID anggota hanya boleh huruf dan angka.");
            }
        }
        if (daftarAnggota.containsKey(id)) {
            return daftarAnggota.get(id);
        }
        Member baru = new Member(id, nama);
        daftarAnggota.put(id, baru);
        return baru;
    }

    // =====================================================
    // 4. PROSES PEMINJAMAN & PENGEMBALIAN
    // =====================================================

    public void pinjamBuku(String idAnggota, String judul)
            throws BookNotFoundException, BookAlreadyBorrowedException, BorrowLimitExceededException {

        Member anggota = daftarAnggota.get(idAnggota);

        // ASSERTION: pastikan data anggota valid sebelum transaksi
        // (aktifkan dengan VM option -ea di NetBeans)
        assert anggota != null : "Anggota tidak boleh null";
        assert anggota.getNama() != null && !anggota.getNama().isEmpty() : "Nama anggota tidak valid";

        Book buku = cariBukuByJudul(judul);              // BookNotFoundException

        if (!buku.isTersedia()) {
            throw new BookAlreadyBorrowedException(buku.getJudul());
        }
        if (anggota.getJumlahPinjaman() >= Member.MAX_PINJAM) {
            throw new BorrowLimitExceededException(anggota.getNama(), Member.MAX_PINJAM);
        }

        // Proses pinjam
        buku.setTersedia(false);
        anggota.tambahPinjaman(buku);

        // Catat statistik untuk laporan
        totalPinjaman++;
        tambahHitungan(hitungPinjamBuku, buku.getJudul());
        tambahHitungan(hitungAktivitasAnggota, anggota.getNama());
        tambahHitungan(hitungPinjamKategori, buku.getKategori().toLowerCase());
    }

    public void kembalikanBuku(String idAnggota, String judul) throws BookNotFoundException {
        Member anggota = daftarAnggota.get(idAnggota);

        assert anggota != null : "Anggota tidak boleh null";

        Book buku = cariBukuByJudul(judul);

        if (!anggota.getDaftarPinjaman().contains(buku)) {
            throw new BookNotFoundException(judul + " (tidak ada di daftar pinjaman anggota ini)");
        }

        buku.setTersedia(true);
        anggota.hapusPinjaman(buku);
    }

    // Method bantu: tambah angka +1 pada HashMap
    private void tambahHitungan(HashMap<String, Integer> map, String key) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + 1);
        } else {
            map.put(key, 1);
        }
    }

    // =====================================================
    // 5. ANALISIS AKTIVITAS
    // =====================================================

    // Cari key dengan nilai terbesar di sebuah HashMap
    private String cariTerbanyak(HashMap<String, Integer> map) {
        String terbanyak = "-";
        int max = 0;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            if (e.getValue() > max) {
                max = e.getValue();
                terbanyak = e.getKey() + " (" + max + "x)";
            }
        }
        return terbanyak;
    }

    public String bukuPalingSeringDipinjam() {
        return cariTerbanyak(hitungPinjamBuku);
    }

    public String anggotaPalingAktif() {
        return cariTerbanyak(hitungAktivitasAnggota);
    }

    public String kategoriPalingPopuler() {
        return cariTerbanyak(hitungPinjamKategori);
    }

    public int getTotalPinjaman() {
        return totalPinjaman;
    }

    public void cetakLaporan() {
        System.out.println("\n===== LAPORAN PERPUSTAKAAN =====");
        System.out.println("Total buku               : " + daftarBuku.size());
        System.out.println("Jumlah total pinjaman    : " + totalPinjaman);
        System.out.println("Buku paling sering dipinjam: " + bukuPalingSeringDipinjam());
        System.out.println("Anggota paling aktif     : " + anggotaPalingAktif());
        System.out.println("Kategori paling populer  : " + kategoriPalingPopuler());

        System.out.println("\nJumlah buku per kategori:");
        for (Map.Entry<String, Integer> e : hitungBukuPerKategori().entrySet()) {
            System.out.println("- " + e.getKey() + ": " + e.getValue());
        }
    }
}
