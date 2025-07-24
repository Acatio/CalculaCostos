package paquete1;

public class MateriaPrima implements Producto
{

    private String nombre;
    private int cantidadDisponible;
    private double costoTotal;
    private String unidadDeMedida;

    public MateriaPrima(String nombre, int cantidadDisponible, double costoTotal, String unidadDeMedida)
    {
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
        this.costoTotal = costoTotal;
        this.unidadDeMedida = unidadDeMedida;
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
        return cantidadDisponible;
    }

    @Override
    public double calcularCostoPorUnidad()
    {
        if (cantidadDisponible <= 0)
        {
            throw new IllegalArgumentException("La cantidad disponible debe ser mayor a 0");
        }
        return costoTotal / cantidadDisponible;
    }
}
