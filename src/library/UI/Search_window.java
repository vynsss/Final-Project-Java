package library.UI;

import library.DB_Access.Access;
import library.Library;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Search_window extends JFrame {
    Access access = new Access();

    private JPanel panel = new JPanel();
    private JTable searchtbl;

    private JLabel IDlbl = new JLabel();
    private JLabel Titlelbl = new JLabel();
    private JLabel Authorlbl = new JLabel();

    public Search_window(String Title){
        this.setTitle("Search!");
        //panel
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(300, 150));
        panel.setLayout(new GridBagLayout());
        panel.setSize(new Dimension(300, 150));
        this.add(panel, BorderLayout.CENTER);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                Library.getWindow().getUi().setEnabled(true);
            }
        });

        this.setLocationRelativeTo(null);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);

        IDlbl.setText("ID                   ");
        IDlbl.setFont(new Font("Helvetuca Neue", Font.BOLD, 10));
        IDlbl.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1;
        panel.add(IDlbl, c);

        Titlelbl.setText("Title             ");
        Titlelbl.setFont(new Font("Helvetuca Neue", Font.BOLD, 10));
        Titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 1; c.gridy = 0; c.gridwidth = 1;
        panel.add(Titlelbl, c);

        Authorlbl.setText("Author           ");
        Authorlbl.setFont(new Font("Helvetuca Neue", Font.BOLD, 10));
        Authorlbl.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 2; c.gridy = 0; c.gridwidth = 1;
        panel.add(Authorlbl, c);

        String[][] temps = new String[access.searchdata(Title).size()][3];
        int counter = 0;
        for (HashMap<String, String> row : access.searchdata(Title))
        {
            String id = row.get("book_id");
            String title = row.get("Title");
            String author = row.get("Author");
            temps[counter][0] = id;
            temps[counter][1] = title;
            temps[counter][2] = author;
            counter++;
        }
        searchtbl = new JTable(temps, new String[] {"id", "Title", "Author"});

        c.gridx = 0; c.gridy = 1; c.gridwidth = 3;
        panel.add(searchtbl, c);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
