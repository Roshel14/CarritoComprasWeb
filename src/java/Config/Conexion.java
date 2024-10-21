package Config;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;

/*
Autor: Yutmer Brosh Jara Santiago
 */
public class Conexion {

    Connection con;
    String url = "jdbc:mysql://localhost:3306/carrito_compras";
    String user = "root";
    String pass = "";

    public Connection getConection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puedo establecer la conexion a la base de datos");
        }
        return con;
    }
}
