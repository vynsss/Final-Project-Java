package library.DB_Access;

import library.Connection.Connection;

import java.sql.*;

public class Login {
    Connection connect = new Connection();

    private static String user;                 //to store the user id from the login and be used in other class

    //to check whether the there is an user with the name and password entered
    public boolean auth(String name, String password){
        try {
            PreparedStatement prepStat = connect.getPrepstat("SELECT user_id, user_name, pass FROM login " +
                    "WHERE user_name = ? AND pass = ?");
            prepStat.setString(1, name);
            prepStat.setString(2, password);
            ResultSet myRs = prepStat.executeQuery();
            if(myRs.next()){
                user = myRs.getString("user_id");               //to store the user id from the logged user
                System.out.println("User " + myRs.getString("user_name") + " Successfully Logged In!");
                return true;
            }
            else {
                System.out.println("Login Declined!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String get_userID(){
        return user;
    }

}
