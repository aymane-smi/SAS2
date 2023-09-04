package org.sas2.DAO;

import org.sas2.Connection.ConnectionJDBC;
import org.sas2.Entity.Borrower;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class BorrowerDAOImpl implements BorrowerDAO{
    private Connection connection;

    public BorrowerDAOImpl(){
        connection = ConnectionJDBC.getConnection();
    }

    @Override
    public Map<Boolean, Integer> create(Borrower borrower){
        Map<Boolean, Integer> resultMap = new HashMap<>();
        try{
            String query = "INSERT INTO Borrower(name) VALUES(?)";
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, borrower.getName());
            int result = stmt.executeUpdate();
            if(result > 0){
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    resultMap.put(true, generatedId);
                    return resultMap;
                }
            }else
                throw new Exception("failed to insert record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            resultMap.put(false, -1);
            return resultMap;
        }
        return resultMap;
    }

    @Override
    public boolean updatebyId(Borrower borrower){
        try{
            String query = "UPDATE Borrower SET name = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, borrower.getName());
            stmt.setInt(2, borrower.getMemeberId());
            int result = stmt.executeUpdate();
            if(result > 1)
                return true;
            else
                throw new Exception("failed to insert record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id){
        try{
            String query = "DELETE Borrower WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            int result = stmt.executeUpdate();
            if(result > 1)
                return true;
            else
                throw new Exception("failed to insert record");
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Borrower getById(int id){
        Borrower tmp = null;
        try{
            String query = "SELECT * FROM Borrower WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            while(result.next()){
                tmp = new Borrower(result.getInt("id"), result.getString("name"));
            }
            return tmp;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return tmp;
        }
    }
}