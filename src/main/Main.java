/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

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
        try
        {
            IConexion conexionSql = new ConexionSQL();
            IInicializacionBd inicializar = new inicializadorBdMySQLImpl(conexionSql);
            inicializar.inicializarBD();
        } catch (InicializacionExeption ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
