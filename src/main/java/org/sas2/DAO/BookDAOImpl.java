package org.sas2.DAO;

import org.sas2.Connection.ConnectionJDBC;
import org.sas2.Entity.Book;
import org.sas2.Entity.BookStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getStatus());
            int result = stmt.executeUpdate();
            stmt.close();
            if(result > 0)
                return true;
            else
                throw new Exception("failed to insert record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Book book) {
        try{
            String query = "UPDATE Book title = ?, author =?, status = ? WHERE isbn = ?";
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
            stmt.setString(1, isbn);
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
            stmt.setString(1, isbn);
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
        List<Book> tmp = new ArrayList<>();
        try{
            String query = "SELECT * FROM Book WHERE author = ? ";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp.add(new Book(result.getString("isbn"), result.getString("title"), result.getString("author"), result.getString("status")));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Book> getAll() {
        List<Book> tmp = new ArrayList<>();
        try{
            String query = "SELECT * FROM Book WHERE status = 'AVAILABLE' ";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp.add(new Book(result.getString("isbn"), result.getString("title"), result.getString("author"), result.getString("status")));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean ReturnBook(String isbn) {
        try{
            String query = "UPDATE Book SET status = ? WHERE isbn = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, BookStatus.AVAILABLE.name());
            stmt.setString(2, isbn);
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

    public boolean updateStatus(String isbn, String status){
        try{
            String query = "UPDATE Book SET status = ? WHERE isbn = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setString(2, isbn);
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


}
