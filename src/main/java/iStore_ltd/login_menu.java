package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login_menu {
    private JLabel logo;
    private JLabel lbl_email;
    private JTextField tf_email;
    private JLabel lbl_password;
    private JPasswordField ptf_password;
    private JButton b_login;
    private JButton b_register;
    private JPanel conn_menu;
    private JButton b_back;


    public login_menu() {

        JFrame frame = new JFrame("iStore");
        frame.setContentPane(this.conn_menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //recupere la connexion existante a la base de donnée ou en recrée une si besoin


        b_login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("get_instance");
                connection_DB db = connection_DB.getInstance();
                System.out.println("get_connexion");
                Connection connection = db.getConnection();

                //get the e-mail
                String email = tf_email.getText();

                //get the password
                String password = String.valueOf(ptf_password.getPassword());

                System.out.println(email);
                System.out.println(password);

                //on verifie si le compte existe

                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE email = ?");
                    statement.setString(1, email);
                    ResultSet result = statement.executeQuery();

                    Boolean find_in_user = false;
                    String password_db = null;

                    while (result.next()) {
                        find_in_user = true;
                        password_db = result.getString("password");
                        System.out.println("mdp_db : " + password_db);

                    }

                    if(find_in_user){
                        System.out.println("user existant");
                        if (BCrypt.checkpw(password, password_db)){
                            System.out.println("mdp bon");

                            //frame.setVisible(false);
                            frame.dispose();


                        }
                        else {
                            System.out.println("mdp mauvais");
                        }
                    }
                    else {
                        System.out.println("user non existant");
                    }

                } catch (SQLException exp) {
                    exp.printStackTrace();
                }


            }
        });

        b_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                new choix_log_or_reg();

            }
        });
    }


}

