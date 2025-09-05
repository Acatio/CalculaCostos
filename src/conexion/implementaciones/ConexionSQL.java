package conexion.implementaciones;

import conexion.Exepciones.ConexionException;
import conexion.interfacesLogicas.IConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionSQL implements IConexion
{

    private static final Logger LOG = Logger.getLogger(ConexionSQL.class.getName());

    // Datos de conexión
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DB = "bd_costos";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB + "?useSSL=false&serverTimezone=UTC";

    @Override
    public Connection getConnection() throws ConexionException
    {
        try
        {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e)
        {
            LOG.log(Level.SEVERE, "Error al conectar la base de datos: ", e);
            throw new ConexionException("Error al conectar con la base de datos", e);
        }
    }

    public static void main(String[] args)
    {
        ConexionSQL conexion = new ConexionSQL();
        try (Connection conn = conexion.getConnection())
        {
            if (conn != null && !conn.isClosed())
            {
                System.out.println("Conexion exitosa " + conn.getCatalog());
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
