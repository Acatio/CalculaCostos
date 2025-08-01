package modelo;

public class MateriaPrima extends Producto
{

    double costo;

    public MateriaPrima()
    {
    }

    public MateriaPrima(int id, String nombre, double cantidad, String unidadDeMedida, double costo)
    {
        super(id, nombre, cantidad, unidadDeMedida);
        this.costo = costo;
    }

    public MateriaPrima(String nombre, double cantidad, String unidadDeMedida, double costo)
    {
        super(nombre, cantidad, unidadDeMedida);
        this.costo = costo;
    }

    @Override
    public double calcularCostoTotal()
    {
        return costo;
    }

}
