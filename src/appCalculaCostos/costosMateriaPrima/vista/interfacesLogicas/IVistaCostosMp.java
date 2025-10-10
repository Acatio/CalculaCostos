/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.CostoMateriaPrima;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jose
 */
public interface IVistaCostosMp
{
    public void setMostrarInsumosListener(Runnable callback);
    public void setAgregarIngredientesListener(Runnable callback);
    public void setMostrarIngredientesListener(Runnable callback);
    public void mostrarInsumos(List<Insumo> insumos);
    public void mostrarIngredientes(CostoMateriaPrima ingredientes);
    public void actualizarVista();
    public void mostrarMensajeError(String mensajeError);
    public void mostrarMensajeExito(String mensajeExito);
    public void mostrarMensaje(String mensaje);
    public Optional<Insumo> getInsumoSeleccionado();
    public int getIDInsumoSeleccionado();
    public double getCantidadInsumo();
}
