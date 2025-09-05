/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IProductoFinalDao;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.CostoDeProducto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;
import conexion.Exepciones.ConexionException;
import conexion.interfacesLogicas.IConexion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jose
 */
public class ProductoFinalDaoImpl implements IProductoFinalDao
{

    final IConexion conexion;

    public ProductoFinalDaoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarProductoFinal(ProductoFinal productoFinal) throws PersistenciaException
    {
        try (Connection conn = conexion.getConnection())
        {
            ValidadorProductoFinal.validar(productoFinal);
            conn.setAutoCommit(false);
            final double COSTO_INICIAL = 0;
            // 1. Insertar producto y obtener ID usando método privado
            int idProductoFinal = guardarProducto(conn, productoFinal.getNombre(), COSTO_INICIAL);

            // 2. Guardar costos asociados
            productoFinal.setId(idProductoFinal);
            guardarCostosDeProducto(productoFinal, conn);

            // 3. Actualizar costo_total
            actualizarCostoProductoFinalCalculado(conn, idProductoFinal);

            // 4. Confirmar transacción
            conn.commit();

        } catch (SQLException | ConexionException e)
        {
            System.out.println(e.getMessage());
            throw new PersistenciaException("Error al guardar el Producto. Contacte al tecnico.", e);
        } catch (DatosNoValidosException e)
        {
            System.out.println(e.getMessage());
            throw new PersistenciaException("Los datos del producto no son validos, Contacte al tecnico", e);
        }
    }

// Método privado que solo inserta y devuelve el ID
    private int guardarProducto(Connection conn, String nombre, double costo) throws SQLException
    {
        final String sql = "INSERT INTO productos_finales (nombre, costo_total) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, nombre);
            ps.setDouble(2, costo);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys())
            {
                if (rs.next())
                {
                    return rs.getInt(1);
                } else
                {
                    throw new SQLException("No se pudo obtener el ID del producto insertado.");
                }
            }
        }
    }

    private void actualizarCostoProductoFinalCalculado(Connection conn, int idProductoFinal) throws SQLException
    {
        final String sqlUpdate = """
        UPDATE productos_finales
        SET costo_total = (
            SELECT COALESCE(SUM(monto), 0)
            FROM costos_de_producto
            WHERE id_producto_final = ?
        )
        WHERE id_producto_final = ?;
        """;

        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate))
        {
            psUpdate.setInt(1, idProductoFinal);
            psUpdate.setInt(2, idProductoFinal);
            psUpdate.executeUpdate();
        }
    }

    private void guardarCostosDeProducto(ProductoFinal productoFinal, Connection conn) throws SQLException
    {
        final String sqlDetalle = """
        INSERT INTO costos_de_producto (id_producto, id_tipo_costo, costo_total)
        VALUES (?, ?, ?);
        """;

        try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle))
        {
            for (CostoDeProducto costo : productoFinal.getCostos())
            {
                psDetalle.setInt(1, productoFinal.getId());
                psDetalle.setInt(2, costo.getTipoCosto().getId());
                psDetalle.setDouble(3, costo.getCosteable().getMonto());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();
        }
    }

    @Override
    public boolean modificarProductoFinal(ProductoFinal productoFinal) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarProductoFinal(int id) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<ProductoFinal> buscarProductoFinalPorId(int id) throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProductoFinal> ListarProductosFinales() throws PersistenciaException
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
