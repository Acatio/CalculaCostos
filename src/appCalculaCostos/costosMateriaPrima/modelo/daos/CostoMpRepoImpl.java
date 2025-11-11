/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.daos;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedidaFactory;
import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DetalleReceta;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoCosto;
import appCalculaCostos.productoFinal.modelo.exepciones.NoPosibleCalcularMonto;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ICostoMpRepo;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import conexion.Exepciones.ConexionException;
import conexion.Exepciones.PersistenciaException;
import conexion.interfacesLogicas.IConexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Optional;

/**
 *
 * @author jose
 */
public class CostoMpRepoImpl implements ICostoMpRepo
{

    private final IConexion conexion;

    public CostoMpRepoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

// Método público que se usa cuando quieres guardar desde fuera (abre y cierra la conexión)
    @Override
    public void guardarCotosMP(int idProductoFinal, List<DetalleReceta> costosMp, IRepositorioProductoFinal repo) throws PersistenciaException
    {
        Connection conn = null;
        try
        {
            conn = conexion.getConnection();
            conn.setAutoCommit(false);

            // usa el método privado que recibe la conexión
            guardarCostosMPLocal(idProductoFinal, costosMp, repo, conn);

            conn.commit();
        } catch (SQLException | ConexionException | NoPosibleCalcularMonto ex)
        {
            // intento de rollback si algo falló
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                } catch (SQLException rbEx)
                {
                    // añadir suppressed para no perder la info original
                    rbEx.addSuppressed(ex);
                    throw new PersistenciaException("Error al hacer rollback al guardar costos de MP", rbEx);
                }
            }
            throw new PersistenciaException("Error al guardar los costos de materia prima", ex);
        } finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                } catch (SQLException closeEx)
                {
                    // opcional: loggear/printStackTrace
                    closeEx.printStackTrace();
                }
            }
        }
    }

// Método privado que realiza la inserción usando la conexión proporcionada.
// NO hace commit/rollback ni cierra la conexión.
    private void guardarCostosMPLocal(int idProductoFinal,
            List<DetalleReceta> costosMp,
            IRepositorioProductoFinal repo,
            Connection conn)
            throws SQLException, NoPosibleCalcularMonto, PersistenciaException
    {

        String sql = "INSERT INTO insumos_de_producto (id_producto, id_insumo, cantidad, costo, tipo,unidad_medida) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            for (DetalleReceta detalle : costosMp)
            {
                ps.setInt(1, idProductoFinal);
                ps.setInt(2, detalle.getInsumo().getId());
                ps.setDouble(3, detalle.getCantidad());
                ps.setDouble(4, detalle.getMonto());
                System.out.println("monto guardado: " + detalle.getMonto());
                ps.setString(5, TipoCosto.MATERIA_PRIMA.name());
                ps.setString(6, detalle.getUnidadMedida().getNombre());
                ps.addBatch();
            }
            ps.executeBatch();
        }

        // actualizar el costo calculado del producto final (usa la misma conexión)
        // Se asume que este método del repo recibe Connection y no la cierra.
        repo.actualizarCostoProductoFinalCalculado(conn, idProductoFinal);
    }

    @Override
    public void actualizarCostosMP(int idProductoFinal, List<DetalleReceta> nuevosCostos, IRepositorioProductoFinal repoPf) throws PersistenciaException
    {
        Connection conn = null;
        try
        {
            conn = conexion.getConnection();
            conn.setAutoCommit(false);

            // Se asume que este método del repo usa la connection pasada y no la cierra.
            repoPf.borrarCostotosDeProductoPorTipo(idProductoFinal, TipoCosto.MATERIA_PRIMA, conn);

            // usa el método privado que recibe la conexión
            guardarCostosMPLocal(idProductoFinal, nuevosCostos, repoPf, conn);

            conn.commit();
        } catch (SQLException | ConexionException ex)
        {

            if (conn != null)
            {
                try
                {
                    conn.rollback();
                } catch (SQLException rbEx)
                {
                    rbEx.addSuppressed(ex);
                    throw new PersistenciaException("Error al hacer rollback al actualizar costos de materia prima", rbEx);
                }
            }
            throw new PersistenciaException("No se pudieron actualizar los costos de materia prima", ex);
        } catch (NoPosibleCalcularMonto ex)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                } catch (SQLException rbEx)
                {
                    rbEx.addSuppressed(ex);
                    throw new PersistenciaException(ex.getMessage(), rbEx);
                }
            }
            throw new PersistenciaException(ex.getMessage(), ex);
        } finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                } catch (SQLException closeEx)
                {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<DetalleReceta> listarCostosMp(int idProducto, IInsumoDAO insumoDao) throws PersistenciaException
    {
        List<DetalleReceta> lista = new ArrayList<>();

        String sql = "SELECT id_insumo, cantidad, unidad_medida FROM insumos_de_producto WHERE id_producto=? AND tipo=?";

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setInt(1, idProducto);
            ps.setString(2, TipoCosto.MATERIA_PRIMA.name());

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    int idInsumo = rs.getInt("id_insumo");
                    double cantidad = rs.getDouble("cantidad");
                    String nombreUnidad = rs.getString("unidad_medida");

                    // Obtener la unidad de medida usando la fábrica
                    UnidadMedida unidad = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(nombreUnidad);

                    // Buscar el insumo correspondiente
                    Optional<Insumo> insumoOpt = insumoDao.buscarInsumoPorID(idInsumo);

                    if (insumoOpt.isEmpty())
                    {
                        throw new PersistenciaException(" No se encontró el insumo con ID: " + idInsumo);

                    }
                    lista.add(new DetalleReceta(insumoOpt.get(), cantidad, unidad));
                }
            }

        } catch (SQLException | ConexionException ex)
        {
            throw new PersistenciaException("Error al listar los costos de materia prima para el producto con ID " + idProducto, ex);
        }

        return lista;
    }

}
