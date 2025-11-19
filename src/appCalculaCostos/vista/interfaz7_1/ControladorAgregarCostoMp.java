package appCalculaCostos.vista.interfaz7_1;

import appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException;
import appCalculaCostos.CostoManoObra.Modelo.Servicio.CostoManoObraService;
import appCalculaCostos.CostoManoObra.Modelo.Servicio.EmpleadoService;
import appCalculaCostos.CostoManoObra.Modelo.dto.EmpleadoDTO;
import appCalculaCostos.CostoManoObra.Modelo.dto.ManoObraDTO;
import appCalculaCostos.CostoManoObra.Modelo.dto.ManoObraDeProductoDTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;

public class ControladorAgregarCostoMp
{

    private CostoManoObraService servicioMo = new CostoManoObraService();
    private EmpleadoService servicioEmpleado = new EmpleadoService();
    private ServicioProductoFinal servicioPf;
    private ProductoFinalAsignarCostoDto productoDto;
    ControladorPf controladorP;
    @FXML
    private TableView<EmpleadoDTO> tbEmpleados;
    @FXML
    private TableColumn<EmpleadoDTO, String> colNombre;
    @FXML
    private TableColumn<EmpleadoDTO, String> colApellido;

    @FXML
    private TableView<ManoObraDTO> tbManoObra;

    @FXML
    private TableColumn<ManoObraDTO, String> colNombreMo;
    @FXML
    private TableColumn<ManoObraDTO, String> colApellidoMo;
    @FXML
    private TableColumn<ManoObraDTO, Float> colTiempoMo;

    @FXML
    private Label lblNombre;

    @FXML
    public void initialize()
    {

        // Tabla de empleados
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        // Tabla de mano de obra asignada
        colNombreMo.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colApellidoMo.setCellValueFactory(new PropertyValueFactory<>("apellidoEmpleado"));
        colTiempoMo.setCellValueFactory(new PropertyValueFactory<>("tiempoAportado"));
        tbManoObra.setEditable(true);

        // Hacer editable la columna de tiempo
        colTiempoMo.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));

        colTiempoMo.setOnEditCommit(event ->
        {
            ManoObraDTO mo = event.getRowValue();
            Float valor;

            try
            {
                // Si el usuario borró el contenido
                if (event.getNewValue() == null)
                {
                    valor = 0.0f;
                } else
                {
                    valor = event.getNewValue();
                }
            } catch (Exception e)
            {
                valor = 0.0f;
            }

            mo.setTiempoAportado(valor);

            // Refrescar la tabla para mostrar el valor corregido
            tbManoObra.refresh();
        });

        listarEmpleados();
    }

    public void listarEmpleados()
    {
        try
        {
            var listaEmpleados = FXCollections.observableArrayList(servicioEmpleado.listarEmpleados());
            tbEmpleados.setItems(listaEmpleados);
        } catch (ManoObraException ex)
        {
            mostrarMensajeError(ex.getMessage());
        }

    }

    @FXML
    private void onAgregar()
    {
        EmpleadoDTO seleccionado = tbEmpleados.getSelectionModel().getSelectedItem();

        if (seleccionado != null)
        {
            // Buscar si ya existe en la tabla
            for (ManoObraDTO empleado : tbManoObra.getItems())
            {
                if (empleado.getIdEmpleado() == seleccionado.getId())
                {
                    return;
                }
            }

            // Si no existe, agregarlo nuevo
            ManoObraDTO nuevo = new ManoObraDTO();
            nuevo.setIdEmpleado(seleccionado.getId());
            nuevo.setNombreEmpleado(seleccionado.getNombre());
            nuevo.setApellidoEmpleado(seleccionado.getApellido());
            tbManoObra.getItems().add(nuevo);

        } else
        {
            mostrarMensajeError("No se ha seleccionado ningún empleado");
        }
    }

    @FXML
    private void onQuitar()
    {

        ManoObraDTO seleccionado = tbManoObra.getSelectionModel().getSelectedItem();
        if (seleccionado != null)
        {
            tbManoObra.getItems().remove(seleccionado);
        } else
        {
            mostrarMensajeError("No se ha seleccionado ningún empleado");
        }

    }

    @FXML
    private void onGuardar()
    {

        try
        {
            ObservableList<ManoObraDTO> manoObra = tbManoObra.getItems();
            // Si quieres trabajarla como una lista normal (por ejemplo, para enviarla a un DAO)
            List<ManoObraDTO> listaManoObra = new ArrayList<>(manoObra);

            if (listaManoObra.isEmpty())
            {
                mostrarMensajeError("Debe seleccionar al meno un empleado");
                return;
            }
            for (ManoObraDTO d : listaManoObra)
            {
                if (d.getTiempoAportado() == 0)
                {
                    mostrarMensajeError("Debe ingresar el tiempo que dedica el empleado a la elaboracion del producto");
                    return;
                }
                System.out.println(d);
            }
            ManoObraDeProductoDTO mop = new ManoObraDeProductoDTO(productoDto.getId(), listaManoObra);
            servicioMo.guardarManoObraDeProducto(mop);
            servicioPf.actualizarCostoTotal(productoDto.getId());
            mostrarMensajeExito("Costos guardados con exito");
            controladorP.actualizarVista();

        } catch (ManoObraException | ProductoFinalException ex)
        {
            mostrarMensajeError("Ocurrio un error al guardar los costos de mano de obra");
        }
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
        System.out.println("Nombre " + productoDto.getNombre());
        System.out.println("Nombre atributo " + this.productoDto.getNombre());
    }

    public ControladorPf getControladorP()
    {
        return controladorP;
    }

    public void setControladorP(ControladorPf controladorP)
    {
        this.controladorP = controladorP;
    }

}
