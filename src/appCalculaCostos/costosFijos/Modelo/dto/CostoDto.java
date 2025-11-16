/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.dto;

/**
 *
 * @author jose
 */
public class CostoDto
{

    private String tipoCosto;
    private double montoTotal;

    public CostoDto(String tipoCosto, double montoTotal)
    {
        this.tipoCosto = tipoCosto;
        this.montoTotal = montoTotal;
    }

    public double getMontoTotal()
    {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal)
    {
        this.montoTotal = montoTotal;
    }

    public String getTipoCosto()
    {
        return tipoCosto;
    }

    public void setTipoCosto(String tipoCosto)
    {
        this.tipoCosto = tipoCosto;
    }

}
