/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.CostoMateriaPrima;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import java.util.List;

/**
 *
 * @author jose
 */
public interface IVistaCostosMp
{
    public void setMostrarInsumosListener(Runnable callback);
    public void mostrarInsumos(List<Insumo> insumos);
    public void mostrarIngredientes(CostoMateriaPrima ingredientes);
    public void setAgregarIngredientesListener(Runnable callback);
    public void actualizarVista();
}
