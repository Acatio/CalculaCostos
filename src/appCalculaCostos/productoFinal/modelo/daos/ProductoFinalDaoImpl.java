/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoCosto;
import conexion.Exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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

    // Actualiza el costo total (materia prima + costos fijos) en productos_finales
    @Override
    public void actualizarCostoTotal(int idProducto, double costoTotal) throws PersistenciaException
    {
        String sql = "UPDATE productos_finales SET costo_total = ? WHERE id_producto = ?";
        try (Connection conn=conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setDouble(1, costoTotal);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (SQLException |ConexionException e)
        {
            throw new PersistenciaException("No se pudo actualizar costo total", e);
        }
    }

    @Override
    public double obtenerCostoMateriaPrima(int idProducto) throws PersistenciaException
    {
        String sql = """
        SELECT COALESCE(SUM(costo), 0) AS total_mp
        FROM insumos_de_producto
        WHERE id_producto = ?
    """;

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, idProducto);

            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getDouble("total_mp");
                } else
                {
                    return 0.0;
                }
            }
        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al obtener costo materia prima para producto " + idProducto, e);
        }
    }

    @Override
    public double obtenerCostoFijoAsignado(int idProducto) throws PersistenciaException
    {
        String sql = """
        SELECT costo_asignado
        FROM producto_costo_fijo
        WHERE id_producto = ?
    """;

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, idProducto);

            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getDouble("costo_asignado");
                } else
                {
                    // Si no existe registro de costo fijo asignado, asumimos 0
                    return 0.0;
                }
            }
        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al obtener costo fijo asignado para producto " + idProducto, e);
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
        final String sql = "SELECT  nombre, costo_total, porcentaje_ganancia, precio_venta, cantidad_vendida FROM  productos_finales  WHERE id_producto=?";
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            ProductoFinal producto = null;
            if (rs.next())
            {
                var nombre = rs.getString("nombre");
                var costo = rs.getDouble("costo_total");
                var porcentaje = rs.getDouble("porcentaje_ganancia");
                var precio = rs.getDouble("precio_venta");
                var cantVendida = rs.getDouble("cantidad_vendida");
                producto = new ProductoFinal(id, nombre, cantVendida, porcentaje, precio, costo);
            }
            return Optional.ofNullable(producto);
        } catch (SQLException | ConexionException ex)
        {
            ex.printStackTrace();
            throw new PersistenciaException("No se encontro un producto con id: " + id, ex);
        }
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
    public void borrarCostotosDeProductoPorTipo(int idProductoFinal, TipoCosto tipoCosto, Connection conn) throws PersistenciaException
    {
        String sql = "DELETE FROM insumos_de_producto WHERE id_producto=? AND tipo=?";
        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, idProductoFinal);
            ps.setString(2, tipoCosto.name());
            System.out.println("borrando del id " + idProductoFinal);
            System.out.println("tipo " + tipoCosto.name());
            ps.executeUpdate();
        } catch (SQLException ex)
        {
            throw new PersistenciaException("Error al borrar costos de tipo " + tipoCosto.name(), ex);
        }
    }

    @Override
    public void actualizarDatosProductoFinal(ProductoFinal productoActualizado) throws PersistenciaException
    {
        String sql = """
        UPDATE productos_finales
        SET nombre = ?, 
            porcentaje_ganancia = ?, 
            precio_venta = ?, 
            cantidad_vendida = ?
        WHERE id_producto = ?
    """;

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setString(1, productoActualizado.getNombre());
            stmt.setDouble(2, productoActualizado.getPorcentajeGanancia());
            stmt.setDouble(3, productoActualizado.getPrecioVenta());
            stmt.setDouble(4, productoActualizado.getCantidadVendida());
            stmt.setInt(5, productoActualizado.getId());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0)
            {
                throw new PersistenciaException("No se encontró un producto con el ID especificado: " + productoActualizado.getId());
            }

        } catch (SQLException | ConexionException ex)
        {
            throw new PersistenciaException("Error al actualizar los datos del producto: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void borrarProductoFinal(int idProducto) throws PersistenciaException
    {
        String sql = "DELETE FROM productos_finales WHERE id_producto = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, idProducto);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0)
            {
                throw new PersistenciaException("No se encontró un producto con el ID especificado: " + idProducto);
            }

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al eliminar el producto con ID " + idProducto + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarPorcentajeGanancia(int idProducto, double nuevoPorcentaje) throws PersistenciaException
    {
        String sql = "UPDATE productos_finales SET porcentaje_ganancia = ? WHERE id_producto = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {

            ps.setDouble(1, nuevoPorcentaje);
            ps.setInt(2, idProducto);
            ps.executeUpdate();

        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al actualizar el porcentaje de ganancia", e);
        }
    }

    @Override
    public List<Integer> listarIdProductosFinales() throws PersistenciaException
    {
        String sql = "SELECT id_producto FROM productos_finales";
        List<Integer> ids = new ArrayList<>();

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery())
        {
            while (rs.next())
            {
                ids.add(rs.getInt("id_producto"));
            }
        } catch (SQLException | ConexionException e)
        {
            throw new PersistenciaException("Error al obtener productos finales.", e);
        }

        return ids;
    }

}
