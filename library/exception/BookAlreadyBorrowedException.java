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
 * Custom exception (tambahan): dilempar ketika buku sedang dipinjam orang lain.
 */
public class BookAlreadyBorrowedException extends Exception {

    public BookAlreadyBorrowedException(String judul) {
        super("Buku '" + judul + "' sedang dipinjam.");
    }
}
