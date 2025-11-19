/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Servicio;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import appCalculaCostos.CostoManoObra.Modelo.Entidades.ManoObra;
import appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException;
import appCalculaCostos.CostoManoObra.Modelo.dao.CostosManoObraRepositoryImpl;
import appCalculaCostos.CostoManoObra.Modelo.dao.EmpleadoRepoImpl;
import appCalculaCostos.CostoManoObra.Modelo.dao.ICostosManoObraRepository;
import appCalculaCostos.CostoManoObra.Modelo.dao.IEmpleadoRepo;
import appCalculaCostos.CostoManoObra.Modelo.dto.ManoObraDTO;
import appCalculaCostos.CostoManoObra.Modelo.dto.ManoObraDeProductoDTO;
import conexion.Exepciones.PersistenciaException;
import conexion.implementaciones.ConexionSQL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jose
 */
public class CostoManoObraService
{

    private ICostosManoObraRepository repo = new CostosManoObraRepositoryImpl(new ConexionSQL());
    private IEmpleadoRepo repoEmpleados = new EmpleadoRepoImpl(new ConexionSQL());

    public CostoManoObraService()
    {
    }

    public CostoManoObraService(ICostosManoObraRepository repo, IEmpleadoRepo repoEmpleados)
    {
        this.repo = repo;
        this.repoEmpleados = repoEmpleados;
    }

    /**
     * Guarda la mano de obra de un producto, reemplazando todo lo anterior.
     *
     * @param dto
     * @throws
     * appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException
     */
    public void guardarManoObraDeProducto(ManoObraDeProductoDTO dto)
            throws ManoObraException
    {
        try
        {

            int idProducto = dto.getIdProducto();

            // 1. Validación básica
            if (dto.getListaManoObra() == null )
            {
                throw new IllegalArgumentException("La lista no es valida.");
            }

            // 2. Convertir DTO → entidades
            List<ManoObra> entidades = new ArrayList<>();

            for (ManoObraDTO moDto : dto.getListaManoObra())
            {

                // Obtener empleado real de la BD
                Empleado empleado = repoEmpleados.buscarPorId(moDto.getIdEmpleado());

                if (empleado == null)
                {
                    throw new ManoObraException(
                            "El empleado con ID " + moDto.getIdEmpleado() + " no existe."
                    );
                }

                // Calcular costo
                double costoCalculado = (moDto.getTiempoAportado() / 60) * empleado.getCostoHora();

                ManoObra mo = new ManoObra(
                        empleado,
                        moDto.getTiempoAportado(),
                        costoCalculado
                );

                entidades.add(mo);
            }

            // 4. Guardar nueva mano de obra
            repo.guardarManoObraDeProducto(idProducto, entidades);

        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error guardando mano de obra: " + e.getMessage(), e);
        }
    }

    public List<ManoObraDTO> listarManoObraPorProducto(int idProducto)
            throws ManoObraException
    {
        if (idProducto <= 0)
        {
            throw new ManoObraException("El id del producto no es válido.");
        }

        try
        {
            List<ManoObra> lista = repo.obtenerManoObraDeProducto(idProducto);
            return convertirAListaDTO(lista);

        } catch (PersistenciaException ex)
        {
            throw new ManoObraException("Error al listar mano de obra: " + ex.getMessage(), ex);
        }
    }

    private List<ManoObraDTO> convertirAListaDTO(List<ManoObra> lista)
    {
        List<ManoObraDTO> dtos = new ArrayList<>();

        for (ManoObra mo : lista)
        {
            Empleado emp = mo.getEmpleado();

            ManoObraDTO dto = new ManoObraDTO();
            dto.setIdEmpleado(emp.getId());
            dto.setNombreEmpleado(emp.getNombre());
            dto.setApellidoEmpleado(emp.getApellido());
            dto.setTiempoAportado(mo.getTiempoAportado());

            dtos.add(dto);
        }

        return dtos;
    }

}
