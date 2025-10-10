/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.DetalleCostoMP;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IDetalleCosto;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepoCosto;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author jose
 */
public class Repo1Prueba implements IRepoCosto
{

    @Override
    public void guardarDetalles(int id, Connection con, List<IDetalleCosto> detalles)
    {

        System.out.println("guardando en el repo 1");
        System.out.println("los costos del producto con id: " + id);
        System.out.println("con la coneccion: " + con);
        for (Object detalle : detalles)
        {
            if (detalle instanceof DetalleCostoMP dm)
            {
                System.out.println("guardando detalle ");
                System.out.print("producto: " + dm.i);
                System.out.print("  Cantidad: " + dm.cantidad);
                System.out.println("");
            }
        }
    }

}
