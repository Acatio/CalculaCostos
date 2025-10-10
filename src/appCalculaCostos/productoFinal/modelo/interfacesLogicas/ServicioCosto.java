/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import conexion.Exepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

/**
 *
 * @author jose
 */
public abstract class ServicioCosto
{

    protected int id;
    protected String nombre;
    protected IRepoCosto repoCosto;
    protected List<IDetalleCosto> detallesCosto;
    protected int idProductoAsociado;

    public ServicioCosto(int id, String nombre, IRepoCosto repoCosto, int idProductoAsociado)
    {
        this.id = id;
        this.nombre = nombre;
        this.repoCosto = repoCosto;
        this.idProductoAsociado = idProductoAsociado;
        detallesCosto=new ArrayList<>();
    }

    public ServicioCosto(String nombre, IRepoCosto repoCosto, int idProductoAsociado)
    {
        this.nombre = nombre;
        this.repoCosto = repoCosto;
        this.idProductoAsociado = idProductoAsociado;
        detallesCosto=new ArrayList<>();
    }

    public int getIdProductoAsociado(){return idProductoAsociado;}
    public int getIdTipoCosto(){ return id;}
    public String getNombreCosto(){return nombre;}
    public List<IDetalleCosto> getDetalles(){return detallesCosto;}
    
    public abstract void guardarCostos(int idProductoFinal, Connection conn) throws PersistenciaException;
    public abstract void agregarDetalle(IDetalleCosto detalle); 
    public void setIdProductoAsociado(int idProductoAsociado)
    {
        if (idProductoAsociado < 1)
        {
            throw new IllegalArgumentException("El id del producto debe ser mayor a cero");
        }
        this.idProductoAsociado = idProductoAsociado;
    }

    public void setIdTipoCosto(int idTipoCosto)
    {
        if (idTipoCosto < 1)
        {
            throw new IllegalArgumentException("El id del costo debe ser mayor a cero");
        }
        this.id = idTipoCosto;
    }

    public void setNombreCosto(String nombreCosto)
    {
        if (nombreCosto == null || nombreCosto.isBlank())
        {
            throw new IllegalArgumentException("El nombre del tipo de costo no es valido");
        }
        this.nombre = nombreCosto;
    }
    public double getMontoAsociado()
    {
        if (detallesCosto==null)
        {
            throw new IllegalStateException("No se ha inicializado la lista de detalles");
        }
        var total=0d;
        for (IDetalleCosto d:detallesCosto)
        {
            total+=d.getMonto();
        }
        return total;
    }
    


}
