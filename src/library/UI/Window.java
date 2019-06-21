package library.UI;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Window extends JFrame{
    public Login_UI login = new Login_UI();
    public User_Interface ui = new User_Interface();
    public Register_UI reg = new Register_UI();
    private String icon = "pic.jpg";        //for icon image of the application

    public Window() throws SQLException {
        this.setTitle("Library");
        this.setMinimumSize(new Dimension(800, 400));
        this.setMaximumSize(new Dimension(1000, 600));
        this.setLocationRelativeTo(null);

        this.setIconImage(icon);

        this.setContentPane(login);         //the initialized panel in the window
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void setIconImage(String s) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(s)));
    }
}
