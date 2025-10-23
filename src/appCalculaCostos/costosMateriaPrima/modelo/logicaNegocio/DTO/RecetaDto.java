/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DetalleReceta;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoInsumo;
import java.util.List;

/**
 *
 * @author jose
 */
public record RecetaDto(
        String nombre,
        double cantidad,
        String nombreUnidadDeMedida,
        TipoInsumo tipoInsumo,
        List<DetalleRecetaDto> costos
        )implements IDatosComunes
        {}
