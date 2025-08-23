/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.producto;

import java.util.HashMap;
import java.util.Map;
import modelo.interfacesLogicas.Costeable;

/**
 *
 * @author jose
 */
public class CostoMateriaPrima implements Costeable
{

    private Map<Insumo, Double> insumosUsados = new HashMap<>();
    
    public void agregarInsumo(Insumo insumo, double cantidad)
    {
        insumosUsados.put(insumo, cantidad);
    }

    @Override
    public double getCosto()
    {
        double total = 0;
        for (Map.Entry<Insumo, Double> entry : insumosUsados.entrySet())
        {
            Insumo insumo = entry.getKey();
            double cantidad = entry.getValue();
            total += insumo.calcularCostoTotal() * cantidad; // Receta o materia prima
        }
        return total;
    }

    public void mostrarInsumos()
    {
        for (Map.Entry<Insumo, Double> entry : insumosUsados.entrySet())
        {
            System.out.println(entry.getValue() + " " + entry.getKey().getUnidadDeMedida()
                    + " de " + entry.getKey().getNombre());
        }
    }
}
