/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.NoPosibleConversion;

/**
 *
 * @author jose
 */
public class DetalleReceta
{

    private final Insumo insumo;
    private final double cantidad;
    private final UnidadMedida unidadMedida;

    public DetalleReceta(Insumo insumo, double cantidad, UnidadMedida unidadMedida)
    {
        this.insumo = insumo;
        this.cantidad = cantidad;
        this.unidadMedida = unidadMedida;
    }

    public Insumo getInsumo()
    {
        return insumo;
    }

    public double getCantidad()
    {
        return cantidad;
    }

    public UnidadMedida getUnidadMedida()
    {
        return unidadMedida;
    }
    public double getMonto() throws NoPosibleConversion
    {
        var cantidadEnUnidadDeMedidaDelInsumo = unidadMedida.aOtraUnidad(insumo.getUnidadDeMedida(), cantidad);

        return insumo.getCostoPorUnidad()*cantidadEnUnidadDeMedidaDelInsumo;
    }
}
