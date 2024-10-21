
package ModeloDAO;

import Config.Conexion;
import Modelo.Producto;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
/*
Autor: Yutmer Jara Santiago
*/
public class ProductoDAO {
    Connection con;
    //Instanciamos la clase Conexion que creamos en paquete config
    Conexion cn = new Conexion();
    //cn es el objeto creado de la instancia de la clase
    PreparedStatement ps;
    //es una plantilla para reconocer sql
    ResultSet rs;
    //para correr el lenguaje sql
    
    public Producto listarId(int id){
        String sql="select * from producto where idProducto="+id;
        Producto prod = new Producto();
        try {
            con=cn.getConection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
                prod.setId(rs.getInt(1));
                prod.setNombre(rs.getString(2));
                prod.setFoto(rs.getBinaryStream(3));
                prod.setDescripcion(rs.getString(4));
                prod.setPrecio(rs.getDouble(5));
                prod.setStock(rs.getInt(6));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar id Producto");
        }
        return prod;
    }
    
    public List listar(){
        List<Producto>productos=new ArrayList();
        String sql="select * from producto";
        try {
            con=cn.getConection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
                Producto prod = new Producto();
                prod.setId(rs.getInt(1));
                prod.setNombre(rs.getString(2));
                prod.setFoto(rs.getBinaryStream(3));
                prod.setDescripcion(rs.getString(4));
                prod.setPrecio(rs.getDouble(5));
                prod.setStock(rs.getInt(6));
                
                productos.add(prod);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar los producto");
        }
        return productos;
    }
    
    public void listarImg(int id, HttpServletResponse response){
        String sql="select * from producto where idProducto="+id;
        InputStream inputStream=null;
        OutputStream outputStream=null;
        BufferedInputStream bufferedInputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        try {
            outputStream=response.getOutputStream();
            con=cn.getConection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            if (rs.next()) {
                inputStream=rs.getBinaryStream("Foto");
            }
            bufferedInputStream=new BufferedInputStream(inputStream);
            bufferedOutputStream=new BufferedOutputStream(outputStream);
            int i=0;
            while ((i=bufferedInputStream.read())!=-1) {                
                bufferedOutputStream.write(i);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar imagenes");
        }
    }
    
}
