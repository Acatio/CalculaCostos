/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Entidades;

/**
 *
 * @author jose
 */
public class Empleado
{

    private int id;
    private String nombre;
    private String apellido;
    private double salarioSemanal;
    private float horasSemana;

    public Empleado()
    {
    }

    public Empleado(int id, String nombre, String apellido, double salarioMensual, float horasSemana)
    {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.salarioSemanal = salarioMensual;
        this.horasSemana = horasSemana;
    }

    public Empleado(String nombre, String apellido, double salarioMensual, float horasSemana)
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
        if (nombre == null || nombre.isBlank())
        {
            throw new IllegalArgumentException("El nombre no es valido");
        }
        this.nombre = nombre;
    }

    public double getSalarioSemanal()
    {
        return salarioSemanal;
    }

    public void setSalarioSemanal(double importeMensual)
    {
        if (importeMensual < 0)
        {
            throw new IllegalArgumentException("importe mensual  no pude ser menor a cero");
        }
        this.salarioSemanal = importeMensual;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido(String apellido)
    {
        if (apellido == null || apellido.isBlank())
        {
            throw new IllegalArgumentException("El nombre no es valido");
        }
        this.apellido = apellido;
    }

    public float getHorasSemana()
    {
        return horasSemana;
    }

    public void setHorasSemana(float horasSemana)
    {
        this.horasSemana = horasSemana;
    }

    public double getCostoHora()
    {
        if (horasSemana<=0)
        {
            throw new IllegalStateException("No se asignaron horas semana validas");
        }
        return salarioSemanal/horasSemana;
    }
    

}
