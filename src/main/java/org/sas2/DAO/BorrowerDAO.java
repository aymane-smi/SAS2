package org.sas2.DAO;

import org.sas2.Entity.Borrower;

import java.util.Map;

public interface BorrowerDAO {
    public Map<Boolean, Integer> create(Borrower borrower);

    public boolean updatebyId(Borrower borrower);

    public boolean deleteById(int id);

    public Borrower getById(int id);
}
