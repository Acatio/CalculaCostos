/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ICosteable;

/**
 *
 * @author jose
 */
public class CostoDeProducto
{
    private TipoCosto tipoCosto;
    private ICosteable costeable;

    public CostoDeProducto()
    {
    }

    public CostoDeProducto(TipoCosto tipoCosto, ICosteable costo)
    {
        this.tipoCosto = tipoCosto;
        this.costeable = costo;
    }

    public double getCostoTotal()
    {
        return costeable.getMonto();
    }

    public TipoCosto getTipoCosto()
    {
        return tipoCosto;
    }

    public void setTipoCosto(TipoCosto tipoCosto)
    {
        this.tipoCosto = tipoCosto;
    }

    public ICosteable getCosteable()
    {
        return costeable;
    }

    public void setCosteable(ICosteable costo)
    {
        this.costeable = costo;
    }

}
