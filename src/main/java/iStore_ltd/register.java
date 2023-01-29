package iStore_ltd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class register {
    private JLabel logo;
    private JLabel lbl_email;
    private JTextField tf_email;
    private JLabel lbl_password;
    private JPasswordField ptf_password;
    private JButton b_login;
    private JButton b_register;
    private JPanel conn_menu;


    public register() {

        JFrame frame = new JFrame("iStore");
        frame.setContentPane(this.conn_menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        b_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = tf_email.getText();
                String password = String.valueOf(ptf_password.getPassword());

                //test testob = new test(email,password);

            }
        });
    }


}

