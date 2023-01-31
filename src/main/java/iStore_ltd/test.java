package iStore_ltd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class test {
    public static void main(String[] args) {

        String BDD = "u788104185_tristan_java";
        String url = "jdbc:mysql://54.37.31.19:3306/" + BDD;
        String user = "u788104185_dev_tristan";
        String passwd = "~g3|8>u#V";

        try {
            // Définir la propriété de connexion "autoReconnect=true"
            // pour éviter les problèmes de déconnexion.
            Properties properties = new Properties();
            properties.put("autoReconnect", "true");

            // Établir la connexion à la base de données.
            Connection connection = DriverManager.getConnection(url, user, passwd);

            // Code pour travailler avec la base de données.

            // Fermer la connexion à la base de données.
            connection.close();
        } catch (SQLException e) {
            // Traiter les erreurs SQL.
        }
    }
}
