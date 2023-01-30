package iStore_ltd;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection_DB {
    private static connection_DB instance = null;
    private Connection connection = null;

    String BDD = "u788104185_tristan_java";
    String url = "jdbc:mysql://54.37.31.19:3306/" + BDD;
    String user = "u788104185_dev_tristan";
    String passwd = "~g3|8>u#V";

    //connection_DB.getInstance().getConnection()

    private connection_DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, passwd);

            System.out.println("connection");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static connection_DB getInstance() {
        if (instance == null) {

            System.out.println("nv_instance");

            instance = new connection_DB();
        }
        System.out.println("returne inst");
        return instance;
    }

    public Connection getConnection() {

        System.out.println("returne conn");

        return connection;
    }
}
