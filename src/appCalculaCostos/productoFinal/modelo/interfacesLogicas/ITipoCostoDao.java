/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import appCalculaCostos.productoFinal.modelo.exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.TipoCosto;

/**
 *
 * @author jose
 */
public interface ITipoCostoDao
{
    public void guardarTipoCosto(TipoCosto tipoCosto) throws PersistenciaException;
    public void modificarTipoCosto(TipoCosto tipoCosto) throws PersistenciaException;
    public void eliminarTipoCosto(int id) throws PersistenciaException;

}
