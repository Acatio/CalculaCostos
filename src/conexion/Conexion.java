/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jose
 */
public class Conexion
{
    private static final Logger LOG = Logger.getLogger(Conexion.class.getName());

    public static Connection getConnection() throws SQLException
    {
        String url = "jdbc:sqlite:calculaCostosDB.db";
        try
        {
            return DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            LOG.log(Level.SEVERE, "Error al conectar la base de datos: ", e);
            throw new SQLException("Error al conectar con la base de datos", e);
        }
    }
}
