/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dao;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObra;
import conexion.Exepciones.ConexionException;
import conexion.Exepciones.PersistenciaException;
import conexion.implementaciones.Conexion;
import conexion.interfacesLogicas.IConexion;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author jose
 */
public class CostosManoObraRepositoryImpl implements ICostosManoObraRepository
{

    private final IConexion conexion;

    public CostosManoObraRepositoryImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarManoObraDeProducto(int idProducto, List<ManoObra> manoObra)
            throws PersistenciaException
    {

        String deleteSQL = "DELETE FROM mano_obra_producto WHERE id_producto = ?";
        String insertSQL = "INSERT INTO mano_obra_producto "
                + "(id_producto, id_empleado, tiempo_aportado, costo_calculado) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.getConnection())
        {

            conn.setAutoCommit(false);  // ⭐ Muy importante

            // 1) ELIMINAR RELACIONES ANTERIORES
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL))
            {
                deleteStmt.setInt(1, idProducto);
                deleteStmt.executeUpdate();
            }

            // 2) INSERTAR NUEVAS RELACIONES
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL))
            {

                for (ManoObra mo : manoObra)
                {
                    insertStmt.setInt(1, idProducto);
                    insertStmt.setInt(2, mo.getEmpleado().getId());
                    insertStmt.setFloat(3, mo.getTiempoAportado());
                    insertStmt.setDouble(4, mo.getCostoCalculado());
                    insertStmt.addBatch();
                }

                insertStmt.executeBatch();
            }

            conn.commit();

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error guardando mano de obra del producto: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ManoObra> obtenerManoObraDeProducto(int idProducto)
            throws PersistenciaException
    {

        String sql = "SELECT mop.id_empleado, mop.tiempo_aportado, mop.costo_calculado, "
                + "e.nombre, e.apellido, e.salario_semanal, e.horas_semana "
                + "FROM mano_obra_producto mop "
                + "INNER JOIN empleados e ON mop.id_empleado = e.id_empleado "
                + "WHERE mop.id_producto = ?";

        List<ManoObra> lista = new ArrayList<>();

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {

                Empleado emp = new Empleado();
                emp.setId(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setSalarioSemanal(rs.getDouble("salario_semanal"));
                emp.setHorasSemana(rs.getFloat("horas_semana"));

                ManoObra mo = new ManoObra(
                        emp,
                        rs.getFloat("tiempo_aportado"),
                        rs.getDouble("costo_calculado")
                );

                lista.add(mo);
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error obteniendo mano de obra del producto: " + e.getMessage(), e);
        }

        return lista;
    }

    @Override
    public void eliminarManoObraDeProducto(int idProducto) throws PersistenciaException
    {
        String sql = "DELETE FROM mano_obra_producto WHERE id_producto = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, idProducto);
            stmt.executeUpdate();

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException(
                    "Error eliminando la mano de obra del producto " + idProducto + ": " + e.getMessage(),
                    e
            );
        }
    }

}
