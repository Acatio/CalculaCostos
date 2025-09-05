/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.validaciones;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.CostoDeProducto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.ProductoFinal;

/**
 *
 * @author jose
 */
public class ValidadorProductoFinal
{

    public static void validar(ProductoFinal productoFinal) throws DatosNoValidosException
    {
        if (productoFinal == null || productoFinal.getNombre() == null || productoFinal.getNombre().isBlank())
        {
            throw new DatosNoValidosException("El producto debe tener un nombre.");
        }
        if (productoFinal.getCostos() == null)
        {
            throw new DatosNoValidosException("La lista de costos no se ha creado.");
        }
        for (CostoDeProducto costo : productoFinal.getCostos())
        {
            if (costo == null || costo.getTipoCosto() == null || costo.getCosteable() == null)
            {
                throw new DatosNoValidosException("Costo de producto inválido.");
            }
        }
    }
}
