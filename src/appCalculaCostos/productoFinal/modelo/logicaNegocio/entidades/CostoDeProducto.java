/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ICosteable;

/**
 *
 * @author jose
 */
public class CostoDeProducto
{

    private int id;
    private String nombre;
    private ICosteable costeable;

    public CostoDeProducto(String nombre, ICosteable costeable)
    {
        this.nombre = nombre;
        this.costeable = costeable;
    }

    public CostoDeProducto(int id, String nombre, ICosteable costeable)
    {
        this.id = id;
        this.nombre = nombre;
        this.costeable = costeable;
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

    public ICosteable getCosteable()
    {
        return costeable;
    }

    public void setCosteable(ICosteable costeable)
    {
        this.costeable = costeable;
    }

    public double getCostoTotal()
    {
        return costeable.getMonto();
    }

}
