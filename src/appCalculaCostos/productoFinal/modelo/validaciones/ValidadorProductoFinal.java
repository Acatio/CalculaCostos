/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.validaciones;

import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.CostoModuloDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;


/**
 *
 * @author jose
 */
public class ValidadorProductoFinal
{

    public static void validarDatos(ProductoFinalCreacionDTO dto) throws DatosNoValidosException
    {
        if (dto == null)
        {
            throw new DatosNoValidosException("El producto no es valido");
        }
        if (dto.nombre()== null || dto.nombre().isBlank())
        {
            throw new DatosNoValidosException("El producto debe tener un nombre.");
        }
        if (dto.porcentajeGanancia()!=null && dto.porcentajeGanancia() < 0)
        {
            throw new DatosNoValidosException("El producto debe tener un porcentaje de ganancia mayor o igual a cero.");
        }
        if (dto.precioVenta()!=null && dto.precioVenta()< 0)
        {
            throw new DatosNoValidosException("El producto debe tener un precio de venta mayor o igual a cero.");
        }
        if (dto.costos() == null)
        {
            throw new DatosNoValidosException("La lista de costos no es valida");
        }
        if (dto.cantidadVendida()<0)
        {
            throw new DatosNoValidosException("La cantidad vendida no puede ser menor a cero");
        }
        for (CostoModuloDTO costo : dto.costos())
        {
            if (costo == null)
            {
                throw new DatosNoValidosException("Costo de producto invalido.");
            }
        }
    }
}
