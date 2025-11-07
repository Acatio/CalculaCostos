package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;

public class MateriaPrima extends Insumo
{


    public MateriaPrima()
    {
    }

    public MateriaPrima(int id, String nombre, double cantidad, UnidadMedida unidadDeMedida, double costo)
    {
        super(id, nombre, cantidad, unidadDeMedida,TipoInsumo.MATERIA_PRIMA);
        super.setCosto(costo);
    }

    public MateriaPrima(String nombre, double cantidad, UnidadMedida unidadDeMedida, double costo)
    {
        super(nombre, cantidad, unidadDeMedida,TipoInsumo.MATERIA_PRIMA);
        super.setCosto(costo);
    }

    @Override
    public double calcularCostoTotal()
    {
        return super.getCosto();
    }

}
