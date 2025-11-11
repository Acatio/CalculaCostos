package appCalculaCostos.vista.interfaz9;

import appCalculaCostos.vista.interfaz8.ControladorCostosFijos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControladorAltaCosto
{

    private ControladorCostosFijos controladorPrincipal;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtImporte;
    @FXML
    private TextField txtPorcentajeUsado;

    @FXML
    private Button btnGuardar;

    @FXML
    public void initialize()
    {

        // Manejo del botón Guardar
        btnGuardar.setOnAction(e ->
        {
            try
            {
                // Capturamos los valores de los campos
                String nombre = txtNombre.getText();

                // Validaciones simples
                if (nombre.isBlank() || txtImporte.getText().isBlank() || txtPorcentajeUsado.getText().isBlank())
                {
                    mostrarMensajeError("Debe llenar todos los campos");
                    return;
                }
                double importe = Double.parseDouble(txtImporte.getText());
                double porcentejeUsado = Double.parseDouble(txtPorcentajeUsado.getText());
                System.out.println(nombre + " " + importe + " " + porcentejeUsado);
                mostrarMensajeExito("guardado");
                if (controladorPrincipal != null)
                {
                    controladorPrincipal.actualizarVista();

                }
                limpiarCampos();

            } catch (NumberFormatException ex)
            {
                mostrarMensajeError("Error: cantidad y costo deben ser números válidos");

            }
        });
    }

    private void limpiarCampos()
    {
        txtImporte.clear();
        txtNombre.clear();
        txtPorcentajeUsado.clear();
    }

    private void mostrarMensajeExito(String mensaje)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

// Método para mostrar mensaje de error
    private void mostrarMensajeError(String mensaje)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setControladorPrincipal(ControladorCostosFijos controladorPrincipal)
    {
        this.controladorPrincipal = controladorPrincipal;
    }

}
