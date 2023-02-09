package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private JTextField tf_search;
    private JLabel lb_search_user;
    private JButton b_search;
    private JLabel lb_email_user;
    private JLabel lb_email_user_result;
    private JLabel lb_pseudo_user_result;
    private JLabel lb_role_user;
    private JLabel lb_role_user_result;
    private JButton b_change;
    private JButton b_back;
    private JButton b_del;
    private JComboBox cb_store;
    private JButton b_search_user_store;
    private JTable tb_user_store;

    private String new_email;
    private String role;

    public user_menu(String email_id) {

    tf_pseudo.setText("test");

    tb_user_store.setModel(new DefaultTableModel(
            null,
            new String[]{"pseudo","e-mail","role"}
    ));

    connection_DB db = connection_DB.getInstance();
    Connection connection = db.getConnection();



    try {

        PreparedStatement statement1 = connection.prepareStatement("SELECT users.pseudo, users.role, whitelist.email FROM whitelist JOIN users ON whitelist.id = users.email_id WHERE whitelist.id = ?;");
        statement1.setString(1, email_id);
        ResultSet result1 = statement1.executeQuery();

        while (result1.next()) {

            String pseudo = result1.getString("pseudo");
            role = result1.getString("role");
            new_email = result1.getString("email");

            tf_email.setText(new_email);
            tf_pseudo.setText(pseudo);
            lb_write_role.setText(role);

        }

        Statement statement2 = connection.createStatement();
        ResultSet result2 = statement2.executeQuery("SELECT name FROM store;");

        while (result2.next()) {

            String store_name = result2.getString("name");
            cb_store.addItem(store_name);

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

            connection_DB db = connection_DB.getInstance();
            Connection connection = db.getConnection();

            String new_pseudo = tf_pseudo.getText();
            new_email = tf_email.getText();

            String password = String.valueOf(pft_pwd.getPassword());
            String password_rep = String.valueOf(pft_pwd_rep.getPassword());

            Boolean all_ok = true;

            try {

                //finir les upsates

                if(!new_email.equals("")){

                    PreparedStatement statement4 = connection.prepareStatement("UPDATE whitelist SET email = ? WHERE id = ?;");
                    statement4.setString(1, new_email);
                    statement4.setString(2, email_id);
                    statement4.executeUpdate();

                }
                else{

                    JOptionPane.showMessageDialog(frame, "Erreur : Erreur votre e-mail ne peut pas être vide", "Erreur", JOptionPane.ERROR_MESSAGE);

                    all_ok = false;

                }

                if(password.equals("")){

                    if(!new_pseudo.equals("")){

                        PreparedStatement statement5 = connection.prepareStatement("UPDATE users SET pseudo = ? WHERE email_id = ?;");
                        statement5.setString(1, new_pseudo);
                        statement5.setString(2, email_id);
                        statement5.executeUpdate();

                    }
                    else{

                        JOptionPane.showMessageDialog(frame, "Erreur : Erreur votre pseudo ne peut pas être vide", "Erreur", JOptionPane.ERROR_MESSAGE);

                        all_ok = false;

                    }
                }
                else{

                    if(password.length() >= 10 & password.matches(".*[A-Z].*") & password.matches("^.*[^a-zA-Z0-9 ].*$") /*& !password.contains(" ")*/ ) {

                        if (password.equals(password_rep)) {

                            String crypt_password = BCrypt.hashpw(password, BCrypt.gensalt());

                            PreparedStatement statement6 = connection.prepareStatement("UPDATE users SET pseudo = ?,password = ? WHERE email_id = ?;");
                            statement6.setString(1, new_pseudo);
                            statement6.setString(2, crypt_password);
                            statement6.setString(3, email_id);
                            statement6.executeUpdate();

                        }
                        else {

                            JOptionPane.showMessageDialog(frame, "Erreur : Erreur le mot de passe a mal été répeté", "Erreur", JOptionPane.ERROR_MESSAGE);

                            all_ok = false;

                        }
                    }
                    else{

                        JOptionPane.showMessageDialog(frame, "Erreur : Votre mot de passe doit contenir au moin 10 caractères, dont au moin un caractère special et une majuscule", "Erreur", JOptionPane.ERROR_MESSAGE);

                        all_ok = false;

                    }
                }

                if(all_ok){

                    JOptionPane.showMessageDialog(frame, "Changement effectué !", "Changement", JOptionPane.INFORMATION_MESSAGE);

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
                new disp_stock(new_email);

            }
        });
        b_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String search = tf_search.getText();

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                try {

                    boolean find = false;

                    PreparedStatement statement1 = connection.prepareStatement("SELECT users.role, whitelist.email FROM whitelist JOIN users ON whitelist.id = users.email_id WHERE users.pseudo = ?;");
                    statement1.setString(1, search);
                    ResultSet result1 = statement1.executeQuery();

                    while (result1.next()) {

                        String role = result1.getString("role");
                        String email = result1.getString("email");

                        lb_email_user_result.setText(email);
                        lb_role_user_result.setText(role);

                        find = true;

                    }
                    if(!find){

                        JOptionPane.showMessageDialog(frame, "Erreur : Utilisateur introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }

                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }

            }
        });
        b_del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                try {

                    int result = JOptionPane.showConfirmDialog(null, "Etes vous sûr de vouloir supprimer votre compte ?", "Suppression du compte", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {

                        PreparedStatement statement1 = connection.prepareStatement("DELETE FROM users WHERE email_id = ?;");
                        statement1.setString(1, email_id);
                        statement1.executeUpdate();

                        PreparedStatement statement3 = connection.prepareStatement("DELETE FROM user_store WHERE email_id = ?;");
                        statement3.setString(1, email_id);
                        statement3.executeUpdate();

                        if(role.equals("admin")){

                            PreparedStatement statement4 = connection.prepareStatement("DELETE FROM admin WHERE email_id = ?;");
                            statement4.setString(1, email_id);
                            statement4.executeUpdate();

                        }

                        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM whitelist WHERE id = ?;");
                        statement2.setString(1, email_id);
                        statement2.executeUpdate();

                        frame.dispose();
                        new choix_log_or_reg();

                    }

                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }
        });
        b_search_user_store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                String store = String.valueOf(cb_store.getSelectedItem());

                DefaultTableModel model2 = (DefaultTableModel) tb_user_store.getModel();
                model2.setRowCount(0);

                try {

                    PreparedStatement statement1 = connection.prepareStatement("SELECT users.pseudo, whitelist.email, users.role FROM users JOIN whitelist ON users.email_id = whitelist.id JOIN user_store ON users.email_id = user_store.email_id JOIN store ON user_store.store_id = store.id WHERE store.name = ?;");
                    statement1.setString(1, store);
                    ResultSet result1 = statement1.executeQuery();

                    while (result1.next()) {

                        String pseudo = result1.getString("pseudo");
                        String email = result1.getString("email");
                        String role = result1.getString("role");

                        DefaultTableModel model = (DefaultTableModel) tb_user_store.getModel();
                        Object[] data = {pseudo, email,role};
                        model.addRow(data);

                    }
                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }

            }
        });
    }
}
