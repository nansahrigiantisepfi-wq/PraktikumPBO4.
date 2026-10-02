/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.main;

/**
 *
 * @author Hype AMD
 */


import java.util.ArrayList;
import java.util.Scanner;

import library.exception.BookAlreadyBorrowedException;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.model.Book;
import library.model.Member;
import library.service.LibraryService;

/**
 * MainApp = class utama (ada method main).
 * Tugasnya hanya menampilkan menu dan membaca input dengan Scanner.
 */
public class MainApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LibraryService service = new LibraryService();

        // Data awal supaya program bisa langsung dicoba
        service.tambahBuku("belajar java dasar", "Budi Santoso", 2020, "Teknologi");
        service.tambahBuku("algoritma dan pemrograman", "Siti Aminah", 2019, "Teknologi");
        service.tambahBuku("laskar pelangi", "Andrea Hirata", 2005, "Novel");
        service.tambahBuku("bumi manusia", "Pramoedya Ananta Toer", 1980, "Novel");
        service.tambahBuku("sejarah indonesia", "Eko Prasetyo", 2015, "Sejarah");

        boolean jalan = true;   // primitive boolean untuk kontrol looping

        while (jalan) {
            tampilkanMenu();
            System.out.print("Pilih menu: ");
            String pilihan = input.nextLine().trim();

            switch (pilihan) {
                case "1":
                    tambahBuku(input, service);
                    break;
                case "2":
                    daftarBuku(service);
                    break;
                case "3":
                    cariBuku(input, service);
                    break;
                case "4":
                    pinjamBuku(input, service);
                    break;
                case "5":
                    kembalikanBuku(input, service);
                    break;
                case "6":
                    service.cetakLaporan();
                    break;
                case "0":
                    System.out.println("Terima kasih, sampai jumpa!");
                    jalan = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid, coba lagi.");
            }
        }
        input.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== PERPUSTAKAAN MINI =====");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Daftar Buku");
        System.out.println("3. Cari Buku");
        System.out.println("4. Pinjam Buku");
        System.out.println("5. Kembalikan Buku");
        System.out.println("6. Laporan Perpustakaan");
        System.out.println("0. Keluar");
    }

    // ---------- 1. Tambah Buku ----------
    private static void tambahBuku(Scanner input, LibraryService service) {
        System.out.print("Judul    : ");
        String judul = input.nextLine();
        System.out.print("Penulis  : ");
        String penulis = input.nextLine();
        System.out.print("Tahun    : ");
        String tahunStr = input.nextLine();
        System.out.print("Kategori : ");
        String kategori = input.nextLine();

        try {
            int tahun = Integer.parseInt(tahunStr.trim());
            service.tambahBuku(judul, penulis, tahun, kategori);
            System.out.println("Buku berhasil ditambahkan.");
        } catch (NumberFormatException e) {
            System.out.println("Gagal: tahun harus berupa angka.");
        }
    }

    // ---------- 2. Daftar Buku ----------
    private static void daftarBuku(LibraryService service) {
        ArrayList<Book> semua = service.getDaftarBuku();
        if (semua.isEmpty()) {
            System.out.println("Belum ada buku.");
            return;
        }
        System.out.println("\nJudul | Penulis | Tahun | Kategori | Status");
        int no = 1;
        for (Book b : semua) {
            System.out.println(no + ". " + b);
            no++;
        }
    }

    // ---------- 3. Cari Buku ----------
    private static void cariBuku(Scanner input, LibraryService service) {
        System.out.print("Kata kunci (judul/kategori): ");
        String kunci = input.nextLine();

        ArrayList<Book> hasil = service.cariBuku(kunci);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada buku yang cocok.");
        } else {
            System.out.println("Hasil pencarian:");
            for (Book b : hasil) {
                System.out.println("- " + b);
            }
        }
    }

    // ---------- 4. Pinjam Buku ----------
    private static void pinjamBuku(Scanner input, LibraryService service) {
        System.out.print("ID anggota   : ");
        String id = input.nextLine().trim();
        System.out.print("Nama anggota : ");
        String nama = input.nextLine().trim();
        System.out.print("Judul buku   : ");
        String judul = input.nextLine();

        try {
            // daftarkan anggota (kalau belum ada) lalu pinjam
            Member anggota = service.daftarkanAnggota(id, nama);
            service.pinjamBuku(anggota.getId(), judul);
            System.out.println("Peminjaman berhasil. Total pinjaman " + anggota.getNama()
                    + ": " + anggota.getJumlahPinjaman() + " buku.");
        } catch (BookNotFoundException | BookAlreadyBorrowedException | BorrowLimitExceededException e) {
            System.out.println("Gagal: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }

    // ---------- 5. Kembalikan Buku ----------
    private static void kembalikanBuku(Scanner input, LibraryService service) {
        System.out.print("ID anggota   : ");
        String id = input.nextLine().trim();
        System.out.print("Nama anggota : ");
        String nama = input.nextLine().trim();
        System.out.print("Judul buku   : ");
        String judul = input.nextLine();

        try {
            Member anggota = service.daftarkanAnggota(id, nama);
            service.kembalikanBuku(anggota.getId(), judul);
            System.out.println("Buku berhasil dikembalikan.");
        } catch (BookNotFoundException e) {
            System.out.println("Gagal: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal: " + e.getMessage());
        }
    }
}