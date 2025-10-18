/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

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

    public static void main(String[] args)
    {
        ServicioProductoFinal spf= new ServicioProductoFinal(new ProductoFinalDaoImpl(new ConexionSQL()));
        ProductoFinalCreacionDTO dto = new ProductoFinalCreacionDTO("pizza Rec", 200, null,450d,new ArrayList<>()); 
        try
        {
            spf.guardarNuevoProducto(dto);
        } catch (ProductoFinalException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
