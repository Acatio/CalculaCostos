/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida;

/**
 *
 * @author jose
 */
public class UnidadMedidaFactory
{

    public static UnidadMedida obtenerUnidadDeMedidaPorNombre(String nombre)
    {
        switch (nombre)
        {
            case "Kilogramo" ->
            {
                return new Kilo();
            }
            case "Gramo" ->
            {
                return new Gramo();
            }
            case "Litro" ->
            {
                return new Litro();
            }
            case "Mililitro" ->
            {
                return new Mililitro();
            }
            case "Pieza" ->
            {
                return new Pieza();
            }
            default -> throw new AssertionError("Esa unidad de medida no es soportada: "+nombre);
        }
    }
}
