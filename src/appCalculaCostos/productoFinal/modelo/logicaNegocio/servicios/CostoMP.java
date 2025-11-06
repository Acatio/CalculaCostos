/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios;

import appCalculaCostos.productoFinal.modelo.exepciones.NoPosibleCalcularMonto;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IDetalleCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeModulo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jose
 */
public class CostoMP extends CostoDeModulo
{

    public CostoMP(List<IDetalleCosto> detalles)
    {
        super(detalles);
    }

    @Override
    public double calcularMontoTotal() throws NoPosibleCalcularMonto
    {
            return super.calcularMontoBase();
    }

}
