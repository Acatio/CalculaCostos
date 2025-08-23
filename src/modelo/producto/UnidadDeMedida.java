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
    int id;
    String nombre;
    String simbolo;

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
    
}
