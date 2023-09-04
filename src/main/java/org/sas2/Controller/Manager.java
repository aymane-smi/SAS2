package org.sas2.Controller;

import org.sas2.DAO.BookDAOImpl;
import org.sas2.DAO.BorrowerDAOImpl;
import org.sas2.DAO.PivoteDAOImpl;
import org.sas2.Entity.Book;
import org.sas2.Entity.BookStatus;
import org.sas2.Entity.Borrower;
import org.sas2.Entity.BorrowerBook;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Manager {
    private BookDAOImpl book;
    private BorrowerDAOImpl borrower;
    private PivoteDAOImpl pivote;

    public Manager(){
        book = new BookDAOImpl();
        borrower = new BorrowerDAOImpl();
        pivote = new PivoteDAOImpl();
    }

    /*
    * add a book
    */

    public void addBook(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("titre:");
        String title = scanner.nextLine();
        System.out.print("auteur:");
        String author = scanner.nextLine();
        String isbn = UUID.randomUUID().toString();
        book.create(new Book(isbn, title, author, BookStatus.AVAILABLE.name()));
        System.out.println("*****   LIVRE AJOUTÉ   *****\n");
    }

    /*
     * get all available book
     */
    public void showBooks(){
        List<Book> books = book.getAll();
        System.out.println("*****   LISTE DES LIVRE DISPONIBLE   *****\n");
        for(Book tmp : books){
            System.out.printf("[ISBN]: %s, [title]: %s, [author]: %s\n", tmp.getIsbn(), tmp.getTitle(), tmp.getAuthor());
        }
        System.out.println("*****   FIN LISTE   *****\n");
    }
    /*
     * costume search
     */
    public void searchBookByAuthor(String author){
            List<Book> books = book.getBookByAuthor(author);
            System.out.printf("*****   LISTE DES LIVRES POUR L'AUTEUR '%s'  *****\n", author);
            System.out.println("==>"+ books.toString());
            for(Book tmp: books)
                System.out.printf("[ISBN]: %s, [title]: %s, [author]: %s\n", tmp.getIsbn(), tmp.getTitle(), tmp.getAuthor());
            System.out.println("*****   FIN LISTE   *****\n");
    }

    public void searchBookByIsbn(String isbn){
        Book tmp = book.getBookByIsbn(isbn);
        System.out.printf("*****   LIVRE AVEC ISBN '%s'  *****\n", isbn);
        System.out.printf("[ISBN]: %s, [title]: %s, [author]: %s\n", tmp.getIsbn(), tmp.getTitle(), tmp.getAuthor());
        System.out.println("*****   FIN   *****\n");
    }

    /*
    * borrow a book
    */

    public void borrowBook(String isbn){
        BorrowerBook BorrowingInfo = pivote.getByIsbn(isbn);
        if(BorrowingInfo == null){
            Scanner scanner = new Scanner(System.in);
            System.out.println("1-ajouter un nouveau emprunteur\n2-eprunter pour un emprunteur existant:");
            int choice = scanner.nextInt();
            try{
                switch(choice){
                    case 1:
                        scanner = new Scanner(System.in);
                        System.out.print("nom complet de l'emprunteur:");
                        String name = scanner.nextLine();
                        Map<Boolean, Integer> resultMap = borrower.create(new Borrower(name));
                        if(resultMap.containsKey(true)){
                            System.out.println("*****   NOUVEAU EPRUNTEUR AJOUTER   *****\n");
                            System.out.print("date de retour(yyyy-mm-dd):");
                            String borrow_end = scanner.nextLine();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            if(pivote.create(isbn, resultMap.get(true), dateFormat.parse(borrow_end))){
                                book.updateStatus(isbn, BookStatus.BORROWED.name());
                                System.out.println("*****   LIVRE EMPRUNTER AVEC SUCCES   *****\n");
                            }else{
                                throw new Exception("BORROWING ERROR");
                            }
                        }else{
                            throw new Exception("BORROWER CREATION ERROR");
                        }
                        break;
                    case 2:
                        System.out.println("id d'emprunteur:");
                        int id = scanner.nextInt();
                        scanner = new Scanner(System.in);
                        System.out.print("date de retour(yyyy-mm-dd):");
                        String borrow_end = scanner.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        if(pivote.create(isbn, id, dateFormat.parse(borrow_end))){
                            book.updateStatus(isbn, BookStatus.BORROWED.name());
                            System.out.println("*****   LIVRE EMPRUNTER AVEC SUCCES   *****\n");
                        }else{
                            throw new Exception("BORROW ERROR");
                        }
                        break;
            }
                System.out.println("*****   FIN D'EMPRUNTE   *****\n");
            }catch(Exception e){
                System.out.printf("*****   %s   *****\n", e.getMessage());
            }
        }else{
            System.out.println("*****   LIVRE DEJA EMPRUNTER   *****");
        }
    }

    /*
    * RETURN BOOK AFTER BORROWING
    */

    public void returnBook(String isbn){
        BorrowerBook BorrowingInfo = pivote.getByIsbn(isbn);
        if(BorrowingInfo != null){
            if(book.ReturnBook(isbn)){
                System.out.println("*****   LIVRE EST DISPONSIBLE   *****");
                pivote.deleteBook(isbn);
            }else{
                System.out.println("*****   IMPOSSIBLE DE CHANGE STATUS DU LIVRE   *****");
            }
        }else{
            System.out.println("*****   LE LIVRE N'EST PAS EMPRUNTER   *****");
        }
    }

    public void getBorrowedBooks(){
        List<BorrowerBook> BorrowerBooks = pivote.getAll();
        if(BorrowerBooks.isEmpty())
            System.out.println("*****   AUCUN LIVRE N'EST EMPRUNTER   *****");
        for(BorrowerBook tmp: BorrowerBooks){
            Book tmp1 = book.getBookByIsbn(tmp.getIsbn());
            Borrower tmp2 = borrower.getById(tmp.getIdBorrower());
            System.out.println("*****   LIVRE/EMPRUNTEUR INFO   *****");
            System.out.printf("[ISBN]: %s, [title]: %s, [author]: %s\n", tmp1.getIsbn(), tmp1.getTitle(), tmp1.getAuthor());
            System.out.printf("[id]: %d, [name]: %s\n", tmp2.getMemeberId(), tmp2.getName());
            System.out.printf("date d'emprunte: %s\n", tmp.getBorrow_start().toString());
            System.out.printf("date de retour: %s\n", tmp.getBorrow_end().toString());
        }
        System.out.println("*****   FIN AFFICHAGE LIVRE EPRUNTER   *****");
    }

    public void removeBook(String isbn){
        BorrowerBook tmp = pivote.getByIsbn(isbn);
        if(tmp != null){
            String status = book.getBookByIsbn(isbn).getStatus();
            if(status.equals(BookStatus.BORROWED.name()) || status.equals(BookStatus.LOST.name())){
                System.out.println("*****   IMPOSSIBLE DE SUPPRIMER UN LIVRE PERDU OU EMPRUNTE   *****");
            }else{
                pivote.deleteBook(isbn);
                book.deleteByIsbn(isbn);
                System.out.println("*****   LIVRE SUPPRIMER   *****");
            }
        }else{
            book.deleteByIsbn(isbn);
            System.out.println("*****   LIVRE SUPPRIMER   *****");
        }
    }

    public void showStats(){
        List<Book> books = book.getAll();
        int nAvailable = 0;
        int nBorrowed = 0;
        int nLost = 0;
        if(books.isEmpty())
            System.out.println("*****   STATISTIQUES   *****");
            System.out.printf("[disponible]:%d, [emprunter]:%d, [perdu]:%d\n", nAvailable, nBorrowed, nLost);
            System.out.println("*****   FIN STATISTIQUES   *****");
        for(Book b: books){
            if(BookStatus.AVAILABLE.name().equals(b.getStatus()))
                nAvailable++;
            else if(BookStatus.BORROWED.name().equals(b.getStatus()))
                nBorrowed++;
            else
                nLost++;
            System.out.println("*****   STATISTIQUES   *****");
            System.out.printf("[disponible]:%d, [emprunter]:%d, [perdu]:%d\n", nAvailable, nBorrowed, nLost);
            System.out.println("*****   FIN STATISTIQUES   *****");
        }
    }
}
