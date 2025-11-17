package appCalculaCostos.vista.interfaz6;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.vista.Rutas;
import appCalculaCostos.vista.interfaz4.ControladorAltaInsumo;
import appCalculaCostos.vista.interfaz5.ControladorReceta;
import appCalculaCostos.vista.interfaz10.ControladorAgregarCostoF;
import conexion.implementaciones.ConexionSQL;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorVistaInsumos
{

    private ControladorAgregarCostoF controladorInsumos;
    private Stage ventanaInsumos;
    private Stage ventanaRecetas;
    String cssPath = "/appCalculaCostos/vista/interfaz1/estilo.css";
    private final MateriaPrimaService mps = new MateriaPrimaService(new InsumoDaoImpl(new ConexionSQL()));

    @FXML
    private TableColumn<InsumoDto, String> colNombre;

    @FXML
    private TableColumn<InsumoDto, String> colTipo;

    @FXML
    private TableColumn<InsumoDto, String> colUnidadMedida;

    @FXML
    private TableColumn<InsumoDto, Double> colCantidad;

    @FXML
    private TableColumn<InsumoDto, Double> colCostoTotal;

    @FXML
    private MenuItem itmNuevo;

    @FXML
    private MenuItem itmEditar;

    @FXML
    private TableView<InsumoDto> tablaProductos;

    @FXML
    public void initialize()
    {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipoInsumo()));
        colUnidadMedida.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().unidadMedida()));
        colCantidad.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().cantidad()).asObject());
        colCostoTotal.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().costo()).asObject());
        actualizarVista();

    }

    public void actualizarVista()
    {
        mostrarInsumos();
    }

    public void mostrarInsumos()
    {
        List<InsumoDto> insumos;
        try
        {
            insumos = mps.listarInsumos();
            tablaProductos.setItems(FXCollections.observableArrayList(insumos));
        } catch (InsumoException ex)
        {
            System.out.println("error al cargar los insumos...");
        }
    }

    @FXML
    private void onActionNuevoMp()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz4/interfazInsumo.fxml"));
            Parent root = loader.load();
            ControladorAltaInsumo controladorInsumo = loader.getController();
            controladorInsumo.setControladorPrincipal(this);
            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            if (ventanaInsumos == null)
            {
                ventanaInsumos = new Stage();
                ventanaInsumos.setTitle("Materias Primas");
                ventanaInsumos.setScene(escena);
                // Opcional: limpiar la referencia cuando se cierre
                ventanaInsumos.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
                ventanaInsumos.setOnHidden(event -> ventanaInsumos = null);
            }

            ventanaInsumos.show();
            ventanaInsumos.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionEditarMp()
    {
    }

    @FXML
    private void onActionEliminarMp()
    {
    }

    @FXML
    private void onActionNuevoR()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz5/interfazReceta.fxml"));
            Parent root = loader.load();

            ControladorReceta controlador = loader.getController();
            controlador.setControlador(this);
            controlador.listarInsumos();

            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            if (ventanaRecetas == null)
            {
                ventanaRecetas = new Stage();
                ventanaRecetas.setTitle("Recetas");
                ventanaRecetas.setOnHidden(e -> ventanaRecetas = null);
                ventanaRecetas.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
            }

            ventanaRecetas.setScene(escena);
            ventanaRecetas.show();
            ventanaRecetas.toFront();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void onActionEditarR()
    {
    }

    @FXML
    private void onActionEliminarR()
    {
    }

    public ControladorAgregarCostoF getControladorInsumos()
    {
        return controladorInsumos;
    }

    public void setControladorInsumos(ControladorAgregarCostoF controladorInsumos)
    {
        this.controladorInsumos = controladorInsumos;
    }

}
