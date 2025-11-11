package appCalculaCostos.vista.interfaz5;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.DetalleRecetaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.RecetaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoInsumo;
import appCalculaCostos.vista.interfaz6.ControladorVistaInsumos;
import conexion.implementaciones.ConexionSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

public class ControladorReceta
{

    private ControladorVistaInsumos controlador;
    MateriaPrimaService mps = new MateriaPrimaService(new InsumoDaoImpl(new ConexionSQL()));

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<String> cmbUnidad;

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
    public void initialize()
    {
        cmbUnidad.getItems().addAll("Kilogramo", "Gramo", "Litro", "Mililitro", "Pieza");
        colInsumo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().nombre()));
        colUnidad.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().unidadMedida()));
        inicializarSeleccionInsumos();
        listarInsumos();

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
        var nombre = txtNombre.getText();
        var cant = txtCantidad.getText();
        var opc = cmbUnidad.getSelectionModel().getSelectedItem();
        var error = validarCampos(nombre, cant, opc);
        if (error != null)
        {
            mostrarMensajeError(error);
            return;
        }

        ObservableList<DetalleRecetaDto> detalles = tbIngredientes.getItems();
        // Si quieres trabajarla como una lista normal (por ejemplo, para enviarla a un DAO)
        List<DetalleRecetaDto> listaDetalles = new ArrayList<>(detalles);

        if (listaDetalles.isEmpty())
        {
            mostrarMensajeError("No hay ingredientes agregados a la receta");
            return;
        }
        for (DetalleRecetaDto d : listaDetalles)
        {
            if (d.getNombreUnidadMedida().equals("Seleccione"))
            {
                mostrarMensajeError("Debe seleccinar una unidad de medida para cada ingrediente");
                return;
            }
        }
        try
        {
            double cantInt = Double.parseDouble(cant);
            RecetaDto dto = new RecetaDto(nombre, cantInt, opc, TipoInsumo.RECETA, listaDetalles);
            mps.guardarReceta(dto);
            mostrarMensajeExito("Receta guardada");
            controlador.actualizarVista();
            limpiarCampos();
        } catch (NumberFormatException ex)
        {
            ex.printStackTrace();
            mostrarMensajeError("La cantidad debe ser un numero");
        } catch (InsumoException ex)
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

    public void setControlador(ControladorVistaInsumos controlador)
    {
        this.controlador = controlador;

    }

    private void limpiarCampos()
    {
        txtNombre.clear();
        txtCantidad.clear();
        cmbUnidad.getSelectionModel().clearSelection();
        tbIngredientes.getItems().clear();
    }
}
