/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.sun.jdi.connect.spi.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jose
 */
public class Conexion
{

    public class DBConnection
    {

        public static Connection getConnection() throws SQLException
        {
            String url = "jdbc:sqlite:calculaCostosDB.db";
            try
            {
                return (Connection) DriverManager.getConnection(url);
            } catch (SQLException e)
            {
                throw new SQLException("Error al conectar con la base de datos", e);
            }
        }

    }

}
