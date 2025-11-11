/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.Entidades;

/**
 *
 * @author jose
 */
public class CostoFijo
{

    private int id;
    private String nombre;
    private double importeMensual;
    private double porcentajeUsado;

    public CostoFijo()
    {
    }

    public CostoFijo(String nombre, double importeMensual, double porcentajeUsado)
    {
        this.nombre = nombre;
        this.importeMensual = importeMensual;
        this.porcentajeUsado = porcentajeUsado;
    }

    public CostoFijo(int id, String nombre, double importeMensual, double porcentajeUsado)
    {
        this.id = id;
        this.nombre = nombre;
        this.importeMensual = importeMensual;
        this.porcentajeUsado = porcentajeUsado;
    }

    
    public double getPorcentajeUsado()
    {
        return porcentajeUsado;
    }

    public void setPorcentajeUsado(double porcentajeUsado)
    {
        this.porcentajeUsado = porcentajeUsado;
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

    public double getImporteMensual()
    {
        return importeMensual;
    }

    public void setImporteMensual(double importeMensual)
    {
        if (importeMensual < 0)
        {
            throw new IllegalArgumentException("importe mensual  no pude ser menor a cero");
        }
        this.importeMensual = importeMensual;
    }

    public double getMontoRealUsado()
    {
        return importeMensual * porcentajeUsado;
    }
}
