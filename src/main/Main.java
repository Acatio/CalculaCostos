/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IProductoFinalDao;
import appCalculaCostos.productoFinal.presentador.ControladorProductoFInal;
import appCalculaCostos.productoFinal.vista.controladorVista.VistaConsolaProductosFImpl;
import appCalculaCostos.productoFinal.vista.controladorVista.VistaProductoFinalSwing;
import appCalculaCostos.productoFinal.vista.interfacesLogicas.IVistaProductos;
import bd.inicializadorBdMySQLImpl;
import conexion.Exepciones.InicializacionExeption;
import conexion.implementaciones.ConexionSQL;
import conexion.interfacesLogicas.IConexion;
import conexion.interfacesLogicas.IInicializacionBd;

/**
 *
 * @author jose
 */
public class Main
{

    public static void main(String[] args)
    {
        IVistaProductos vista = null;
        try
        {
            // Seleccionas la vista
             vista = new VistaConsolaProductosFImpl();
           // vista = new VistaProductoFinalSwing();
            IConexion conexionSql = new ConexionSQL();
            IInicializacionBd inicializar = new inicializadorBdMySQLImpl(conexionSql);
            inicializar.inicializarBD();

            ControladorProductoFInal controlador = new ControladorProductoFInal(vista, new ProductoFinalDaoImpl(conexionSql));
            controlador.iniciar();

        } catch (InicializacionExeption ex)
        {
            if (vista != null)
            {
                vista.mostrarMensajeError(ex.getMessage());
            } else
            {
                // fallback: si aún no hay vista, usa consola
                System.out.println("Error crítico: " + ex.getMessage());
            }
        }
    }

}
