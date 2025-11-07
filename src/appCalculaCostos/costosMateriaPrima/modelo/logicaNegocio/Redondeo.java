/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

/**
 *
 * @author jose
 */
public class Redondeo
{

    public static double redondear(double valor, int decimales)
    {
        double factor = Math.pow(10, decimales);
        return Math.round(valor * factor) / factor;
    }
}
