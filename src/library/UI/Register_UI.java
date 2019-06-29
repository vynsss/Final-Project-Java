package library.UI;

import library.DB_Access.Register;
import library.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register_UI extends JPanel{
    Register reg = new Register();

    //panel
    private JPanel panel = new JPanel();
    //label
    private JLabel name = new JLabel();
    private JLabel pass = new JLabel();
    private JLabel auth = new JLabel();
    //field
    private JTextField user_name = new JTextField(20);
    private JPasswordField password = new JPasswordField(20);
    //button
    private JButton register = new JButton();
    private JButton cancel = new JButton();

    public Register_UI() {
        //panel
        this.setLayout(new BorderLayout());
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
        cancel.setText("Cancel");
        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        panel.add(cancel, c);

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
                    Library.getWindow().setLogin(new Login_UI());
                    Library.getWindow().setContentPane(Library.getWindow().getLogin());
                    Library.getWindow().revalidate();
                }
                else{
                    auth.setVisible(true);
                    user_name.setText("");
                    password.setText("");
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Library.getWindow().setLogin(new Login_UI());
                Library.getWindow().setContentPane(Library.getWindow().getLogin());
                Library.getWindow().revalidate();
            }
        });

    }
}
