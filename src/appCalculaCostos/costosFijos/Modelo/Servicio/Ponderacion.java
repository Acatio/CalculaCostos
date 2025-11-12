/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.Servicio;

/**
 *
 * @author jose
 */
public class Ponderacion
{

    private int idProducto;
    private float tamanio; // 1 a 5
    private float tiempoPreparacion; // 1 a 5
    private float cantidadRecursosUsados; // 1 a 5

    public Ponderacion()
    {
    }

    
    public Ponderacion(int idProducto, float tamanio, float tiempoPreparacion, float cantidadRecursosUsados)
    {
        this.idProducto = idProducto;
        this.tamanio = tamanio;
        this.tiempoPreparacion = tiempoPreparacion;
        this.cantidadRecursosUsados = cantidadRecursosUsados;
    }

    public double calcularPonderacion()
    {
        double pesoTamanio = 0.3;
        double pesoTiempo = 0.5;
        double pesoRecursos = 0.2;

        return (tamanio * pesoTamanio)
                + (tiempoPreparacion * pesoTiempo)
                + (cantidadRecursosUsados * pesoRecursos);
    }

    public int getIdProducto()
    {
        return idProducto;
    }

    public void setIdProducto(int idProducto)
    {
        this.idProducto = idProducto;
    }

    public float getTamanio()
    {
        return tamanio;
    }

    public void setTamanio(float tamanio)
    {
        this.tamanio = tamanio;
    }

    public float getTiempoPreparacion()
    {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(float tiempoPreparacion)
    {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public float getCantidadRecursosUsados()
    {
        return cantidadRecursosUsados;
    }

    public void setCantidadRecursosUsados(float cantidadRecursosUsados)
    {
        this.cantidadRecursosUsados = cantidadRecursosUsados;
    }
    
}
