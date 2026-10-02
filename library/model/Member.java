/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

/**
 *
 * @author Hype AMD
 */

import java.util.ArrayList;

/**
 * Class Member = representasi anggota perpustakaan.
 * Atribut: id, nama, daftarPinjaman.
 */
public class Member {

    public static final int MAX_PINJAM = 3;   // batas maksimal pinjam

    private String id;
    private String nama;
    private ArrayList<Book> daftarPinjaman;   // menyimpan buku yang sedang dipinjam

    public Member(String id, String nama) {
        this.id = id;
        this.nama = nama;
        this.daftarPinjaman = new ArrayList<>();
    }

    public void tambahPinjaman(Book buku) {
        daftarPinjaman.add(buku);
    }

    public void hapusPinjaman(Book buku) {
        daftarPinjaman.remove(buku);
    }

    public int getJumlahPinjaman() {
        return daftarPinjaman.size();
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public ArrayList<Book> getDaftarPinjaman() {
        return daftarPinjaman;
    }

    @Override
    public String toString() {
        return id + " - " + nama + " (pinjam: " + daftarPinjaman.size() + ")";
    }
}
