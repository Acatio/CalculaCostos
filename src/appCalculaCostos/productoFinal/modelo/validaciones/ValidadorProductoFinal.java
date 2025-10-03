/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.validaciones;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeProducto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;

/**
 *
 * @author jose
 */
public class ValidadorProductoFinal
{

    public static void validar(ProductoFinal productoFinal) throws DatosNoValidosException
    {
        if (productoFinal == null)
        {
            throw new DatosNoValidosException("El producto no se ha inicializado correctamente.");
        }
        if (productoFinal.getNombre() == null || productoFinal.getNombre().isBlank())
        {
            throw new DatosNoValidosException("El producto debe tener un nombre.");
        }
        if (productoFinal.getPorcentajeGanancia() < 0)
        {
            throw new DatosNoValidosException("El producto debe tener un porcentaje de ganancia mayor o igual a cero.");
        }
        if (productoFinal.getCostos() == null)
        {
            throw new DatosNoValidosException("La lista de costos no se ha creado.");
        }
        for (CostoDeProducto costo : productoFinal.getCostos())
        {
            if (costo == null || costo.getNombre() == null || costo.getNombre().isBlank() || costo.getCosteable() == null)
            {
                throw new DatosNoValidosException("Costo de producto invalido.");
            }
        }
    }
}
