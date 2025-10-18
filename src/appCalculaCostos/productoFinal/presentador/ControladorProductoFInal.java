/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.presentador;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.vista.interfacesLogicas.IVistaProductos;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;

/**
 *
 * @author jose
 */
public class ControladorProductoFInal
{

    IVistaProductos vista;
    ServicioProductoFinal service;

    public ControladorProductoFInal(IVistaProductos vista, ServicioProductoFinal service)
    {
        this.vista = vista;
        this.service = service;
    }


}
