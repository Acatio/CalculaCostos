/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.exepciones;

/**
 *
 * @author jose
 */
public class DatosNoValidosException extends Exception
{
    public DatosNoValidosException(String message, Throwable cause)
    {
        super(message, cause);
    }
    public DatosNoValidosException(String message)
    {
        super(message);
    }
}
