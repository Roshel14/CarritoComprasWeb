package ModeloDAO;

import Config.Conexion;
import Modelo.Carrito;
import Modelo.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/*
Autor: Yutmer Jara Santiago
 */
public class CompraDAO {

    Connection con;
    //Instanciamos la clase Conexion que creamos en paquete config
    Conexion cn = new Conexion();
    //cn es el objeto creado de la instancia de la clase
    PreparedStatement ps;
    //es una plantilla para reconocer sql
    ResultSet rs;
    //para correr el lenguaje sql
    int r = 0;

    public int GenerarCompra(Compra compra) {
        int idcompras;
        String sql="insert into compras(idCliente,FechaCompras,Monto,Estado,idPago)values(?,?,?,?,?)";
        try {
            con=cn.getConection();
            ps=con.prepareStatement(sql);
            ps.setInt(1, compra.getCliente().getId());
            ps.setString(2, compra.getFecha());
            ps.setDouble(3, compra.getMonto());
            ps.setString(4, compra.getEstado());
            ps.setInt(5, compra.getIdpago());
            r=ps.executeUpdate();
            
            sql="select @@IDENTITY AS idCompras";
            rs=ps.executeQuery(sql);
            rs.next();
            idcompras=rs.getInt("idCompras");
            rs.close();
            
            for (Carrito detalle : compra.getDetallecompra()) {
                sql="insert into detalle_compras(idProducto,idCompras,Cantidad,PrecioCompra)values(?,?,?,?)";
                ps=con.prepareStatement(sql);
                ps.setInt(1, detalle.getIdProducto());
                ps.setInt(2, idcompras);
                ps.setInt(3, detalle.getCantidad());
                ps.setDouble(4, detalle.getPrecioCompra());
                r=ps.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar compra");
        }
        return r;
    }
}
