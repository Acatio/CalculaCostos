package appCalculaCostos.vista.interfaz8;

import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.InsumoDto;
import appCalculaCostos.vista.interfaz1.ControladorPf;
import appCalculaCostos.vista.interfaz9.ControladorAltaCosto;
import conexion.implementaciones.ConexionSQL;
import java.io.IOException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ControladorCostosFijos
{

    private ControladorPf controladorPrincipal;
    private Stage ventanaAltaCostoFijo;

    @FXML
    private TableColumn<InsumoDto, String> colNombre;

    @FXML
    private TableColumn<InsumoDto, String> colImporteMensual;

    @FXML
    private TableView<InsumoDto> tablaCostosFijos;

    @FXML
    public void initialize()
    {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        colImporteMensual.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().tipoInsumo()));
        actualizarVista();

    }

    public void actualizarVista()
    {
        mostrarCostosFijos();
        System.out.println("vista costos fijos actualizada");
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
    }

    @FXML
    private void onActionEliminarCf()
    {
    }

    public ControladorPf getControladorPrincipal()
    {
        return controladorPrincipal;
    }

    public void setControladorPrincipal(ControladorPf controladorPrincipal)
    {
        this.controladorPrincipal = controladorPrincipal;
    }

}
