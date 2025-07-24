package paquete1;

import java.util.HashMap;
import java.util.Map;

public class Receta implements Producto
{

    private String nombre;
    private String unidadDeMedida;
    private double cantidadProducida;
    private Map<Producto, Double> ingredientes = new HashMap<>();

    public Receta(String nombre, double cantidadProducida, String unidadDeMedida)
    {
        this.nombre = nombre;
        this.cantidadProducida = cantidadProducida;
        this.unidadDeMedida = unidadDeMedida;
    }

    public void agregarIngrediente(Producto producto, double cantidadUsada)
    {
        ingredientes.put(producto, cantidadUsada);
    }

    public double calcularCostoTotal()
    {
        double total = 0;
        for (Map.Entry<Producto, Double> entry : ingredientes.entrySet())
        {
            Producto prod = entry.getKey();
            double cantidad = entry.getValue();
            total += prod.calcularCostoPorUnidad() * cantidad;
        }
        return total;
    }

    @Override
    public double calcularCostoPorUnidad()
    {
        if (cantidadProducida <= 0)
        {
            throw new IllegalArgumentException("La cantidad producida debe ser mayor a 0");
        }
        return calcularCostoTotal() / cantidadProducida;
    }

    @Override
    public String getNombre()
    {
        return nombre;
    }

    @Override
    public String getUnidadDeMedida()
    {
        return unidadDeMedida;
    }

    @Override
    public int getCantidadDisponible()
    {
        return (int) cantidadProducida;
    }

    public void mostrarReceta()
    {
        System.out.println("++++ RECETA: " + nombre + " ++++");
        for (Map.Entry<Producto, Double> entry : ingredientes.entrySet())
        {
            Producto prod = entry.getKey();
            System.out.println("- " + entry.getValue() + " " + prod.getUnidadDeMedida() + ": " + prod.getNombre());
        }
    }
}
