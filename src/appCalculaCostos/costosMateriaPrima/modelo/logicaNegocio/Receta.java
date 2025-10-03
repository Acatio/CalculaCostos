package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import java.util.HashMap;
import java.util.Map;

public class Receta extends Insumo
{

    private Map<Insumo, CantidadInsumo> ingredientes = new HashMap<>();

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
        for (Map.Entry<Insumo, CantidadInsumo> entry : ingredientes.entrySet())
        {
            Insumo prod = entry.getKey();
            double cantidad = entry.getValue().getEnCantidadEstandar();
            total += prod.getCostoPorUnidad() * cantidad;
        }
        return total;
    }

    public void agregarIngrediente(Insumo producto, CantidadInsumo cantidad)
    {
        ingredientes.put(producto, cantidad);
    }

    public void mostrarReceta()
    {
        System.out.println("++++ RECETA: " + super.getNombre() + " ++++");
        for (Map.Entry<Insumo, CantidadInsumo> entry : ingredientes.entrySet())
        {
            Insumo prod = entry.getKey();
            System.out.println("- " + entry.getValue().cantidad + " " + entry.getValue().unidadMedida + ": " + prod.getNombre());
        }
    }
    

}
