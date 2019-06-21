package library.UI;

import library.DB_Access.Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register_UI extends JPanel{
    Register reg = new Register();
    User_Interface ui = new User_Interface();

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
    JButton register = new JButton();

    public Register_UI() throws SQLException {
        System.out.println("Register panel");

        //panel
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
        panel.setLayout(new GridBagLayout());
        panel.setSize(new Dimension(300, 400));
        this.add(panel, BorderLayout.CENTER);

        //to initialize the constraints
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);

        //text label for user name
        name.setText("USERNAME:");
        name.setFont(new Font("Helvetuca Neue", Font.PLAIN, 15));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1;
        panel.add(name, c);

        //text label for password
        pass.setText("PASSWORD:");
        pass.setFont(new Font("Helvetuca Neue", Font.PLAIN, 15));
        c.gridx = 0; c.gridy = 1; c.gridwidth = 1;
        panel.add(pass, c);

        //text field for user name
        c.gridx = 1; c.gridy = 0; c.gridwidth = 1;
        panel.add(user_name, c);

        //text field for password
        c.gridx = 1; c.gridy = 1; c.gridwidth = 1;
        panel.add(password, c);

        //register button
        register.setText("Register");
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        panel.add(register, c);

        //cancel button
//        cancel.setText("Cancel");
//        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
//        panel.add(cancel, c);

        //text label for authentication purpose
        auth.setText("The user with this ID is already listed!");
        auth.setFont(new Font("Helevtica Neue", Font.ITALIC, 13));
        auth.setForeground(Color.RED);
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        panel.add(auth, c);
        auth.setVisible(false);

        panel.setVisible(true);
        this.setVisible(true);

        //register button action listener
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = user_name.getText();
                String password_ = String.valueOf(password.getPassword());

                //execute if there is no person with the user name or password entered
                if(!reg.check_user(name, password_)){
                    reg.register(name, password_);
                    remove(panel);
                    revalidate();
                    add(ui);
                }
                else{
                    auth.setVisible(true);
                    user_name.setText("");
                    password.setText("");
                }
            }
        });

    }
}
