/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dao;

import appCalculaCostos.costosFijos.Modelo.Entidades.CostoFijo;
import appCalculaCostos.costosFijos.Modelo.Servicio.Ponderacion;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import conexion.Exepciones.PersistenciaException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jose
 */
public interface IEmpleadoRepo
{
    public void guardarEmpleado(Empleado empleado)throws PersistenciaException;
    public void actualizarEmpleado(Empleado actualizado)throws PersistenciaException;
    public void eliminarEmpleado(int id)throws PersistenciaException;
    public List<Empleado> listarEmpleados()throws PersistenciaException;
   
    
}
