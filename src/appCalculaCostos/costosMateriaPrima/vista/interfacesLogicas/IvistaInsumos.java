/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas;

import java.util.List;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;


/**
 *
 * @author jose
 */
public interface IvistaInsumos
{
    public void iniciarVista();
    public void mostrarInsumos(List<Insumo> insumos);
    public int getIDInsumoSeleccionado();
    public void mostrarMensaje(String mensaje);
    public void mostrarMensajeError(String mensajeError);
    public void setGuardarListener(Runnable callback);
    public void setEliminarListener(Runnable callback);
    public void setActualizarListener(Runnable callback);
    public void setMostrarInsumosListener(Runnable callback);
    public void actualizarVista();

}
