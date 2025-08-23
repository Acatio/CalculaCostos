/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto;

import java.util.ArrayList;
import java.util.List;
import modelo.interfacesLogicas.Costeable;

/**
 *
 * @author jose
 */
public class Producto
{

    private int id;
    private String nombre;
    private List<CostoDetalle> costos;
    private double porcentajeGanancia;

    public Producto()
    {
    }

    public Producto(int id, String nombre, double porcentajeGanancia)
    {
        this.id = id;
        this.nombre = nombre;
        this.costos = new ArrayList<>();
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public Producto(String nombre, double porcentajeGanancia)
    {
        this.nombre = nombre;
        this.costos = new ArrayList<>();
        this.porcentajeGanancia = porcentajeGanancia;
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
    public List<CostoDetalle> getCostos()
    {
        return costos;
    }

    /**
     * @param costos the costos to set
     */
    public void setCostos(List<CostoDetalle> costos)
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
        return costos.stream()
                .mapToDouble(CostoDetalle::getCostoTotal)
                .sum();
    }

}
