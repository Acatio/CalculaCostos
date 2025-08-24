/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto;

/**
 *
 * @author jose
 */
public class UnidadDeMedida
{
    private int id;
    private String nombre;
    private String simbolo;

    public UnidadDeMedida()
    {
    }

    public UnidadDeMedida(int id, String nombre, String simbolo)
    {
        this.id = id;
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public UnidadDeMedida(String nombre, String simbolo)
    {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public String getSimbolo()
    {
        return simbolo;
    }

    public void setSimbolo(String simbolo)
    {
        this.simbolo = simbolo;
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

    @Override
    public String toString()
    {
        return String.valueOf(id);
    }
    
}
