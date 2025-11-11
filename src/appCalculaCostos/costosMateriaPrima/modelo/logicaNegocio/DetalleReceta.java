/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.NoPosibleConversion;
import appCalculaCostos.productoFinal.modelo.exepciones.NoPosibleCalcularMonto;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IDetalleCosto;

/**
 *
 * @author jose
 */
public class DetalleReceta implements IDetalleCosto
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

    /**
     *
     * @return  monto del detalle
     * @throws appCalculaCostos.productoFinal.modelo.exepciones.NoPosibleCalcularMonto
     */
    @Override
    public double getMonto() throws NoPosibleCalcularMonto
    {
        try
        {
            var cantidadEnUnidadDeMedidaDelInsumo = unidadMedida.aOtraUnidad(insumo.getUnidadDeMedida(), cantidad);
            if (insumo instanceof Receta r)
            {
                return (r.getCosto()/r.getCantidad())*cantidadEnUnidadDeMedidaDelInsumo;
            }
            return insumo.getCostoPorUnidad() * cantidadEnUnidadDeMedidaDelInsumo;
        } catch (NoPosibleConversion ex)
        {
            throw new NoPosibleCalcularMonto( ex.getMessage());
        }
    }
}
