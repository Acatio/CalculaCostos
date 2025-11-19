/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dto;

/**
 *
 * @author jose
 */
public class ManoObraDTO
{

    private int idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private float tiempoAportado;

    public ManoObraDTO()
    {
    }

    public ManoObraDTO(int idEmpleado, String nombreEmpleado, String apellidoEmpleado, float tiempoAportado)
    {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.tiempoAportado = tiempoAportado;
    }

    public int getIdEmpleado()
    {
        return idEmpleado;
    }

    public String getNombreEmpleado()
    {
        return nombreEmpleado;
    }

    public String getApellidoEmpleado()
    {
        return apellidoEmpleado;
    }

    public float getTiempoAportado()
    {
        return tiempoAportado;
    }

    public void setIdEmpleado(int idEmpleado)
    {
        this.idEmpleado = idEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado)
    {
        this.nombreEmpleado = nombreEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado)
    {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public void setTiempoAportado(float tiempoAportado)
    {
        this.tiempoAportado = tiempoAportado;
    }

    @Override
    public String toString()
    {
        return "ManoObraDTO{" + "idEmpleado=" + idEmpleado + ", nombreEmpleado=" + nombreEmpleado + ", apellidoEmpleado=" + apellidoEmpleado + ", tiempoAportado=" + tiempoAportado + '}';
    }

}
