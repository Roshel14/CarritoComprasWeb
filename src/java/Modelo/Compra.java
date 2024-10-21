
package Modelo;

import java.util.List;

/*
Autor: Yutmer Jara Santiago
*/
public class Compra {
    private int id;
    private Cliente cliente;
    private int idpago;
    private String fecha;
    private Double monto;
    private String estado;
    
    private List<Carrito>detallecompra;

    public Compra() {
    }

    public Compra(Cliente cliente, int idpago, String fecha, Double monto, String estado, List<Carrito> detallecompra) {
        this.cliente = cliente;
        this.idpago = idpago;
        this.fecha = fecha;
        this.monto = monto;
        this.estado = estado;
        this.detallecompra = detallecompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Carrito> getDetallecompra() {
        return detallecompra;
    }

    public void setDetallecompra(List<Carrito> detallecompra) {
        this.detallecompra = detallecompra;
    }
    
    
}
