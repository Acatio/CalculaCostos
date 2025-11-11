/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.Servicio;

import appCalculaCostos.costosFijos.Modelo.Entidades.CostoFijo;
import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.dao.ICostoFijoRepo;
import appCalculaCostos.costosFijos.Modelo.dto.CostoFijoDto;
import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
import java.util.ArrayList;
import java.util.List;

public class CostosFijosService
{

    private final ICostoFijoRepo repoCostosFijos;

    public CostosFijosService(ICostoFijoRepo repoCostosFijos)
    {
        this.repoCostosFijos = repoCostosFijos;
    }

    public void guardarCostoFijo(CostoFijoDto dto) throws CostoFijoException
    {
        try
        {
            validarCostoFijo(dto);
            CostoFijo entidadCosto = construirCostoFijo(dto);
            repoCostosFijos.guardarCostoFijo(entidadCosto);
        } catch (PersistenciaException | DatosNoValidosException ex)
        {
            throw new CostoFijoException("Error al guardar el costo fijo: " + ex.getMessage(), ex);
        }
    }

    private void validarCostoFijo(CostoFijoDto dto) throws DatosNoValidosException
    {
        if (dto == null)
        {
            throw new DatosNoValidosException("El costo fijo no puede ser nulo.");
        }

        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty())
        {
            throw new DatosNoValidosException("El nombre del costo fijo es obligatorio.");
        }

        if (dto.getImporteMensual() < 0)
        {
            throw new DatosNoValidosException("El importe mensual no puede ser negativo.");
        }

        if (dto.getPorcentajeUsado() < 0 || dto.getPorcentajeUsado() > 1)
        {
            throw new DatosNoValidosException("El porcentaje usado debe estar entre 0 y 1.");
        }
    }

    private CostoFijo construirCostoFijo(CostoFijoDto dto)
    {
        CostoFijo costo = new CostoFijo();
        costo.setId(dto.getId());
        costo.setNombre(dto.getNombre());
        costo.setImporteMensual(dto.getImporteMensual());
        costo.setPorcentajeUsado(dto.getPorcentajeUsado());
        return costo;
    }

    public void modificarCostoFijo(CostoFijoDto dto) throws CostoFijoException
    {
        try
        {
            validarCostoFijo(dto);
            CostoFijo entidadCosto = construirCostoFijo(dto);
            repoCostosFijos.actualizarCostoFijo(entidadCosto);
        } catch (PersistenciaException | DatosNoValidosException ex)
        {
            throw new CostoFijoException("Error al modificar el costo fijo: " + ex.getMessage(), ex);
        }
    }

    public void eliminarCostoFijo(int id) throws CostoFijoException
    {
        try
        {
            repoCostosFijos.eliminarrCostoFijo(id);
        } catch (PersistenciaException e)
        {
            throw new CostoFijoException("Error al eliminar el costo fijo con id " + id, e);
        }
    }

    public List<CostoFijoDto> listarTodos() throws CostoFijoException
    {
        try
        {
            List<CostoFijo> entidadesCostos = repoCostosFijos.listarCostosFijos();
            return construirDtos(entidadesCostos);
        } catch (PersistenciaException e)
        {
            throw new CostoFijoException("Error al listar los costos fijos", e);
        }
    }

    private List<CostoFijoDto> construirDtos(List<CostoFijo> entidadesCostos)
    {
        List<CostoFijoDto> listaDtos = new ArrayList<>();

        for (CostoFijo entidad : entidadesCostos)
        {
            CostoFijoDto dto = new CostoFijoDto();
            dto.setId(entidad.getId());
            dto.setNombre(entidad.getNombre());
            dto.setImporteMensual(entidad.getImporteMensual());
            dto.setPorcentajeUsado(entidad.getPorcentajeUsado());
            listaDtos.add(dto);
        }

        return listaDtos;
    }
}
