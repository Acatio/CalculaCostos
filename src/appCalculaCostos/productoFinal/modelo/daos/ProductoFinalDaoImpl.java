/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

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

    @Override
    public void guardarProductoFinalYsusCostos(ProductoFinal productoFinal) throws PersistenciaException
    {
        try (Connection conn = conexion.getConnection())
        {
            
            conn.setAutoCommit(false);
            // 1. Insertar producto y obtener ID usando método privado
            int idProductoFinal = guardarProducto(conn, productoFinal);

            // 2. Guardar costos asociados
            productoFinal.setId(idProductoFinal);
            guardarCostosDeProducto(productoFinal, conn);

            // 3. Actualizar costo_total
            actualizarCostoProductoFinalCalculado(conn, idProductoFinal);

            // 4. Confirmar transacción
            conn.commit();

        } catch (SQLException | ConexionException e)
        {
            e.printStackTrace();
            throw new PersistenciaException("Error al guardar el Producto. Contacte al tecnico.", e);
        } 
    }

    private void guardarCostosDeProducto(ProductoFinal productoFinal, Connection conn) throws PersistenciaException
    {
        for (CostoDeModulo costo : productoFinal.getCostos())
        {
            
            RepoDeDetallesFactory factory =new RepoDeDetallesFactory();
            IRepositorioDetalles repoDetalles = factory.crearRepo(costo);//se crea un repositorio de acuerdo al tipo de costo
            repoDetalles.eliminarDetallesPorProducto(productoFinal.getId(), conn);
            repoDetalles.insertarDetalles(productoFinal.getId(), costo.getDetalles(), conn);
        }
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

    private void actualizarCostoProductoFinalCalculado(Connection conn, int idProductoFinal) throws SQLException
    {
        final String sqlUpdate = """
        UPDATE productos_finales
        SET costo_total = (
            SELECT COALESCE(SUM(costo_total), 0)
            FROM costos_de_producto
            WHERE id_producto = ?
        )
        WHERE id_producto = ?;
        """;

        try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate))
        {
            psUpdate.setInt(1, idProductoFinal);
            psUpdate.setInt(2, idProductoFinal);
            psUpdate.executeUpdate();
        }
    }

    @Override
    public List<ProductoFinal> ListarProductosFinales() throws PersistenciaException
    {
        List<ProductoFinal> productos = new ArrayList<>();

        final String sql = "SELECT id_producto, nombre, porcentaje_ganancia, precio_venta, costo_total FROM productos_finales";

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery())
        {

            while (rs.next())
            {
                ProductoFinal producto = new ProductoFinal(rs.getInt("id_producto"),rs.getString("nombre"),rs.getDouble("cantidadVendida"),rs.getDouble("porcentaje_ganancia"),rs.getDouble("precio_venta"),rs.getDouble("costo_total"));
         
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

}
