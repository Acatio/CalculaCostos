/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author jose
 */
public interface IConexion
{
        public abstract Connection getConnection() throws SQLException;

    
}
