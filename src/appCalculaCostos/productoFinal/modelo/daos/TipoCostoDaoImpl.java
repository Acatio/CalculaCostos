/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.daos;

import appCalculaCostos.productoFinal.modelo.exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ITipoCostoDao;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.TipoCosto;
import conexion.Exepciones.ConexionException;
import conexion.interfacesLogicas.IConexion;
import java.sql.*;

/**
 *
 * @author jose
 */
public class TipoCostoDaoImpl implements ITipoCostoDao
{

    final IConexion conexion;

    public TipoCostoDaoImpl(IConexion conexion)
    {
        this.conexion = conexion;
    }

    @Override
    public void guardarTipoCosto(TipoCosto tipoCosto) throws PersistenciaException
    {
        final String sql = """
            INSERT INTO tipo_costo (nombre)
            VALUES (?);
            """;
        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, tipoCosto.getNombre());
            ps.executeUpdate();
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            throw new PersistenciaException("Error al guardar el tipo de costo. Contacte al técnico.", e);
        } catch (ConexionException e)
        {
            throw new PersistenciaException("Error al conectar con la base de datos.", e);   
        }
    }

    @Override
    public void modificarTipoCosto(TipoCosto tipoCosto)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarTipoCosto(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
