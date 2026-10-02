/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

/**
 *
 * @author Hype AMD
 */

/**
 * Class Book = representasi satu buku di perpustakaan.
 * Menggunakan constructor untuk inisialisasi data.
 */
public class Book {

    // ===== Variabel (atribut) =====
    private String judul;          // reference type
    private String penulis;        // reference type
    private int tahunTerbit;       // primitive type
    private String kategori;       // reference type
    private boolean tersedia;      // primitive type (statusKetersediaan)

    // ===== Constructor =====
    public Book(String judul, String penulis, int tahunTerbit, String kategori) {
        this.judul = formatKapital(judul);   // manipulasi String + Character
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori;
        this.tersedia = true;                // buku baru otomatis tersedia
    }

    /**
     * Mengubah huruf pertama tiap kata jadi kapital.
     * Contoh: "belajar java dasar" -> "Belajar Java Dasar"
     * Menggunakan manipulasi Character (isLetter, toUpperCase) dan String.
     */
    public static String formatKapital(String teks) {
        String hasil = "";
        boolean awalKata = true;
        for (int i = 0; i < teks.length(); i++) {
            char c = teks.charAt(i);
            if (awalKata && Character.isLetter(c)) {
                hasil += Character.toUpperCase(c);
                awalKata = false;
            } else {
                hasil += Character.toLowerCase(c);
            }
            if (c == ' ') {
                awalKata = true;
            }
        }
        return hasil.trim();
    }

    // ===== Getter & Setter =====
    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public String getKategori() {
        return kategori;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    @Override
    public String toString() {
        String status = tersedia ? "Tersedia" : "Dipinjam";
        return judul + " | " + penulis + " | " + tahunTerbit + " | " + kategori + " | " + status;
    }
}