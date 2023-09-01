package org.sas2.Entity;

public class Borrower {
    private String name;

    private int memeberId;

    public Borrower(int memeberId, String name) {
        this.name = name;
        this.memeberId = memeberId;
    }

    public Borrower(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getMemeberId() {
        return memeberId;
    }
}
