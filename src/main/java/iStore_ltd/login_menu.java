package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
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
        Image icon = new ImageIcon("src/main/resources/image/icon.png").getImage();
        frame.setIconImage(icon);
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
                if(email.equals("")){
                    JOptionPane.showMessageDialog(frame, "Erreur : Le champ e-mail est vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                //get the password
                String password = String.valueOf(ptf_password.getPassword());
                if(password.equals("")){
                    JOptionPane.showMessageDialog(frame, "Erreur : Le champ mot de passe est vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                }


                if(!email.equals("") & !password.equals("")){

                    //on verifie si le compte existe

                    try {
                        PreparedStatement statement = connection.prepareStatement("SELECT password FROM users WHERE email_id = (SELECT id FROM whitelist WHERE email = ?);");
                        statement.setString(1, email);
                        ResultSet result = statement.executeQuery();

                        Boolean find_in_user = false;
                        String password_db = null;

                        while (result.next()) {
                            find_in_user = true;
                            password_db = result.getString("password");

                        }

                        if(find_in_user){

                            System.out.println("user existant");

                            if (BCrypt.checkpw(password, password_db)){
                                System.out.println("mdp bon");

                                //frame.setVisible(false);
                                frame.dispose();
                                new disp_stock(email);


                            }
                            else {

                                JOptionPane.showMessageDialog(frame, "Erreur : Mot de passe erroné", "Erreur", JOptionPane.ERROR_MESSAGE);

                                System.out.println("mdp mauvais");
                            }
                        }
                        else {

                            JOptionPane.showMessageDialog(frame, "Erreur : Cette adresse mail n'est associée à aucun compte, essayer de vous inscrire", "Erreur", JOptionPane.ERROR_MESSAGE);

                            System.out.println("user non existant");
                        }

                    } catch (SQLException exp) {

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

