package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

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
public user_menu(String email_id) {

    tf_pseudo.setText("test");

    connection_DB db = connection_DB.getInstance();
    Connection connection = db.getConnection();

    try {

        PreparedStatement statement1 = connection.prepareStatement("SELECT users.pseudo, users.role, whitelist.email FROM whitelist JOIN users ON whitelist.id = users.email_id WHERE whitelist.id = ?;");
        statement1.setString(1, email_id);
        ResultSet result1 = statement1.executeQuery();

        while (result1.next()) {

            String pseudo = result1.getString("pseudo");
            String role = result1.getString("role");
            String email = result1.getString("email");

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

                //finir les upsates
                if(password.length() >= 10 & password.matches(".*[A-Z].*") & password.matches("^.*[^a-zA-Z0-9 ].*$") /*& !password.contains(" ")*/ ) {

                    if (password.equals(password_rep)) {

                        PreparedStatement statement4 = connection.prepareStatement("UPDATE whitelist SET email = ? WHERE id = ?;");
                        statement4.setString(1, new_email);
                        statement4.setString(2, email_id);
                        statement4.executeUpdate();

                        String crypt_password = BCrypt.hashpw(password, BCrypt.gensalt());

                        PreparedStatement statement5 = connection.prepareStatement("UPDATE user SET pseudo = ?,password = ? WHERE id = ?;");
                        statement5.setString(1, new_pseudo);
                        statement5.setString(2, crypt_password);
                        statement5.setString(3, email_id);
                        statement5.executeUpdate();

                    }
                    else {

                        JOptionPane.showMessageDialog(frame, "Erreur : Erreur le mot de passe a mal était répeté", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }
                }
                else{

                    JOptionPane.showMessageDialog(frame, "Erreur : Votre mot de passe doit contenir au moin 10 caractere, dont au moin un caractere special et une majuscule", "Erreur", JOptionPane.ERROR_MESSAGE);

                }
            }
            catch (SQLException exp) {
                exp.printStackTrace();
            }

        }
    });
}
}
