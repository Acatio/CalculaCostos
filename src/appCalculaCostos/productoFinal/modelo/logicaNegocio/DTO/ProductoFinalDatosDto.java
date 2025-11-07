/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO;

/**
 *
 * @author jose
 */
public class ProductoFinalDatosDto
{
    private int id;
    private String nombre;
    private double porcentajeGanancia;
    private double precioVenta;
    private double costoTotal;
    private double cantidadVendida;

    public ProductoFinalDatosDto(int id, String nombre, double porcentajeGanancia, double precioVenta, double costoTotal, double cantidadVendida)
    {
        this.id = id;
        this.nombre = nombre;
        this.porcentajeGanancia = porcentajeGanancia;
        this.precioVenta = precioVenta;
        this.costoTotal = costoTotal;
        this.cantidadVendida = cantidadVendida;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public double getPorcentajeGanancia()
    {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(double porcentajeGanancia)
    {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public double getPrecioVenta()
    {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta)
    {
        this.precioVenta = precioVenta;
    }

    public double getCostoTotal()
    {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal)
    {
        this.costoTotal = costoTotal;
    }

    public double getCantidadVendida()
    {
        return cantidadVendida;
    }

    public void setCantidadVendida(double cantidadVendida)
    {
        this.cantidadVendida = cantidadVendida;
    }
    
    
}
