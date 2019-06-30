package library.UI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    private Login_UI login = new Login_UI();
    private Register_UI register_ui;
    private User_Interface ui;
    private String icon = "pic.jpg";        //for icon image of the application

    public Window() {
        this.setTitle("Library");
        this.setMinimumSize(new Dimension(800, 400));
        this.setMaximumSize(new Dimension(1000, 600));
        this.setLocationRelativeTo(null);

        this.setIconImage(icon);

        this.setContentPane(login);         //the initialized panel in the window
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //to set the icon image of the application
    private void setIconImage(String img) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(img)));
    }

    //setter and getter to change the panel in the frame
    public Login_UI getLogin() {
        return login;
    }

    public void setLogin(Login_UI login) {
        this.login = login;
    }

    public Register_UI getRegister_ui() {
        return register_ui;
    }

    public void setRegister_ui(Register_UI register_ui) {
        this.register_ui = register_ui;
    }

    public User_Interface getUi() {
        return ui;
    }

    public void setUi(User_Interface ui) {
        this.ui = ui;
    }
}
