package iStore_ltd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class disp_stock {
    private JLabel logo;
    private JTextField tf_quanti_sell;
    private JButton b_submit;
    private JLabel lab_tf_quanti;
    private JLabel lab_obj_sell_nb;
    private JPanel disp_stock;
    private JComboBox comb_obj;
    private JComboBox comb_sell_add;
    private JTable tab_data;
    private JScrollPane data_table;
    private JButton b_menu;
    private String user_id = null;
    private int store_id = 0;
    private String user_pseudo = null;

    public disp_stock(String email) {

        //System.out.println(email);

        tab_data.setModel(new DefaultTableModel(
                null,
                new String[]{"nom du produit","quantité","prix/unité"}
        ));



        connection_DB db = connection_DB.getInstance();
        Connection connection = db.getConnection();


        try {

            Statement statement1 = connection.createStatement();
            ResultSet result1 = statement1.executeQuery("SELECT name,id,price FROM product;");
            String[] list = {};
            String[] list_name_product = {};
            String[] list_price_product = {};

            while (result1.next()) {

                String name_product = result1.getString("name");
                String product_id = result1.getString("id");
                String product_price = result1.getString("price");
                comb_obj.addItem(name_product);

                String[] new_list = new String[list.length + 1];
                System.arraycopy(list, 0, new_list, 0, list.length);
                new_list[new_list.length - 1] = product_id;
                list = new_list;

                String[] new_list_name_product = new String[list_name_product.length + 1];
                System.arraycopy(list_name_product, 0, new_list_name_product, 0, list_name_product.length);
                new_list_name_product[new_list_name_product.length - 1] = name_product;
                list_name_product = new_list_name_product;

                String[] new_list_price_product = new String[list_price_product.length + 1];
                System.arraycopy(list_price_product, 0, new_list_price_product, 0, list_price_product.length);
                new_list_price_product[new_list_price_product.length - 1] = product_price;
                list_price_product = new_list_price_product;

            }

            /*
            for (int i = 0; i < list.length; i++) {
                System.out.println(list[i]);
            }
            */

            PreparedStatement statement2 = connection.prepareStatement("SELECT email_id, pseudo FROM users WHERE email_id = (SELECT id FROM whitelist WHERE email = ?);");
            statement2.setString(1, email);
            ResultSet result2 = statement2.executeQuery();
            while (result2.next()) {

                user_id = result2.getString("email_id");
                user_pseudo = result2.getString("pseudo");

            }

            PreparedStatement statement3 = connection.prepareStatement("SELECT store_id FROM user_store WHERE email_id = (SELECT id FROM whitelist WHERE email = ?);");
            statement3.setString(1, String.valueOf(email));
            ResultSet result3 = statement3.executeQuery();
            while (result3.next()) {

                store_id = result3.getInt("store_id");

            }

            String[] list2 = {};

            PreparedStatement statement4 = connection.prepareStatement("SELECT product.name, product_in_store.quantity, product.price, product.id FROM product JOIN product_in_store ON product.id = product_in_store.product_id WHERE store_id = ?;");
            statement4.setString(1, String.valueOf(store_id));
            ResultSet result4 = statement4.executeQuery();
            while (result4.next()) {

                String name_product = result4.getString("product.name");
                int quantity_product = result4.getInt("product_in_store.quantity");
                int product_price = result4.getInt("product.price");
                String product_id = result4.getString("product.id");

                DefaultTableModel model = (DefaultTableModel) tab_data.getModel();
                Object[] data = {name_product, quantity_product,product_price};
                model.addRow(data);

                String[] new_list2 = new String[list2.length + 1];
                System.arraycopy(list2, 0, new_list2, 0, list2.length);
                new_list2[new_list2.length - 1] = product_id;
                list2 = new_list2;


            }

            boolean find = false;

            for (int i = 0; i < list.length; i++) {

                find = false;

                for (int j = 0; j < list2.length; j++) {

                    if(list[i].equals(list2[j])){
                        find = true;
                        break;

                    }
                }
                if(!find){
                    PreparedStatement statement5 = connection.prepareStatement("INSERT INTO product_in_store (product_id, store_id, quantity) VALUES (?,?,?);");
                    statement5.setString(1, list[i]);
                    statement5.setString(2, String.valueOf(store_id));
                    statement5.setString(3, "0");
                    statement5.executeUpdate();

                    DefaultTableModel model = (DefaultTableModel) tab_data.getModel();
                    Object[] data = {list_name_product[i], 0,list_price_product[i]};
                    model.addRow(data);
                }
            }


        }
        catch (SQLException exp) {
            exp.printStackTrace();
        }

        JFrame frame = new JFrame("iStore");
        Image icon = new ImageIcon("src/main/resources/image/icon.png").getImage();
        frame.setIconImage(icon);
        frame.setContentPane(this.disp_stock);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        b_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connection_DB db = connection_DB.getInstance();
                Connection connection = db.getConnection();

                try {

                    String product = String.valueOf(comb_obj.getSelectedItem());
                    String product_name = null;
                    String product_price = null;

                    PreparedStatement statement = connection.prepareStatement("SELECT name,id,price FROM product WHERE name = ?;");
                    statement.setString(1, product);
                    ResultSet result = statement.executeQuery();
                    while (result.next()) {

                        product = result.getString("id");
                        product_name = result.getString("name");
                        product_price = result.getString("price");

                    }



                    String quantity = tf_quanti_sell.getText();
                    if(quantity.equals("") || quantity.equals("0")){
                        JOptionPane.showMessageDialog(frame, "Erreur : La quantité ne peut pas etre null", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //System.out.println(tab_data.getRowCount());

                    for (int i = 0; i < tab_data.getRowCount() ; i++) {

                        if(String.valueOf(tab_data.getValueAt(i, 0)).equals(product_name)){

                            int product_id = comb_obj.getSelectedIndex();
                            String str_val = String.valueOf(tab_data.getValueAt(product_id, 1));

                            int value_int = 0;

                            if(comb_sell_add.getSelectedIndex() == 0) {

                                value_int = Integer.parseInt(str_val) - Integer.parseInt(quantity);

                                if(value_int < 0 ){

                                    JOptionPane.showMessageDialog(frame, "Erreur : Stock insuffisant pour cette action", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    return;

                                }

                            } else if (comb_sell_add.getSelectedIndex() == 1) {

                                value_int = Integer.parseInt(str_val) + Integer.parseInt(quantity);

                            }

                            DefaultTableModel model = (DefaultTableModel) tab_data.getModel();
                            model.setValueAt(value_int, product_id, 1);

                            PreparedStatement statement2 = connection.prepareStatement("UPDATE product_in_store SET quantity = ? WHERE product_id = ? AND store_id = ?;");

                            statement2.setString(1, String.valueOf(value_int));
                            statement2.setString(2, product);
                            statement2.setString(3, String.valueOf(store_id));
                            statement2.executeUpdate();

                            return;

                        }
                    }
                }
                catch(SQLException exp){
                        exp.printStackTrace();
                }
            }
        });
        b_menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.out.println(user_id);
                new user_menu(user_id);
            }
        });
    }
}
