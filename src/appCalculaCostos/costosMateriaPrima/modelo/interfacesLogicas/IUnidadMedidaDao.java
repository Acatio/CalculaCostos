/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.UnidadDeMedida;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
public interface IUnidadMedidaDao
{
      public UnidadDeMedida buscarUnidadDeMedidaPorID()throws PersistenciaException;
}
