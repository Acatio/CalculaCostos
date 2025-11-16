package appCalculaCostos.vista.interfaz11;

import appCalculaCostos.costosFijos.Modelo.dto.CostoDto;
import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.DetalleRecetaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.costosMateriaPrima.modelo.daos.CostoMpRepoImpl;
import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalAsignarCostoDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.vista.interfaz1.ControladorPf;
import conexion.implementaciones.ConexionSQL;
import conexion.interfacesLogicas.IConexion;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorResumenCostos
{

    private final IConexion conexion = new ConexionSQL();
    private ServicioProductoFinal spf = new ServicioProductoFinal(new ProductoFinalDaoImpl(conexion), new CostoMpRepoImpl(conexion), new InsumoDaoImpl(conexion));
    private ProductoFinalAsignarCostoDto productoDto;

    @FXML
    private TableView<CostoDto> tbCostos;
    @FXML
    private TableColumn<CostoDto, String> colTipoCosto;
    @FXML
    private TableColumn<CostoDto, Double> colMonto;

    @FXML
    private Label lblNombre;

    @FXML
    public void initialize()
    {
        colTipoCosto.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getTipoCosto()));
        colMonto.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().getMontoTotal()));
    }

    public void listarResumenCostos()
    {
        try
        {
            var listaCostos = FXCollections.observableArrayList(spf.listarTotalPorTipoCosto(productoDto.getId()));
            tbCostos.setItems(listaCostos);
            var total=0d;
            for (CostoDto costo:listaCostos)
            {
                total+=costo.getMontoTotal();
            }
        } catch (ProductoFinalException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    private String validarCampos(String nombre, String cantidad, String unidad)
    {
        String error = null;
        if (nombre == null || nombre.isBlank())
        {
            error = "Debe ponerle un nombre a la receta";
            return error;
        }
        if (cantidad.isBlank())
        {
            error = "Debe establecer la cantidad producida en la receta";
            return error;
        }
        if (unidad == null)
        {
            error = "Debe indicar la unidad de medida de la receta";
        }
        return error;
    }



    public void mostrarMensajeExito(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    public void mostrarMensajeError(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    public ProductoFinalAsignarCostoDto getProductoDto()
    {
        return productoDto;
    }

    public void setProductoDto(ProductoFinalAsignarCostoDto productoDto)
    {
        this.productoDto = productoDto;
        lblNombre.setText(productoDto.getNombre());
        System.out.println(productoDto);
        listarResumenCostos();
    }

    public ServicioProductoFinal getSpf()
    {
        return spf;
    }

    public void setSpf(ServicioProductoFinal spf)
    {
        this.spf = spf;
    }

}
