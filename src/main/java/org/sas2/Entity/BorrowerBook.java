package org.sas2.Entity;

import java.util.Date;

public class BorrowerBook {

    private String isbn;
    private int idBorrower;
    private Date borrow_start;
    public String getIsbn() {
        return isbn;
    }
    public BorrowerBook(String isbn, int idBorrower, Date borrow_start, Date borrow_end) {
        this.isbn = isbn;
        this.idBorrower = idBorrower;
        this.borrow_start = borrow_start;
        this.borrow_end = borrow_end;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getIdBorrower() {
        return idBorrower;
    }

    public void setIdBorrower(int idBorrower) {
        this.idBorrower = idBorrower;
    }

    public Date getBorrow_start() {
        return borrow_start;
    }

    public void setBorrow_start(Date borrow_start) {
        this.borrow_start = borrow_start;
    }

    public Date getBorrow_end() {
        return borrow_end;
    }

    public void setBorrow_end(Date borrow_end) {
        this.borrow_end = borrow_end;
    }

    private Date borrow_end;
}
