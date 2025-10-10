/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.DetalleCostoMP;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose
 */
public class ProductoFinal
{

    private int id;
    private String nombre;
    private double porcentajeGanancia;
    private double precioVenta;
    private double costoTotal;
    private final List<ServicioCosto> costos;

    public ProductoFinal(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
        this.costos = new ArrayList<>();
    }

    public ProductoFinal(String nombre)
    {
        this.nombre = nombre;
        this.costos = new ArrayList<>();
    }

    public ProductoFinal(int id, String nombre, double porcentajeGanancia, double precioVenta,double costoTotal)
    {
        this.id = id;
        this.nombre = nombre;
        this.porcentajeGanancia = porcentajeGanancia;
        this.precioVenta = precioVenta;
        this.costoTotal=costoTotal;
        this.costos = new ArrayList<>();

    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * @return the costos
     */
    public List<ServicioCosto> getCostos()
    {
        return costos;
    }

    /**
     * @return the porcentajeGanancia
     */
    public double getPorcentajeGanancia()
    {
        return porcentajeGanancia;
    }

    public double getCostoTotal()
    {
        return costoTotal;
    }

    /**
     * @param porcentajeGanancia the porcentajeGanancia to set
     */
    public void setPorcentajeGanancia(double porcentajeGanancia)
    {
        if (porcentajeGanancia < 0)
        {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede ser menor a cero");
        }
        if (porcentajeGanancia > 1)
        {
            this.porcentajeGanancia = porcentajeGanancia / 100;
        } else
        {
            this.porcentajeGanancia = porcentajeGanancia;
        }
        this.precioVenta = calcularPrecioVenta();
    }

    /**
     * @return the precioVenta
     */
    public double getPrecioVenta()
    {
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(double precioVenta)
    {
        if (precioVenta < 0)
        {
            throw new IllegalArgumentException("El precio de venta no puede ser menor a cero");
        }
        this.precioVenta = precioVenta;
        this.porcentajeGanancia = calcularPorcentajeGanancia();
    }

    @Override
    public String toString()
    {
        var s = "Id: " + getId() + " \nNombre: " + getNombre() + " \nPorcentaje de Ganancia: " + porcentajeGanancia + "\nPrecio Venta: " + precioVenta + "\nMonto ganancia: " + calcularMontoGanancia() + "\n";
        s += "----COSTOS---\n";
        for (ServicioCosto c : costos)
        {
            s += c.getNombreCosto() + " " + c.getMontoAsociado() + "\n";
        }
        s += "Costo total: " + costoTotal;
        return s;
    }

    public double calcularCostoTotal()
    {
        return getCostos().stream()
                .mapToDouble(ServicioCosto::getMontoAsociado)
                .sum();
    }

    public double calcularMontoGanancia()
    {
        return precioVenta - calcularCostoTotal();
    }

    public double calcularPrecioVenta()
    {
        if (calcularCostoTotal() == 0)
        {
            return 0;
        }
        if (getPorcentajeGanancia() == 1)
        {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede ser mayor o igual al 100%");
        }
        return calcularCostoTotal() / (1 - getPorcentajeGanancia());
    }

    public double calcularPorcentajeGanancia()
    {
        var costo = calcularCostoTotal();
        var ganancia = precioVenta - costo;
        return (ganancia / precioVenta);
    }

    public double getGanancia()
    {
        if (calcularPrecioVenta() == 0)
        {
            return 0;
        }
        return calcularPrecioVenta() - calcularCostoTotal();
    }

    public void agregarCosto(ServicioCosto costo)
    {
        if (costo == null)
        {
            throw new IllegalArgumentException("El modulo de costo no es valido");
        }
        if (costos == null)
        {
            throw new IllegalArgumentException("La lista de costos del producto no es valida");
        }
        costos.add(costo);
        this.costoTotal = calcularCostoTotal();
        this.porcentajeGanancia = calcularPorcentajeGanancia();

    }

    
}
