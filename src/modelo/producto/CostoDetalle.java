/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto;

import modelo.interfacesLogicas.Costeable;

/**
 *
 * @author jose
 */
public class CostoDetalle
{

    private TipoCosto tipoCosto;
    private Costeable costo;

    public CostoDetalle()
    {
    }

    public CostoDetalle(TipoCosto tipoCosto, Costeable costo)
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

    public Costeable getCosto()
    {
        return costo;
    }

    public void setCosto(Costeable costo)
    {
        this.costo = costo;
    }

}
