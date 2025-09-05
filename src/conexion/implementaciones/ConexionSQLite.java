/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion.implementaciones;

import conexion.Exepciones.ConexionException;
import conexion.interfacesLogicas.IConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jose
 */
public class ConexionSQLite implements IConexion
{
    private static final Logger LOG = Logger.getLogger(ConexionSQLite.class.getName());

    @Override
    public Connection getConnection() throws ConexionException
    {
        String url = "jdbc:sqlite:calculaCostosDB.db";
        try
        {
            return DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            LOG.log(Level.SEVERE, "Error al conectar la base de datos: ", e);
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }
}
