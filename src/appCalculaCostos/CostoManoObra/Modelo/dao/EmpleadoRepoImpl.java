/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dao;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import conexion.Exepciones.ConexionException;
import conexion.Exepciones.PersistenciaException;
import conexion.interfacesLogicas.IConexion;
import java.util.List;

/**
 *
 * @author jose
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepoImpl implements IEmpleadoRepo
{

    private final IConexion conexion;

    public EmpleadoRepoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarEmpleado(Empleado empleado) throws PersistenciaException
    {
        String sql = "INSERT INTO empleados (nombre, apellido, salario_semanal, horas_semana) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getApellido());
            stmt.setDouble(3, empleado.getSalarioSemanal());
            stmt.setDouble(4, empleado.getHorasSemana());

            stmt.executeUpdate();

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al guardar empleado", e);
        }
    }

    @Override
    public void actualizarEmpleado(Empleado actualizado) throws PersistenciaException
    {
        String sql = "UPDATE empleados SET nombre = ?, apellido = ?, salario_semanal = ?, horas_semana = ? WHERE id_empleado = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, actualizado.getNombre());
            stmt.setString(2, actualizado.getApellido());
            stmt.setDouble(3, actualizado.getSalarioSemanal());
            stmt.setFloat(4, actualizado.getHorasSemana());
            stmt.setInt(5, actualizado.getId());

            int filas = stmt.executeUpdate();
            if (filas == 0)
            {
                throw new PersistenciaException(
                        "No se encontró un empleado con ID: " + actualizado.getId()
                );
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al actualizar empleado" , e);
        }
    }

    @Override
    public void eliminarEmpleado(int id) throws PersistenciaException
    {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();
            if (filas == 0)
            {
                throw new PersistenciaException("No se encontró un empleado con ID: " + id);
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al eliminar empleado", e);
        }
    }

    @Override
    public List<Empleado> listarEmpleados() throws PersistenciaException
    {
        String sql = "SELECT id_empleado, nombre, apellido, salario_semanal, horas_semana FROM empleados";
        List<Empleado> lista = new ArrayList<>();

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery())
        {

            while (rs.next())
            {
                Empleado emp = new Empleado();
                emp.setId(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setSalarioSemanal(rs.getDouble("salario_semanal"));
                emp.setHorasSemana(rs.getFloat("horas_semana"));

                lista.add(emp);
            }

            return lista;

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al listar empleados", e);
        }
    }

    @Override
    public Empleado buscarPorId(int id) throws PersistenciaException
    {
        String sql = "SELECT id_empleado, nombre, apellido, salario_semanal, horas_semana "
                + "FROM empleados WHERE id_empleado = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                return new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getDouble("salario_semanal"),
                        rs.getFloat("horas_semana")
                );
            }

            return null;

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error buscando empleado por ID", e);
        }
    }

}
