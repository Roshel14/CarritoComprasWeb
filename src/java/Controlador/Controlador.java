package Controlador;

import Config.Fecha;
import Modelo.Carrito;
import Modelo.Cliente;
import Modelo.Compra;
import Modelo.Pago;
import Modelo.Producto;
import ModeloDAO.CompraDAO;
import ModeloDAO.ProductoDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controlador extends HttpServlet {

    ProductoDAO proDAO = new ProductoDAO();
    Producto prod = new Producto();
    List<Producto> productos = new ArrayList<>();

    List<Carrito> listaCarrito = new ArrayList<>();
    int items;
    double totalPagar;
    int cantidad = 1;
    int idprod;
    Carrito car;
    
    Fecha fecha = new Fecha();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        productos = proDAO.listar();
        switch (accion) {
            case "Comprar":
                totalPagar = 0.0;
                idprod = Integer.parseInt(request.getParameter("id"));
                prod = proDAO.listarId(idprod);
                items = items + 1;
                car = new Carrito();
                car.setItem(items);
                car.setIdProducto(prod.getId());
                car.setNombres(prod.getNombre());
                car.setDescripcion(prod.getDescripcion());
                car.setPrecioCompra(prod.getPrecio());
                car.setCantidad(cantidad);
                car.setSubTotal(cantidad * prod.getPrecio());
                listaCarrito.add(car);
                for (int i = 0; i < listaCarrito.size(); i++) {
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.setAttribute("carrito", listaCarrito);
                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "AgregarCarrito":
                int pos = 0;
                int cantidad = 1;
                idprod = Integer.parseInt(request.getParameter("id"));
                prod = proDAO.listarId(idprod);
                if (listaCarrito.size() > 0) {
                    for (int i = 0; i < listaCarrito.size(); i++) {
                        if (idprod == listaCarrito.get(i).getIdProducto()) {
                            pos = i;
                        }
                    }
                    if (idprod == listaCarrito.get(pos).getIdProducto()) {
                        cantidad = listaCarrito.get(pos).getCantidad() + cantidad;
                        double subTotal = listaCarrito.get(pos).getPrecioCompra() * cantidad;
                        listaCarrito.get(pos).setCantidad(cantidad);
                        listaCarrito.get(pos).setSubTotal(subTotal);
                    } else {
                        items = items + 1;
                        car = new Carrito();
                        car.setItem(items);
                        car.setIdProducto(prod.getId());
                        car.setNombres(prod.getNombre());
                        car.setDescripcion(prod.getDescripcion());
                        car.setPrecioCompra(prod.getPrecio());
                        car.setCantidad(cantidad);
                        car.setSubTotal(cantidad * prod.getPrecio());
                        listaCarrito.add(car);
                    }
                } else {
                    items = items + 1;
                    car = new Carrito();
                    car.setItem(items);
                    car.setIdProducto(prod.getId());
                    car.setNombres(prod.getNombre());
                    car.setDescripcion(prod.getDescripcion());
                    car.setPrecioCompra(prod.getPrecio());
                    car.setCantidad(cantidad);
                    car.setSubTotal(cantidad * prod.getPrecio());
                    listaCarrito.add(car);
                }

                request.setAttribute("contador", listaCarrito.size());
                request.getRequestDispatcher("Controlador?accion=home").forward(request, response);
                break;
            case "Delete":
                int idproducto = Integer.parseInt(request.getParameter("id"));
                for (int i = 0; i < listaCarrito.size(); i++) {
                    if (listaCarrito.get(i).getIdProducto() == idproducto) {
                        listaCarrito.remove(i);
                    }
                }
                break;
            case "ActualizarCantidad":
                int idpro=Integer.parseInt(request.getParameter("id"));
                int cant=Integer.parseInt(request.getParameter("cantidad"));
                for (int i = 0; i < listaCarrito.size(); i++) {
                    if (listaCarrito.get(i).getIdProducto() == idpro) {
                        listaCarrito.get(i).setCantidad(cant);
                        double st=listaCarrito.get(i).getPrecioCompra()*cant;
                        listaCarrito.get(i).setSubTotal(st);
                    }
                }
                break;
            case "Carrito":
                totalPagar = 0.0;
                request.setAttribute("carrito", listaCarrito);
                for (int i = 0; i < listaCarrito.size(); i++) {
                    totalPagar = totalPagar + listaCarrito.get(i).getSubTotal();
                }
                request.setAttribute("totalPagar", totalPagar);
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            case "GenerarCompra":
                Cliente cliente = new Cliente();
                cliente.setId(12);
                CompraDAO dao = new CompraDAO();
                Compra compra = new Compra(cliente,19,fecha.FechaBD() , totalPagar, "Cancelado", listaCarrito);
                int res=dao.GenerarCompra(compra);
                if(res!=0&&totalPagar!=0){
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }else{
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
                break;
            default:
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
