/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO;

import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoInsumo;

/**
 *
 * @author jose
 */
public interface IDatosComunes
{
    String nombre();
    double cantidad();
    String nombreUnidadDeMedida();
    TipoInsumo tipoInsumo();
}
