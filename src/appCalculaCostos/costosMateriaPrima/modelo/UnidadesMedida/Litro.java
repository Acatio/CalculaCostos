/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida;

import appCalculaCostos.costosMateriaPrima.modelo.excepciones.NoPosibleConversion;

/**
 *
 * @author jose
 */
public class Litro extends UnidadMedida
{

    public Litro()
    {
        super("Litro", "L");
    }

    @Override
    public double aOtraUnidad(UnidadMedida unidadDestino, double cantidad) throws NoPosibleConversion
    {
        if (unidadDestino == null)
        {
            throw new IllegalArgumentException("La unidad de medida no es valida");
        }
        if (cantidad <= 0)
        {
            throw new IllegalArgumentException("La cantidad no es valida");
        }
        if (unidadDestino instanceof Litro)
        {
            return cantidad;
        }
        if (unidadDestino instanceof Mililitro)
        {
            return cantidad *1000;
        }

        throw new NoPosibleConversion("No es posible convertir de Litros a: " + unidadDestino.getNombre());
    }

}
