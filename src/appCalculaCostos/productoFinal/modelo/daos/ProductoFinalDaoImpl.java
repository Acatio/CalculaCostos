/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoCosto;
import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import conexion.Exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ServicioCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;
import conexion.Exepciones.ConexionException;
import conexion.interfacesLogicas.IConexion;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeModulo;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.RepoDeDetallesFactory;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioDetalles;

/**
 *
 * @author jose
 */
public class ProductoFinalDaoImpl implements IRepositorioProductoFinal
{

    final IConexion conexion;

    public ProductoFinalDaoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

// Método privado que solo inserta y devuelve el ID
    private int guardarProducto(Connection conn, ProductoFinal p) throws SQLException
    {
        final String sql = "INSERT INTO productos_finales (nombre, costo_total, porcentaje_ganancia,precio_venta, cantidad_vendida) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getCostoTotal());
            ps.setDouble(3, p.getPorcentajeGanancia());
            ps.setDouble(4, p.getPrecioVenta());
            ps.setDouble(5, p.getCantidadVendidaMes());
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

    @Override
    public void actualizarCostoProductoFinalCalculado(Connection conn, int idProductoFinal) throws PersistenciaException
    {
        final String sqlUpdate = """
        UPDATE productos_finales
        SET costo_total = (
            SELECT COALESCE(SUM(costo), 0)
            FROM insumos_de_producto
            WHERE id_producto = ?
        )
        WHERE id_producto = ?;
        """;

        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate))
        {
            psUpdate.setInt(1, idProductoFinal);
            psUpdate.setInt(2, idProductoFinal);
            psUpdate.executeUpdate();
        } catch (SQLException ex)
        {
            throw new PersistenciaException("No se pudo actualizar el costo del producto con id: " + idProductoFinal, ex);
        }

    }

    @Override
    public List<ProductoFinal> ListarProductosFinales() throws PersistenciaException
    {
        List<ProductoFinal> productos = new ArrayList<>();

        final String sql = "SELECT id_producto, nombre, porcentaje_ganancia, precio_venta, cantidad_vendida, costo_total FROM productos_finales";

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery())
        {

            while (rs.next())
            {
                ProductoFinal producto = new ProductoFinal(rs.getInt("id_producto"), rs.getString("nombre"), rs.getDouble("cantidad_vendida"), rs.getDouble("porcentaje_ganancia"), rs.getDouble("precio_venta"), rs.getDouble("costo_total"));

                // si quieres, aquí también podrías cargar los costos asociados
                // producto.setCostos(obtenerCostosDeProducto(conn, producto.getId()));
                productos.add(producto);
            }

        } catch (SQLException | ConexionException e)
        {
            e.printStackTrace();
            throw new PersistenciaException("Error al listar productos finales.", e);
        }

        return productos;
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
    public void guardarProductoFinalSinCostos(ProductoFinal productoFinal) throws PersistenciaException
    {
        try (Connection conn = conexion.getConnection())
        {
            guardarProducto(conn, productoFinal);
        } catch (SQLException | ConexionException e)
        {
            e.printStackTrace();
            throw new PersistenciaException("Error al guardar el Producto. Contacte al tecnico.", e);
        }
    }
  
    @Override
    public void borrarCostotosDeProductoPorTipo(int idProductoFinal, TipoCosto tipoCosto,Connection conn) throws PersistenciaException
    {
        String sql = "DELETE FROM insumos_de_producto WHERE id_producto=? AND tipo=?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setInt(1, idProductoFinal);
            ps.setString(2, tipoCosto.name());
            ps.executeUpdate();

        } catch (SQLException ex)
        {
            throw new PersistenciaException("No se pudieron borrar los antiguos costos de: "
                    + tipoCosto.name(), ex);
        }
    }
}
