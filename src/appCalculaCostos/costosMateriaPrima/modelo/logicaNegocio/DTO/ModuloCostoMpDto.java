/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO;

import java.util.List;

/**
 *
 * @author jose
 */
public class ModuloCostoMpDto 
{
    private int idProductoFinal;
    private List<DetalleRecetaDto> ingredientes;

    public ModuloCostoMpDto(int idProducto, List<DetalleRecetaDto> ingredientes)
    {
        this.ingredientes = ingredientes;
    }
    
    List<DetalleRecetaDto>  getDetalles()
    {
        return ingredientes;  
    }

    public List<DetalleRecetaDto> getIngredientes()
    {
        return ingredientes;
    }

    public void setIngredientes(List<DetalleRecetaDto> ingredientes)
    {
        this.ingredientes = ingredientes;
    }

    public int getIdProductoFinal()
    {
        return idProductoFinal;
    }

    public void setIdProductoFinal(int idProductoFinal)
    {
        this.idProductoFinal = idProductoFinal;
    }
    
    
}
