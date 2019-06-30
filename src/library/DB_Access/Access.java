package library.DB_Access;

import library.Connection.Connection;

import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Access {
    Connection connect = new Connection();
    Book book = new Book();
    Login login = new Login();

    //to check whether the book id already taken or not
    public boolean checkid(int id) {
        try {
            PreparedStatement prepStat = connect.getPrepstat("SELECT book_id FROM books " +
                    "WHERE book_id = ?");
            prepStat.setInt(1, id);

            ResultSet rs = prepStat.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //to return the result of the array list and return it to be accessed in the JTable
    public ArrayList<HashMap<String, String>> showdata() {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            /*the inner join is to check whether inside the storage table
            * it check the book id of the user and get the data of the book in the books table*/
            PreparedStatement prepStat = connect.getPrepstat( "SELECT book_id, Title, Author FROM books " +
                    "INNER JOIN storage ON books.book_id = storage.book_id_store " +
                    "WHERE user_id = ?");
            prepStat.setString(1, login.get_userID());
            ResultSet myRs = prepStat.executeQuery();
            while (myRs.next())
            {
                //to store the row of data and store it in the arraylist
                HashMap<String, String> row = new HashMap<>();
                row.put("book_id", Integer.toString(myRs.getInt("book_id")));
                row.put("Title", myRs.getString("Title"));
                row.put("Author", myRs.getString("Author"));
                result.add(row);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //to return the data based on the searched title
    public ArrayList<HashMap<String, String>> searchdata(String title) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            //to show the data based on the user and the title searched
            PreparedStatement prepStat = connect.getPrepstat("SELECT book_id, Title, Author FROM books " +
                    "INNER JOIN storage ON books.book_id = storage.book_id_store " +
                    "WHERE user_id = ? AND Title = ?");
            prepStat.setString(1, login.get_userID());
            prepStat.setString(2, title);
            ResultSet myRs = prepStat.executeQuery();
            while (myRs.next())
            {
                HashMap<String, String> row = new HashMap<>();
                row.put("book_id", Integer.toString(myRs.getInt("book_id")));
                row.put("Title", myRs.getString("Title"));
                row.put("Author", myRs.getString("Author"));
                result.add(row);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //to add the data to the books table in the database
    public void add_data(String title, String author, String file_loc){
        try {
            /*to initialize the insert query into the database
             * the 4 ? represent the 4 columns in the books database*/
            PreparedStatement prepStat = connect.getPrepstat("insert into books " +
                    "value(?,?,?,?)");

            //to store the blob file
            File file = new File(file_loc);
            InputStream inputStream = new FileInputStream(file);

            int random = random_number();

            prepStat.setInt(1, random);
            prepStat.setString(2, title);
            prepStat.setString(3, author);
            prepStat.setBlob(4, inputStream);

            int i = prepStat.executeUpdate();
            if (i > 0) {
                book.add_Book(random);                  //to add the data into the storage table too
                JOptionPane.showMessageDialog(null, "Data is added");
            } else {
                JOptionPane.showMessageDialog(null, "Data is not saved");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "The data is too big, please try again!");
        }
    }

    //to retrieve and delete the data form books table
    public void retreive_data(int id, String file_loc) {
        if(book.check_book(id)) {                   //it will first check whether the user have that book
            try {
                /*to set the query to store the Loc if the title is equal to ?
                executing the query and storing the data of the Loc*/
                PreparedStatement prepStat = connect.getPrepstat("SELECT Loc FROM books WHERE book_id=?");
                prepStat.setInt(1, id);

                ResultSet result = prepStat.executeQuery();

                // write binary stream into file
                File file = new File(file_loc);
                FileOutputStream output = new FileOutputStream(file);

                if (result.next()) {
                    InputStream input = result.getBinaryStream("Loc");          //to read the binary of the file in stream
                    byte[] buffer = new byte[1024];
                    while (input.read(buffer) > 0) {
                        output.write(buffer);
                    }

                    //to set the query to delete a row of value
                    PreparedStatement pdsmt = connect.getPrepstat("delete from books " +
                            "WHERE book_id = ?");
                    pdsmt.setInt(1, id);
                    int i = pdsmt.executeUpdate();
                    if(i > 0){
                        book.remove_book(id);                   //to remove the book data from the tables
                        JOptionPane.showMessageDialog(null, "Book retrieved successfully. Writing to file " + file.getAbsolutePath());
                    } else {
                        JOptionPane.showMessageDialog(null, "Data is unsuccessfully removed");
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //function for the file chooser
    public String browse_location(){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String file = f.getAbsolutePath();

        return file;
    }

    //to generate random value for the book id
    private int random_number(){
        Random random = new Random();
        int id = random.nextInt(Integer.MAX_VALUE);
        /*to check if the id is used or not
         * if it is used, it will loop until it get the id that is not used*/
        while(true){
            if(checkid(id)){
                id = random.nextInt(Integer.MAX_VALUE);
                continue;
            }
            else break;
        }
        return id;
    }



}
