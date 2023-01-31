package iStore_ltd;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//afin de ne pas avoir a se reconnecter a la base de donnée a chaque requete on vien crée un objet qui va crée une instance de connexion
//il faut verifier qu'une seule connexion est en cours a chaque fois
//Si aucune instance n'est en cours on en crée une sinon on retourne simplement cet instance.
//on appele cette technique du connection pooling.

public class connection_DB extends Thread{
    private static connection_DB instance = null;
    private Connection connection = null;

    String BDD = "u788104185_tristan_java";
    String url = "jdbc:mysql://54.37.31.19:3306/" + BDD;
    String user = "u788104185_dev_tristan";
    String passwd = "~g3|8>u#V";



    //connection_DB.getInstance().getConnection()

    public connection_DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, passwd);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static connection_DB getInstance() {
        if (instance == null) {
            instance = new connection_DB();
        }
        return instance;
    }



    public Connection getConnection() {

        //System.out.println("returne_conn");


        return connection;
    }
}
