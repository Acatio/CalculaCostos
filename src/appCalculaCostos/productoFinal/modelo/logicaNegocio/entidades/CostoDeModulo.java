/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IDetalleCosto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jose
 */
public abstract class CostoDeModulo
{

    private List<IDetalleCosto> detalles;

    // Constructor que fuerza la inicialización y recibe los detalles
    public CostoDeModulo(List<IDetalleCosto> detalles)
    {
        // Usa una copia defensiva para asegurar inmutabilidad externa
        this.detalles = Collections.unmodifiableList(new ArrayList<>(detalles));
    }

    public void setDetalles(List<IDetalleCosto> nuevosDetalles)
    {

        if (nuevosDetalles == null)
        {
            throw new IllegalArgumentException("La lista de detalles no puede ser null.");
        }
        // Asignar una copia defensiva para proteger de manipulaciones externas
        this.detalles = new ArrayList<>(nuevosDetalles);
    }

    public List<IDetalleCosto> getDetalles()
    {
        return detalles;
    }

    public final double calcularMontoBase()
    {
        return this.detalles.stream().mapToDouble(IDetalleCosto::getMonto).sum();
    }

    public abstract double calcularMontoTotal();
}
