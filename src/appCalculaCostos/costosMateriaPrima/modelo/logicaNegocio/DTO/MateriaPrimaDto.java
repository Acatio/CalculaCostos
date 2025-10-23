/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoInsumo;

/**
 *
 * @author jose
 */
public record MateriaPrimaDto(
        String nombre,
        double cantidad,
        String nombreUnidadDeMedida,
        TipoInsumo tipoInsumo,
        double costo
        ) implements IDatosComunes
        {}
