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
    private double salarioSemanal;
    private float horasSemana;

    public EmpleadoDTO()
    {
    }

    public EmpleadoDTO(int id, String nombre, String apellido, double salarioMensual, float horasSemana)
    {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.salarioSemanal = salarioMensual;
        this.horasSemana = horasSemana;
    }

    public EmpleadoDTO(String nombre, String apellido, double salarioMensual, float horasSemana)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.salarioSemanal = salarioMensual;
        this.horasSemana = horasSemana;
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

    public double getSalarioSemanal()
    {
        return salarioSemanal;
    }

    public void setSalarioSemanal(double salarioMensual)
    {
        this.salarioSemanal = salarioMensual;
    }

    public float getHorasSemana()
    {
        return horasSemana;
    }

    public void setHorasSemana(float horasSemana)
    {
        this.horasSemana = horasSemana;
    }

    
    @Override
    public String toString()
    {
        return "EmpleadoDTO{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", salarioMensual=" + salarioSemanal + '}';
    }

}
