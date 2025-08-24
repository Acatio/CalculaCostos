/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import conexion.ConexionSQL;
import controller.ControladorInsumo;
import datos.insumoDao.InsumoDaoImpl;
import vista.vistaConsola.VistaConsola;
import datos.insumoDao.IInsumoDAO;
import vista.IvistaInsumos;

/**
 *
 * @author jose
 */
public class Main
{
    public static void main(String[] args)
    {
        IInsumoDAO insumoDao = new InsumoDaoImpl(new ConexionSQL());
        IvistaInsumos vistaInsumos =new VistaConsola();
        ControladorInsumo controladorMP=new ControladorInsumo(insumoDao, vistaInsumos);
        try
        {
            controladorMP.iniciarApp();
        } catch (Exception ex)
        {
           vistaInsumos.mostrarMensajeError(ex.getMessage());
        }
    }
    
}
