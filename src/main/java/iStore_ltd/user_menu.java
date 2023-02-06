package iStore_ltd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class user_menu {
    private JPanel user_menu;
    private JLabel lb_profil;
    private JLabel lb_search;
    private JTextField tf_pseudo;
    private JLabel lb_pseudo;
    private JTextField tf_email;
    private JLabel lb_email;
    private JPasswordField pft_pwd;
    private JLabel plb_pwd;
    private JPasswordField pft_pwd_rep;
    private JLabel plb_pwd_rep;
    private JLabel lb_role;
    private JLabel lb_write_role;
    private JTextField textField3;
    private JLabel lb_search_user;
    private JButton b_search;
    private JLabel lb_email_user;
    private JLabel lb_email_user_result;
    private JLabel lb_pseudo_user;
    private JLabel lb_pseudo_user_result;
    private JLabel lb_role_user;
    private JLabel lb_role_user_result;
    private JButton b_change;
public user_menu(String email) {

    tf_pseudo.setText("test");

    connection_DB db = connection_DB.getInstance();
    Connection connection = db.getConnection();

    try {

        PreparedStatement statement1 = connection.prepareStatement("SELECT users.pseudo, users.role FROM whitelist JOIN users ON whitelist.id = users.email_id WHERE whitelist.email = ?;");
        statement1.setString(1, email);
        ResultSet result1 = statement1.executeQuery();

        while (result1.next()) {

            String pseudo = result1.getString("pseudo");
            String role = result1.getString("role");

            tf_email.setText(email);
            tf_pseudo.setText(pseudo);
            lb_write_role.setText(role);

        }
    }
    catch (SQLException exp) {
        exp.printStackTrace();
    }

    JFrame frame = new JFrame("iStore");
    Image icon = new ImageIcon("src/main/resources/image/icon.png").getImage();
    frame.setIconImage(icon);
    frame.setContentPane(this.user_menu);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    b_change.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String new_pseudo = tf_pseudo.getText();
            String new_email = tf_email.getText();

            String password = String.valueOf(pft_pwd.getPassword());
            String password_rep = String.valueOf(pft_pwd_rep.getPassword());

            try {

                if(password.equals(password_rep)){

                    PreparedStatement statement4 = connection.prepareStatement("UPDATE whitelist SET email = ? WHERE email = ?;");
                    statement4.setString(1, new_email);
                    statement4.setString(2, email);
                    statement4.executeUpdate();

                }
                else{

                    JOptionPane.showMessageDialog(frame, "Erreur : Erreur le mot de passe a mal était répeté", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;

                }

                PreparedStatement statement4 = connection.prepareStatement("UPDATE whitelist SET email = ? WHERE email = ?;");
                statement4.setString(1, new_email);
                statement4.setString(2, email);
                statement4.executeUpdate();

            }
            catch (SQLException exp) {
                exp.printStackTrace();
            }

        }
    });
}
}
