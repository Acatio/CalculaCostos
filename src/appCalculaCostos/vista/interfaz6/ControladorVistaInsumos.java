package appCalculaCostos.vista.interfaz6;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.Insumo;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.vista.interfaz2.InterfazProductoController;
import appCalculaCostos.vista.interfaz5.ControladorReceta;
import conexion.implementaciones.ConexionSQL;
import interfaz4.ControladorAltaInsumo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorVistaInsumos
{

    private ControladorReceta controladorInsumos;
    private Stage ventanaInsumos;
    private Stage ventanaRecetas;

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

        mostrarInsumos();

    }

    public void actualizarVista()
    {
        mostrarInsumos();
        System.out.println("vista actualizada");
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
            if (ventanaInsumos == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz4/interfazInsumo.fxml"));
                Parent root = loader.load();
                ControladorAltaInsumo controladorInsumo = loader.getController();
                controladorInsumo.setControladorPrincipal(this);
//                controllerPF.setProductoService(productoService);
                ventanaInsumos = new Stage();
                ventanaInsumos.setTitle("Nueva Ventana");
                ventanaInsumos.setScene(new Scene(root));

                // Opcional: limpiar la referencia cuando se cierre
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
            if (ventanaRecetas == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz5/interfazReceta.fxml"));
                Parent root = loader.load();
                ControladorReceta controlador = loader.getController();
                controlador.setControlador(this);
//                controllerPF.setProductoService(productoService);
                ventanaRecetas = new Stage();
                ventanaRecetas.setTitle("Nueva Ventana");
                ventanaRecetas.setScene(new Scene(root));
                actualizarVista();
                // Opcional: limpiar la referencia cuando se cierre
                ventanaRecetas.setOnHidden(event -> ventanaInsumos = null);
            }

            ventanaRecetas.show();
            ventanaRecetas.toFront(); // Si ya estaba abierta, la trae al frente
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

    public ControladorReceta getControladorInsumos()
    {
        return controladorInsumos;
    }

    public void setControladorInsumos(ControladorReceta controladorInsumos)
    {
        this.controladorInsumos = controladorInsumos;
    }

}
