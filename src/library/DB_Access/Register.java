package library.DB_Access;

import library.Connection.Connection;

import java.sql.*;
import java.util.UUID;

public class Register {
    Connection connect = new Connection();

    private String random = random_UUID();      //to store the randomized if

    //to store the name and password and the randomized user id to the user table
    public void register(String user_name, String password){
        try {
            PreparedStatement prepStat = connect.getPrepstat("INSERT INTO login " +
                    "VALUES(?,?,?)");
            prepStat.setString(1, random);
            prepStat.setString(2, user_name);
            prepStat.setString(3, password);
            int i = prepStat.executeUpdate();
            if(i > 0){
                System.out.println( "Successfully Registered!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //to randomized the user id
    private String random_UUID(){
        UUID uuid = UUID.randomUUID();
        String random = uuid.toString();
        while(true){
            if(check_userID(random)){                   //to check whether a user with this id is present or not
                random = uuid.toString();               //if yes it will randomize the id again
                continue;
            }
            else {
                break;
            }
        }
        return random;
    }

    //to check whether the randomized user id is already taken or not
    private boolean check_userID(String id){
        try {
            PreparedStatement prepStat = connect.getPrepstat("SELECT user_id FROM login " +
                    "WHERE user_id = ?");
            prepStat.setString(1, id);

            ResultSet myRs = prepStat.executeQuery();
            if(myRs.next()){
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

    //to check whether there is a user with the username or password in the database
    public boolean check_user(String user, String password){
        try {
            PreparedStatement prepStat = connect.getPrepstat("SELECT user_name, pass FROM login " +
                    "WHERE user_name = ? AND pass = ?");
            prepStat.setString(1, user);
            prepStat.setString(2, password);

            ResultSet myRs = prepStat.executeQuery();
            if(myRs.next()){
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
