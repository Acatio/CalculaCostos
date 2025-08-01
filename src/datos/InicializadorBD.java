/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

/**
 *
 * @author jose
 */
public class InicializadorBD
{

    public static void inicializarBD()
    {
        crearTablaMateriaPrima();
        crearTablaReceta();

    }

    private static void crearTablaMateriaPrima()
    {
        final String SQL = """
                           CREATE TABLE IF NOT EXISTS materiaPrima (id INTEGER, )
                        
                        """;
    }

    private static void crearTablaReceta()
    {
    }
}
