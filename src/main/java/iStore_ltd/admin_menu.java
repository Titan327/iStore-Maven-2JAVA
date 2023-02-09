package iStore_ltd;

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class admin_menu {
    private JLabel lb_logo;
    private JLabel lb_search_user;
    private JLabel lb_whitelist;
    private JLabel lb_email_search_admin;
    private JTextField tf_email_search_admin;
    private JLabel lb_email_search;
    private JTextField tf_email;
    private JLabel lb_pseudo_search;
    private JTextField tf_pseudo;
    private JLabel lb_role_search;
    private JComboBox cb_role;
    private JButton b_val_modif;
    private JTextField tf_email_whitelist;
    private JLabel lb_email_whitelist;
    private JButton b_add_whitelist;
    private JLabel lb_store_list;
    private JLabel lb_del_store;
    private JButton b_del_store;
    private JLabel lb_add_store;
    private JTextField tf_store_name;
    private JButton b_add_store;
    private JTable t_product;
    private JComboBox cb_nom_store_del;
    private JComboBox cb_nom_product_del;
    private JButton b_del_product;
    private JTextField tf_nom_product_add;
    private JTextField tf_price_product_add;
    private JButton b_add_product;
    private JButton b_search;
    private JPanel admin_menu;
    private JButton b_back;
    private JComboBox cb_role_new;
    private JTable t_shop;
    private JComboBox cb_store_user;
    private JPasswordField ptf_pswd_rep;
    private JPasswordField ptf_pswd;
    private JComboBox cb_store_new_user;
    private JButton b_del_user;
    private JLabel lb_pswd;
    private JLabel lb_pswd_rep;
    private JLabel lb_store_user;
    private String user_search_id = null;
    private String role = null;

    public admin_menu(String email_admin) {

        tf_email.setVisible(false);
        tf_pseudo.setVisible(false);
        cb_role.setVisible(false);
        cb_store_user.setVisible(false);
        ptf_pswd_rep.setVisible(false);
        ptf_pswd.setVisible(false);

        lb_email_search.setVisible(false);
        lb_pseudo_search.setVisible(false);
        lb_pswd.setVisible(false);
        lb_pswd_rep.setVisible(false);
        lb_role_search.setVisible(false);
        lb_store_user.setVisible(false);

        b_val_modif.setVisible(false);
        b_del_user.setVisible(false);



        t_shop.setModel(new DefaultTableModel(
                null,
                new String[]{"nom du magasin"}
        ));

        t_product.setModel(new DefaultTableModel(
                null,
                new String[]{"nom","prix"}
        ));

        connection_DB db = connection_DB.getInstance();
        Connection connection = db.getConnection();

        try {

            Statement statement1 = connection.createStatement();
            ResultSet result1 = statement1.executeQuery("SELECT name FROM store;");

            while (result1.next()) {

                String name_shop = result1.getString("name");

                DefaultTableModel model = (DefaultTableModel) t_shop.getModel();
                Object[] data = {name_shop};
                model.addRow(data);

                cb_nom_store_del.addItem(name_shop);
                cb_store_user.addItem(name_shop);
                cb_store_new_user.addItem(name_shop);

            }

            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT name,price FROM product;");

            while (result2.next()) {

                String name_product = result2.getString("name");
                String price_product = result2.getString("price");

                DefaultTableModel model = (DefaultTableModel) t_product.getModel();
                Object[] data = {name_product,price_product};
                model.addRow(data);

                cb_nom_product_del.addItem(name_product);

            }

        }
        catch (SQLException exp) {
            exp.printStackTrace();
        }



        JFrame frame = new JFrame("iStore");
        Image icon = new ImageIcon("src/main/resources/image/icon.png").getImage();
        frame.setIconImage(icon);
        frame.setContentPane(this.admin_menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        b_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                try {

                    boolean find = false;

                    String search_email = tf_email_search_admin.getText();

                    PreparedStatement statement1 = connection.prepareStatement("SELECT whitelist.email, users.pseudo, users.role, store.name, whitelist.id FROM whitelist JOIN users ON whitelist.id = users.email_id LEFT JOIN user_store ON whitelist.id = user_store.email_id LEFT JOIN store ON user_store.store_id = store.id WHERE whitelist.email = ?;");
                    statement1.setString(1, search_email);
                    ResultSet result1 = statement1.executeQuery();

                    while (result1.next()) {

                        find = true;

                        String email = result1.getString("email");
                        String pseudo = result1.getString("pseudo");
                        role = result1.getString("role");
                        String store_name = result1.getString("name");
                        user_search_id = result1.getString("id");

                        tf_email.setText(email);
                        tf_pseudo.setText(pseudo);

                        DefaultComboBoxModel model = (DefaultComboBoxModel) cb_role.getModel();
                        cb_role.setSelectedIndex(model.getIndexOf(role));

                        DefaultComboBoxModel model2 = (DefaultComboBoxModel) cb_store_user.getModel();
                        cb_store_user.setSelectedIndex(model2.getIndexOf(store_name));


                        tf_email.setVisible(true);
                        tf_pseudo.setVisible(true);
                        ptf_pswd_rep.setVisible(true);
                        ptf_pswd.setVisible(true);
                        cb_role.setVisible(true);
                        cb_store_user.setVisible(true);


                        lb_email_search.setVisible(true);
                        lb_pseudo_search.setVisible(true);
                        lb_pswd.setVisible(true);
                        lb_pswd_rep.setVisible(true);
                        lb_role_search.setVisible(true);
                        lb_store_user.setVisible(true);

                        b_val_modif.setVisible(true);
                        b_del_user.setVisible(true);


                    }
                    if(!find){

                        JOptionPane.showMessageDialog(frame, "Erreur : Utilisateur introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }
                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }

                frame.pack();

            }
        });
        b_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                new disp_stock(email_admin);

            }
        });
        b_add_whitelist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                boolean find = false;

                try {

                    String email_whitelist = tf_email_whitelist.getText();
                    String role = String.valueOf(cb_role_new.getSelectedItem()).toLowerCase();
                    String store = String.valueOf(cb_store_new_user.getSelectedItem());

                    String new_user_id = null;


                    PreparedStatement statement1 = connection.prepareStatement("SELECT email FROM whitelist WHERE email = ?");
                    statement1.setString(1, email_whitelist);
                    ResultSet result1 = statement1.executeQuery();

                    while (result1.next()) {

                        find = true;

                    }

                    if(find){

                        JOptionPane.showMessageDialog(frame, "Erreur : Email déjà dans la whiteliste", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }
                    else{

                        PreparedStatement statement2 = connection.prepareStatement("INSERT into whitelist (email)  VALUES (?);");
                        statement2.setString(1, email_whitelist);
                        statement2.executeUpdate();

                        PreparedStatement statement3 = connection.prepareStatement("INSERT INTO user_store (email_id, store_id) SELECT whitelist.id, store.id FROM whitelist JOIN store ON store.name = ? WHERE whitelist.email = ?;");
                        statement3.setString(1, store);
                        statement3.setString(2, email_whitelist);
                        statement3.executeUpdate();

                        if(role.equals("admin")){

                            PreparedStatement statement4 = connection.prepareStatement("INSERT INTO admin (email_id) SELECT id FROM whitelist WHERE email = ?;");
                            statement4.setString(1, email_whitelist);
                            statement4.executeUpdate();

                        }

                        tf_email_whitelist.setText("");

                        JOptionPane.showMessageDialog(frame, "Email ajouté à la whitelist !", "Ajouté", JOptionPane.INFORMATION_MESSAGE);

                    }
                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }

            }
        });
        b_del_store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                String store = String.valueOf(cb_nom_store_del.getSelectedItem());
                String id = null;

                try {

                    int result = JOptionPane.showConfirmDialog(null, "Etes vous sûr de vouloir supprimer ce magasin ?", "Suppression du compte", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {

                        PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM store WHERE name = ?");
                        statement1.setString(1, store);
                        ResultSet result1 = statement1.executeQuery();

                        while (result1.next()) {

                            id = result1.getString("id");

                        }

                        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM product_in_store  WHERE store_id = ?;");
                        statement2.setString(1, id);
                        statement2.executeUpdate();

                        PreparedStatement statement3 = connection.prepareStatement("DELETE FROM user_store  WHERE store_id = ?;");
                        statement3.setString(1, id);
                        statement3.executeUpdate();

                        PreparedStatement statement4 = connection.prepareStatement("DELETE FROM store  WHERE id = ?;");
                        statement4.setString(1, id);
                        statement4.executeUpdate();

                        DefaultTableModel model = (DefaultTableModel) t_shop.getModel();
                        for (int i = model.getRowCount() - 1; i >= 0; i--) {
                            if (model.getValueAt(i, 0).equals(store)) {
                                model.removeRow(i);
                            }
                        }
                        cb_nom_store_del.removeItem(store);
                        cb_store_user.removeItem(store);
                        cb_store_new_user.removeItem(store);


                        JOptionPane.showMessageDialog(frame, "Magasin supprimé avec succes !", "Suppression", JOptionPane.INFORMATION_MESSAGE);


                    }


                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }

            }
        });
        b_add_store.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                try {

                    String new_store = tf_store_name.getText();

                    if(!new_store.equals("")){

                        boolean find = false;

                        PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM store WHERE name = ?");
                        statement1.setString(1, new_store);
                        ResultSet result1 = statement1.executeQuery();

                        while (result1.next()) {

                            find = true;

                        }

                        if(!find){

                            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO store (name) VALUES (?);");
                            statement2.setString(1, new_store);
                            statement2.executeUpdate();

                            DefaultTableModel model = (DefaultTableModel) t_shop.getModel();
                            Object[] data = {new_store};
                            model.addRow(data);

                            tf_store_name.setText("");
                            cb_nom_store_del.addItem(new_store);
                            cb_store_user.addItem(new_store);
                            cb_store_new_user.addItem(new_store);

                            JOptionPane.showMessageDialog(frame, "Magasin crée avec succes !", "Création", JOptionPane.INFORMATION_MESSAGE);


                        }
                        else{

                            JOptionPane.showMessageDialog(frame, "Erreur : Ce magasin existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);

                        }
                    }
                    else{

                        JOptionPane.showMessageDialog(frame, "Erreur : Le nom du magasin ne peut pas etre vide", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }
                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }
            }
        });
        b_del_product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                String id = null;
                String product_name = String.valueOf(cb_nom_product_del.getSelectedItem());


                try {

                    PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM product WHERE name = ?");
                    statement1.setString(1, product_name);
                    ResultSet result1 = statement1.executeQuery();

                    while (result1.next()) {

                        id = result1.getString("id");

                    }

                    int result = JOptionPane.showConfirmDialog(null, "Etes vous sûr de vouloir supprimer ce produit ?", "Suppression du compte", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {

                        PreparedStatement statement3 = connection.prepareStatement("DELETE FROM product_in_store  WHERE product_id = ?;");
                        statement3.setString(1, id);
                        statement3.executeUpdate();

                        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM product  WHERE id = ?;");
                        statement2.setString(1, id);
                        statement2.executeUpdate();

                        DefaultTableModel model = (DefaultTableModel) t_product.getModel();
                        for (int i = model.getRowCount() - 1; i >= 0; i--) {
                            if (model.getValueAt(i, 0).equals(product_name)) {
                                model.removeRow(i);
                            }
                        }
                        cb_nom_product_del.removeItem(product_name);

                        JOptionPane.showMessageDialog(frame, "Produit supprimé avec succes !", "Suppression", JOptionPane.INFORMATION_MESSAGE);

                    }

                } catch(SQLException exp){
                    exp.printStackTrace();
                }
            }
        });
        b_add_product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                String new_product_name = tf_nom_product_add.getText();
                String new_product_price = tf_price_product_add.getText();

                String id = null;

                boolean find = false;

                try {

                    if(!new_product_name.equals("") || !new_product_price.equals("")) {


                        PreparedStatement statement1 = connection.prepareStatement("SELECT id FROM product WHERE name = ?");
                        statement1.setString(1, new_product_name);
                        ResultSet result1 = statement1.executeQuery();

                        while (result1.next()) {

                            find = true;

                            id = result1.getString("id");

                        }

                        if (find) {

                            JOptionPane.showMessageDialog(frame, "Erreur : Ce produit existe déjà", "Erreur", JOptionPane.ERROR_MESSAGE);

                        } else {

                            PreparedStatement statement2 = connection.prepareStatement("INSERT into product (name,price)  VALUES (?,?);");
                            statement2.setString(1, new_product_name);
                            statement2.setString(2, new_product_price);
                            statement2.executeUpdate();

                            tf_nom_product_add.setText("");
                            tf_price_product_add.setText("");

                            DefaultTableModel model = (DefaultTableModel) t_product.getModel();
                            Object[] data = {new_product_name,new_product_price};
                            model.addRow(data);

                            cb_nom_product_del.addItem(new_product_name);

                            JOptionPane.showMessageDialog(frame, "Produit ajouté !", "Ajouté", JOptionPane.INFORMATION_MESSAGE);


                        }
                    }
                    else{

                        JOptionPane.showMessageDialog(frame, "Erreur : Le champ nom ou prix du nouveau produit est vide", "Erreur", JOptionPane.ERROR_MESSAGE);

                    }


                } catch(SQLException exp){
                    exp.printStackTrace();
                }

            }
        });
        b_val_modif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                String email = tf_email.getText();
                String pseudo = tf_pseudo.getText();
                String role = String.valueOf(cb_role.getSelectedItem());
                String store = String.valueOf(cb_store_user.getSelectedItem());

                String password = String.valueOf(ptf_pswd.getPassword());
                String password_rep = String.valueOf(ptf_pswd_rep.getPassword());

                boolean find = false;


                try {

                    if(!email.equals("") || !pseudo.equals("")){

                        PreparedStatement statement1 = connection.prepareStatement("UPDATE users SET pseudo = ?, role = ? WHERE email_id = ? ;");
                        statement1.setString(1, pseudo);
                        statement1.setString(2, role);
                        statement1.setString(3, user_search_id);
                        statement1.executeUpdate();

                        PreparedStatement statement2 = connection.prepareStatement("UPDATE whitelist SET email = ? WHERE id = ? ;");
                        statement2.setString(1, email);
                        statement2.setString(2, user_search_id);
                        statement2.executeUpdate();

                    }
                    else{

                        JOptionPane.showMessageDialog(frame, "Erreur : Les champs pseudo ou email ne peut etre vide", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;

                    }

                    PreparedStatement statement2 = connection.prepareStatement("UPDATE user_store SET store_id = (SELECT id FROM store WHERE name = ?) WHERE email_id = ?;");
                    statement2.setString(1, store);
                    statement2.setString(2, user_search_id);
                    statement2.executeUpdate();


                    PreparedStatement statement_a = connection.prepareStatement("SELECT email_id FROM admin WHERE email_id = ?");
                    statement_a.setString(1, user_search_id);
                    ResultSet result_a = statement_a.executeQuery();
                    while (result_a.next()) {

                        find = true;

                    }

                    if(!find & role.equals("admin")){

                        PreparedStatement statement4 = connection.prepareStatement("INSERT INTO admin VALUES (?)");
                        statement4.setString(1, user_search_id);
                        statement4.executeUpdate();

                    }
                    if(find & role.equals("user")){

                        PreparedStatement statement4 = connection.prepareStatement("DELETE FROM admin  WHERE email_id = ?;");
                        statement4.setString(1, user_search_id);
                        statement4.executeUpdate();

                    }


                    if(!password.equals("")) {
                        if (password.length() >= 10 & password.matches(".*[A-Z].*") & password.matches("^.*[^a-zA-Z0-9 ].*$") /*& !password.contains(" ")*/) {

                            if (password.equals(password_rep)) {

                                String crypt_password = BCrypt.hashpw(password, BCrypt.gensalt());

                                PreparedStatement statement4 = connection.prepareStatement("UPDATE users SET password = ? WHERE email_id = ?;");
                                statement4.setString(1, crypt_password);
                                statement4.setString(2, user_search_id);
                                statement4.executeUpdate();

                            } else {

                                JOptionPane.showMessageDialog(frame, "Erreur : Erreur le mot de passe a mal été répeté", "Erreur", JOptionPane.ERROR_MESSAGE);
                                return;

                            }
                        } else {

                            JOptionPane.showMessageDialog(frame, "Erreur : Votre mot de passe doit contenir au moin 10 caractères, dont au moin un caractère special et une majuscule", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;

                        }
                    }

                    JOptionPane.showMessageDialog(frame, "Utilisateur modifié !", "Modification", JOptionPane.INFORMATION_MESSAGE);


                } catch(SQLException exp){
                    exp.printStackTrace();
                }
            }
        });
        b_del_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                try {

                    int result = JOptionPane.showConfirmDialog(null, "Etes vous sûr de vouloir supprimer ce compte ?", "Suppression du compte", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {

                        PreparedStatement statement1 = connection.prepareStatement("DELETE FROM users WHERE email_id = ?;");
                        statement1.setString(1, user_search_id);
                        statement1.executeUpdate();

                        PreparedStatement statement3 = connection.prepareStatement("DELETE FROM user_store WHERE email_id = ?;");
                        statement3.setString(1, user_search_id);
                        statement3.executeUpdate();

                        if(role.equals("admin")){

                            PreparedStatement statement4 = connection.prepareStatement("DELETE FROM admin WHERE email_id = ?;");
                            statement4.setString(1, user_search_id);
                            statement4.executeUpdate();

                        }

                        PreparedStatement statement2 = connection.prepareStatement("DELETE FROM whitelist WHERE id = ?;");
                        statement2.setString(1, user_search_id);
                        statement2.executeUpdate();

                        tf_email_search_admin.setText("");
                        tf_email.setText("");
                        tf_pseudo.setText("");
                        ptf_pswd_rep.setText("");
                        ptf_pswd.setText("");

                        tf_email.setVisible(false);
                        tf_pseudo.setVisible(false);
                        ptf_pswd_rep.setVisible(false);
                        ptf_pswd.setVisible(false);
                        cb_role.setVisible(false);
                        cb_store_user.setVisible(false);

                        lb_email_search.setVisible(false);
                        lb_pseudo_search.setVisible(false);
                        lb_pswd.setVisible(false);
                        lb_pswd_rep.setVisible(false);
                        lb_role_search.setVisible(false);
                        lb_store_user.setVisible(false);

                        b_val_modif.setVisible(false);
                        b_del_user.setVisible(false);


                    }

                }
                catch (SQLException exp) {
                    exp.printStackTrace();
                }

            }
        });
    }
}
