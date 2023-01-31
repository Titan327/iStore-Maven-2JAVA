package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class register_menu {
    private JLabel logo;
    private JTextField tf_email;
    private JPasswordField ptf_password;
    private JLabel password;
    private JLabel email;
    private JButton b_register;
    private JTextField tf_pseudo;
    private JLabel pseudo;
    private JPasswordField ptf_conf_password;
    private JLabel conf_password;
    private JPanel register_menu;
    private JButton b_back;

    public register_menu() {

        JFrame frame = new JFrame("iStore");
        frame.setContentPane(this.register_menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("get_instance");
                connection_DB db = connection_DB.getInstance();
                System.out.println("get_connexion");
                Connection connection = db.getConnection();

                //get the e-mail
                String email = tf_email.getText();

                //encrypt the password
                String crypt_password = BCrypt.hashpw(String.valueOf(ptf_password.getPassword()), BCrypt.gensalt());

                System.out.println(email);
                System.out.println(crypt_password);


                //requete préparer
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT email FROM users WHERE email = ?");
                    statement.setString(1, email);
                    ResultSet result = statement.executeQuery();

                    Boolean find_in_user = false;

                    while (result.next()) {

                        find_in_user = true;

                    }
                    if(find_in_user){

                        System.out.println("user existant");

                    }
                    else {

                        Boolean find_in_whitelist = false;

                        PreparedStatement statement2 = connection.prepareStatement("SELECT email FROM whitelist WHERE email = ?");
                        statement2.setString(1, email);
                        ResultSet result2 = statement2.executeQuery();

                        while (result2.next()) {

                            find_in_whitelist = true;

                        }
                        if(find_in_whitelist){
                            PreparedStatement statement3 = connection.prepareStatement("INSERT into users VALUES (?,?);");
                            statement3.setString(1, email);
                            statement3.setString(2, crypt_password);
                            statement3.executeUpdate();

                            System.out.println("user crée");

                            //frame.setVisible(false);
                            frame.dispose();

                        }
                        else{

                            System.out.println("user not whitelist");

                        }
                    }
                }
                catch (SQLException exp) {
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
