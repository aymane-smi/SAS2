package org.sas2.DAO;
import java.util.List;

public interface PivoteDAO {
    /**
     * Creates a new record with the given ISBN and ID.
     *
     * @param isbn The ISBN of the book.
     * @param id   The ID associated with the book.
     * @return true if the record was created successfully, false otherwise.
     */
    boolean create(String isbn, int id);

    /**
     * Deletes a book record by its ISBN.
     *
     * @param isbn The ISBN of the book to delete.
     * @return true if the record was deleted successfully, false otherwise.
     */
    boolean deleteBook(String isbn);

    /**
     * Deletes a record by ISBN and ID.
     *
     * @param isbn The ISBN of the book.
     * @param id   The ID associated with the book.
     * @return true if the record was deleted successfully, false otherwise.
     */
    boolean delete(String isbn, int id);

    /**
     * Retrieves a list of IDs associated with a given ISBN.
     *
     * @param isbn The ISBN to query.
     * @return A List of integers representing IDs associated with the ISBN.
     */
    List<Integer> getByIsbn(String isbn);

    /**
     * Retrieves a list of ISBNs associated with a given ID.
     *
     * @param id The ID to query.
     * @return A List of strings representing ISBNs associated with the ID.
     */
    List<String> getById(int id);

    /**
     * Deletes a specific record by ID and ISBN.
     *
     * @param id   The ID associated with the book.
     * @param isbn The ISBN of the book.
     * @return true if the record was deleted successfully, false otherwise.
     */
    boolean deleteOne(int id, String isbn);
}
