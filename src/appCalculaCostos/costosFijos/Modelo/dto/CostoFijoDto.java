/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.dto;

/**
 *
 * @author jose
 */
public class CostoFijoDto
{

    private int id;
    private String nombre;
    private double importeMensual;
    private double porcentajeUsado;

    public CostoFijoDto()
    {
    }

    public CostoFijoDto(int id, String nombre, double importeMensual, double porcentajeUsado)
    {
        this.id = id;
        this.nombre = nombre;
        this.importeMensual = importeMensual;
        this.porcentajeUsado = porcentajeUsado;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public double getImporteMensual()
    {
        return importeMensual;
    }

    public void setImporteMensual(double importeMensual)
    {
        this.importeMensual = importeMensual;
    }

    public double getPorcentajeUsado()
    {
        return porcentajeUsado;
    }

    public void setPorcentajeUsado(double porcentajeUsado)
    {
        this.porcentajeUsado = porcentajeUsado;
    }

}
