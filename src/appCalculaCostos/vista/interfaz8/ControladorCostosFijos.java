package appCalculaCostos.vista.interfaz8;

import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.Servicio.CostosFijosService;
import appCalculaCostos.costosFijos.Modelo.dao.CostoFijoRepoImpl;
import appCalculaCostos.costosFijos.Modelo.dto.CostoFijoDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalDatosDto;
import appCalculaCostos.vista.interfaz1.ControladorPf;
import appCalculaCostos.vista.interfaz2.InterfazProductoController;
import appCalculaCostos.vista.interfaz9.ControladorAltaCosto;
import conexion.implementaciones.ConexionSQL;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ControladorCostosFijos
{

    private ControladorPf controladorPrincipal;
    private CostosFijosService servicioCostosFijos = new CostosFijosService(new CostoFijoRepoImpl(new ConexionSQL()));
    private Stage ventanaAltaCostoFijo;

    @FXML
    private TableColumn<CostoFijoDto, String> colNombre;

    @FXML
    private TableColumn<CostoFijoDto, Double> colImporteMensual;

    @FXML
    private TableColumn<CostoFijoDto, Double> colPorcentajeUsado;

    @FXML
    private TableView<CostoFijoDto> tablaCostosFijos;

    @FXML
    public void initialize()
    {
        // Configura las columnas
        colNombre.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colImporteMensual.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getImporteMensual()).asObject()
        );

        colPorcentajeUsado.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getPorcentajeUsado()).asObject()
        );

        // Carga los datos
        actualizarVista();
    }

    public void actualizarVista()
    {
        try
        {
            List<CostoFijoDto> lista = servicioCostosFijos.listarTodos();
            tablaCostosFijos.getItems().setAll(lista);
        } catch (CostoFijoException e)
        {
            mostrarMensajeError("Error al cargar los costos fijos");

        }
    }

    public void mostrarCostosFijos()
    {

    }

    @FXML
    private void onActionNuevoCf()
    {
        try
        {
            if (ventanaAltaCostoFijo == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz9/interfazCostoFijo.fxml"));
                Parent root = loader.load();
                ControladorAltaCosto controladorAltaCostoFijo = loader.getController();
                controladorAltaCostoFijo.setControladorPrincipal(this);
                controladorAltaCostoFijo.setServicioCostoFijo(servicioCostosFijos);

                ventanaAltaCostoFijo = new Stage();
                ventanaAltaCostoFijo.setTitle("Nueva Ventana");
                ventanaAltaCostoFijo.setScene(new Scene(root));

                // Opcional: limpiar la referencia cuando se cierre
                ventanaAltaCostoFijo.setOnHidden(event -> ventanaAltaCostoFijo = null);
            }

            ventanaAltaCostoFijo.show();
            ventanaAltaCostoFijo.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionEditarCf()
    {
        try
        {
            if (ventanaAltaCostoFijo == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz9/interfazCostoFijo.fxml"));
                Parent root = loader.load();
                CostoFijoDto productoSeleccionado = tablaCostosFijos.getSelectionModel().getSelectedItem();
                if (productoSeleccionado == null)
                {
                    mostrarMensajeError("No se ha seleccionado ningun costo fijo");
                    return; // No hacer nada si no se seleccionó
                }

                ControladorAltaCosto controllerPF = loader.getController();
                controllerPF.setControladorPrincipal(this);
                controllerPF.setServicioCostoFijo(servicioCostosFijos);
                controllerPF.setModoEdicion(productoSeleccionado);
                ventanaAltaCostoFijo = new Stage();
                ventanaAltaCostoFijo.setTitle("Editar");
                ventanaAltaCostoFijo.setScene(new Scene(root));
                // Opcional: limpiar la referencia cuando se cierre
                ventanaAltaCostoFijo.setOnHidden(event -> ventanaAltaCostoFijo = null);
            }

            ventanaAltaCostoFijo.show();
            ventanaAltaCostoFijo.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionEliminarCf()
    {
        // Obtener el costo fijo seleccionado de la tabla
        CostoFijoDto seleccionado = tablaCostosFijos.getSelectionModel().getSelectedItem();

        if (seleccionado == null)
        {
            mostrarMensajeError("No se ha seleccionado ningún costo fijo para eliminar.");
            return;
        }

        // Mostrar confirmación antes de eliminar
        boolean confirmar = mostrarMensajeConfirmacion("¿Estás seguro de eliminar el costo fijo \"" + seleccionado.getNombre() + "\"?");

        if (!confirmar)
        {
            return; // el usuario canceló
        }

        try
        {
            servicioCostosFijos.eliminarCostoFijo(seleccionado.getId());
            mostrarMensajeExito("Costo fijo eliminado correctamente.");
            actualizarVista();

        } catch (CostoFijoException e)
        {
            mostrarMensajeError("Error al eliminar el costo fijo: " + e.getMessage());
        }
    }

    public boolean mostrarMensajeConfirmacion(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null); // sin texto superior
        alert.setContentText(mensaje);

        // Esperar respuesta del usuario
        var resultado = alert.showAndWait();

        // Devuelve true si presionó Aceptar, false si Cancelar o cerró la ventana
        return resultado.isPresent() && resultado.get() == ButtonType.OK;
    }

    private void mostrarMensajeExito(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

// Método para mostrar mensaje de error
    private void mostrarMensajeError(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public ControladorPf getControladorPrincipal()
    {
        return controladorPrincipal;
    }

    public void setControladorPrincipal(ControladorPf controladorPrincipal)
    {
        this.controladorPrincipal = controladorPrincipal;
    }

    public CostosFijosService getServicioCostosFijos()
    {
        return servicioCostosFijos;
    }

}
