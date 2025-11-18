/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dto;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;

/**
 *
 * @author jose
 */
public class EmpleadoProductoTiempoDTO
{
    private int idEmpleado;
    private int idProducto;
    private float tiempoAportado;

    public EmpleadoProductoTiempoDTO()
    {
    }

    public int getIdEmpleado()
    {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado)
    {
        this.idEmpleado = idEmpleado;
    }

    public int getIdProducto()
    {
        return idProducto;
    }

    public void setIdProducto(int idProducto)
    {
        this.idProducto = idProducto;
    }

    public float getTiempoAportado()
    {
        return tiempoAportado;
    }

    public void setTiempoAportado(float tiempoAportado)
    {
        this.tiempoAportado = tiempoAportado;
    }
    
}
