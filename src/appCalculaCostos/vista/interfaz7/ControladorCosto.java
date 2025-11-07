package appCalculaCostos.vista.interfaz7;

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
import appCalculaCostos.vista.interfaz1.Controlador;
import conexion.implementaciones.ConexionSQL;
import conexion.interfacesLogicas.IConexion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class ControladorCosto
{

    private MateriaPrimaService mps = new MateriaPrimaService(new InsumoDaoImpl(new ConexionSQL()));
    private final IConexion conexion = new ConexionSQL();
    private ServicioProductoFinal spf = new ServicioProductoFinal(new ProductoFinalDaoImpl(conexion), new CostoMpRepoImpl(conexion), new InsumoDaoImpl(conexion));
    private ProductoFinalAsignarCostoDto productoDto;
    Controlador controladorP;
    @FXML
    private TableView<InsumoDto> tbInsumos;
    @FXML
    private TableColumn<InsumoDto, String> colInsumo;
    @FXML
    private TableColumn<InsumoDto, String> colUnidad;

    @FXML
    private TableView<DetalleRecetaDto> tbIngredientes;

    @FXML
    private TableColumn<DetalleRecetaDto, String> colIngrediente;
    @FXML
    private TableColumn<DetalleRecetaDto, Double> colCantidad;
    @FXML
    private TableColumn<DetalleRecetaDto, String> colUnidadIngrediente;

    @FXML
    private Label lblNombre;

    @FXML
    public void initialize()
    {
        colInsumo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().nombre()));
        colUnidad.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().unidadMedida()));
        listarInsumos();
        inicializarSeleccionInsumos();

    }

    public void listarInsumos()
    {
        try
        {
            var listaInsumos = FXCollections.observableArrayList(mps.listarInsumos());
            tbInsumos.setItems(listaInsumos);
        } catch (InsumoException ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void onAgregar()
    {
        InsumoDto insumo = tbInsumos.getSelectionModel().getSelectedItem();

        if (insumo != null)
        {
            // Buscar si ya existe en la tabla
            for (DetalleRecetaDto detalle : tbIngredientes.getItems())
            {
                if (detalle.getIdInsumo() == insumo.id())
                {
                    // Crear un nuevo objeto con la cantidad incrementada
                    DetalleRecetaDto actualizado = new DetalleRecetaDto(
                            detalle.getIdInsumo(),
                            detalle.getNombre(),
                            detalle.getCantidad() + 1,
                            detalle.getNombreUnidadMedida()
                    );

                    // Reemplazar el objeto viejo por el nuevo
                    int index = tbIngredientes.getItems().indexOf(detalle);
                    tbIngredientes.getItems().set(index, actualizado);

                    return; // Ya se actualizó, salir del método
                }
            }

            // Si no existe, agregarlo nuevo
            DetalleRecetaDto nuevo = new DetalleRecetaDto(
                    insumo.id(),
                    insumo.nombre(),
                    1, // cantidad inicial
                    "Seleccione"
            );

            tbIngredientes.getItems().add(nuevo);

        } else
        {
            mostrarMensajeError("No se ha seleccionado ningún insumo");
        }
    }

    @FXML
    private void onQuitar()
    {

        DetalleRecetaDto seleccionado = tbIngredientes.getSelectionModel().getSelectedItem();
        if (seleccionado != null)
        {
            tbIngredientes.getItems().remove(seleccionado);
        } else
        {
            mostrarMensajeError("No se ha seleccionado ningún insumo");
        }

    }

    @FXML
    private void onGuardar()
    {

        try
        {
            ObservableList<DetalleRecetaDto> detalles = tbIngredientes.getItems();
            // Si quieres trabajarla como una lista normal (por ejemplo, para enviarla a un DAO)
            List<DetalleRecetaDto> listaDetalles = new ArrayList<>(detalles);

//            if (listaDetalles.isEmpty())
//            {
//                mostrarMensajeError("No hay ingredientes agregados a la receta");
//                return;
//            }

            for (DetalleRecetaDto d : listaDetalles)
            {
                if (d.getNombreUnidadMedida().equals("Seleccione"))
                {
                    mostrarMensajeError("Debe seleccinar una unidad de medida para cada ingrediente");
                    return;
                }
            }
            spf.modificarCostosMp(productoDto.getId(), listaDetalles);
            mostrarMensajeExito("Costos MP agregados");
            controladorP.actualizarVista();

        } catch (ProductoFinalException ex)
        {
            mostrarMensajeError(ex.getMessage());
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

    @FXML
    private void onCancelar()
    {
        System.out.println("Insumo cancelado");
    }

    @FXML
    private void onCambiarUnidad()
    {
        System.out.println("Cambiando Unidad");
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

    private void inicializarSeleccionInsumos()
    {
        colIngrediente.setCellValueFactory(c -> c.getValue().nombreProperty());

        colCantidad.setCellValueFactory(c -> c.getValue().cantidadProperty().asObject());
        colCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colCantidad.setOnEditCommit(event ->
        {
            DetalleRecetaDto detalle = event.getRowValue();
            detalle.setCantidad(event.getNewValue());
            tbIngredientes.refresh();
        });

        ObservableList<String> unidades = FXCollections.observableArrayList(
                "Seleccione", "Kilogramo", "Gramo", "Litro", "Mililitro", "Pieza"
        );

        colUnidadIngrediente.setCellValueFactory(c -> c.getValue().nombreUnidadMedidaProperty());
        colUnidadIngrediente.setCellFactory(ComboBoxTableCell.forTableColumn(unidades));
        colUnidadIngrediente.setOnEditCommit(event
                -> event.getRowValue().setNombreUnidadMedida(event.getNewValue())
        );

        tbIngredientes.setEditable(true);

    }

    public ProductoFinalAsignarCostoDto getProductoDto()
    {
        return productoDto;
    }

    public void setProductoDto(ProductoFinalAsignarCostoDto productoDto)
    {
        this.productoDto = productoDto;
        lblNombre.setText(productoDto.getNombre());
        System.out.println("Nombre "+productoDto.getNombre());
        System.out.println("Nombre atributo "+this.productoDto.getNombre());
        cargarIngredientes();
    }

    public MateriaPrimaService getMps()
    {
        return mps;
    }

    public void setMps(MateriaPrimaService mps)
    {
        this.mps = mps;
    }

    public ServicioProductoFinal getSpf()
    {
        return spf;
    }

    public void setSpf(ServicioProductoFinal spf)
    {
        this.spf = spf;
    }

    public Controlador getControladorP()
    {
        return controladorP;
    }

    public void setControladorP(Controlador controladorP)
    {
        this.controladorP = controladorP;
    }

    private void cargarIngredientes()
    {
        try
        {
            // 1. Obtener la lista de ingredientes (DTOs)
            List<DetalleRecetaDto> ingredientes = spf.listarCostosMp(productoDto.getId());

            // 2. Configurar las columnas para que muestren los datos del DTO
            colIngrediente.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            colUnidadIngrediente.setCellValueFactory(new PropertyValueFactory<>("NombreUnidadMedida"));

            // 3. Convertir la lista a un ObservableList (JavaFX necesita esto)
            ObservableList<DetalleRecetaDto> listaObservable = FXCollections.observableArrayList(ingredientes);

            // 4. Asignar la lista a la tabla
            tbIngredientes.setItems(listaObservable);

        } catch (ProductoFinalException ex)
        {
            mostrarMensajeError("No se pudieron cargar los ingredientes del producto: " + productoDto.getNombre());
        }
    }
}
