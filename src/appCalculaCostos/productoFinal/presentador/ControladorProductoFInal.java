/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.presentador;


import appCalculaCostos.productoFinal.modelo.exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IProductoFinalDao;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.ProductoFinal;
import appCalculaCostos.productoFinal.vista.interfacesLogicas.IVistaProductos;


/**
 *
 * @author jose
 */
public class ControladorProductoFInal
{

    IVistaProductos vista;
    IProductoFinalDao dao;

    public ControladorProductoFInal(IVistaProductos vista, IProductoFinalDao dao)
    {
        this.vista = vista;
        this.dao = dao;
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
                final int COSTO_TOTAL_INICIAL = 0;
                var nombre = vista.leerNombre();
                var porcentajeGanancia = vista.leerPorcentajeDeGanancia();
                ProductoFinal nuevo = new ProductoFinal(nombre, porcentajeGanancia, COSTO_TOTAL_INICIAL);
                dao.guardarProductoFinal(nuevo);
                vista.mostrarMensajeExito("Producto agregado correctamente");
                vista.actualizarVista();
            } catch (PersistenciaException ex)
            {
                vista.mostrarMensajeError(ex.getMessage());
                ex.printStackTrace();
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

}
