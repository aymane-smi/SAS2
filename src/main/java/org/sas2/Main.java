package org.sas2;

import org.sas2.Connection.ConnectionJDBC;
import org.sas2.Controller.Manager;

import java.sql.Connection;

public class Main {
    public static void main(String[] args){
        Manager manager = new Manager();

        //manager.addBook();
        manager.showStats();
    }
}
