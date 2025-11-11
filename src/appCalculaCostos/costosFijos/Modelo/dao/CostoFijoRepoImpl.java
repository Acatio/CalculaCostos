/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.dao;

import appCalculaCostos.costosFijos.Modelo.Entidades.CostoFijo;
import conexion.Exepciones.ConexionException;
import conexion.Exepciones.PersistenciaException;
import conexion.interfacesLogicas.IConexion;

/**
 *
 * @author jose
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CostoFijoRepoImpl implements ICostoFijoRepo
{

    private final IConexion conexion;

    public CostoFijoRepoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarCostoFijo(CostoFijo costo) throws PersistenciaException
    {
        String sql = "INSERT INTO costos_fijos (nombre, importe_mensual, porcentaje_usado) VALUES (?, ?, ?)";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, costo.getNombre());
            stmt.setDouble(2, costo.getImporteMensual());
            stmt.setDouble(3, costo.getPorcentajeUsado());

            stmt.executeUpdate();

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al guardar el costo fijo", e);
        }
    }

    @Override
    public void actualizarCostoFijo(CostoFijo actualizado) throws PersistenciaException
    {
        String sql = "UPDATE costos_fijos SET nombre = ?, importe_mensual = ?, porcentaje_usado = ? WHERE id = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, actualizado.getNombre());
            stmt.setDouble(2, actualizado.getImporteMensual());
            stmt.setDouble(3, actualizado.getPorcentajeUsado());
            stmt.setInt(4, actualizado.getId());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0)
            {
                throw new PersistenciaException("No se encontró un costo fijo con el ID especificado: " + actualizado.getId());
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al actualizar el costo fijo", e);
        }
    }

    @Override
    public void eliminarrCostoFijo(int id) throws PersistenciaException
    {
        String sql = "DELETE FROM costos_fijos WHERE id = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas == 0)
            {
                throw new PersistenciaException("No se encontró un costo fijo con el ID especificado: " + id);
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al eliminar el costo fijo con ID " + id, e);
        }
    }

    @Override
    public List<CostoFijo> listarCostosFijos() throws PersistenciaException
    {
        String sql = "SELECT id, nombre, importe_mensual, porcentaje_usado FROM costos_fijos";
        List<CostoFijo> lista = new ArrayList<>();

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery())
        {

            while (rs.next())
            {
                CostoFijo costo = new CostoFijo();
                costo.setId(rs.getInt("id"));
                costo.setNombre(rs.getString("nombre"));
                costo.setImporteMensual(rs.getDouble("importe_mensual"));
                costo.setPorcentajeUsado(rs.getDouble("porcentaje_usado"));
                lista.add(costo);
            }

            return lista;

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al listar los costos fijos", e);
        }
    }

    @Override
    public CostoFijo buscarCostoFijoPorID(int id) throws PersistenciaException
    {
        String sql = "SELECT id, nombre, importe_mensual, porcentaje_usado FROM costos_fijos WHERE id = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    CostoFijo costo = new CostoFijo();
                    costo.setId(rs.getInt("id"));
                    costo.setNombre(rs.getString("nombre"));
                    costo.setImporteMensual(rs.getDouble("importe_mensual"));
                    costo.setPorcentajeUsado(rs.getDouble("porcentaje_usado"));
                    return costo;
                } else
                {
                    throw new PersistenciaException("No se encontró un costo fijo con el ID especificado: " + id);
                }
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al buscar el costo fijo con ID " + id , e);
        }
    }
}
