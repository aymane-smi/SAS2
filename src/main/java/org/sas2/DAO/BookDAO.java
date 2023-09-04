package org.sas2.DAO;

import org.sas2.Entity.Book;

import java.util.List;
import java.util.Map;

public interface BookDAO {
    public boolean create(Book book);
    public boolean update(Book book);

    public boolean deleteByIsbn(String isbn);

    public Book getBookByIsbn(String isbn);

    public List<Book> getBookByAuthor(String name);

    public List<Book> getAll();

    public boolean ReturnBook(String isbn);

    public boolean updateStatus(String isbn, String status);
}
