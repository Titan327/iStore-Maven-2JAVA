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
    private int user_id = 0;
    private int store_id = 0;

    public disp_stock() {

        tab_data.setModel(new DefaultTableModel(
                null,
                new String[]{"nom du produit","quantité"}
        ));


        connection_DB db = connection_DB.getInstance();
        Connection connection = db.getConnection();


        try {

            Statement statement1 = connection.createStatement();
            ResultSet result1 = statement1.executeQuery("SELECT name FROM product");
            while (result1.next()) {

                String name_product = result1.getString("name");
                comb_obj.addItem(name_product);

            }


            Statement statement2 = connection.createStatement();
            ResultSet result2 = statement2.executeQuery("SELECT id,pseudo FROM users");
            while (result2.next()) {

                user_id = result2.getInt("id");

            }

            PreparedStatement statement3 = connection.prepareStatement("SELECT store_id FROM user_store WHERE user_id=?");
            statement3.setString(1, String.valueOf(user_id));
            ResultSet result3 = statement3.executeQuery();
            while (result3.next()) {

                store_id = result3.getInt("store_id");

            }

            PreparedStatement statement4 = connection.prepareStatement("SELECT product.name, product_in_store.quantity FROM product JOIN product_in_store ON product.id = product_in_store.product_id WHERE store_id = ?;");
            statement4.setString(1, String.valueOf(store_id));
            ResultSet result4 = statement4.executeQuery();
            while (result4.next()) {

                String name_product = result4.getString("product.name");
                int quantity_product = result4.getInt("product_in_store.quantity");

                DefaultTableModel model = (DefaultTableModel) tab_data.getModel();
                Object[] data = {name_product, quantity_product};
                model.addRow(data);


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

                    PreparedStatement statement = connection.prepareStatement("SELECT id FROM product WHERE name = ?");
                    statement.setString(1, product);
                    ResultSet result = statement.executeQuery();
                    while (result.next()) {

                        product = result.getString("id");

                    }


                    String quantity = tf_quanti_sell.getText();

                    int product_id = comb_obj.getSelectedIndex();
                    Object value = tab_data.getValueAt(product_id, 1);

                    String str_val = String.valueOf(value);

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

                    PreparedStatement statement2 = connection.prepareStatement("UPDATE product_in_store  SET quantity = ? WHERE product_id = ? AND store_id = ?;");

                    statement2.setString(1, String.valueOf(value_int));
                    statement2.setString(2, product);
                    statement2.setString(3, String.valueOf(store_id));
                    statement2.executeUpdate();


                }
                catch(SQLException exp){
                        exp.printStackTrace();
                    }



                //update le tableau en interface graphique


            }
        });
    }


}
