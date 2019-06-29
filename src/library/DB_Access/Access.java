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

    public ArrayList<HashMap<String, String>> showdata() {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            //to show the data based on the user
            PreparedStatement prepStat = connect.getPrepstat( "SELECT book_id, Title, Author FROM books " +
                    "INNER JOIN storage ON books.book_id = storage.book_id_store " +
                    "WHERE user_id = ?");
//            Array array = connect.getMyConn().createArrayOf("INT", book_data.Data().toArray());
            prepStat.setString(1, login.get_userID());
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

    public ArrayList<HashMap<String, String>> searchdata(String title) {
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        try {
            //to show the data based on the user
            PreparedStatement prepStat = connect.getPrepstat("SELECT book_id, Title, Author FROM books " +
                    "INNER JOIN storage ON books.book_id = storage.book_id_store " +
                    "WHERE user_id = ? AND Title = ?");
//            Array array = connect.getMyConn().createArrayOf("INT", book_data.Data().toArray());
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

    //to add the data to the mySQL table
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
                book.add_Book(random);
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

    //to retreive and delete the data form mySQL table
    public void retreive_data(int id, String file_loc) {
        if(book.check_book(id)) {
            try {
            /*to set the query to store the Loc if the title is equal to ?
            executing the query and storing the data of the Loc*/
                PreparedStatement prepStat = connect.getPrepstat("SELECT Loc FROM books WHERE book_id=?");
                //to set the RTitle to the parameter of the query
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
                    pdsmt.executeUpdate();

                    book.remove_book(id);
                    JOptionPane.showMessageDialog(null, "Book retrieved successfully. Writing to file " + file.getAbsolutePath());
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
