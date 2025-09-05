/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bd;

import conexion.Exepciones.ConexionException;
import conexion.Exepciones.InicializacionExeption;
import conexion.interfacesLogicas.IConexion;
import conexion.interfacesLogicas.IInicializacionBd;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.*;

public class InicializadorBDSqlite implements IInicializacionBd
{

    IConexion conexion;

    public InicializadorBDSqlite(IConexion conexion)
    {
        this.conexion = conexion;
    }

    private static final Logger LOGGER = Logger.getLogger(InicializadorBDSqlite.class.getName());

    @Override
    public void inicializarBD() throws InicializacionExeption
    {
        try
        {
            crearTablaMateriaPrima();
            crearTablaRecetas();
            LOGGER.info("Base de datos inicializada correctamente.");
        } catch (SQLException | ConexionException e)
        {
            LOGGER.log(Level.SEVERE, "Error al inicializar la base de datos", e);
            throw new InicializacionExeption("Error al inicializar la base de datos", e);
        }
    }

    private void crearTablaMateriaPrima() throws SQLException, ConexionException
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
        }
    }

    private void crearTablaRecetas() throws SQLException, ConexionException
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
        }
    }

}
