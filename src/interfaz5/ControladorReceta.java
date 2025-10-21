package interfaz5;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ControladorReceta
{

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<String> cmbUnidad;

    @FXML
    private TableView<?> tablaInsumosDisponibles;
    @FXML
    private TableColumn<?, ?> colIdInsumo;
    @FXML
    private TableColumn<?, ?> colNombreInsumo;
    @FXML
    private TableColumn<?, ?> colUnidadInsumo;

    @FXML
    private TableView<?> tablaInsumosSeleccionados;
    @FXML
    private TableColumn<?, ?> colIdSel;
    @FXML
    private TableColumn<?, ?> colNombreSel;
    @FXML
    private TableColumn<?, ?> colCantidadSel;

    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnQuitar;
    @FXML
    private Button btnGuardar;

    @FXML
    public void initialize()
    {
        cmbUnidad.getItems().addAll("Piezas", "Litros", "Kilogramos", "Porciones");

        btnAgregar.setOnAction(e -> System.out.println("Insumo agregado a la receta"));
        btnQuitar.setOnAction(e -> System.out.println("Insumo quitado de la receta"));
        btnGuardar.setOnAction(e -> System.out.println("Receta guardada"));
    }
}
