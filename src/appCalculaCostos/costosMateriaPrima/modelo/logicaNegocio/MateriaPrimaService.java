/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedidaFactory;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.DetalleRecetaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.IDatosComunes;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.MateriaPrimaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.RecetaDto;
import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import conexion.Exepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jose
 */
public class MateriaPrimaService
{

    IInsumoDAO repoInsumos;

    public MateriaPrimaService(IInsumoDAO repoInsumos)
    {
        this.repoInsumos = repoInsumos;
    }

    public void guardarMateriaPrima(MateriaPrimaDto dto) throws InsumoException
    {
        try
        {
            validarDatosComunes(dto);
            validarCosto(dto);
            MateriaPrima nuevo = crearMateriaPrima(dto);
            repoInsumos.guardarMateriaPrima(nuevo);
        } catch (DatosNoValidosException ex)
        {
            throw new InsumoException(ex.getMessage());
        } catch (PersistenciaException ex)
        {
            throw new InsumoException("Ocurrio un error al guardar el insumo");
        }

    }

    public void guardarReceta(RecetaDto dto) throws InsumoException
    {
        try
        {
            validarDatosComunes(dto);
            validarDetalles(dto);
            Receta nuevo = crearReceta(dto);
            repoInsumos.guardarReceta(nuevo);
        } catch (DatosNoValidosException ex)
        {
            ex.printStackTrace();
            throw new InsumoException(ex.getMessage());
        } catch (PersistenciaException ex)
        {
            ex.printStackTrace();
            throw new InsumoException("Ocurrio un error al guardar el insumo: "+ex.getMessage());
        }

    }

    private void validarDatosComunes(IDatosComunes dto) throws DatosNoValidosException
    {

        if (dto == null)
        {
            throw new IllegalArgumentException("El dto no es valido");
        }
        if (dto.nombreUnidadDeMedida() == null || dto.nombreUnidadDeMedida().isBlank())
        {
            throw new IllegalArgumentException("La unidad de medida no es valida");
        }
        if (dto.tipoInsumo() == null || (dto.tipoInsumo() != TipoInsumo.MATERIA_PRIMA && dto.tipoInsumo() != TipoInsumo.RECETA))
        {
            throw new IllegalArgumentException("El tipo de insumo no es valido");
        }
        if (dto.nombre().isBlank())
        {
            throw new IllegalArgumentException("La unidad de medida no tiene un nombre valido");
        }
        if (dto.nombre().isBlank())
        {
            throw new DatosNoValidosException("El nombre no puede estar vacio");
        }
        if (dto.cantidad() <= 0)
        {
            throw new DatosNoValidosException("La cantidad no puede ser menor o igual a cero");
        }
    }

    private MateriaPrima crearMateriaPrima(MateriaPrimaDto dto)
    {
        UnidadMedida unidadMedida = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(dto.nombreUnidadDeMedida());
        MateriaPrima nuevo = new MateriaPrima(dto.nombre(), dto.cantidad(), unidadMedida, dto.costo());
        return nuevo;
    }

    private Receta crearReceta(RecetaDto dto) throws PersistenciaException, InsumoException
    {
        UnidadMedida unidadMedida = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(dto.nombreUnidadDeMedida());
        Receta nuevo = new Receta(dto.nombre(), dto.cantidad(), unidadMedida);
        List<DetalleReceta> detalles = crearDetalles(dto.costos());
        nuevo.setIngredientes(detalles);
        return nuevo;

    }

    private void validarDetalles(RecetaDto dto) throws DatosNoValidosException
    {
        System.out.println("validando detalles");
    }

    private void validarCosto(MateriaPrimaDto dto) throws DatosNoValidosException
    {
        if (dto.costo() < 0)
        {
            throw new DatosNoValidosException("El costo no puede ser menor a cero");
        }
    }

    private List<DetalleReceta> crearDetalles(List<DetalleRecetaDto> detallesDto) throws PersistenciaException, InsumoException
    {
        ArrayList<DetalleReceta> detalles = new ArrayList<>();
        for (DetalleRecetaDto detalleDto : detallesDto)
        {
            DetalleReceta detalle = crearDetalle(detalleDto);
            detalles.add(detalle);
        }
        return detalles;
    }

    private DetalleReceta crearDetalle(DetalleRecetaDto detalleDto) throws PersistenciaException, InsumoException
    {

        var optionalInsumo = repoInsumos.buscarInsumoPorID(detalleDto.getIdInsumo());
        if (optionalInsumo.isEmpty())
        {
            throw new InsumoException("No se encontró el insumo con ID " + detalleDto.getIdInsumo());
        }
        var insumo = optionalInsumo.get();
        var unidadMedida = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(detalleDto.getNombreUnidadMedida());

        return new DetalleReceta(insumo, detalleDto.getCantidad(), unidadMedida);

    }

    public List<InsumoDto> listarInsumos() throws InsumoException
    {
        try
        {
            return repoInsumos.listarDtoInsumos();
        } catch (PersistenciaException ex)
        {
            throw new InsumoException("Error: No se pudieron cargar los insumos...");
        }
    }

}
