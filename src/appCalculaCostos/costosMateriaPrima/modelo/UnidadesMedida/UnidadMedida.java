/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida;

/**
 *
 * @author jose
 */
public abstract class UnidadMedida
{

    private final String nombre;
    private final String simbolo;

    public UnidadMedida(String nombre, String simbolo)
    {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getSimbolo()
    {
        return simbolo;
    }

    public abstract double aEstandar(double cantidad);

    public abstract double desdeEstandar(double cantidadEstandar);
}
