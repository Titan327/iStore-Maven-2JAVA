package iStore_ltd;

import java.sql.*;

public class Main {
    public static void main(String [] args){



        try (Connection connection = connection_DB.getInstance().getConnection();
             Statement statement = connection.createStatement()){
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                System.out.println("email : " + email + ", password : " + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        new connexion_menu();

    }

}