/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.daos;

import com.sun.jdi.connect.spi.Connection;
import conexion.Conexion;
import conexion.ConexionSQL;
import conexion.IConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.UnidadDeMedida;

/**
 *
 * @author chemo
 */
public class UnidadDeMedidaDAO
{

    public UnidadDeMedida buscarPorNombre(String nombre) throws Exception
    {
        final String sql = "SELECT id, nombre, simbolo FROM unidades_medida WHERE nombre = ?";

        try (java.sql.Connection conn = Conexion.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql))
        {

            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                return new UnidadDeMedida(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("simbolo")
                );
            } else
            {
                throw new Exception("No existe la unidad de medida: " + nombre);
            }
        }
    }
}
