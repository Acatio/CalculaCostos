/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.exepciones;

/**
 *
 * @author jose
 */
public class NoPosibleCalcularMonto extends Exception
{

    public NoPosibleCalcularMonto(String message)
    {
        super(message);
    }

    public NoPosibleCalcularMonto(String message, Throwable cause)
    {
        super(message, cause);
    }

}
