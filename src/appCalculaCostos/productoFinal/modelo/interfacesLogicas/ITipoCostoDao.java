/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
public interface ITipoCostoDao
{
    public void guardarTipoCosto() throws PersistenciaException;
    public void modificarTipoCosto() throws PersistenciaException;
    public void eliminarTipoCosto(int id) throws PersistenciaException;

}
