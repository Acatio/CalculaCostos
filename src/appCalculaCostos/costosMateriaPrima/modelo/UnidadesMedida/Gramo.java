/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida;



/**
 *
 * @author jose
 */
public class Gramo extends UnidadMedida
{

    public Gramo()
    {
        super("Gramo", "g");
    }

    @Override
    public double aEstandar(double cantidad)
    {
        return cantidad;
    }

    @Override
    public double desdeEstandar(double cantidadEstandar)
    {
        return cantidadEstandar;
    }
}
