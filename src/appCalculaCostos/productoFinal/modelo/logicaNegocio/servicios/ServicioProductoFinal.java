/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios;

import appCalculaCostos.costosFijos.Modelo.dto.CostoDto;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedidaFactory;
import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.DetalleRecetaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DetalleReceta;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.productoFinal.modelo.exepciones.DatosNoValidosException;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.CostoModuloDTO;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.ICostoMpRepo;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.validaciones.ValidadorProductoFinal;
import conexion.Exepciones.PersistenciaException;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioProductoFinal;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.ModuloCostoMpDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Redondeo;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoCosto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalAsignarCostoDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalDatosDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeModulo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jose
 */
public class ServicioProductoFinal
{

    private final IRepositorioProductoFinal repo;
    private final ICostoMpRepo repoCostoMp;
    private final IInsumoDAO insumoDao;

    public ServicioProductoFinal(IRepositorioProductoFinal repo, ICostoMpRepo repoMp, IInsumoDAO insumoDao)
    {
        this.repo = repo;
        this.repoCostoMp = repoMp;
        this.insumoDao = insumoDao;
    }

    public void guardarNuevoProductoSinCostos(ProductoFinalCreacionDTO dto) throws ProductoFinalException
    {
        try
        {
            // 1. Validar la entrada de datos
            validarDatosEntrada(dto);
            // 2. Crear y construir la entidad de dominio
            ProductoFinal productoFinal = construirProductoFinalSinCostos(dto);
            // 3. Aplicar los cálculos de precio y ganancia
            aplicarLogicaPrecioVenta(dto, productoFinal);
            // 4. Persistir el agregado completo
            repo.guardarProductoFinalSinCostos(productoFinal);
            System.out.println("producto SIN costos guardado");
            System.out.println(productoFinal.toString());
        } catch (PersistenciaException e)
        {
            // Captura errores de la capa inferior y relanza una excepción de la capa de servicio
            throw new ProductoFinalException("No se pudo guardar el producto debido a un error de persistencia.", e);
        }
    }

    /**
     * Crea la entidad ProductoFinal e inserta todos los módulos de costo.
     */
    private ProductoFinal construirProductoFinalSinCostos(ProductoFinalCreacionDTO dto) throws PersistenciaException
    {
        final double COSTO_INICIAL = 0;

        //Creación de la Entidad y adjunción de Costos
        ProductoFinal productoFinal = new ProductoFinal(dto.nombre(), dto.cantidadVendida());
        // Calcular y fijar el costo total (snapshot)
        productoFinal.setCostoTotal(COSTO_INICIAL);
        return productoFinal;
    }

    //--------------------------------------------------------------------------
    // Nivel de Abstracción 2: Validación y Construcción
    /**
     * Valida los datos crudos del DTO.
     */
    private void validarDatosEntrada(ProductoFinalCreacionDTO dto) throws DatosNoValidosException
    {
        ValidadorProductoFinal.validarDatos(dto);
    }

    //--------------------------------------------------------------------------
    // Nivel de Abstracción 2: Orquestación de Precio/Ganancia
    /**
     * Aplica la lógica de consistencia entre Porcentaje de Ganancia y Precio de
     * Venta
     */
    private void aplicarLogicaPrecioVenta(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {

        if (dto.porcentajeGanancia() != null)
        {
            // El usuario eligió fijar el porcentaje.
            aplicarPorcentajeFijo(dto, productoFinal);
        } else if (dto.precioVenta() != null)
        {
            // El usuario eligió fijar el precio de venta.
            aplicarPrecioVentaFijo(dto, productoFinal);
        } else
        {
            // Ninguno fue elegido. Aplica el default.
            aplicarValoresDefault(productoFinal);
        }
    }

    // Nivel de Abstracción 3: Implementación de la Lógica
    private void aplicarPorcentajeFijo(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {
        // Asignamos el porcentaje elegido por el usuario
        productoFinal.setPorcentajeGanancia(dto.porcentajeGanancia());

        // La Entidad calcula el valor derivado (Precio de Venta)
        var precioVentaCalculado = productoFinal.calcularPrecioVenta();
        productoFinal.setPrecioVenta(precioVentaCalculado);
    }

    private void aplicarPrecioVentaFijo(ProductoFinalCreacionDTO dto, ProductoFinal productoFinal)
    {
        // Establecemos el precio de venta elegido por el usuario
        productoFinal.setPrecioVenta(dto.precioVenta());

        // La Entidad calcula el valor derivado (Porcentaje de Ganancia)
        var porcentajeGananciaCalculado = productoFinal.calcularPorcentajeGanancia();
        productoFinal.setPorcentajeGanancia(porcentajeGananciaCalculado);
    }

    private void aplicarValoresDefault(ProductoFinal productoFinal)
    {
        productoFinal.setPorcentajeGanancia(0.0);
        productoFinal.setPrecioVenta(0.0);
    }

    public List<ProductoFinalDatosDto> listarProductosFinales() throws ProductoFinalException
    {
        try
        {
            List<ProductoFinal> productosF = repo.ListarProductosFinales();
            List<ProductoFinalDatosDto> productosDto = new ArrayList<>();
            for (ProductoFinal p : productosF)
            {
                productosDto.add(new ProductoFinalDatosDto(p.getId(), p.getNombre(), Redondeo.redondear(p.getPorcentajeGanancia(), 2),
                        p.getPrecioVenta(), Redondeo.redondear(p.getCostoTotal(), 1), p.getCantidadVendida()));

            }
            return productosDto;
        } catch (PersistenciaException e)
        {
            throw new ProductoFinalException("No se pudo obtener la lista de productos", e);
        }
    }

    public void guardarCostosMp(int idProducto, List<DetalleRecetaDto> costosMp) throws ProductoFinalException
    {
        try
        {

            validarCostosMP(costosMp);
            List<DetalleReceta> costos = construirDetalles(costosMp);
            repoCostoMp.guardarCotosMP(idProducto, costos);
            actualizarCostoTotal(idProducto);
            actualizarPorcentajeDeGanancia(idProducto);

        } catch (PersistenciaException ex)
        {
            // Reempaqueta la excepción de persistencia como una excepción de negocio
            throw new ProductoFinalException("Error al guardar los costos de materia prima: " + ex.getMessage(), ex);
        }
    }

    public void actualizarCostoTotal(int idProducto) throws ProductoFinalException
    {

        try
        {
            var costoMp = repo.obtenerCostoMateriaPrima(idProducto);
            var costoF = repo.obtenerCostoFijoAsignado(idProducto);
            var costoTotal = costoMp + costoF;
            repo.actualizarCostoTotal(idProducto, costoTotal);
        } catch (PersistenciaException ex)
        {
            ex.printStackTrace();
            throw new ProductoFinalException("Ocurrio un error al actualizar el costo total del producto", ex);

        }
    }

    public List<CostoDto> listarTotalPorTipoCosto(int idProducto) throws ProductoFinalException
    {
        try
        {
            List<CostoDto>costosDelProdutco=new ArrayList<>();
            var costoMp = repo.obtenerCostoMateriaPrima(idProducto);
            var costoF = repo.obtenerCostoFijoAsignado(idProducto);
            costosDelProdutco.add(new CostoDto("Costo de materia Prima", Redondeo.redondear(costoMp, 1)));
            costosDelProdutco.add(new CostoDto("Costos Fijos", Redondeo.redondear(costoF, 1)));
            return costosDelProdutco;
        } catch (PersistenciaException ex)
        {
           throw new ProductoFinalException("Ocurrio un error al obtener los costos del producto");
        }

    }

    public List<DetalleRecetaDto> listarCostosMp(int idProducto) throws ProductoFinalException
    {
        try
        {
            List<DetalleReceta> costosMp = repoCostoMp.listarCostosMp(idProducto, insumoDao);
            List<DetalleRecetaDto> costosDto = new ArrayList<>();

            for (DetalleReceta detalle : costosMp)
            {
                DetalleRecetaDto detalleDto = new DetalleRecetaDto(
                        detalle.getInsumo().getId(),
                        detalle.getInsumo().getNombre(),
                        detalle.getCantidad(),
                        detalle.getUnidadMedida().getNombre()
                );
                costosDto.add(detalleDto);
            }

            return costosDto;
        } catch (PersistenciaException ex)
        {
            throw new ProductoFinalException("No se pudieron obtener los costos de materia prima", ex);
        }
    }

    public void modificarCostosMp(int idProducto, List<DetalleRecetaDto> costosMp) throws ProductoFinalException
    {
        try
        {
            validarCostosMP(costosMp);
            List<DetalleReceta> costos = construirDetalles(costosMp);
            repoCostoMp.actualizarCostosMP(idProducto, costos, repo);

        } catch (PersistenciaException ex)
        {
            // Reempaqueta la excepción de persistencia como una excepción de negocio
            throw new ProductoFinalException("Error al guardar los costos de materia prima: " + ex.getMessage(), ex);
        }
    }

    private void validarCostosMP(List<DetalleRecetaDto> costosMp) throws ProductoFinalException
    {
        if (costosMp == null)
        {
            throw new IllegalArgumentException("la lsita de detalles es invalida");
        }
        for (DetalleRecetaDto d : costosMp)
        {
            if (d.getCantidad() < 0)
            {
                throw new ProductoFinalException("la cantidad del detalle: " + d.getNombre() + " no es valida");
            }
        }
    }

    /**
     * Convierte una lista de DetalleRecetaDto a una lista de DetalleReceta.
     */
    private List<DetalleReceta> construirDetalles(List<DetalleRecetaDto> detallesDto) throws PersistenciaException
    {
        List<DetalleReceta> detalles = new ArrayList<>();

        for (DetalleRecetaDto dto : detallesDto)
        {
            // Buscar el insumo en la BD
            Optional<Insumo> insumoOpt = insumoDao.buscarInsumoPorID(dto.getIdInsumo());

            // Si no existe el insumo, lanzamos una excepción
            Insumo insumo = insumoOpt.orElseThrow(()
                    -> new PersistenciaException("No se encontró el insumo con ID: " + dto.getIdInsumo()));

            // Obtener la unidad de medida correspondiente
            UnidadMedida unidad = UnidadMedidaFactory.obtenerUnidadDeMedidaPorNombre(dto.getNombreUnidadMedida());

            // Crear el DetalleReceta con el insumo, cantidad y unidad
            DetalleReceta detalle = new DetalleReceta(insumo, dto.getCantidad(), unidad);

            detalles.add(detalle);
        }

        return detalles;
    }

    public IRepositorioProductoFinal getRepo()
    {
        return repo;
    }

    public ICostoMpRepo getRepoCostoMp()
    {
        return repoCostoMp;
    }

    public IInsumoDAO getInsumoDao()
    {
        return insumoDao;
    }

    public void actualizar(ProductoFinalDatosDto productoEditadoDto) throws ProductoFinalException
    {
        try
        {
            // Validar datos del DTO antes de convertir
            ValidadorProductoFinal.validarDatosProductoEditado(productoEditadoDto);
            // Convertir DTO → Entidad de dominio
            ProductoFinal productoEditado = convertirADominio(productoEditadoDto);

            // Enviar la entidad al repositorio
            repo.actualizarDatosProductoFinal(productoEditado);
            actualizarPorcentajeDeGanancia(productoEditado.getId());

        } catch (PersistenciaException ex)
        {
            ex.printStackTrace();
            throw new ProductoFinalException(
                    "No se pudieron actualizar los datos del producto: " + productoEditadoDto.getNombre()
            );
        }
    }

    private ProductoFinal convertirADominio(ProductoFinalDatosDto dto)
    {
        return new ProductoFinal(
                dto.getId(),
                dto.getNombre(),
                dto.getCantidadVendida(),
                dto.getPorcentajeGanancia(),
                dto.getPrecioVenta(),
                dto.getCostoTotal()
        );
    }

    public void borrarProductoFinalPorId(int idProductoFinal) throws ProductoFinalException
    {
        try
        {
            repo.borrarProductoFinal(idProductoFinal);
        } catch (PersistenciaException ex)
        {
            throw new ProductoFinalException("Ocurrio un error al intentar borrar el producto.");
        }
    }

    /**
     * metodo para calcular y actualizar el porcentaje de ganancia de un
     * producto por su id para que funcionese tiene que tener cargados los
     * valores correctamente en la tabla productos_finales en la base de datos
     *
     * @param idProducto
     * @throws ProductoFinalException
     */
    public void actualizarPorcentajeDeGanancia(int idProducto) throws ProductoFinalException
    {
        try
        {
            Optional<ProductoFinal> productoOpt = repo.buscarProductoFinalPorId(idProducto);
            if (productoOpt.isEmpty())
            {
                throw new ProductoFinalException("No se encnotro el producto con id: " + idProducto);
            }
            ProductoFinal producto = productoOpt.get();
            double nuevoPorcentaje = producto.calcularPorcentajeGanancia();
            repo.actualizarPorcentajeGanancia(idProducto, nuevoPorcentaje);
        } catch (PersistenciaException ex)
        {
            ex.printStackTrace();
            throw new ProductoFinalException("Ocurrio un error al actualizar el nuevo porcentaje de ganancia");
        }
    }

}
