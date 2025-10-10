/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author jose
 */
public interface IRepoCosto
{
    public void guardarDetalles(int id,Connection con,List<IDetalleCosto>detalles);
}
