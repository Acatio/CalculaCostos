/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dto;

/**
 *
 * @author jose
 */
public class EmpleadoDTO
{

    private int id;
    private String nombre;
    private String apellido;
    private double salarioMensual;

    public EmpleadoDTO()
    {
    }

    public EmpleadoDTO(String nombre, String apellido, double salarioMensual)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.salarioMensual = salarioMensual;
    }

    public EmpleadoDTO(int id, String nombre, String apellido, double salarioMensual)
    {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.salarioMensual = salarioMensual;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }

    public double getSalarioMensual()
    {
        return salarioMensual;
    }

    public void setSalarioMensual(double salarioMensual)
    {
        this.salarioMensual = salarioMensual;
    }

    @Override
    public String toString()
    {
        return "EmpleadoDTO{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", salarioMensual=" + salarioMensual + '}';
    }

}
