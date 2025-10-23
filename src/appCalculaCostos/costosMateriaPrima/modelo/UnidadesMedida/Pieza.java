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
public class Pieza extends UnidadMedida
{

    public Pieza()
    {
        super("Pieza", "pz");
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
        if (unidadDestino instanceof Pieza)
        {
            return cantidad;
        }
        throw new NoPosibleConversion("No es posible convertir de piezas a: " + unidadDestino.getNombre());
    }

}
