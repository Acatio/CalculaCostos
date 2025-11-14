package appCalculaCostos.vista.interfaz1;

import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.Servicio.CalculoCostosFijosService;
import appCalculaCostos.costosFijos.Modelo.Servicio.CostosFijosService;
import appCalculaCostos.costosFijos.Modelo.dao.CostoFijoRepoImpl;
import appCalculaCostos.costosFijos.Modelo.dto.PonderacionDto;
import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrimaService;
import appCalculaCostos.costosMateriaPrima.modelo.daos.CostoMpRepoImpl;
import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalAsignarCostoDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalDatosDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.vista.interfaz10.ControladorAgregarCostoF;
import appCalculaCostos.vista.interfaz2.InterfazProductoController;
import appCalculaCostos.vista.interfaz6.ControladorVistaInsumos;
import appCalculaCostos.vista.interfaz7.ControladorAgregarCostoMp;
import appCalculaCostos.vista.interfaz8.ControladorCostosFijos;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorPf
{

    private Stage ventanaNuevoProducto;
    private Stage ventanaInsumos;
    private Stage ventanaCmp;
    private Stage ventanaAltaCostosFijos;
    private Stage ventanaAsignacionCostosFijos;
    IConexion conexion = new ConexionSQL();

    private ServicioProductoFinal productoService = new ServicioProductoFinal(new ProductoFinalDaoImpl(conexion), new CostoMpRepoImpl(conexion), new InsumoDaoImpl(conexion));
    private CostosFijosService servicioCostoFijo = new CostosFijosService(new CostoFijoRepoImpl(conexion));
    private CalculoCostosFijosService ServicioCalculoCostoFijo = new CalculoCostosFijosService(new CostoFijoRepoImpl(conexion), new ProductoFinalDaoImpl(conexion), productoService);

    @FXML
    private TableColumn<ProductoFinalDatosDto, String> colNombre;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colPorcentajeGanancia;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colPrecioVenta;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colCosto;

    @FXML
    private MenuItem itmNuevo;

    @FXML
    private MenuItem itmEditar;

    @FXML
    private TableView<ProductoFinalDatosDto> tablaProductos;

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
        List<ProductoFinalDatosDto> productos;
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
                ventanaNuevoProducto.setTitle("Nuevo Producto");
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
        try
        {
            if (ventanaNuevoProducto == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz2/interfazProducto.fxml"));
                Parent root = loader.load();
                ProductoFinalDatosDto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();
                if (productoSeleccionado == null)
                {
                    mostrarMensajeError("No se ha seleccionado ningun producto");
                    return; // No hacer nada si no se seleccionó
                }

                InterfazProductoController controllerPF = loader.getController();
                controllerPF.setPrincipalController(this);
                controllerPF.setProductoService(productoService);
                controllerPF.setProducto(productoSeleccionado);
                ventanaNuevoProducto = new Stage();
                ventanaNuevoProducto.setTitle("Editar");
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
    private void onActionEliminar()
    {
        ProductoFinalDatosDto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null)
        {
            mostrarMensajeError("No se ha seleccionado ningun producto");
            return; // No hacer nada si no se seleccionó
        }

        if (mostrarMensajeConfirmacion("Se borrará el producto: " + productoSeleccionado.getNombre() + " y sus costos asociados"))
        {
            try
            {
                productoService.borrarProductoFinalPorId(productoSeleccionado.getId());
                actualizarVista();
            } catch (ProductoFinalException ex)
            {
                mostrarMensajeError(ex.getMessage());
            }
        }

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
                ControladorVistaInsumos controlerVi = loader.getController();
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
            ProductoFinalDatosDto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null)
            {
                mostrarMensajeError("No se ha seleccionado ningun producto");
                return; // No hacer nada si no se seleccionó
            }

            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz7/interfazAgregarCosto.fxml"));
            Parent root = loader.load();
            ControladorAgregarCostoMp controladorCostoMp = loader.getController();
            controladorCostoMp.setControladorP(this);
            // Obtener el controlador y pasarle el DTO
            controladorCostoMp.setProductoDto(new ProductoFinalAsignarCostoDto(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre()
            ));
            System.out.println("Mandaado: " + productoSeleccionado.getNombre());

            // Crear la nueva ventana
            ventanaCmp = new Stage();
            ventanaCmp.setTitle("Costos de Materia Prima");
            ventanaCmp.setScene(new Scene(root));

            // Mostrar la ventana
            ventanaCmp.show();
        } catch (IOException ex)
        {
            mostrarMensajeError("Ocurrio un error al cargar la ventana");
        }
    }

    @FXML
    public void onInventarioCostosFijos()
    {
        try
        {
            if (ventanaAltaCostosFijos == null)
            {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz8/vistaCostosFijos.fxml"));
                Parent root = loader.load();
                ControladorCostosFijos controladorCf = loader.getController();
                controladorCf.setControladorPrincipal(this);
                ventanaAltaCostosFijos = new Stage();
                ventanaAltaCostosFijos.setTitle("Costos Fijos");
                ventanaAltaCostosFijos.setScene(new Scene(root));

                // Opcional: limpiar la referencia cuando se cierre
                ventanaAltaCostosFijos.setOnHidden(event -> ventanaNuevoProducto = null);
            }

            ventanaAltaCostosFijos.show();
            ventanaAltaCostosFijos.toFront(); // Si ya estaba abierta, la trae al frente
        } catch (IOException e)
        {
            mostrarMensajeError("Ocurrio un error al cargar la ventana");
        }

    }

    @FXML
    public void onAgregarCf()
    {
        try
        {
            ProductoFinalDatosDto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null)
            {
                mostrarMensajeError("No se ha seleccionado ningun producto");
                return; // No hacer nada si no se seleccionó
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz10/interfazAgregarCostoF.fxml"));
            Parent root = loader.load();
            ControladorAgregarCostoF controlador = loader.getController();
            controlador.setControladorP(this);
            PonderacionDto ponderacion = servicioCostoFijo.cargarPonderacion(productoSeleccionado.getId());
            controlador.setPonderacion(ponderacion);
            controlador.setProductoDto(new ProductoFinalAsignarCostoDto(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre()
            ));

            // Crear la nueva ventana
            ventanaAsignacionCostosFijos = new Stage();
            ventanaAsignacionCostosFijos.setTitle("Costos Fijos");
            ventanaAsignacionCostosFijos.setScene(new Scene(root));

            // Mostrar la ventana
            ventanaAsignacionCostosFijos.show();
        } catch (IOException ex)
        {
            mostrarMensajeError("Ocurrio un error al cargar la ventana");
        } catch (CostoFijoException ex)
        {
            mostrarMensajeError(ex.getMessage());
        }
    }

    @FXML
    public void onCalcularCostosFijos()
    {
        try
        {
            if (mostrarMensajeConfirmacion("El costo fijo se asignara a todos los producto en base a su ponderacion."))
            {
                ServicioCalculoCostoFijo.calcularCostosFijos();
                mostrarMensajeExito("Se asigno el costo fijo a todos los productos.");
                actualizarVista();
            }
            
        } catch (CostoFijoException ex)
        {
            mostrarMensajeError(ex.getMessage());
        }
    }

    public void mostrarMensajeError(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    public void mostrarMensajeExito(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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

    public ServicioProductoFinal getProductoService()
    {
        return productoService;
    }

    public void setProductoService(ServicioProductoFinal productoService)
    {
        this.productoService = productoService;
    }

}
