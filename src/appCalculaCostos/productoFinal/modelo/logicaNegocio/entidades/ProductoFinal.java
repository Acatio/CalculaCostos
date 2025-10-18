/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades;

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
    private double cantidadVendida;//TODO VERIFICAR SI ES MEJOR UTILIZAR O AGREGAR CANTIDAD PRODUCIDA x unidad de tiempo
    private final List<CostoDeModulo> costos;

    public ProductoFinal(int id, String nombre, double cantidadVendida)
    {
        this.id = id;
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
        this.costos = new ArrayList<>();
    }

    public ProductoFinal(String nombre, double cantidadVendida)
    {
        this.nombre = nombre;
        this.cantidadVendida = cantidadVendida;
        this.costos = new ArrayList<>();
    }

    public ProductoFinal(int id, String nombre, double cantidadVendida, double porcentajeGanancia, double precioVenta, double costoTotal)
    {
        this.id = id;
        this.nombre = nombre;
        this.porcentajeGanancia = porcentajeGanancia;
        this.precioVenta = precioVenta;
        this.costoTotal = costoTotal;
        this.cantidadVendida = cantidadVendida;
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
    public List<CostoDeModulo> getCostos()
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
        if (porcentajeGanancia < 0 || porcentajeGanancia > 1)
        {
            throw new IllegalArgumentException("El porcentaje de ganancia no puede ser menor a cero ni mayor a uno");
        } else
        {
            this.porcentajeGanancia = porcentajeGanancia;
        }

    }

    public double calcularPrecioVenta()
    {

        if (costoTotal <= 0)
        {
            return 0.0;
        }

        // Regla de negocio: El margen de ganancia no puede ser del 100% o más
        if (porcentajeGanancia >= 1.0)
        {
            // En lugar de una excepción de IllegalArgumentException (que sugiere un error de entrada)
            // usamos IllegalStateException, ya que el objeto está en un estado no permitido para el cálculo.
            throw new IllegalStateException("El porcentaje de ganancia (" + (porcentajeGanancia * 100) + "%) no permite calcular el precio de venta (margen sobre venta debe ser < 100%).");
        }

        // Fórmula correcta: Costo Total / (1 - Margen de Ganancia)
        return costoTotal / (1.0 - porcentajeGanancia);
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
    }

    @Override
    public String toString()
    {
        var s = "Id: " + getId() + " \nNombre: " + getNombre() + " \nPorcentaje de Ganancia: " + porcentajeGanancia + "\nPrecio Venta: " + precioVenta + "\nMonto ganancia: " + calcularMontoGanancia() + "\n";
        s += "----COSTOS---\n";
        for (CostoDeModulo c : costos)
        {
            s += c.getClass().getName() + " " + c.calcularMontoTotal() + "\n";
        }
        s += "Costo total: " + costoTotal;
        return s;
    }

    public double calcularCostoTotal()
    {
        return getCostos().stream()
                .mapToDouble(CostoDeModulo::calcularMontoTotal)
                .sum();
    }

    public double calcularMontoGanancia()
    {

        return precioVenta - calcularCostoTotal();
    }

    public double calcularPorcentajeGanancia()
    {
        if (precioVenta == 0)
        {
            return 0;
        }
        var ganancia = precioVenta - costoTotal;
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

    public void agregarCosto(CostoDeModulo costo)
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

    }

    public double getCantidadVendidaMes()
    {
        return cantidadVendida;
    }

    public double getMontoVendido()
    {
        return cantidadVendida * precioVenta;
    }

    public void setCostoTotal(double costoTotal)
    {
        if (costoTotal < 0)
        {
            throw new IllegalArgumentException("El costo total no puede ser menor a cero!!");
        }
        this.costoTotal = costoTotal;
    }

    public void setCantidadVendida(double cantidadVendida)
    {     if (cantidadVendida < 0)
        {
            throw new IllegalArgumentException("La cantidad vendida no puede ser menor a cero!!");
        }
        this.cantidadVendida = cantidadVendida;
    }
}
