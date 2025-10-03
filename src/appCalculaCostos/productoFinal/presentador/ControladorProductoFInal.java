/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.presentador;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ICosteable;
import conexion.Exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IProductoFinalDao;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeProducto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;
import appCalculaCostos.productoFinal.vista.interfacesLogicas.IVistaProductos;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jose
 */
public class ControladorProductoFInal
{
    
    IVistaProductos vista;
    IProductoFinalDao dao;
    private static ProductoFinal productoActual;
    
    public ControladorProductoFInal(IVistaProductos vista, IProductoFinalDao dao)
    {
        this.vista = vista;
        this.dao = dao;
    }
    
    public void iniciar()
    {
        inicialiarListenerGuardar();
        inicialiarListenerMostrarProductos();
        inicializarCrearProductoInicialSinCostos();
        vista.iniciarVista();
    }
    
    private void mostrarProductos()
    {
        try
        {
            var productos = dao.ListarProductosFinales();
            vista.mostrarProductos(productos);
        } catch (PersistenciaException ex)
        {
            vista.mostrarMensajeError(ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void inicialiarListenerGuardar()
    {
        vista.setGuardarListener(() ->
        {
            try
            {
                ValidadorProductoFinal.validar(productoActual);
                dao.guardarProductoFinalYsusCostos(productoActual);
                vista.mostrarMensajeExito("Producto agregado correctamente");
                vista.actualizarVista();
            } catch (PersistenciaException | DatosNoValidosException ex)
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
    
    private void inicializarCrearProductoInicialSinCostos()
    {
        vista.setCrearProductoInicialSinCostosListener(() ->
        {
            try
            {
                productoActual = crearProductoActualSinCostos();
                vista.mostrarMensajeExito("Producto creado con exito: " + productoActual.getNombre());
            } catch (DatosNoValidosException ex)
            {
                productoActual = null;
                vista.mostrarMensajeError(ex.getMessage());
            }
        });
    }
    
    public void agregarCostoAProducto(CostoDeProducto costo)
    {
        try
        {
            productoActual.agregarCosto(costo);
        } catch (DatosNoValidosException ex)
        {
            vista.mostrarMensajeError(ex.getMessage());
        }
    }
    
    private ProductoFinal crearProductoActualSinCostos() throws DatosNoValidosException
    {
        final int COSTO_TOTAL_INICIAL = 0;
        var nombre = vista.leerNombre();
        var porcentajeGanancia = vista.leerPorcentajeDeGanancia();
        var nuveo = new ProductoFinal(nombre, porcentajeGanancia, COSTO_TOTAL_INICIAL);
        ValidadorProductoFinal.validar(nuveo);
        return nuveo;
    }
    
}
