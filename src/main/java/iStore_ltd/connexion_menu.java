package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connexion_menu {
    private JLabel logo;
    private JLabel lbl_email;
    private JTextField tf_email;
    private JLabel lbl_password;
    private JPasswordField ptf_password;
    private JButton b_login;
    private JButton b_register;
    private JPanel conn_menu;


    public connexion_menu() {

        JFrame frame = new JFrame("iStore");
        frame.setContentPane(this.conn_menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Connection obj = new connexion();


        b_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get the e-mail
                String email = tf_email.getText();

                //test the password
                String crypt_password = BCrypt.hashpw(String.valueOf(ptf_password.getPassword()), BCrypt.gensalt());

                System.out.println(email);
                System.out.println(crypt_password);

                //test testob = new test(email,password);

            }
        });
        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //get the e-mail
                String email = tf_email.getText();

                //encrypt the password
                String crypt_password = BCrypt.hashpw(String.valueOf(ptf_password.getPassword()), BCrypt.gensalt());

                System.out.println(email);
                System.out.println(crypt_password);

                try (Connection connection = connection_DB.getInstance().getConnection();
                     Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
                    System.out.println("ici1");
                    while (resultSet.next()) {
                        String email_db = resultSet.getString("email");
                        String password_db = resultSet.getString("password");
                        System.out.println("email : " + email_db + ", password : " + password_db);
                    }
                } catch (Exception exept) {
                    System.out.println("ici");
                    exept.printStackTrace();
                }

            }
        });
    }


}

