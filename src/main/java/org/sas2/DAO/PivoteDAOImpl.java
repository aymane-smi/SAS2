package org.sas2.DAO;

import org.sas2.Connection.ConnectionJDBC;
import org.sas2.Entity.BorrowerBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PivoteDAOImpl implements PivoteDAO{
    private Connection connection;

    public PivoteDAOImpl(){
        connection = ConnectionJDBC.getConnection();
    }
    @Override
    public boolean create(String isbn, int id, Date borrow_end) {
        try{
            String query = "INSERT INTO Borrower_Book(isbn, idBorrower, borrow_start, borrow_end) VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, isbn);
            stmt.setInt(2, id);
            //setting current date for borrowing or loan
            Calendar calendar = Calendar.getInstance();
            stmt.setDate(3, new java.sql.Date(calendar.getTime().getTime()));
            //setting end of borrow/loan date
            stmt.setDate(4, new java.sql.Date(borrow_end.getTime()));
            int result = stmt.executeUpdate();
            if(result > 0)
                return true;
            else
                throw new Exception("failed o insert record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteBook(String isbn) {
        try{
            BookDAOImpl book = new BookDAOImpl();
            String bookSatus = book.getBookByIsbn(isbn).getStatus();
            if(bookSatus.equals("LOST") || bookSatus.equals("BORROWED"))
                throw new Exception("can't delete book with status <LOST|BORROWED>");
            else{
                String query = "DELETE FROM Borrower_Book WHERE isbn = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, isbn);
                int result = stmt.executeUpdate();
                if(result > 0)
                    return true;
                else
                    throw new Exception("failed o delete record");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String isbn, int id) {
        try{
            BookDAOImpl book = new BookDAOImpl();
            String bookSatus = book.getBookByIsbn(isbn).getStatus();
            if(bookSatus.equals("LOST") || bookSatus.equals("BORROWED"))
                throw new Exception("can't delete book with status <LOST|BORROWED>");
            else {
                String query = "DELETE FROM Borrower_Book WHERE isbn = ? AND idBorrower = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, isbn);
                stmt.setInt(2, id);
                int result = stmt.executeUpdate();
                if (result > 0)
                    return true;
                else
                    throw new Exception("failed o delete record");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public BorrowerBook getByIsbn(String isbn) {
        BorrowerBook tmp = null;
        try{
            String query = "SELECT * FROM Borrower_Book WHERE isbn = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, isbn);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp = new BorrowerBook(isbn, result.getInt("idBorrower"), result.getDate("borrow_start"), result.getDate("borrow_end"));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<BorrowerBook> getById(int id) {
        List<BorrowerBook> tmp = new ArrayList<>();
        try{
            String query = "SELECT * FROM Borrower_Book WHERE idBorrower = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp.add(new BorrowerBook(result.getString("isbn"), id, result.getDate("borrow_start"), result.getDate("borrow_end")));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<BorrowerBook> getAll() {
        List<BorrowerBook> tmp = new ArrayList<>();
        try{
            String query = "SELECT * FROM Borrower_Book";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp.add(new BorrowerBook(result.getString("isbn"), result.getInt("idBorrower"), result.getDate("borrow_start"), result.getDate("borrow_end")));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
