package appCalculaCostos.vista.principalEmpleados;

import appCalculaCostos.CostoManoObra.Modelo.Excepciones.ManoObraException;
import appCalculaCostos.CostoManoObra.Modelo.Servicio.EmpleadoService;
import appCalculaCostos.CostoManoObra.Modelo.dto.EmpleadoDTO;
import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.Servicio.CostosFijosService;
import appCalculaCostos.costosFijos.Modelo.dao.CostoFijoRepoImpl;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalDatosDto;
import appCalculaCostos.vista.Rutas;
import appCalculaCostos.vista.altaEmpleado.ControladorAltaEmpleado;
import appCalculaCostos.vista.interfaz1.ControladorPf;
import appCalculaCostos.vista.interfaz2.InterfazProductoController;
import appCalculaCostos.vista.interfaz9.ControladorAltaCosto;
import conexion.implementaciones.ConexionSQL;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorPrincipalEmpleados
{

    private final EmpleadoService servicioEmpleado =new EmpleadoService();
    private Stage ventanaAltaEmpleado;
    String cssPath = "/appCalculaCostos/vista/interfaz1/estilo.css";
    @FXML
    private TableColumn<EmpleadoDTO, String> colNombre;

    @FXML
    private TableColumn<EmpleadoDTO, String> colApellido;

    @FXML
    private TableColumn<EmpleadoDTO, Double> colSalario;
    
    @FXML
    private TableColumn<EmpleadoDTO, Float> colHoras;

    @FXML
    private TableView<EmpleadoDTO> tablaEmpleados;

    @FXML
    public void initialize()
    {
        // Configura las columnas
        colNombre.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNombre())
        );

        colApellido.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getApellido())
        );

        colSalario.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getSalarioSemanal()).asObject()
        );
        colHoras.setCellValueFactory(cellData
                -> new SimpleFloatProperty(cellData.getValue().getHorasSemana()).asObject()
        );

        // Carga los datos
        actualizarVista();
    }

    public void actualizarVista()
    {
        try
        {
            List<EmpleadoDTO> lista = servicioEmpleado.listarEmpleados();
            tablaEmpleados.getItems().setAll(lista);
        } catch (ManoObraException e)
        {
            mostrarMensajeError("Error al cargar los empleados");

        }
    }

    public void mostrarCostosFijos()
    {

    }

    @FXML
    private void onNuevoEmpleado()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/altaEmpleado/interfazAltaEmpleado.fxml"));
            Parent root = loader.load();
            ControladorAltaEmpleado controladorAltaEmpleado = loader.getController();
            controladorAltaEmpleado.setControladorPrincipal(this);
            controladorAltaEmpleado.setServicioEmpleado(servicioEmpleado);

            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            if (ventanaAltaEmpleado == null)
            {


                ventanaAltaEmpleado = new Stage();
                ventanaAltaEmpleado.setTitle("Empleados");
                ventanaAltaEmpleado.setScene(escena);
                ventanaAltaEmpleado.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));

                // Opcional: limpiar la referencia cuando se cierre
                ventanaAltaEmpleado.setOnHidden(event -> ventanaAltaEmpleado = null);
            }

            ventanaAltaEmpleado.show();
            ventanaAltaEmpleado.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEditarEmpleado()
    {
        try
        {
            EmpleadoDTO empleadoSeleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();
            if (empleadoSeleccionado == null)
            {
                mostrarMensajeError("No se ha seleccionado ningun empleado");
                return; // No hacer nada si no se seleccionó
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/altaEmpleado/interfazAltaEmpleado.fxml"));
            Parent root = loader.load();
            ControladorAltaEmpleado controladorAltaEmpleado = loader.getController();
            controladorAltaEmpleado.setControladorPrincipal(this);
            controladorAltaEmpleado.setModoEdicion(empleadoSeleccionado);
            controladorAltaEmpleado.setServicioEmpleado(servicioEmpleado);
            System.out.println(empleadoSeleccionado);
            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            if (ventanaAltaEmpleado == null)
            {

                ventanaAltaEmpleado = new Stage();
                ventanaAltaEmpleado.setTitle("Editar");
                ventanaAltaEmpleado.setScene(escena);
                ventanaAltaEmpleado.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
                // Opcional: limpiar la referencia cuando se cierre
                ventanaAltaEmpleado.setOnHidden(event -> ventanaAltaEmpleado = null);
            }

            ventanaAltaEmpleado.show();
            ventanaAltaEmpleado.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarEmpleado()
    {
        // Obtener el costo fijo seleccionado de la tabla
        EmpleadoDTO seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();

        if (seleccionado == null)
        {
            mostrarMensajeError("No se ha seleccionado ningún empleado para eliminar.");
            return;
        }

        // Mostrar confirmación antes de eliminar
        boolean confirmar = mostrarMensajeConfirmacion("¿Estás seguro de eliminar el empleado\"" + seleccionado.getNombre() + "\"?");

        if (!confirmar)
        {
            return; // el usuario canceló
        }

        try
        {
            servicioEmpleado.eliminarEmpleado(seleccionado.getId());
            mostrarMensajeExito("Empleado eliminado correctamente.");
            actualizarVista();

        } catch (ManoObraException e)
        {
            mostrarMensajeError( e.getMessage());
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



}
