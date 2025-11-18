/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.CostoManoObra.Modelo.Servicio;

import appCalculaCostos.CostoManoObra.Modelo.Entidades.Empleado;
import appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException;
import appCalculaCostos.CostoManoObra.Modelo.dao.EmpleadoRepoImpl;
import appCalculaCostos.CostoManoObra.Modelo.dao.IEmpleadoRepo;
import appCalculaCostos.CostoManoObra.Modelo.dto.EmpleadoDTO;
import conexion.Exepciones.PersistenciaException;
import conexion.implementaciones.ConexionSQL;
import java.util.List;

/**
 *
 * @author jose
 */
public class EmpleadoService
{

    private IEmpleadoRepo repoEmpleado = new EmpleadoRepoImpl(new ConexionSQL());

    public EmpleadoService()
    {
    }
    
    
    public EmpleadoService(IEmpleadoRepo repoEmpleado)
    {
        this.repoEmpleado = repoEmpleado;
    }

    public void guardarEmpleado(EmpleadoDTO dto) throws ManoObraException
    {
        try
        {
            validarEmpleadoDTO(dto);
            Empleado empleado = convertir(dto);
            repoEmpleado.guardarEmpleado(empleado);

        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al guardar empleado", e);
        }
    }

    public void actualizarEmpleado(EmpleadoDTO dto) throws ManoObraException
    {
        try
        {
            validarEmpleadoDTO(dto);
            Empleado empleado = convertir(dto);
            repoEmpleado.actualizarEmpleado(empleado);

        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al actualizar empleado", e);
        }
    }

    public void eliminarEmpleado(int id) throws ManoObraException
    {
        try
        {
            if (id <= 0)
            {
                throw new ManoObraException("ID inválido");
            }
            repoEmpleado.eliminarEmpleado(id);
        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al eliminar empleado", e);
        }
    }

    public List<EmpleadoDTO> listarEmpleados() throws ManoObraException
    {
        try
        {
            List<Empleado> lista = repoEmpleado.listarEmpleados();
            return lista.stream()
                    .map(this::convertir)
                    .toList();
        } catch (PersistenciaException e)
        {
            throw new ManoObraException("Error al listar empleados", e);
        }
    }

    // ---------- Validación ----------
    private void validarEmpleadoDTO(EmpleadoDTO dto) throws ManoObraException
    {
        if (dto == null)
        {
            throw new ManoObraException("El DTO no puede ser nulo");
        }

        if (dto.getNombre() == null || dto.getNombre().isBlank())
        {
            throw new ManoObraException("Nombre requerido");
        }

        if (dto.getApellido() == null || dto.getApellido().isBlank())
        {
            throw new ManoObraException("Apellido requerido");
        }

        if (dto.getSalarioMensual() <= 0)
        {
            throw new ManoObraException("El salario debe ser mayor que 0");
        }
    }

    // ---------- Conversión ----------
    private Empleado convertir(EmpleadoDTO dto)
    {
        return new Empleado(
                dto.getId(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getSalarioMensual(),
                dto.getHorasSemana()
        );
    }

    private EmpleadoDTO convertir(Empleado empleado)
    {
        return new EmpleadoDTO(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getSalarioSemanal(),
                empleado.getHorasSemana()
        );
    }
}
