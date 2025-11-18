/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Servicio;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObra;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObraDeProducto;
import appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException;
import appCalculaCostos.CostoManoObra.Modelo.dao.ICostoManoObraRepo;
import appCalculaCostos.CostoManoObra.Modelo.dao.IEmpleadoRepo;
import appCalculaCostos.CostoManoObra.Modelo.dto.EmpleadoProductoTiempoDTO;
import appCalculaCostos.CostoManoObra.Modelo.dto.MostrarManoObraDTO;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import conexion.Exepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jose
 */
public class CalcularCostoMoService
{

    private final IEmpleadoRepo empleadoRepo;
    private final ICostoManoObraRepo manoObraRepo;
    private final IRepositorioProductoFinal productoFinalRepo;

    public CalcularCostoMoService(
            IEmpleadoRepo empleadoRepo,
            ICostoManoObraRepo manoObraRepo,
            IRepositorioProductoFinal productoFinalRepo
    )
    {
        this.empleadoRepo = empleadoRepo;
        this.manoObraRepo = manoObraRepo;
        this.productoFinalRepo = productoFinalRepo;
    }

    // ================================================================
    //  GUARDAR COMPLETA LA MANO DE OBRA DE UN PRODUCTO
    // ================================================================
    public void guardarManoObraDeProducto(int idProducto, List<EmpleadoProductoTiempoDTO> listaDto)
            throws ManoObraException
    {

        validarLista(idProducto, listaDto);

        try
        {
            // 1. Validar que el producto exista
            if (productoFinalRepo.buscarProductoFinalPorId(idProducto).isEmpty())
            {
                throw new ManoObraException("No existe el producto especificado.");
            }

            // 2. Convertir DTOs a entidad de dominio
            ManoObraDeProducto dominio = construirEntidadDominio(idProducto, listaDto);

            // 3. Guardar en BD
            manoObraRepo.guardarCostosManoObra(List.of(dominio));

        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al guardar la mano de obra: " + e.getMessage(), e);
        }
    }

    // ================================================================
    //  ACTUALIZAR MANO DE OBRA COMPLETA DE UN PRODUCTO
    // ================================================================
    public void actualizarManoObraDeProducto(int idProducto, List<EmpleadoProductoTiempoDTO> listaDto)
            throws ManoObraException
    {

        validarLista(idProducto, listaDto);

        try
        {
            // 1. Verificar que el producto exista
            if (productoFinalRepo.buscarProductoFinalPorId(idProducto).isEmpty())
            {
                throw new ManoObraException("No existe el producto especificado.");
            }

            // 2. Construir la entidad nueva
            ManoObraDeProducto dominio = construirEntidadDominio(idProducto, listaDto);

            // 3. Actualizar todo
            manoObraRepo.actualizarCostosManoObra(List.of(dominio));

        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al actualizar la mano de obra: " + e.getMessage(), e);
        }
    }

    // ================================================================
    //  LISTAR MANO DE OBRA
    // ================================================================
    public List<MostrarManoObraDTO> listarManoObraPorProducto(int idProducto)
            throws ManoObraException
    {

        if (idProducto <= 0)
        {
            throw new IllegalArgumentException("El idProducto debe ser mayor a cero.");
        }

        try
        {
            var lista = manoObraRepo.listarCostosManoObra(idProducto);

            if (lista.isEmpty())
            {
                return List.of();
            }

            return construirDtos(lista.get(0).getManoDeObra());

        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al listar mano de obra: " + e.getMessage(), e);
        }
    }

    // ================================================================
    //  CONVERSIONES
    // ================================================================
    private ManoObraDeProducto construirEntidadDominio(
            int idProducto,
            List<EmpleadoProductoTiempoDTO> listaDto
    ) throws ManoObraException, PersistenciaException
    {

        List<ManoObra> lista = new ArrayList<>();

        for (EmpleadoProductoTiempoDTO dto : listaDto)
        {

            // Cargar empleado
            var empOpt = empleadoRepo.buscarEmpleado(dto.getIdEmpleado());
            if (empOpt.isEmpty())
            {
                throw new ManoObraException("No existe el empleado con id " + dto.getIdEmpleado());
            }

            var empleado = empOpt.get();

            // Calcular costo
            double costo = calcularCosto(
                    empleado.getPrecioPorHora(),
                    dto.getTiempoAportado()
            );

            // Crear relación
            ManoObra mo = new ManoObra();
            mo.setEmpleado(empleado);
            mo.setTiempoAportado(dto.getTiempoAportado());
            mo.setCostoCalculado(costo);

            lista.add(mo);
        }

        ManoObraDeProducto mop = new ManoObraDeProducto();
        mop.setIdProducto(idProducto);
        mop.setManoDeObra(lista);

        return mop;
    }

    private List<MostrarManoObraDTO> construirDtos(List<ManoObra> lista)
    {
        List<MostrarManoObraDTO> dtos = new ArrayList<>();

        for (ManoObra mo : lista)
        {
            MostrarManoObraDTO dto = new MostrarManoObraDTO();
            dto.setIdEmpleado(mo.getEmpleado().getId());
            dto.setNombreEmpleado(mo.getEmpleado().getNombre());
            dto.setApellidoEmpleado(mo.getEmpleado().getApellido());
            dto.setTiempoInvertido(mo.getTiempoAportado());
            dto.setCostoAsociado(mo.getCostoCalculado());
            dtos.add(dto);
        }

        return dtos;
    }

    // ================================================================
    //  VALIDACIONES
    // ================================================================
    private void validarLista(int idProducto, List<EmpleadoProductoTiempoDTO> lista)
            throws ManoObraException
    {

        if (lista == null)
        {
            throw new IllegalArgumentException("La lista no puede ser nula (error del programador).");
        }

        if (lista.isEmpty())
        {
            throw new ManoObraException("Debe proporcionar al menos un empleado.");
        }

        if (idProducto <= 0)
        {
            throw new ManoObraException("El id del producto es inválido.");
        }

        for (var dto : lista)
        {
            if (dto.getIdEmpleado() <= 0)
            {
                throw new ManoObraException("Hay un empleado inválido.");
            }

            if (dto.getTiempoAportado() <= 0)
            {
                throw new ManoObraException("El tiempo aportado debe ser mayor a cero.");
            }
        }
    }

    private double calcularCosto(double precioHora, float minutos)
    {
        return (minutos / 60.0) * precioHora;
    }
}
