package appCalculaCostos.vista.interfaz9;

import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.Servicio.CostosFijosService;
import appCalculaCostos.costosFijos.Modelo.dto.CostoFijoDto;
import appCalculaCostos.vista.interfaz8.ControladorCostosFijos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControladorAltaCosto
{

    private ControladorCostosFijos controladorPrincipal;
    private CostosFijosService servicioCostoFijo;
    boolean modoEdicion = false;
    private CostoFijoDto costoEditable;

    @FXML
    private Label lblTitulo;
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
                CostoFijoDto dto;
                if (modoEdicion)
                {
                    dto = new CostoFijoDto(costoEditable.getId(), nombre, importe, porcentejeUsado);
                    servicioCostoFijo.modificarCostoFijo(dto);
                    mostrarMensajeExito("Actuzlizado");
                } else
                {
                    dto = new CostoFijoDto(0, nombre, importe, porcentejeUsado);
                    servicioCostoFijo.guardarCostoFijo(dto);
                    mostrarMensajeExito("Guardado");
                    limpiarCampos();
                }
                if (controladorPrincipal != null)
                {
                    controladorPrincipal.actualizarVista();
                }

            } catch (NumberFormatException ex)
            {
                mostrarMensajeError("Error: cantidad y costo deben ser números válidos");

            } catch (CostoFijoException ex)
            {
                mostrarMensajeError(ex.getMessage());
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

    public CostosFijosService getServicioCostoFijo()
    {
        return servicioCostoFijo;
    }

    public void setServicioCostoFijo(CostosFijosService servicioCostoFijo)
    {
        this.servicioCostoFijo = servicioCostoFijo;
    }

    public void setModoEdicion(CostoFijoDto editable)
    {
        modoEdicion = true;
        lblTitulo.setText("Editar Costo Fijo");
        this.costoEditable = editable;
        cargarDatos(editable);
    }

    private void cargarDatos(CostoFijoDto editable)
    {
        txtNombre.setText(editable.getNombre());
        txtImporte.setText(String.valueOf(editable.getImporteMensual()));
        txtPorcentajeUsado.setText(String.valueOf(editable.getPorcentajeUsado()));
    }

}
