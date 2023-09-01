package org.sas2.DAO;

import java.util.List;

public class PivoteDAOImpl implements PivoteDAO{
    @Override
    public boolean create(String isbn, int id) {
        return false;
    }

    @Override
    public boolean deleteBook(String isbn) {
        return false;
    }

    @Override
    public boolean delete(String isbn, int id) {
        return false;
    }

    @Override
    public List<Integer> getByIsbn(String isbn) {
        return null;
    }

    @Override
    public List<String> getById(int id) {
        return null;
    }

    @Override
    public boolean deleteOne(int id, String isbn) {
        return false;
    }
}
