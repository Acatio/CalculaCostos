/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Entidades;

import java.util.List;

/**
 *
 * @author jose
 */
public class ManoObraDeProducto
{
    private int idProducto;
    private List<ManoObra> manoDeObra;

    public ManoObraDeProducto()
    {
    }

    public int getIdProducto()
    {
        return idProducto;
    }

    public void setIdProducto(int idProducto)
    {
        this.idProducto = idProducto;
    }

    public List<ManoObra> getManoDeObra()
    {
        return manoDeObra;
    }

    public void setManoDeObra(List<ManoObra> manoDeObra)
    {
        this.manoDeObra = manoDeObra;
    }
    
}
