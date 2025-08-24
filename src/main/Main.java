/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import conexion.ConexionSQL;
import controller.ControladorMateriaPrima;
import datos.materiaPDAO.IMateriaPrimaDAO;
import datos.materiaPDAO.MateriaPrimaDAO;
import vista.IvistaMateriasPrimas;
import vista.vistaConsola.VistaConsola;

/**
 *
 * @author jose
 */
public class Main
{
    public static void main(String[] args)
    {
        IMateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO(new ConexionSQL());
        IvistaMateriasPrimas vistaMP =new VistaConsola();
        ControladorMateriaPrima controladorMP=new ControladorMateriaPrima(materiaPrimaDAO, vistaMP);
        try
        {
            controladorMP.iniciarApp();
        } catch (Exception ex)
        {
           vistaMP.mostrarMensajeError(ex.getMessage());
        }
    }
    
}
