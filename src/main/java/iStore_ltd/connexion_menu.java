package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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

        //recupere la connexion existante a la base de donnée ou en recrée une si besoin


        b_login.addActionListener(new ActionListener() {

            connection_DB db = connection_DB.getInstance();
            Connection connection = db.getConnection();

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

                connection_DB db = connection_DB.getInstance();
                System.out.println("instance");
                Connection connection = db.getConnection();
                System.out.println("connexion");

                //get the e-mail
                String email = tf_email.getText();

                //encrypt the password
                String crypt_password = BCrypt.hashpw(String.valueOf(ptf_password.getPassword()), BCrypt.gensalt());

                System.out.println(email);
                System.out.println(crypt_password);



                /*
                //requete non preparer
                try {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery("SELECT * FROM users");
                    while (result.next()) {
                        String email_db = result.getString("email");
                        String password_db = result.getString("password");
                        System.out.println("3 email : " + email_db + ", password : " + password_db);
                    }
                } catch (SQLException exp) {
                    exp.printStackTrace();
                }
                 */

                //requete préparer
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT email FROM users WHERE email = ?");
                    statement.setString(1, email);
                    ResultSet result = statement.executeQuery();

                    Boolean find_in_user = false;

                    while (result.next()) {
                        find_in_user = true;
                        String email_db = result.getString("email");
                        System.out.println("3 email : " + email_db);
                    }
                    if(find_in_user){

                        System.out.println("user existant");

                    }
                    else {

                        Boolean find_in_whitelist = false;

                        PreparedStatement statement2 = connection.prepareStatement("SELECT email FROM whitelist WHERE email = ?");
                        statement2.setString(1, email);
                        ResultSet result2 = statement.executeQuery();


                        while (result2.next()) {
                            find_in_whitelist = true;
                            String email_db = result.getString("email");
                            System.out.println("3 email : " + email_db);
                        }
                        if(find_in_whitelist){
                            PreparedStatement statement3 = connection.prepareStatement("INSERT into users VALUES (?,?);");
                            statement3.setString(1, email);
                            statement3.setString(2, crypt_password);
                            statement3.executeUpdate();
                        }
                        else{
                            System.out.println("existe pas dans whitelist");
                        }



                    }
                } catch (SQLException exp) {
                    exp.printStackTrace();
                }

            }
        });
    }


}

