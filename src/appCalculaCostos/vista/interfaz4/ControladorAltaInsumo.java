package interfaz4;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.MateriaPrimaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.TipoInsumo;
import appCalculaCostos.vista.interfaz6.ControladorVistaInsumos;
import conexion.implementaciones.ConexionSQL;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAltaInsumo
{

    private final MateriaPrimaService mps = new MateriaPrimaService(new InsumoDaoImpl(new ConexionSQL()));
    private ControladorVistaInsumos controladorPrincipal;
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

        // Inicializa el ComboBox de unidades
        cmbUnidad.getItems().addAll("Kilogramo", "Gramo", "Litro", "Mililitro", "Pieza");

        // Manejo del botón Guardar
        btnGuardar.setOnAction(e ->
        {
            try
            {
                // Capturamos los valores de los campos
                String nombre = txtNombre.getText();
                String cantidadStr = txtCantidad.getText();
                String unidad = cmbUnidad.getValue() != null ? cmbUnidad.getValue() : "";
                String costoStr = txtCosto.getText();

                // Validaciones simples
                if (nombre.isBlank() || cantidadStr.isBlank() || unidad.isBlank() || costoStr.isBlank())
                {
                    System.out.println("Todos los campos son obligatorios");
                    return;
                }

                // Convertimos cantidad y costo a double
                double cantidad = Double.parseDouble(cantidadStr);
                double costo = Double.parseDouble(costoStr);

                // Creamos el DTO
                MateriaPrimaDto dto = new MateriaPrimaDto(
                        nombre,
                        cantidad,
                        unidad,
                        TipoInsumo.MATERIA_PRIMA,
                        costo
                );

                // Guardamos usando el servicio/DAO
                mps.guardarMateriaPrima(dto);
                System.out.println(dto.toString());

                mostrarMensajeExito("guardado");
                controladorPrincipal.actualizarVista();

            } catch (NumberFormatException ex)
            {
                mostrarMensajeError("Error: cantidad y costo deben ser números válidos");

            } catch (InsumoException ex)
            {
                mostrarMensajeError(ex.getMessage());
                ex.printStackTrace();
            }
        });
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

    public void setControladorPrincipal(ControladorVistaInsumos controladorPrincipal)
    {
        this.controladorPrincipal = controladorPrincipal;
    }

}
