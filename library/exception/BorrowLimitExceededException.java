/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.exception;

/**
 *
 * @author Hype AMD
 */

/**
 * Custom exception: dilempar ketika anggota mencoba meminjam lebih dari 3 buku.
 */
public class BorrowLimitExceededException extends Exception {

    public BorrowLimitExceededException(String namaAnggota, int batas) {
        super("Anggota '" + namaAnggota + "' sudah mencapai batas pinjam (" + batas + " buku).");
    }
}