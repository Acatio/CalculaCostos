/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.daos;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedidaFactory;
import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DetalleReceta;
import conexion.interfacesLogicas.IConexion;
import java.util.List;
import java.util.Optional;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrima;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Receta;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoInsumo;
import conexion.Exepciones.ConexionException;
import java.sql.Statement;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
public class InsumoDaoImpl implements IInsumoDAO
{

    final private IConexion conexion;

    public InsumoDaoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarMateriaPrima(MateriaPrima materiaP) throws PersistenciaException
    {
        final String sql = """
            INSERT INTO insumos (nombre, tipo, unidad_medida, cantidad, costo)
            VALUES (?, ?, ?, ?, ?);
            """;

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, materiaP.getNombre());
            ps.setString(2, materiaP.getTipoInsumo().name());
            ps.setString(3, materiaP.getUnidadDeMedida().getNombre());
            ps.setDouble(4, materiaP.getCantidad());
            ps.setDouble(5, materiaP.calcularCostoTotal());
            ps.executeUpdate();
            System.out.println("insumo guardado");
            System.out.println(materiaP.toString());
        } catch (Exception e)
        {
            System.out.println(e);
            throw new PersistenciaException("Ocurrio un error al guardar la materia prima comuniquese con el tecnico", e);
        }
    }

    @Override
    public void guardarReceta(Receta receta) throws PersistenciaException
    {
        final String sqlReceta = """
        INSERT INTO insumos (nombre, tipo, unidad_medida, cantidad, costo)
        VALUES (?, ?, ?, ?, ?);
        """;

        Connection conn = null;

        try
        {
            conn = conexion.getConnection();
            conn.setAutoCommit(false); // Desactivamos autocommit para atomicidad

            // Guardar receta
            try (PreparedStatement psReceta = conn.prepareStatement(sqlReceta, Statement.RETURN_GENERATED_KEYS))
            {
                psReceta.setString(1, receta.getNombre());
                psReceta.setString(2, receta.getTipoInsumo().name());
                psReceta.setString(3, receta.getUnidadDeMedida().getNombre());
                psReceta.setDouble(4, receta.getCantidad());
                psReceta.setDouble(5, receta.calcularCostoTotal());
                psReceta.executeUpdate();

                // Obtener ID generado
                int recetaId = 0;
                try (ResultSet rs = psReceta.getGeneratedKeys())
                {
                    if (rs.next())
                    {
                        recetaId = rs.getInt(1);

                    }
                }

                // Guardar detalles de la receta
                guardarDetalles(recetaId, receta.getIngredientes(), conn);
            }

            conn.commit(); // Confirmamos toda la transacción
            System.out.println("Receta y detalles guardados correctamente");
            System.out.println(receta.toString());

        } catch (SQLException | ConexionException e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback(); // Revertimos todo si ocurre un error
                } catch (SQLException ex)
                {
                    ex.printStackTrace();//TODO revisar si no se lanza la exepcion
                }
            }
            e.printStackTrace();
            throw new PersistenciaException("Ocurrió un error al guardar la receta, comuníquese con el técnico", e);
        } finally
        {
            if (conn != null)
            {
                try
                {
                    conn.setAutoCommit(true); // Restauramos autocommit por si acaso
                    conn.close();            // Cerramos la conexión
                } catch (SQLException ignored) //TODO revisar si debe ser ignorada
                {
                }
            }
        }
    }

    private void guardarDetalles(int recetaId, List<DetalleReceta> ingredientes, Connection conn) throws SQLException
    {
        final String sql = """
        INSERT INTO detalle_receta (id_receta, id_insumo, cantidad, unidad_medida)
        VALUES (?, ?, ?, ?);
        """;
        System.out.println("recetaId: " + recetaId);
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            for (DetalleReceta d : ingredientes)
            {
                ps.setInt(1, recetaId);
                ps.setInt(2, d.getInsumo().getId());
                ps.setDouble(3, d.getCantidad());
                ps.setString(4, d.getUnidadMedida().getNombre());
                ps.addBatch();
            }
            ps.executeBatch(); // ejecuta todos los inserts a la vez
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
    public Optional<Insumo> buscarInsumoPorID(int id) throws PersistenciaException
    {
        final String sql = """
            SELECT id_insumo, tipo, nombre, unidad_medida, cantidad, costo FROM insumos WHERE id_insumo=?;
            """;

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Insumo insumo = null;
            if (rs.next())
            {
                var nombre = rs.getString("nombre");
                var unidadMedida = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(rs.getString("unidad_medida"));
                var cantidad = rs.getDouble("cantidad");
                var costo = rs.getDouble("costo");

                if (rs.getString("tipo").equals(TipoInsumo.MATERIA_PRIMA.name()))
                {
                    insumo = new MateriaPrima(nombre, cantidad, unidadMedida, costo);
                    insumo.setId(id);
                } else
                {
                    if (rs.getString("tipo").equals(TipoInsumo.RECETA.name()))
                    {
                        insumo = new Receta(nombre, cantidad, unidadMedida);
                        insumo.setId(id);
                    } else
                    {
                        throw new AssertionError("Tipo de insumo no conocido: " + rs.getString("tipo"));
                    }
                }

            }
            return Optional.ofNullable(insumo);
        } catch (SQLException | ConexionException e)
        {
            e.printStackTrace();
            throw new PersistenciaException("Ocurrio un error al buscar el insumo con id: " + id, e);
        }

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

        UnidadMedida unidad = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(rs.getString("unidad_medida"));

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
    public List<Insumo> ListarInsumos() throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Insumo> ListarInsumosDeProductoPorID(int id) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
