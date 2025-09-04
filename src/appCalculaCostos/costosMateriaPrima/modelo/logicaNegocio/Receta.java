package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import java.util.HashMap;
import java.util.Map;

public class Receta extends Insumo
{

    private Map<Insumo, Double> ingredientes = new HashMap<>();

    public Receta()
    {
        ingredientes = new HashMap<>();
    }

    public Receta(int id, String nombre, double masaDrenada, UnidadDeMedida unidadDeMedida)
    {
        super(id, nombre, masaDrenada, unidadDeMedida, TipoInsumo.RECETA);
        ingredientes = new HashMap<>();
    }

    public Receta(String nombre, double masaDrenada, UnidadDeMedida unidadDeMedida)
    {
        super(nombre, masaDrenada, unidadDeMedida, TipoInsumo.RECETA);
        ingredientes = new HashMap<>();
    }

    @Override
    public double calcularCostoTotal()
    {
        double total = 0;
        for (Map.Entry<Insumo, Double> entry : ingredientes.entrySet())
        {
            Insumo prod = entry.getKey();
            double cantidad = entry.getValue();
            total += prod.getCostoPorUnidad() * cantidad;
        }
        return total;
    }

    public void agregarIngrediente(Insumo producto, double cantidadUsada)
    {
        ingredientes.put(producto, cantidadUsada);
    }

    public void mostrarReceta()
    {
        System.out.println("++++ RECETA: " + super.getNombre() + " ++++");
        for (Map.Entry<Insumo, Double> entry : ingredientes.entrySet())
        {
            Insumo prod = entry.getKey();
            System.out.println("- " + entry.getValue() + " " + prod.getUnidadDeMedida() + ": " + prod.getNombre());
        }
    }
    

}
