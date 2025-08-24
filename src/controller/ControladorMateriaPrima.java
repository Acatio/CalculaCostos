/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import datos.materiaPDAO.IMateriaPrimaDAO;
import java.util.List;
import modelo.producto.MateriaPrima;
import modelo.producto.UnidadDeMedida;
import vista.IvistaMateriasPrimas;

/**
 *
 * @author jose
 */
public class ControladorMateriaPrima
{

    IMateriaPrimaDAO materiaDao;
    IvistaMateriasPrimas vista;
    boolean appFinalizada = false;
    final int NUEVA_MATERIA_PRIMA = 1, MOSTRAR_MATERIAS_PRIMAS = 2, ELIMINAR_MATERIA_PRIMA = 3, SALIR = 4;

    public ControladorMateriaPrima(IMateriaPrimaDAO materiaDao, IvistaMateriasPrimas vista)
    {
        this.materiaDao = materiaDao;
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
            case NUEVA_MATERIA_PRIMA -> nuevoInsumo();
            case MOSTRAR_MATERIAS_PRIMAS -> mostrarMateriasPrimas();
            case ELIMINAR_MATERIA_PRIMA -> vista.mostrarMensaje("Funcion no soportada aun!!");
            case SALIR ->
            {
                appFinalizada = true;
                vista.mostrarMensaje("Prograna finalizado!!");
            }
            default -> vista.mostrarMensajeError("Opcion invalida!!");
        }
    }

    public void nuevoInsumo() throws Exception
    {
        var nombre = vista.leerNombre();
        var cantidad = vista.leerCantidad();
        var costo = vista.leerCosto();
        if (datosInsumoValidos(nombre, cantidad, costo))
        {
            materiaDao.guardarMateriaP(new MateriaPrima(nombre, cantidad, new UnidadDeMedida("kilo", "kg"), costo));
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

    public void mostrarMateriasPrimas() throws Exception
    {
        List<MateriaPrima> materiasPrimas = materiaDao.ListarMateriaPrima();
        if (materiasPrimas != null)
        {
            vista.mostrarMateriasP(materiasPrimas);
        }

    }
}
