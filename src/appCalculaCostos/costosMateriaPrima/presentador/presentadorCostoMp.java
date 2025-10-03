/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.presentador;

import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.CostoMateriaPrima;
import appCalculaCostos.costosMateriaPrima.vista.interfacesLogicas.IVistaCostosMp;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
public class presentadorCostoMp
{

    IVistaCostosMp vista;
    IInsumoDAO dao;
    CostoMateriaPrima costoMp;

    public presentadorCostoMp(IVistaCostosMp vista, IInsumoDAO dao)
    {
        this.vista = vista;
        this.dao = dao;
        costoMp = new CostoMateriaPrima();
    }

    public void inicializar()
    {

    }

    public void inicializarMostrarInsumos()
    {
        vista.setMostrarInsumosListener(() ->
        {
            try
            {
                var insumos = dao.ListarInsumos();
                vista.mostrarInsumos(insumos);
            } catch (PersistenciaException ex)
            {
                vista.mostrarMensajeError(ex.getMessage());
            }
        });
    }

    public void inicializarMostrarIngredientes()
    {
        vista.setMostrarIngredientesListener(() ->
        {
            vista.mostrarIngredientes(costoMp);
        });
    }

    public void agregarIngrediente()
    {
        vista.setAgregarIngredientesListener(() ->
        {
            try
            {
                var idSeleccionado = vista.getIDInsumoSeleccionado();
                var cantidad = vista.getCantidadInsumo();
                var insumo = dao.buscarInsumoPorID(idSeleccionado);
            } catch (PersistenciaException ex)
            {
                vista.mostrarMensajeError(ex.getMessage());
            }
        });
    }

    public void actualizarVista()
    {
    }
}
