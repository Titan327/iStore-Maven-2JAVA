package iStore_ltd;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class choix_log_or_reg {
    private JButton b_login;
    private JButton b_register;
    private JPanel log_or_rgist;

    public choix_log_or_reg() {

    JFrame frame = new JFrame("iStore");
    frame.setContentPane(this.log_or_rgist);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

    b_login.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            frame.dispose();
            new login_menu();

        }
    });
        b_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                new register_menu();

            }
        });
    }
}
