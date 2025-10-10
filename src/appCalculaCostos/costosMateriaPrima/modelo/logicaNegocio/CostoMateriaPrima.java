/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import java.util.HashMap;
import java.util.Map;
import conexion.Exepciones.PersistenciaException;
import java.util.List;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import java.sql.Connection;

/**
 *
 * @author jose
 */
public class CostoMateriaPrima implements ServicioCosto
{
    
    private final Map<Insumo, Double> insumosUsados = new HashMap<>();
    
    public void agregarInsumo(Insumo insumo, double cantidad)
    {
        insumosUsados.put(insumo, cantidad);
    }


    public void mostrarInsumos()
    {
        for (Map.Entry<Insumo, Double> entry : insumosUsados.entrySet())
        {
            System.out.println(entry.getValue() + " " + entry.getKey().getUnidadDeMedida()
                    + " de " + entry.getKey().getNombre());
        }
    }

    public Map<Insumo, Double> getInsumosUsados()
    {
        return insumosUsados;
    }


}
