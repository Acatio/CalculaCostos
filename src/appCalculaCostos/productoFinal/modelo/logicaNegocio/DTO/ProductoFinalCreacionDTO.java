/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO;

import appCalculaCostos.productoFinal.modelo.interfacesLogicas.CostoModuloDTO;
import java.util.List;

/**
 *
 * @author jose
 */
public record ProductoFinalCreacionDTO(
    String nombre,
    int cantidadVendida,
    Double porcentajeGanancia,
    Double precioVenta,
    List<CostoModuloDTO> costos
) {}