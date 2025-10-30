/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO;

/**
 *
 * @author jose
 */
public record InsumoDto(
        int id,
        String nombre,
        double cantidad,
        String unidadMedida,
        String tipoInsumo,
        double costo
        )
        {

}
