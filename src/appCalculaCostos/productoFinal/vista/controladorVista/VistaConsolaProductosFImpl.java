/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.vista.controladorVista;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.CostoMateriaPrima;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas.IVistaCostosMp;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.vista.interfacesLogicas.IVistaProductos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author jose
 */
public class VistaConsolaProductosFImpl implements IVistaProductos, IVistaCostosMp
{

    private Runnable guardarCallback;
    private Runnable mostrarProductosCallback;

    @Override
    public void mostrarProductos(List<ProductoFinal> productos)
    {
        productos.forEach(System.out::println);
    }

    @Override
    public void mostrarMensaje(String mensaje)
    {
        System.out.println(mensaje);
    }

    @Override
    public void mostrarMensajeError(String mensajeError)
    {
        System.out.println("Error: " + mensajeError);
    }

    @Override
    public int getIDProductoSeleccionado()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String leerNombre()
    {
        Scanner entrada = new Scanner(System.in);
        mostrarMensaje("Ingrese el nombre del producto: ");
        return entrada.nextLine();
    }

    @Override
    public double leerPorcentajeDeGanancia()
    {
        Scanner entrada = new Scanner(System.in);
        mostrarMensaje("Ingrese el porcentaje de ganancia: ");
        return entrada.nextDouble();
    }

    @Override
    public void mostrarMensajeExito(String mensajeExito)
    {
        System.out.println("Exito: " + mensajeExito);
    }

    @Override
    public void iniciarVista()
    {
        iniciarApp();

    }

    private void iniciarApp()
    {
        int opcion;
        do
        {
            mostrarMenu();
            mostrarMensaje("Seleccione una opcion: ");
            opcion = leerEntero();
            manejarOpcion(opcion);
        } while (opcion != 3);

    }

    private void mostrarMenu()
    {
        var menu = """
                   MENU
                   1.Agregar producto
                   2.Mostrar productos
                   3.Salir
                 """;
        mostrarMensaje(menu);

    }

    private int leerEntero()
    {
        Scanner entrada = new Scanner(System.in);
        return entrada.nextInt();
    }

    private void manejarOpcion(int opc)
    {
        switch (opc)
        {
            case 1:
                if (guardarCallback != null)
                {
                    guardarCallback.run();
                }
                break;
            case 2:
                mostrarMensaje("---- Productos Finales ------");

                if (mostrarProductosCallback != null)
                {
                    mostrarProductosCallback.run();
                }
                break;
            case 3:
                mostrarMensaje("programa finalizado...");
                System.exit(0);
                break;
            default:

                mostrarMensajeError("Opcion no valida!!");
        }
    }

    @Override
    public void setGuardarListener(Runnable callback)
    {
        this.guardarCallback = callback;
    }

    @Override
    public void setEliminarListener(Runnable callback)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setActualizarListener(Runnable callback)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<ProductoFinal> getProductoSeleccionado()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setMostrarProductosListener(Runnable callback)
    {
        this.mostrarProductosCallback = callback;
    }

    @Override
    public void actualizarVista()
    {
       
    }

    @Override
    public void setMostrarInsumosListener(Runnable callback)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarInsumos(List<Insumo> insumos)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarIngredientes(CostoMateriaPrima ingredientes)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAgregarIngredientesListener(Runnable callback)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostarMensaje(String mensaje)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setMostrarIngredientesListener(Runnable callback)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Insumo> getInsumoSeleccionado()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setCrearProductoInicialSinCostosListener(Runnable callback)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getCantidadInsumo()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getIDInsumoSeleccionado()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
