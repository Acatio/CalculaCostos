/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.presentador;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
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

    public void iniciar()
    {
            inicialiarListenerGuardar();
            inicialiarListenerMostrarProductos();
            vista.iniciarVista();
    }

    private void mostrarProductos()
    {
        try
        {
            var productos = service.listarProductosFinales();
            vista.mostrarProductos(productos);
        } catch (ProductoFinalException ex)
        {
            vista.mostrarMensajeError("Ocurrio un error al listar los productos");
            ex.printStackTrace();
        }
    }

    private void inicialiarListenerGuardar()
    {
        vista.setGuardarListener(() ->
        {
            try
            {
                ProductoFinal nuevo = crearProductoSinCostos();
                service.guardarProductoFinalYsusCostos(nuevo);
                vista.mostrarMensajeExito("Producto agregado correctamente");
                vista.actualizarVista();
            } catch (ProductoFinalException ex)
            {
                vista.mostrarMensajeError(ex.getMessage());
            }
        });

    }

    private void inicialiarListenerMostrarProductos()
    {
        vista.setMostrarProductosListener(() ->
        {
            mostrarProductos();
        });

    }

    private ProductoFinal crearProductoSinCostos() throws DatosNoValidosException
    {
        var nombre = vista.leerNombre();
        var nuveo = new ProductoFinal(nombre);
        ValidadorProductoFinal.validar(nuveo);
        return nuveo;
    }
//    
//    public void agregarCostoAProducto(CostoDeProducto costo)
//    {
//        try
//        {
//            productoActual.agregarCosto(costo);
//        } catch (DatosNoValidosException ex)
//        {
//            vista.mostrarMensajeError(ex.getMessage());
//        }
//    }

}
