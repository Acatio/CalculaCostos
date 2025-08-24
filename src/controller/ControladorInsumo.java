/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import modelo.producto.MateriaPrima;
import modelo.producto.UnidadDeMedida;
import datos.insumoDao.IInsumoDAO;
import modelo.producto.Insumo;
import vista.IvistaInsumos;

/**
 *
 * @author jose
 */
public class ControladorInsumo
{

    IInsumoDAO insumoDAO;
    IvistaInsumos vista;
    boolean appFinalizada = false;
    final int NUEVO_INSUMO = 1, MOSTRAR_INSUMOS = 2, ELIMINAR_INSUMO = 3, SALIR = 4;

    public ControladorInsumo(IInsumoDAO materiaDao, IvistaInsumos vista)
    {
        this.insumoDAO = materiaDao;
        this.vista = vista;
    }

    public void iniciarApp() throws Exception
    {
        do
        {
            vista.mostrarMenu();
            var opc = vista.leerOpcion();
            manejarOpciones(opc);
        } while (!appFinalizada);
    }

    public void manejarOpciones(int opc) throws Exception
    {
        switch (opc)
        {
            case NUEVO_INSUMO ->
                nuevoInsumo();
            case MOSTRAR_INSUMOS ->
                mostrarInsumos();
            case ELIMINAR_INSUMO ->
                vista.mostrarMensaje("Funcion no soportada aun!!");
            case SALIR ->
            {
                appFinalizada = true;
                vista.mostrarMensaje("Prograna finalizado!!");
            }
            default ->
                vista.mostrarMensajeError("Opcion invalida!!");
        }
    }

    public void nuevoInsumo() throws Exception
    {
        var nombre = vista.leerNombre();
        var cantidad = vista.leerCantidad();
        var costo = vista.leerCosto();
        if (datosInsumoValidos(nombre, cantidad, costo))
        {
            insumoDAO.guardarInsumo(new MateriaPrima(nombre, cantidad, new UnidadDeMedida("kilo", "kg"), costo));
            vista.mostrarMensaje("Producto guardado con exito!!");
        } else
        {
            vista.mostrarMensajeError("El producto no fue guardado!!");
        }

    }

    public boolean datosInsumoValidos(String nombre, double cantidad, double costo)
    {
        if (nombre.isEmpty())
        {
            vista.mostrarMensajeError("Ingrese un nombre valido...");
            return false;
        }
        if (cantidad < 0)
        {
            vista.mostrarMensajeError("Ingrese una cantidad mayor o igual a cero...");
            return false;
        }
        if (costo < 0)
        {
            vista.mostrarMensajeError("Ingrese un costo mayor o igual a cero...");
            return false;
        }
        return true;
    }

    public void mostrarInsumos() throws Exception
    {
        List<Insumo> insumos = insumoDAO.ListarInsumos();
        if (insumos != null)
        {
            vista.mostrarInsumos(insumos);
        }

    }
}
