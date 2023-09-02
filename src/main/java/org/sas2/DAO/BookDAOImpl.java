package org.sas2.DAO;

import org.sas2.Connection.ConnectionJDBC;
import org.sas2.Entity.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class BookDAOImpl implements BookDAO{

    private Connection connection;

    public BookDAOImpl(){
        connection = ConnectionJDBC.getConnection();
    }
    @Override
    public boolean create(Book book) {
        try{
            String query = "INSERT INTO Book(isbn, title, author, status) VALUES(?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            int result = stmt.executeUpdate();
            stmt.close();
            if(result > 0)
                return true;
            else
                throw new Exception("failedto insert record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Book book) {
        try{
            String query = "UPDATE Book title = ? author =? status = ? WHERE isbn = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getStatus());
            stmt.setString(4, book.getIsbn());
            int result = stmt.executeUpdate();
            stmt.close();
            if(result > 0)
                return true;
            else
                throw new Exception("failed to update record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteByIsbn(String isbn) {
        try{
            String query = "DELETE FROM Book WHERE isbn = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(4, isbn);
            int result = stmt.executeUpdate();
            if(result > 0)
                return true;
            else
                throw new Exception("failed to update record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        Book tmp = null;
        try{
            String query = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(4, isbn);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp = new Book(result.getString("isbn"), result.getString("title"), result.getString("author"), result.getString("status"));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Book> getBookByAuthor(String name) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public boolean ReturnBook(String isbn) {
        return false;
    }
}
