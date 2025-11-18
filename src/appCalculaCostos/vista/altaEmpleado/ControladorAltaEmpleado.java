package appCalculaCostos.vista.altaEmpleado;

import appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException;
import appCalculaCostos.CostoManoObra.Modelo.Servicio.EmpleadoService;
import appCalculaCostos.CostoManoObra.Modelo.dto.EmpleadoDTO;
import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.Servicio.CostosFijosService;
import appCalculaCostos.vista.interfaz8.ControladorCostosFijos;
import appCalculaCostos.vista.principalEmpleados.ControladorPrincipalEmpleados;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControladorAltaEmpleado
{

    private ControladorPrincipalEmpleados controladorPrincipal;
    private EmpleadoService servicioEmpleado;
    boolean modoEdicion = false;
    private EmpleadoDTO editable;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtSalario;
    @FXML
    private TextField txtHoras;

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
                String apellido = txtApellido.getText();
                String salario = txtSalario.getText();
                String horasSemana = txtHoras.getText();
                // Validaciones simples
                if (nombre.isBlank() || apellido.isBlank() || salario.isBlank() || horasSemana.isBlank())
                {
                    mostrarMensajeError("Debe llenar todos los campos");
                    return;
                }
                double salarioDouble = Double.parseDouble(salario);
                float horasSemanaFloat = Float.parseFloat(horasSemana);
                EmpleadoDTO dto = new EmpleadoDTO(nombre, apellido, salarioDouble, horasSemanaFloat);
                if (modoEdicion)
                {
                    dto.setId(editable.getId());
                    servicioEmpleado.actualizarEmpleado(dto);
                    mostrarMensajeExito("Actuzlizado");
                } else
                {
                    servicioEmpleado.guardarEmpleado(dto);
                    mostrarMensajeExito("Guardado");
                    limpiarCampos();
                }
                if (controladorPrincipal != null)
                {
                    controladorPrincipal.actualizarVista();
                }

            } catch (NumberFormatException ex)
            {
                mostrarMensajeError("Error: el salario debe ser numerico");

            } catch (ManoObraException ex)
            {
                ex.printStackTrace();
                mostrarMensajeError(ex.getMessage());
            }
        });
    }

    private void limpiarCampos()
    {
        txtNombre.clear();
        txtApellido.clear();
        txtSalario.clear();
        txtHoras.clear();
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

    public void setControladorPrincipal(ControladorPrincipalEmpleados controladorPrincipal)
    {
        this.controladorPrincipal = controladorPrincipal;
    }

    public void setModoEdicion(EmpleadoDTO editable)
    {
        this.editable = editable;
        modoEdicion = true;
        lblTitulo.setText("Editar Empleado");
        cargarDatos(editable);
    }

    private void cargarDatos(EmpleadoDTO editable)
    {

        txtNombre.setText(editable.getNombre());
        txtApellido.setText(String.valueOf(editable.getApellido()));
        txtSalario.setText(String.valueOf(editable.getSalarioMensual()));
        txtHoras.setText(String.valueOf(editable.getHorasSemana()));
    }

    public void setServicioEmpleado(EmpleadoService servicioEmpleado)
    {
        this.servicioEmpleado = servicioEmpleado;
    }

}
