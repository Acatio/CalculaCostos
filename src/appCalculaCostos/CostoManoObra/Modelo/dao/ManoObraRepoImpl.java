/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dao;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObra;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObraDeProducto;
import conexion.Exepciones.ConexionException;
import conexion.Exepciones.PersistenciaException;
import conexion.interfacesLogicas.IConexion;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author jose
 */
public class ManoObraRepoImpl implements ICostoManoObraRepo
{

    private final IConexion conexion;

    public ManoObraRepoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarCostosManoObra(List<ManoObraDeProducto> lista) throws PersistenciaException
    {
        String sql = "INSERT INTO mano_obra_producto (id_producto, id_empleado, tiempo_aportado, costo_calculado) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            for (ManoObraDeProducto mop : lista)
            {
                for (ManoObra mo : mop.getManoDeObra())
                {

                    stmt.setInt(1, mop.getIdProducto());
                    stmt.setInt(2, mo.getEmpleado().getId());
                    stmt.setFloat(3, mo.getTiempoAportado());
                    stmt.setDouble(4, mo.getCostoCalculado());
                    stmt.addBatch();
                }
            }

            stmt.executeBatch();

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error guardando mano de obra: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarCostosManoObra(List<ManoObraDeProducto> lista) throws PersistenciaException
    {
        String sqlDelete = "DELETE FROM mano_obra_producto WHERE id_producto = ?";
        String sqlInsert = "INSERT INTO mano_obra_producto "
                + "(id_producto, id_empleado, tiempo_aportado, costo_calculado) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.getConnection())
        {
            conn.setAutoCommit(false); // inicia transacción

            try (PreparedStatement stmtDelete = conn.prepareStatement(sqlDelete); PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert))
            {

                for (ManoObraDeProducto mop : lista)
                {
                    // --- 1) BORRAR REGISTROS ANTERIORES POR PRODUCTO ---
                    stmtDelete.setInt(1, mop.getIdProducto());
                    stmtDelete.executeUpdate();

                    // --- 2) INSERTAR NUEVOS REGISTROS ---
                    for (ManoObra mo : mop.getManoDeObra())
                    {
                        stmtInsert.setInt(1, mop.getIdProducto());
                        stmtInsert.setInt(2, mo.getEmpleado().getId());
                        stmtInsert.setFloat(3, mo.getTiempoAportado());
                        stmtInsert.setDouble(4, mo.getCostoCalculado());

                        stmtInsert.addBatch();
                    }
                }

                stmtInsert.executeBatch();
                conn.commit(); // confirmar cambios
            } catch (SQLException ex)
            {
                conn.rollback(); // rollback en caso de error
                throw ex;
            } finally
            {
                conn.setAutoCommit(true);
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error actualizando mano de obra: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ManoObraDeProducto> listarCostosManoObra(int idProducto) throws PersistenciaException
    {

        String sql = "SELECT m.id_empleado, e.nombre, e.apellido, e.salario_semanal, e.horas_semana, "
                + "m.tiempo_aportado, m.costo_calculado "
                + "FROM mano_obra_producto m "
                + "JOIN empleados e ON m.id_empleado = e.id_empleado "
                + "WHERE m.id_producto = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, idProducto);

            try (ResultSet rs = stmt.executeQuery())
            {

                List<ManoObra> manoDeObra = new ArrayList<>();

                while (rs.next())
                {
                    Empleado emp = new Empleado();
                    emp.setId(rs.getInt("id_empleado"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellido(rs.getString("apellido"));
                    emp.setSalarioSemanal(rs.getDouble("salario_semanal"));
                    emp.setHorasSemana(rs.getFloat("horas_semana"));

                    ManoObra mo = new ManoObra();
                    mo.setEmpleado(emp);
                    mo.setTiempoAportado(rs.getFloat("tiempo_aportado"));
                    mo.setCostoCalculado(rs.getDouble("costo_calculado"));

                    manoDeObra.add(mo);
                }

                ManoObraDeProducto mop = new ManoObraDeProducto();
                mop.setIdProducto(idProducto);
                mop.setManoDeObra(manoDeObra);

                return List.of(mop);
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al listar mano de obra del producto: " + e.getMessage(), e);
        }
    }

}
