/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.CostoModuloDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import conexion.implementaciones.ConexionSQL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jose
 */
public class Main
{

    public static void main(String[] args) throws InsumoException
    {
        MateriaPrimaService mps =new MateriaPrimaService(new InsumoDaoImpl(new ConexionSQL()));
        
        var insumos= mps.listarInsumos();
        for (InsumoDto d:insumos)
        {
            System.out.println(d.toString());
        }
    }
}
