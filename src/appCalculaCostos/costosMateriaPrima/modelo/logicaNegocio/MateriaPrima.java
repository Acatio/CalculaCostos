package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;

public class MateriaPrima extends Insumo
{

    private double costo;

    public MateriaPrima()
    {
    }

    public MateriaPrima(int id, String nombre, double cantidad, UnidadMedida unidadDeMedida, double costo)
    {
        super(id, nombre, cantidad, unidadDeMedida,TipoInsumo.MATERIA_PRIMA);
        this.costo = costo;
    }

    public MateriaPrima(String nombre, double cantidad, UnidadMedida unidadDeMedida, double costo)
    {
        super(nombre, cantidad, unidadDeMedida,TipoInsumo.MATERIA_PRIMA);
        this.costo = costo;
    }

    @Override
    public double calcularCostoTotal()
    {
        return costo;
    }

    public double getCosto()
    {
        return costo;
    }

    public void setCosto(double costo)
    {
        this.costo = costo;
    }
    

}
