package library.UI;

import library.DB_Access.Access;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User_Interface extends JPanel{
    private JPanel panel1;
    private JButton add_button;
    public JTable table1;
    private JTextField author;
    private JTextField loc;
    private JTextField title;
    private JTextField rID;
    private JButton retrieveButton;
    private JTextField File;
    private JButton add_browse;
    private JButton cancelButton;
    private JButton browseButton;
    private JTextField searchT;
    private JButton searchButton;
    private JButton cancelButton1;

    Access access = new Access();

    public User_Interface() throws SQLException {
        System.out.println("User Interface panel");

        this.setLayout(new BorderLayout());
        panel1.setLayout(new GridBagLayout());
        this.add(panel1, BorderLayout.CENTER);

        table1.setModel(DbUtils.resultSetToTableModel(access.showdata()));

        //button to add the data in the add section
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //to get the text of the field and set it into a string
                String Title = title.getText();
                String Author = author.getText();
                String Loc = loc.getText();

                if(Title == "" || Author == "" ||  Loc == ""){
                    JOptionPane.showMessageDialog(null, "Please fill all of the required data!");
                }
                else{
                    access.add_data(Title, Author, Loc);
                }

                //to update the table each time the user add a value.
                table1.setModel(DbUtils.resultSetToTableModel(access.showdata()));
                //to set the text field to null after every add
                title.setText("");
                author.setText("");
                loc.setText("");

            }
        });

        //button for browsing file location in the add section
        add_browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loc.setText(access.browse_location());
            }
        });

        //button to cancel all the inputted data in the add section
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title.setText("");
                author.setText("");
                loc.setText("");
            }
        });

        //button to retrieve the data and delete them in the retrieve section
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int RID = Integer.parseInt(rID.getText());
                String fileName = File.getText();

                //to check whether the title book to be retrieved already exist or not
                if(access.checkid(RID)) {
                    access.retreive_data(RID, fileName);
                }
                else{
                    JOptionPane.showMessageDialog(null, "The book you are trying to retrieve does not exist, please try again!");
                }

                table1.setModel(DbUtils.resultSetToTableModel(access.showdata()));
                rID.setText("");
                File.setText("");
            }
        });

        //button to browse file location to put the file into in the retrieve section
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File.setText(access.browse_location());
            }
        });

        //button to search the data and show it in the table in the view section
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Title = searchT.getText();
                table1.setModel(DbUtils.resultSetToTableModel(access.searchdata(Title)));
            }
        });

        //to reset the content of the search strings and return the data in the table to the original dara in the view section
        cancelButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table1.setModel(DbUtils.resultSetToTableModel(access.showdata()));
                searchT.setText("");
            }
        });

    }


}
