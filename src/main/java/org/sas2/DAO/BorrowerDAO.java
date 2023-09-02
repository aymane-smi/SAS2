package org.sas2.DAO;

import org.sas2.Entity.Borrower;

public interface BorrowerDAO {
    public boolean create(Borrower borrower);

    public boolean updatebyId(Borrower borrower);

    public boolean deleteById(int id);

    public Borrower getById(int id);
}
