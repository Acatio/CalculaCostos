/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.excepciones;

/**
 *
 * @author jose
 */
public class NoPosibleConversion extends Exception
{
    public NoPosibleConversion(String message, Throwable cause)
    {
        super(message, cause);
    }
    public NoPosibleConversion(String message)
    {
        super(message);
    }
}
