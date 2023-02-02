package iStore_ltd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//afin de ne pas avoir a se reconnecter a la base de donnée a chaque requete on vien crée un objet qui va crée une instance de connexion
//il faut verifier qu'une seule connexion est en cours a chaque fois
//Si aucune instance n'est en cours on en crée une sinon on retourne simplement cet instance.
//on appele cette technique du connection pooling.

public class connection_DB {
    private static connection_DB instance = null;
    private static Connection connection = null;

    String BDD = "u788104185_tristan_java";
    String url = "jdbc:mysql://54.37.31.19:3306/" + BDD;
    String user = "u788104185_dev_tristan";
    String passwd = "~g3|8>u#V";


    public connection_DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println("co_via_driver");
            connection = DriverManager.getConnection(url, user, passwd);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static connection_DB getInstance() {
        try {
                //on test si l'instance est null ou si la connexion n'est pas valide.
                if (instance == null || !connection.isValid(5)) {
                    //on se reconecte
                    //System.out.println("nv_instance");
                    instance = new connection_DB();
                }
                //System.out.println("return_instance");
                return instance;
            }
        catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }



    public Connection getConnection() {

        //System.out.println("return_conn");

        return connection;
    }
}
