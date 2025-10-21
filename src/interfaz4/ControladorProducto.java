package interfaz4;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControladorProducto
{

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox<String> cmbUnidad;
    @FXML
    private TextField txtCosto;
    @FXML
    private Button btnGuardar;

    @FXML
    public void initialize()
    {

        cmbUnidad.getItems().addAll("Piezas", "Litros", "Kilogramos", "Metros", "Caja", "Paquete");

        btnGuardar.setOnAction(e ->
        {
            System.out.println("Guardado: "
                    + txtNombre.getText() + " | "
                    + txtCantidad.getText() + " | "
                    + cmbUnidad.getValue() + " | "
                    + txtCosto.getText());
        });
    }
}
