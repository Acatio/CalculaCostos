/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dto;

/**
 *
 * @author jose
 */
public class MostrarManoObraDTO
{

    private int idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private float tiempoInvertido;
    private double costoAsociado;

    public MostrarManoObraDTO()
    {
    }

    public MostrarManoObraDTO(int idEmpleado, String nombreEmpleado, String apellidoEmpleado, float tiempoInvertido, double costoAsociado)
    {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.tiempoInvertido = tiempoInvertido;
        this.costoAsociado = costoAsociado;
    }

    public int getIdEmpleado()
    {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado)
    {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado()
    {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado)
    {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado()
    {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado)
    {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public float getTiempoInvertido()
    {
        return tiempoInvertido;
    }

    public void setTiempoInvertido(float tiempoInvertido)
    {
        this.tiempoInvertido = tiempoInvertido;
    }

    public double getCostoAsociado()
    {
        return costoAsociado;
    }

    public void setCostoAsociado(double costoAsociado)
    {
        this.costoAsociado = costoAsociado;
    }
    
    
}
