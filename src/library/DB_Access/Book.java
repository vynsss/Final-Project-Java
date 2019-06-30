package library.DB_Access;

import library.Connection.Connection;

import java.sql.*;

public class Book {
    Connection connect = new Connection();
    Login login = new Login();

    //to add the data based on the book id generated and the user to the storage table
    public void add_Book(int book_id){
        try {
            PreparedStatement prepStat = connect.getPrepstat("INSERT INTO storage " +
                    "VALUES(?,?)");
            prepStat.setString(1, login.get_userID());
            prepStat.setInt(2, book_id);
            int i = prepStat.executeUpdate();
            if(i > 0){
                System.out.println("Data is added to storage");
            }
            else{
                System.out.println("Data failed to be added to storage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to remove the data from the storage table
    public void remove_book(int book_id){
        try {
            PreparedStatement prepStat = connect.getPrepstat("DELETE FROM storage " +
                    "WHERE book_id_store = ?");
            prepStat.setInt(1, book_id);
            int i = prepStat.executeUpdate();
            //if the book from the storage table is deleted, it will delete the data in the book table as well
            if(i > 0){
                PreparedStatement pdstmt = connect.getPrepstat("DELETE FROM books WHERE book_id = ?");
                pdstmt.setInt(1, book_id);
                pdstmt.executeUpdate();
                System.out.println("Data have been removed from storage");
            }
            else{
                System.out.println("Data failed to be removed from storage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to check whether the id is owned by the user or not
    public boolean check_book(int book_id){
        try {
            PreparedStatement prepStat = connect.getPrepstat("SELECT * FROM storage " +
                    "WHERE user_id = ? AND book_id_store = ?");
            prepStat.setString(1, login.get_userID());
            prepStat.setInt(2, book_id);

            ResultSet myRs = prepStat.executeQuery();
            if(myRs.next()){            //to check whether the book id is in the table or not
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
