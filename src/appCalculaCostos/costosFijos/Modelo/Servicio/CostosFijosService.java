/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosFijos.Modelo.Servicio;

import appCalculaCostos.costosFijos.Modelo.Entidades.CostoFijo;
import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.dao.ICostoFijoRepo;
import appCalculaCostos.costosFijos.Modelo.dto.CostoFijoDto;
import appCalculaCostos.costosFijos.Modelo.dto.PonderacionDto;
import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import conexion.Exepciones.PersistenciaException;

/**
 *
 * @author jose
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void guardarPonderacion(PonderacionDto dto) throws CostoFijoException
    {
        try
        {
            validarPonderacion(dto);
            Ponderacion entidadDominio = construirPonderacion(dto);
            repoCostosFijos.guardarPonderacion(entidadDominio);
        } catch (PersistenciaException | DatosNoValidosException ex)
        {
            throw new CostoFijoException(ex.getMessage(), ex);
        }
    }

    public void editarPonderacion(PonderacionDto dto) throws CostoFijoException
    {
        try
        {
            validarPonderacion(dto);
            Ponderacion entidadDominio = construirPonderacion(dto);
            repoCostosFijos.actualizarPonderacion(entidadDominio);
        } catch (PersistenciaException | DatosNoValidosException ex)
        {
            throw new CostoFijoException(ex.getMessage(), ex);
        }
    }

    public PonderacionDto cargarPonderacion(int idProducto) throws CostoFijoException
    {
        try
        {
            Optional<Ponderacion> opPonderacion = repoCostosFijos.cargarPonderacion(idProducto);

            if (opPonderacion.isPresent())
            {
                return construirPonderacionDto(opPonderacion.get());
            } else
            {
                // Si no hay ponderación, se devuelve un DTO vacío con el id del producto
                PonderacionDto dtoVacio = new PonderacionDto();
                dtoVacio.setIdProducto(idProducto);
                dtoVacio.setTamanio(0);
                dtoVacio.setTiempoPreparacion(0);
                dtoVacio.setCantidadRecursosUsados(0);
                return dtoVacio;
            }

        } catch (PersistenciaException ex)
        {
            throw new CostoFijoException("Error al cargar la ponderación del producto.", ex);
        }
    }

    // --- Métodos auxiliares ---
    private void validarPonderacion(PonderacionDto dto) throws DatosNoValidosException
    {
        if (dto == null)
        {
            throw new DatosNoValidosException("La ponderación no puede ser nula");
        }

        if (dto.getIdProducto() <= 0)
        {
            throw new DatosNoValidosException("El producto no es válido");
        }

        if (dto.getTamanio() < 1 || dto.getTamanio() > 5)
        {
            throw new DatosNoValidosException("El tamaño debe estar entre 1 y 5");
        }

        if (dto.getTiempoPreparacion() < 1 || dto.getTiempoPreparacion() > 5)
        {
            throw new DatosNoValidosException("El tiempo de preparación debe estar entre 1 y 5");
        }

        if (dto.getCantidadRecursosUsados() < 1 || dto.getCantidadRecursosUsados() > 5)
        {
            throw new DatosNoValidosException("La cantidad de recursos debe estar entre 1 y 5");
        }
    }

    private Ponderacion construirPonderacion(PonderacionDto dto)
    {
        Ponderacion entidad = new Ponderacion();
        entidad.setIdProducto(dto.getIdProducto());
        entidad.setTamanio(dto.getTamanio());
        entidad.setTiempoPreparacion(dto.getTiempoPreparacion());
        entidad.setCantidadRecursosUsados(dto.getCantidadRecursosUsados());
        return entidad;
    }

    private PonderacionDto construirPonderacionDto(Ponderacion entidad)
    {
        PonderacionDto dto = new PonderacionDto();
        dto.setIdProducto(entidad.getIdProducto());
        dto.setTamanio(entidad.getTamanio());
        dto.setTiempoPreparacion(entidad.getTiempoPreparacion());
        dto.setCantidadRecursosUsados(entidad.getCantidadRecursosUsados());
        return dto;
    }

}
