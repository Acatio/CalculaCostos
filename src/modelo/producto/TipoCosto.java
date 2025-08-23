/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto;

/**
 *
 * @author jose
 */
public class TipoCosto
{

    private int id;
    private String nombre;

    public TipoCosto()
    {
    }
    public TipoCosto(String nombre)
    {

        this.nombre = nombre;
    }

    public TipoCosto(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
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

}
