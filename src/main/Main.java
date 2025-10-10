/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalRepoPrueba;
import appCalculaCostos.productoFinal.modelo.daos.Repo1Prueba;
import appCalculaCostos.productoFinal.modelo.daos.Repo2Prueba;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.DetalleCostoMP;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.costoPrueba;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.productoFinal.presentador.ControladorProductoFInal;
import appCalculaCostos.productoFinal.vista.controladorVista.vistaSwing.VistaPrincipal;
import appCalculaCostos.productoFinal.vista.controladorVista.vistaSwing.VistaProductoFinalSwing;
import appCalculaCostos.productoFinal.vista.interfacesLogicas.IVistaProductos;
import bd.inicializadorBdMySQLImpl;
import conexion.Exepciones.InicializacionExeption;
import conexion.Exepciones.PersistenciaException;
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
        ProductoFinal p1 = new ProductoFinal(1, "pizza chica");

        ServicioCosto costoMateriaP = new costoPrueba(1, "Materia Prima", new Repo1Prueba(), p1.getId());
        ServicioCosto costoManoO = new costoPrueba(2, "Mano Obra", new Repo2Prueba(), p1.getId());

        costoMateriaP.agregarDetalle(new DetalleCostoMP("Insumo 1", 1));
        costoMateriaP.agregarDetalle(new DetalleCostoMP("Insumo 2", 2));
        costoMateriaP.agregarDetalle(new DetalleCostoMP("Insumo 3", 1));

        costoManoO.agregarDetalle(new DetalleCostoMP("Juan", 1));//se agrega un detalle de materia prima pero solo es para simular, en su caso deberia 
        //crearse un costo especifico para mano de obra con su detalle correspondiente

        p1.agregarCosto(costoMateriaP);
        p1.agregarCosto(costoManoO);

        p1.setPrecioVenta(100);

        //aqui el servicio de producto final debeeria llamar al repositorio para guardarlo con sus costos
        //pero para simplicidad llamaremos al repositorio directamente para verificar que la logica funciona
        ProductoFinalRepoPrueba repoPF = new ProductoFinalRepoPrueba();
        try
        {
            repoPF.guardarProductoFinalYsusCostos(p1);
        } catch (PersistenciaException ex)
        {
            System.out.println("Error al guardar el producto..");
        }
        System.out.println(p1.toString());

        System.out.println("*******Vista de Materia prima***** ");
        var detallesMp = costoMateriaP.getDetalles();
        System.out.println("Detalles costo MP ");
        for (Object detalle : detallesMp)
        {
            if (detalle instanceof DetalleCostoMP dm)
            {
                System.out.print("producto: " + dm.i);
                System.out.print("  Cantidad: " + dm.cantidad);
            }
        }
        System.out.println("");

        System.out.println("*******Vista de Mano de obra***** ");
        var detallesMp2 = costoManoO.getDetalles();
        System.out.println("Detalles costo MO ");
        for (Object detalle : detallesMp2)
        {
            if (detalle instanceof DetalleCostoMP dm)
            {
                System.out.print("producto: " + dm.i);
                System.out.print("  Cantidad: " + dm.cantidad);
            }
        }
        System.out.println("");
    }
//    public static void main(String[] args)
//    {
//        IVistaProductos vista = null;
//        try
//        {
//            // Seleccionas la vista
//            vista = new VistaPrincipal();
//            
//            IConexion conexionSql = new ConexionSQL();
//            IInicializacionBd inicializar = new inicializadorBdMySQLImpl(conexionSql);
//            inicializar.inicializarBD();
//            ControladorProductoFInal controlador = new ControladorProductoFInal(vista,new ServicioProductoFinal(new ProductoFinalDaoImpl(new ConexionSQL())));
//            controlador.iniciar();
//
//        } catch (InicializacionExeption ex)
//        {
//            if (vista != null)
//            {
//                vista.mostrarMensajeError(ex.getMessage());
//            } else
//            {
//                // fallback: si aún no hay vista, usa consola
//                System.out.println("Error crítico: " + ex.getMessage());
//            }
//        }
//    }

}
