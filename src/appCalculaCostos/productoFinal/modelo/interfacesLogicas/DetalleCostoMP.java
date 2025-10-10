/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;

/**
 *
 * @author jose
 */
public class DetalleCostoMP implements IDetalleCosto
{

    public String i;
    public double cantidad;
    public DetalleCostoMP(String i, double cantidad)
    {
        this.i = i;
        this.cantidad = cantidad;
    }


    @Override
    public double getMonto()
    {
        return cantidad * 10;
    }
    
}
