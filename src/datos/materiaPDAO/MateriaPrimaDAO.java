/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.materiaPDAO;

import conexion.IConexion;
import java.util.List;
import java.util.Optional;
import modelo.producto.Insumo;
import modelo.producto.MateriaPrima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.producto.TipoInsumo;
import modelo.producto.UnidadDeMedida;

/**
 *
 * @author jose
 */
public class MateriaPrimaDAO implements IMateriaPrimaDAO
{

    IConexion conexion;

    public MateriaPrimaDAO(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarMateriaP(MateriaPrima materiaP) throws Exception
    {
        final String SQL = """
            INSERT INTO insumos (nombre, tipo, unidad_medida, cantidad, costo)
            VALUES (?, ?, ?, ?, ?);
            """;

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL))
        {
            ps.setString(1, materiaP.getNombre());
            ps.setString(2, TipoInsumo.MATERIA_PRIMA.name());
            ps.setInt(3, materiaP.getUnidadDeMedida().getId());
            ps.setDouble(4, materiaP.getCantidad());
            ps.setDouble(5, materiaP.getCosto());
            ps.executeUpdate();

        } catch (Exception e)
        {
            throw new Exception("Ocurrio un error al guardar la materia prima comuniquese con el tecnico", e);
        }
    }

    @Override
    public boolean modificarMateriaPrima(MateriaPrima materiaP)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarMateriaPrima(MateriaPrima materiaP)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Insumo> buscarMateriaPrimaPorId(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<MateriaPrima> ListarMateriaPrima() throws Exception
    {
        List<MateriaPrima> lista = new ArrayList<>();

        final String SQL = """
            SELECT id, nombre, tipo, unidad_medida, cantidad, costo
            FROM insumos
            WHERE tipo = 'MATERIA_PRIMA';
            """;

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL); ResultSet rs = ps.executeQuery())
        {

            while (rs.next())
            {
                MateriaPrima materia = new MateriaPrima();
                materia.setId(rs.getInt("id"));
                materia.setNombre(rs.getString("nombre"));

                UnidadDeMedida unidad = new UnidadDeMedida();
                unidad.setId(rs.getInt("unidad_medida"));

                materia.setUnidadDeMedida(unidad);
                materia.setCantidad(rs.getDouble("cantidad"));
                materia.setCosto(rs.getDouble("costo"));
                lista.add(materia);
            }

        } catch (Exception e)
        {
            throw new Exception("Ocurrio un error al l listar las materias primas comuniquese con el tecnico", e);
        }

        return lista;
    }

}
