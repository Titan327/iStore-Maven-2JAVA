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
                if(email.equals("")){
                    JOptionPane.showMessageDialog(frame, "Erreur : Le champ e-mail est vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                //get password
                String password = String.valueOf(ptf_password.getPassword());
                if(password.equals("")){
                    JOptionPane.showMessageDialog(frame, "Erreur : Le champ mot de passe est vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                //get password repeated
                String password_rep = String.valueOf(ptf_conf_password.getPassword());
                if(password_rep.equals("")){
                    JOptionPane.showMessageDialog(frame, "Erreur : Le champ répéter est vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                //get the pseudo
                String pseudo = tf_pseudo.getText();
                if(pseudo.equals("")){
                    JOptionPane.showMessageDialog(frame, "Erreur : Le champ pseudo est vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }


                if(!email.equals("") & !password.equals("") & !password_rep.equals("") & !pseudo.equals("")){

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

                            JOptionPane.showMessageDialog(frame, "Erreur : Cette adresse mail est déja associer a un compte", "Erreur", JOptionPane.ERROR_MESSAGE);

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

                            PreparedStatement statement3 = connection.prepareStatement("SELECT email FROM admin WHERE email = ?");
                            statement3.setString(1, email);
                            ResultSet result3 = statement3.executeQuery();

                            String role = "user";

                            while (result3.next()) {

                                role = "admin";

                            }


                            if(find_in_whitelist){

                                if(password.length() >= 10 & password.matches(".*[A-Z].*") & password.matches("^.*[^a-zA-Z0-9 ].*$") /*& !password.contains(" ")*/ ){

                                    if(password.equals(password_rep)){


                                        String crypt_password = BCrypt.hashpw(String.valueOf(ptf_password.getPassword()), BCrypt.gensalt());

                                        PreparedStatement statement4 = connection.prepareStatement("INSERT into users (email,pseudo,password,role) VALUES (?,?,?,?);");
                                        statement4.setString(1, email);
                                        statement4.setString(2, pseudo);
                                        statement4.setString(3, crypt_password);
                                        statement4.setString(4, role);
                                        statement4.executeUpdate();

                                        System.out.println("user crée");

                                        //frame.setVisible(false);

                                        frame.dispose();
                                        new login_menu();

                                    }
                                    else{

                                        JOptionPane.showMessageDialog(frame, "Erreur : Vous avez mal répeté votre mot de passe", "Erreur", JOptionPane.ERROR_MESSAGE);

                                    }

                                }
                                else{

                                    JOptionPane.showMessageDialog(frame, "Erreur : Votre mot de passe doit contenir au moin 10 caractere, dont au moin un caractere special et une majuscule", "Erreur", JOptionPane.ERROR_MESSAGE);


                                }
                            }
                            else{

                                JOptionPane.showMessageDialog(frame, "Erreur : Cette adresse mail n'est pas autorisé", "Erreur", JOptionPane.ERROR_MESSAGE);
                                System.out.println("user not whitelist");

                            }
                        }
                    }
                    catch (SQLException exp) {

                        exp.printStackTrace();

                    }
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
