package appCalculaCostos.vista.interfaz1;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.costosMateriaPrima.modelo.daos.CostoMpRepoImpl;
import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalAsignarCostoDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.ProductoFinal;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.vista.interfaz2.InterfazProductoController;
import appCalculaCostos.vista.interfaz6.ControladorVistaInsumos;
import appCalculaCostos.vista.interfaz7.ControladorCosto;
import conexion.implementaciones.ConexionSQL;
import conexion.interfacesLogicas.IConexion;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controlador
{

    private Stage ventanaNuevoProducto;
    private Stage ventanaInsumos;
    private Stage ventanaCmp;
    IConexion conexion=new ConexionSQL();
    private ServicioProductoFinal productoService = new ServicioProductoFinal(new ProductoFinalDaoImpl(conexion), new CostoMpRepoImpl(conexion), new InsumoDaoImpl(conexion));
    
    @FXML
    private TableColumn<ProductoFinal, String> colNombre;

    @FXML
    private TableColumn<ProductoFinal, Double> colPorcentajeGanancia;

    @FXML
    private TableColumn<ProductoFinal, Double> colPrecioVenta;

    @FXML
    private TableColumn<ProductoFinal, Double> colCosto;

    @FXML
    private MenuItem itmNuevo;

    @FXML
    private MenuItem itmEditar;

    @FXML
    private TableView<ProductoFinal> tablaProductos;

    @FXML
    public void initialize()
    {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPorcentajeGanancia.setCellValueFactory(new PropertyValueFactory<>("PorcentajeGanancia"));
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("PrecioVenta"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("CostoTotal"));
        mostrarProductos();

    }

    public void actualizarVista()
    {
        mostrarProductos();
        System.out.println("vista actualizada");
    }
    public void mostrarProductos()
    {
        List<ProductoFinal> productos;
        try
        {
            productos = productoService.listarProductosFinales();
            tablaProductos.setItems(FXCollections.observableArrayList(productos));
        } catch (ProductoFinalException ex)
        {
            System.out.println("error al cargar los productos...");
        }
    }

    @FXML
    private void onActionNuevo()
    {
        try
        {
            if (ventanaNuevoProducto == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz2/interfazProducto.fxml"));
                Parent root = loader.load();
                InterfazProductoController controllerPF = loader.getController();
                controllerPF.setPrincipalController(this);
                controllerPF.setProductoService(productoService);
                ventanaNuevoProducto = new Stage();
                ventanaNuevoProducto.setTitle("Nueva Ventana");
                ventanaNuevoProducto.setScene(new Scene(root));

                // Opcional: limpiar la referencia cuando se cierre
                ventanaNuevoProducto.setOnHidden(event -> ventanaNuevoProducto = null);
            }

            ventanaNuevoProducto.show();
            ventanaNuevoProducto.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void onActionEditar()
    {
        System.out.println("Editar");
    }

    @FXML
    private void onActionInsumos()
    {
        try
        {
            if (ventanaInsumos == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz6/vistaInsumos.fxml"));
                Parent root = loader.load();

                ventanaInsumos = new Stage();
                ventanaInsumos.setTitle("Nueva Ventana");
                ventanaInsumos.setScene(new Scene(root));
                ControladorVistaInsumos controlerVi= loader.getController();
                controlerVi.actualizarVista();

                // Opcional: limpiar la referencia cuando se cierre
                ventanaInsumos.setOnHidden(event -> ventanaNuevoProducto = null);
            }

            ventanaInsumos.show();
            ventanaInsumos.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAgregarCmp()
    {
        try
        {
            // Obtener el producto seleccionado
            ProductoFinal productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null)
            {
                mostrarMensajeError("No se ha seleccionado ningun producto");
                return; // No hacer nada si no se seleccionó
            }

            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz7/interfazAgregarCosto.fxml"));
            Parent root = loader.load();
            ControladorCosto controladorCostoMp= loader.getController();
            controladorCostoMp.setControladorP(this);
            // Crear la nueva ventana
            ventanaCmp = new Stage();
            ventanaCmp.setTitle("Costos de Materia Prima");
            ventanaCmp.setScene(new Scene(root));

            // Obtener el controlador y pasarle el DTO
            ControladorCosto controlador = loader.getController();
            controlador.setProductoDto(new ProductoFinalAsignarCostoDto(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre()
            ));
            // Mostrar la ventana
            ventanaCmp.show();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void mostrarMensajeExito(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    public void mostrarMensajeError(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    public ServicioProductoFinal getProductoService()
    {
        return productoService;
    }

    public void setProductoService(ServicioProductoFinal productoService)
    {
        this.productoService = productoService;
    }
    
}
