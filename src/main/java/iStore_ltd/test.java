package iStore_ltd;

import javax.swing.*;

public class test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tableau Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] columns = {"Colonne 1", "Colonne 2", "Colonne 3"};
        Object[][] data = {{"Donnée 1,1", "Donnée 1,2", "Donnée 1,3"},
                {"Donnée 2,1", "Donnée 2,2", "Donnée 2,3"},
                {"Donnée 3,1", "Donnée 3,2", "Donnée 3,3"}};
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setSize(450, 200);
        frame.setVisible(true);

        System.out.println("test");
    }
}