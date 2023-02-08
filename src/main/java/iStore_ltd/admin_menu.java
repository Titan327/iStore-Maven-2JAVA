package iStore_ltd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class admin_menu {
    private JLabel lb_logo;
    private JLabel lb_search_user;
    private JLabel lb_whitelist;
    private JLabel lb_pseudo_search_admin;
    private JTextField tf_pseudo_search_admin;
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
    private JTable table1;
    private JLabel lb_del_store;
    private JButton b_del_store;
    private JLabel lb_add_store;
    private JTextField tf_store_name;
    private JButton b_add_store;
    private JTable t_product;
    private JScrollPane t_shop;
    private JComboBox cb_nom_store_del;
    private JComboBox comboBox1;
    private JButton b_del_product;
    private JTextField textField1;
    private JTextField textField2;
    private JButton b_add_product;
    private JButton b_search;
    private JPanel admin_menu;

    public admin_menu() {

    JFrame frame = new JFrame("iStore");
    Image icon = new ImageIcon("src/main/resources/image/icon.png").getImage();
    frame.setIconImage(icon);
    frame.setContentPane(this.admin_menu);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    b_val_modif.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
}
}
