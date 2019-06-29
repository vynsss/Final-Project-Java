package library.UI;

import library.DB_Access.Access;
import library.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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
    private JButton logoutButton;

    Access access;

    public User_Interface(){
        this.setLayout(new BorderLayout());
        panel1.setLayout(new GridBagLayout());
        this.add(panel1, BorderLayout.CENTER);

        //button to add the data in the add section
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //to get the text of the field and set it into a string
                String Title = title.getText();
                String Author = author.getText();
                String Loc = loc.getText();

                access.add_data(Title, Author, Loc);

                //to set the text field to null after every add
                title.setText("");
                author.setText("");
                loc.setText("");

                Library.getWindow().setUi(new User_Interface());
                Library.getWindow().setContentPane(Library.getWindow().getUi());
                Library.getWindow().revalidate();
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

                Library.getWindow().setUi(new User_Interface());
                Library.getWindow().setContentPane(Library.getWindow().getUi());
                Library.getWindow().revalidate();

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
                setEnabled(false);
                String Title = searchT.getText();
                Search_window search_window = new Search_window(Title);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Library.getWindow().setContentPane(Library.getWindow().getLogin());
                Library.getWindow().revalidate();
            }
        });
    }

    private void createUIComponents() {
        this.access = new Access();
        // TODO: place custom component creation code here

        String[][] temps = new String[access.showdata().size()][3];
        int counter = 0;
        for (HashMap<String, String> row : access.showdata())
        {
            String id = row.get("book_id");
            String title = row.get("Title");
            String author = row.get("Author");
            temps[counter][0] = id;
            temps[counter][1] = title;
            temps[counter][2] = author;
            counter++;
        }
        table1 = new JTable(temps, new String[] {"id", "Title", "Author"});
    }
}
