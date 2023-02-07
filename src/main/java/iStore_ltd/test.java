package iStore_ltd;

import javax.swing.JOptionPane;

public class test {
    public static void main(String[] args) {
        int result = JOptionPane.showConfirmDialog(null, "Voulez-vous continuer ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            System.out.println("L'utilisateur a cliqué sur 'Oui'");
        }
    }
}
