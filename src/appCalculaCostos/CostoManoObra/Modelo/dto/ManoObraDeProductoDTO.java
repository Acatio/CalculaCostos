/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.dto;

import java.util.List;

/**
 *
 * @author jose
 */
public class ManoObraDeProductoDTO {

    private int idProducto;
    private List<ManoObraDTO> listaManoObra;

    public ManoObraDeProductoDTO(int idProducto, List<ManoObraDTO> listaManoObra) {
        this.idProducto = idProducto;
        this.listaManoObra = listaManoObra;
    }

    public int getIdProducto() { return idProducto; }
    public List<ManoObraDTO> getListaManoObra() { return listaManoObra; }

}
