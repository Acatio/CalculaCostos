/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Entidades;

/**
 *
 * @author jose
 */
public class ManoObra
{

    private Empleado empleado;
    private float tiempoAportado;     // En horas (puede ser 0.5, 1.25, etc.)
    private double costoCalculado;    // costo = tiempo * costoHora del empleado

    public ManoObra()
    {
    }

    public ManoObra(Empleado empleado, float tiempoAportado, double costoCalculado)
    {
        this.empleado = empleado;
        this.tiempoAportado = tiempoAportado;
        this.costoCalculado = costoCalculado;
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

    @Override
    public String toString()
    {
        return "ManoObra{"
                + "empleado=" + empleado
                + ", tiempoAportado=" + tiempoAportado
                + ", costoCalculado=" + costoCalculado
                + '}';
    }
}
