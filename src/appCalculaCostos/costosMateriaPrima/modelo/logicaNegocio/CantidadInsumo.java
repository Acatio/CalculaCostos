/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;

/**
 *
 * @author jose
 */
public class CantidadInsumo
{

    public double cantidad;
    public UnidadMedida unidadMedida;

    public double getEnCantidadEstandar()
    {
        return unidadMedida.aEstandar(cantidad);
    }

}
