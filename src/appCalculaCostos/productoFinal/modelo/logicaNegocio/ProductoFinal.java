/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio;

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
    private List<CostoDeProducto> costos;
    private double porcentajeGanancia;
    private double costoTotal;

    public ProductoFinal()
    {
    }

    public ProductoFinal(int id, String nombre, double porcentajeGanancia, double costoTotal)
    {
        this.id = id;
        this.nombre = nombre;
        this.costos = new ArrayList<>();
        this.porcentajeGanancia = porcentajeGanancia;
        this.costoTotal = costoTotal;
    }

    public ProductoFinal(String nombre, double porcentajeGanancia, double costoTotal)
    {
        this.nombre = nombre;
        this.costos = new ArrayList<>();
        this.porcentajeGanancia = porcentajeGanancia;
        this.costoTotal = costoTotal;
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
    public List<CostoDeProducto> getCostos()
    {
        return costos;
    }

    /**
     * @param costos the costos to set
     */
    public void setCostos(List<CostoDeProducto> costos)
    {
        this.costos = costos;
    }

    /**
     * @return the porcentajeGanancia
     */
    public double getPorcentajeGanancia()
    {
        return porcentajeGanancia;
    }

    /**
     * @param porcentajeGanancia the porcentajeGanancia to set
     */
    public void setPorcentajeGanancia(double porcentajeGanancia)
    {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public double calcularCostoTotal()
    {
        return getCostos().stream()
                .mapToDouble(CostoDeProducto::getCostoTotal)
                .sum();
    }

    @Override
    public String toString()
    {
        var s = "Id: " + getId() + " Nombre: " + getNombre() + " Porcentaje de Ganancia: " + getPorcentajeGanancia() + " Costo total: "+costoTotal+"\n";
        for (CostoDeProducto c : costos)
        {
            s += c.getTipoCosto().getNombre() + " " + c.getCostoTotal() + "\n";
        }
        return s;
    }

    /**
     * @return the costoTotal
     */
    public double getCostoTotal()
    {
        return costoTotal;
    }

    /**
     * @param costoTotal the costoTotal to set
     */
    public void setCostoTotal(double costoTotal)
    {
        this.costoTotal = costoTotal;
    }

}
