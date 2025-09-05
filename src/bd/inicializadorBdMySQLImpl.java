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
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author jose
 */
public class inicializadorBdMySQLImpl implements IInicializacionBd
{

    private final IConexion conexion;

    public inicializadorBdMySQLImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void inicializarBD() throws InicializacionExeption
    {
        try (Connection conn = conexion.getConnection())
        {
            conn.setAutoCommit(false); // transacción
            inicializarUnidadesDeMedida(conn);
            inicializarTiposCosto(conn);
            conn.commit();
            System.out.println("Base de datos inicializada..");
        } catch (SQLException | ConexionException e)
        {
            e.printStackTrace();
            throw new InicializacionExeption("Error al inicializar la base de datos.", e);
        }
    }

// Inicializa las unidades de medida estándar
    private void inicializarUnidadesDeMedida(Connection conn) throws SQLException
    {
        // Lista de unidades con símbolo exacto
        String[][] unidades =
        {
            {
                "Litros", "L"
            },
            {
                "Kilos", "Kg"
            },
            {
                "Gramos", "g"
            },
            {
                "Pieza", "pz"
            },
            {
                "Mililitros", "ml"
            }
        };

        final String sqlInsert = "INSERT IGNORE INTO unidades_medida (id_unidad_medida, nombre, simbolo) VALUES (?,?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sqlInsert))
        {
            int id = 1;
            for (String[] unidad : unidades)
            {
                ps.setInt(1, id); // id
                ps.setString(2, unidad[0]); // nombre
                ps.setString(3, unidad[1]); // símbolo
                ps.executeUpdate(); // insertar uno por uno
                id++;
            }
        }
    }

// Inicializa los tipos de costo estándar
    private void inicializarTiposCosto(Connection conn) throws SQLException
    {
        String[] tipos =
        {
            "Materia Prima", "Mano de Obra", "Servicios", "Extra"
        };

        final String sqlInsert = """
        INSERT IGNORE INTO tipo_costo (id_tipo_costo, nombre)
        VALUES (?,?);
        """;

        try (PreparedStatement ps = conn.prepareStatement(sqlInsert))
        {
            int id = 1;
            for (String tipo : tipos)
            {
                ps.setInt(1, id);
                ps.setString(2, tipo);
                ps.addBatch();
                id++;
            }
            ps.executeBatch();
        }
    }

}
