package library.UI;

import library.DB_Access.Access;
import library.DB_Access.Login;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login_UI extends JPanel{
    Login log = new Login();
    User_Interface ui = new User_Interface();
    Register_UI reg = new Register_UI();
    Access access = new Access();

    //panel
    JPanel panel = new JPanel();
    //label
    JLabel name = new JLabel();
    JLabel pass = new JLabel();
    JLabel auth = new JLabel();
    //field
    JTextField user_name = new JTextField(20);
    JPasswordField password = new JPasswordField(20);
    //button
    JButton login = new JButton();
    JButton register = new JButton();

    public Login_UI() throws SQLException {
        System.out.println("Login panel");

        //panel
        this.setLayout(new BorderLayout());
        panel.setLayout(new GridBagLayout());
//        panel.setSize(new Dimension(300, 400));
        this.add(panel, BorderLayout.CENTER);

        //to initialize the constraints
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);

        //text label for user name
        name.setText("USERNAME");
        name.setFont(new Font("Helvetuca Neue", Font.PLAIN, 15));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1;
        panel.add(name, c);

        //text label for password
        pass.setText("PASSWORD");
        pass.setFont(new Font("Helvetuca Neue", Font.PLAIN, 15));
        c.gridx = 0; c.gridy = 2; c.gridwidth = 1;
        panel.add(pass, c);

        //text field for user name
        c.gridx = 0; c.gridy = 1; c.gridwidth = 1;
        panel.add(user_name, c);

        //text field for password
        c.gridx = 0; c.gridy = 3; c.gridwidth = 1;
        panel.add(password, c);

        //login and register button
        login.setText("Login");
        login.setBounds(395, 270, 100, 25);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 1;
        panel.add(login, c);

        //register button
        register.setText("Register");
        register.setBounds(395, 270, 100, 25);
        c.gridx = 0; c.gridy = 5; c.gridwidth = 1;
        panel.add(register, c);

        //text label for authentication purpose
        auth.setText("Invalid Username or Password");
        auth.setFont(new Font("Helevtica Neue", Font.ITALIC, 13));
        auth.setForeground(Color.RED);
        c.gridx = 0; c.gridy = 6; c.gridwidth = 1;
        panel.add(auth, c);
        auth.setVisible(false);                 //the text will appear once the the username or password is wrong

        panel.setVisible(true);
        this.setVisible(true);

        //login button action listener
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = user_name.getText();
                String password_ = String.valueOf(password.getPassword());

                if(log.auth(name, password_)){                  //if the user signed up already, it will move to the main panel
                    removeAll();
                    revalidate();
                    ui.table1.setModel(DbUtils.resultSetToTableModel(access.showdata()));       //to initialize the table in the main panel
                    add(ui);
                }
                else{
                    auth.setVisible(true);
                    user_name.setText("");
                    password.setText("");
                }
            }
        });

        //register button action listener
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panel);
                revalidate();
                add(reg);
            }
        });

    }
}
