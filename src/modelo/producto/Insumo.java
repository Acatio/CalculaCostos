/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.producto;

public abstract class Insumo
{

    private int id;
    private String nombre;
    private double cantidad;
    private UnidadDeMedida unidadDeMedida;

    public Insumo()
    {
    }

    public Insumo(int id, String nombre, double cantidad, UnidadDeMedida unidadDeMedida)
    {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidadDeMedida = unidadDeMedida;
    }

    public Insumo(String nombre, double cantidad, UnidadDeMedida unidadDeMedida)
    {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidadDeMedida = unidadDeMedida;
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
     * @return the masaDrenada
     */
    public double getCantidad()
    {
        return cantidad;
    }

    /**
     * @param cantidad the masaDrenada to set
     */
    public void setCantidad(double cantidad)
    {
        this.cantidad = cantidad;
    }

    /**
     * @return the unidadDeMedida
     */
    public UnidadDeMedida getUnidadDeMedida()
    {
        return unidadDeMedida;
    }

    /**
     * @param unidadDeMedida the unidadDeMedida to set
     */
    public void setUnidadDeMedida(UnidadDeMedida unidadDeMedida)
    {
        this.unidadDeMedida = unidadDeMedida;
    }

    public double getCostoPorUnidad()
    {
        return calcularCostoTotal() / cantidad;
    }
    public abstract double calcularCostoTotal();
}
