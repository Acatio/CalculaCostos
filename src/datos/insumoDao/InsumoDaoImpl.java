/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos.insumoDao;

import conexion.Conexion;
import conexion.IConexion;
import java.util.List;
import java.util.Optional;
import modelo.producto.Insumo;
import modelo.producto.MateriaPrima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import modelo.producto.Receta;
import modelo.producto.TipoInsumo;
import modelo.producto.UnidadDeMedida;

/**
 *
 * @author jose
 */
public class InsumoDaoImpl implements IInsumoDAO
{


    @Override
    public void guardarInsumo(Insumo insumo) throws Exception
    {
        final String sql = """
            INSERT INTO insumos (nombre, tipo, unidad_medida, cantidad, costo)
            VALUES (?, ?, ?, ?, ?);
            """;

        try (java.sql.Connection conn = Conexion.conectar(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, insumo.getNombre());
            ps.setString(2, TipoInsumo.MATERIA_PRIMA.name());
            ps.setInt(3, insumo.getUnidadDeMedida().getId());
            ps.setDouble(4, insumo.getCantidad());
            ps.setDouble(5, insumo.calcularCostoTotal());
            ps.executeUpdate();

        } catch (Exception e)
        {
            System.out.println(e);
            throw new Exception("Ocurrio un error al guardar la materia prima comuniquese con el tecnico", e);
        }
    }

    @Override
    public boolean modificarInsumo(Insumo insumo)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarInsumo(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<Insumo> buscarInsumoPorID(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

//    @Override
//    public List<Insumo> ListarInsumos() throws Exception
//    {
//        List<Insumo> lista = new ArrayList<>();
//
//        final String SQL = """
//        SELECT id, nombre, tipo, unidad_medida, cantidad, costo
//        FROM insumos;
//        """;
//
//        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL); ResultSet rs = ps.executeQuery())
//        {
//
//            while (rs.next())
//            {
//                lista.add(mapearInsumo(rs));
//            }
//
//        } catch (Exception e)
//        {
//            throw new Exception("Ocurrió un error al listar los insumos. Comuníquese con el técnico.", e);
//        }
//
//        return lista;
//    }

    /**
     * Mapea un ResultSet a un objeto Insumo (MateriaPrima o Receta)
     */
    private Insumo mapearInsumo(ResultSet rs) throws SQLException
    {
        String tipo = rs.getString("tipo");

        UnidadDeMedida unidad = new UnidadDeMedida();
        unidad.setId(rs.getInt("unidad_medida"));

        if (tipo.equals(TipoInsumo.MATERIA_PRIMA.name()))
        {
            return new MateriaPrima(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("cantidad"),
                    unidad,
                    rs.getDouble("costo")
            );
        } else
        {
            return new Receta(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("cantidad"),
                    unidad
            );
        }
    }

    @Override
    public List<Insumo> ListarInsumos() throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    

}
