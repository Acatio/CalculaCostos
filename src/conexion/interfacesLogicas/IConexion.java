/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package conexion.interfacesLogicas;

import conexion.Exepciones.ConexionException;
import java.sql.Connection;

/**
 *
 * @author jose
 */
public interface IConexion
{

    public abstract Connection getConnection() throws ConexionException;

}
