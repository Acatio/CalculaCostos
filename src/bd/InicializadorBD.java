/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd;

import conexion.IConexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.*;

public class InicializadorBD
{

    IConexion conexion;

    public InicializadorBD(IConexion conexion)
    {
        this.conexion = conexion;
    }
    
    private static final Logger LOGGER = Logger.getLogger(InicializadorBD.class.getName());

    public void inicializarBD() throws SQLException
    {
        try
        {
            crearTablaMateriaPrima();
            crearTablaRecetas();
            LOGGER.info("Base de datos inicializada correctamente.");
        } catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Error al inicializar la base de datos", e);
            throw new SQLException("Error técnico en la base de datos. Consulta el log para más detalles.", e);
        }
    }

    private void crearTablaMateriaPrima() throws SQLException
    {
        final String SQL = """
            CREATE TABLE IF NOT EXISTS materia_prima (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                cantidad REAL NOT NULL,
                unidad_medida TEXT NOT NULL,
                costo REAL NOT NULL
            );
        """;

        try (Connection conn = conexion.getConnection(); Statement stmt = conn.createStatement())
        {
            stmt.execute(SQL);
            LOGGER.log(Level.INFO, "Tabla materia_prima creada con \u00e9xito.");
        } catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Fallo al crear la tabla materia_prima", e);
            throw new SQLException("Ocurrio un error al crear la tabla Materia Prima en la base de datos.", e);
        }
    }

    private void crearTablaRecetas() throws SQLException
    {

        final String SQL = """
            CREATE TABLE IF NOT EXISTS recetas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                cantidad REAL NOT NULL,
                unidad_medida TEXT NOT NULL
            );
        """;

        try (Connection conn = conexion.getConnection(); Statement stmt = conn.createStatement())
        {
            stmt.execute(SQL);
            LOGGER.log(Level.INFO, "Tabla recetas creada con \u00e9xito.");
        } catch (SQLException e)
        {
            LOGGER.log(Level.SEVERE, "Fallo al crear la tabla recetas", e);
            throw new SQLException("Ocurrio un error al crear la tabla Recetas en la base de datos.", e);
        }
    }

}
