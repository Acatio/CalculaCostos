/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida;


/**
 *
 * @author jose
 */
public class Kilo extends UnidadMedida
{

    public Kilo()
    {
        super("Kilogramo", "kg");
    }

    @Override
    public double aEstandar(double cantidad)
    {
        return cantidad * 1000;
    }

    @Override
    public double desdeEstandar(double cantidadEstandar)
    {
        return cantidadEstandar / 1000;
    }
}
