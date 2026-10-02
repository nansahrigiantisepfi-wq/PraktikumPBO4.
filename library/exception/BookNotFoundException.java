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
 * Custom exception: dilempar ketika buku tidak ditemukan.
 */
public class BookNotFoundException extends Exception {

    public BookNotFoundException(String judul) {
        super("Buku dengan judul '" + judul + "' tidak ditemukan.");
    }
}
