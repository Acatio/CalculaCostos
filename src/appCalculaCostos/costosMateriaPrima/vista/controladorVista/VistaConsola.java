/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.vista.controladorVista;

import java.util.List;
import java.util.Scanner;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas.IvistaInsumos;

/**
 *
 * @author jose
 */
public class VistaConsola implements IvistaInsumos
{

    @Override
    public void mostrarMenu()
    {
        String menu = """
                      \n
                    1.Agregar nuevo insumo
                    2.Mostrar insumos
                    3.Eliminar insumo
                    4.Salir
                    """;
        mostrarMensaje(menu);
    }

    @Override
    public int leerOpcion()
    {
        try
        {
            mostrarMensaje("Seleccione una opcion: ");
            Scanner entrada = new Scanner(System.in);
            var opc = entrada.nextInt();
            return opc;
        } catch (Exception e)
        {
            mostrarMensajeError("Digite una entrada valida\n");
        }
        return -1;
    }

    @Override
    public void mostrarInsumos(List<Insumo> insumos)
    {
        insumos.forEach(System.out::println);
    }

    @Override
    public int getIDInsumoSeleccionado()
    {
        mostrarMensaje("Digite el id del insumo: ");
        return leerOpcion();

    }

    @Override
    public void mostrarMensaje(String mensaje)
    {
        System.out.print(mensaje);
    }

    @Override
    public void mostrarMensajeError(String mensajeError)
    {
        System.out.println("ERROR: " + mensajeError);
    }

    @Override
    public String leerNombre()
    {
        mostrarMensaje("Ingrese el nombre del insumo: ");
        Scanner entrada = new Scanner(System.in);
        return entrada.nextLine();
    }

    @Override
    public double leerCosto()
    {
        try
        {
            mostrarMensaje("Ingrese el costo del insumo: ");
            Scanner entrada = new Scanner(System.in);
            var costo = entrada.nextDouble();
            return costo;
        } catch (Exception e)
        {
            mostrarMensajeError("Digite una entrada valida\n");
        }
        return -1;
    }

    @Override
    public double leerCantidad()
    {
        try
        {
            mostrarMensaje("Ingrese la cantidad del insumo: ");
            Scanner entrada = new Scanner(System.in);
            var cantidad = entrada.nextDouble();
            return cantidad;
        } catch (Exception e)
        {
            mostrarMensajeError("Digite una entrada valida\n");
        }
        return -1;
    }

    @Override
    public int seleccionarIDUnidadDeMedida()
    {
        mostrarMensaje("digite el id de la unidad de medida: ");
        return leerOpcion();
    }

}
