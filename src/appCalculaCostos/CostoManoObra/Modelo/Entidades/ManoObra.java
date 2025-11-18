/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Entidades;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;

/**
 *
 * @author jose
 */
public class ManoObra
{
    private Empleado empleado;
    private float tiempoAportado;
    private double costoCalculado;

    public ManoObra()
    {
    }

    public Empleado getEmpleado()
    {
        return empleado;
    }

    public void setEmpleado(Empleado empleado)
    {
        this.empleado = empleado;
    }

    public float getTiempoAportado()
    {
        return tiempoAportado;
    }

    public void setTiempoAportado(float tiempoAportado)
    {
        this.tiempoAportado = tiempoAportado;
    }

    public double getCostoCalculado()
    {
        return costoCalculado;
    }

    public void setCostoCalculado(double costoCalculado)
    {
        this.costoCalculado = costoCalculado;
    }


    
}
