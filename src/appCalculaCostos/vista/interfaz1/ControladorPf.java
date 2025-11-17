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
import appCalculaCostos.vista.Rutas;
import appCalculaCostos.vista.interfaz10.ControladorAgregarCostoF;
import appCalculaCostos.vista.interfaz11.ControladorResumenCostos;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControladorPf
{

    private Stage ventanaNuevoProducto;
    private Stage ventanaInsumos;
    private Stage ventanaCmp;
    private Stage ventanaAltaCostosFijos;
    private Stage ventanaAsignacionCostosFijos;
    private Stage ventanaResumenCostos;
    IConexion conexion = new ConexionSQL();
    String cssPath = "/appCalculaCostos/vista/interfaz1/estilo.css";
    private ServicioProductoFinal productoService = new ServicioProductoFinal(new ProductoFinalDaoImpl(conexion), new CostoMpRepoImpl(conexion), new InsumoDaoImpl(conexion));
    private final CostosFijosService servicioCostoFijo = new CostosFijosService(new CostoFijoRepoImpl(conexion));
    private final CalculoCostosFijosService ServicioCalculoCostoFijo = new CalculoCostosFijosService(new CostoFijoRepoImpl(conexion), new ProductoFinalDaoImpl(conexion), productoService);

    @FXML
    private TableColumn<ProductoFinalDatosDto, String> colNombre;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colPorcentajeGanancia;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colPrecioVenta;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colCosto;

    @FXML
    private TableColumn<ProductoFinalDatosDto, Double> colVentasMensuales;

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
        colVentasMensuales.setCellValueFactory(new PropertyValueFactory<>("CantidadVendida"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz2/interfazProducto.fxml"));
            Parent root = loader.load();
            InterfazProductoController controllerPF = loader.getController();
            controllerPF.setPrincipalController(this);
            controllerPF.setProductoService(productoService);
            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            if (ventanaNuevoProducto == null)
            {
                ventanaNuevoProducto = new Stage();
                ventanaNuevoProducto.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
                ventanaNuevoProducto.setTitle("Nuevo Producto");
                ventanaNuevoProducto.setScene(escena);
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

        ProductoFinalDatosDto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null)
        {
            mostrarMensajeError("No se ha seleccionado ningún producto");
            return;
        }

        try
        {
            // Siempre cargar nuevo FXML para asegurarte de limpiar datos previos
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/appCalculaCostos/vista/interfaz2/interfazProducto.fxml"
            ));
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            InterfazProductoController controller = loader.getController();
            controller.setPrincipalController(this);
            controller.setProductoService(productoService);
            controller.setProducto(productoSeleccionado);

            if (ventanaNuevoProducto == null)
            {
                ventanaNuevoProducto = new Stage();
                ventanaNuevoProducto.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
                ventanaNuevoProducto.setTitle("Editar");
                ventanaNuevoProducto.setOnHidden(e -> ventanaNuevoProducto = null);
            }

            // Actualizar escena siempre
            ventanaNuevoProducto.setScene(escena);
            ventanaNuevoProducto.show();
            ventanaNuevoProducto.toFront();

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz6/vistaInsumos.fxml"));
            Parent root = loader.load();
            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            if (ventanaInsumos == null)
            {
                ventanaInsumos = new Stage();
                ventanaInsumos.setTitle("Insumos");
                ventanaInsumos.setScene(escena);
                ventanaInsumos.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
                // Opcional: limpiar la referencia cuando se cierre
                ventanaInsumos.setOnHidden(event -> ventanaNuevoProducto = null);
            }
            ControladorVistaInsumos controlerVi = loader.getController();
            controlerVi.actualizarVista();
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
                mostrarMensajeError("No se ha seleccionado ningún producto");
                return;
            }

            // Si la ventana YA está abierta → solo traerla enfrente
            if (ventanaCmp != null)
            {
                ventanaCmp.toFront();
                return;
            }

            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/appCalculaCostos/vista/interfaz7/interfazAgregarCosto.fxml"
            ));
            Parent root = loader.load();

            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            // Configurar controlador
            ControladorAgregarCostoMp controladorCostoMp = loader.getController();
            controladorCostoMp.setControladorP(this);
            controladorCostoMp.setProductoDto(new ProductoFinalAsignarCostoDto(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre()
            ));

            // Crear la nueva ventana
            ventanaCmp = new Stage();
            ventanaCmp.setTitle("Costos de Materia Prima");
            ventanaCmp.setScene(escena);
            ventanaCmp.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
            // IMPORTANTE: limpiar referencia al cerrarse
            ventanaCmp.setOnHidden(e -> ventanaCmp = null);

            ventanaCmp.show();

        } catch (IOException ex)
        {
            mostrarMensajeError("Ocurrió un error al cargar la ventana");
        }
    }

    @FXML
    public void onInventarioCostosFijos()
    {
        try
        {
            if (ventanaAltaCostosFijos == null)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/appCalculaCostos/vista/interfaz8/vistaCostosFijos.fxml"
                ));
                Parent root = loader.load();

                ControladorCostosFijos controladorCf = loader.getController();
                controladorCf.setControladorPrincipal(this);

                Scene escena = new Scene(root);
                escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

                ventanaAltaCostosFijos = new Stage();
                ventanaAltaCostosFijos.setTitle("Costos Fijos");
                ventanaAltaCostosFijos.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
                ventanaAltaCostosFijos.setScene(escena);

                ventanaAltaCostosFijos.setOnHidden(event -> ventanaAltaCostosFijos = null);
            }

            ventanaAltaCostosFijos.show();
            ventanaAltaCostosFijos.toFront();
        } catch (IOException e)
        {
            mostrarMensajeError("Ocurrió un error al cargar la ventana");
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
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/appCalculaCostos/vista/interfaz10/interfazAgregarCostoF.fxml"
            ));
            Parent root = loader.load();

            ControladorAgregarCostoF controlador = loader.getController();
            controlador.setControladorP(this);

            PonderacionDto ponderacion = servicioCostoFijo.cargarPonderacion(productoSeleccionado.getId());
            controlador.setPonderacion(ponderacion);

            controlador.setProductoDto(new ProductoFinalAsignarCostoDto(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre()
            ));

            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

            ventanaAsignacionCostosFijos = new Stage();
            ventanaAsignacionCostosFijos.setTitle("Costos Fijos");
            ventanaAsignacionCostosFijos.setScene(escena);
            ventanaAsignacionCostosFijos.getIcons().add(new Image(getClass().getResourceAsStream(Rutas.RUTA_LOGO)));
            // ⭐ Esta línea es MUY importante
            ventanaAsignacionCostosFijos.setOnHidden(e -> ventanaAsignacionCostosFijos = null);

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
    private void onVerResumen()
    {
        try
        {
            ProductoFinalDatosDto productoSeleccionado = tablaProductos.getSelectionModel().getSelectedItem();

            if (productoSeleccionado == null)
            {
                mostrarMensajeError("No se ha seleccionado ningún producto");
                return;
            }

            // Siempre carga el FXML para actualizar los datos
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/appCalculaCostos/vista/interfaz11/interfazResumenCostos.fxml"
            ));
            Parent root = loader.load();

            ControladorResumenCostos controlador = loader.getController();
            controlador.setSpf(productoService);
            controlador.setProductoDto(new ProductoFinalAsignarCostoDto(
                    productoSeleccionado.getId(),
                    productoSeleccionado.getNombre()
            ));

            // Crear o reutilizar ventana
            if (ventanaResumenCostos == null)
            {
                ventanaResumenCostos = new Stage();
                ventanaResumenCostos.setTitle("Resumen de costos");
                ventanaResumenCostos.setOnHidden(e -> ventanaResumenCostos = null);
            }
            Scene escena = new Scene(root);
            escena.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            ventanaResumenCostos.setScene(escena);
            ventanaResumenCostos.show();
            ventanaResumenCostos.toFront();

        } catch (IOException e)
        {
            e.printStackTrace();
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
