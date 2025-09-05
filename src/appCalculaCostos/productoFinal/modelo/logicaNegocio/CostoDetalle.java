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
public class CostoDetalle
{

    private TipoCosto tipoCosto;
    private ICosteable costo;

    public CostoDetalle()
    {
    }

    public CostoDetalle(TipoCosto tipoCosto, ICosteable costo)
    {
        this.tipoCosto = tipoCosto;
        this.costo = costo;
    }

    public double getCostoTotal()
    {
        return costo.getCosto();
    }

    public TipoCosto getTipoCosto()
    {
        return tipoCosto;
    }

    public void setTipoCosto(TipoCosto tipoCosto)
    {
        this.tipoCosto = tipoCosto;
    }

    public ICosteable getCosto()
    {
        return costo;
    }

    public void setCosto(ICosteable costo)
    {
        this.costo = costo;
    }

}
