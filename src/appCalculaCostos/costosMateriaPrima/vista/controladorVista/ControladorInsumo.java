package appCalculaCostos.costosMateriaPrima.vista.controladorVista;

import appCalculaCostos.costosMateriaPrima.modelo.interfacesLogicas.IInsumoDAO;
import appCalculaCostos.costosMateriaPrima.modelo.daos.UnidadDeMedidaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.MateriaPrima;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.UnidadDeMedida;
import conexion.Conexion;
import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;


public class ControladorInsumo
{

    public ControladorInsumo()
    {
    }

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCosto;

    @FXML
    private TextField txtCantidad;

    @FXML
    private Button btnAceptar;

    private IInsumoDAO insumoDAO;

    @FXML
    public void initialize()
    {
        try
        {
            insumoDAO = new InsumoDaoImpl();

            btnAceptar.setOnAction(e ->
            {
                try
                {
                    nuevoInsumo();
                } catch (Exception ex)
                {
                    mostrarError("Error al guardar el insumo: " + ex.getMessage());
                }
            });

        } catch (Exception e)
        {
            mostrarError("Error al inicializar DAO: " + e.getMessage());
        }
    }

    @FXML
    public void nuevoInsumo() throws Exception
    {
        String nombre = txtNombre.getText().trim();
        double cantidad;
        double costo;

        try
        {
            cantidad = Double.parseDouble(txtCantidad.getText().trim());
        } catch (NumberFormatException e)
        {
            mostrarError("Ingrese una cantidad válida");
            return;
        }

        try
        {
            costo = Double.parseDouble(txtCosto.getText().trim());
        } catch (NumberFormatException e)
        {
            mostrarError("Ingrese un costo válido");
            return;
        }

        if (datosInsumoValidos(nombre, cantidad, costo))
        {
            UnidadDeMedidaDAO unidadDAO = new UnidadDeMedidaDAO();
            UnidadDeMedida unidad = unidadDAO.buscarPorNombre("Kilogramo");

            MateriaPrima insumo = new MateriaPrima(nombre, cantidad, unidad, costo);

            insumoDAO.guardarInsumo(insumo);
            mostrarMensaje("Producto guardado con éxito!!");

            txtNombre.clear();
            txtCantidad.clear();
            txtCosto.clear();
        }
    }

    private boolean datosInsumoValidos(String nombre, double cantidad, double costo)
    {
        if (nombre.isEmpty())
        {
            mostrarError("Ingrese un nombre válido");
            return false;
        }
        if (cantidad < 0)
        {
            mostrarError("Ingrese una cantidad mayor o igual a cero");
            return false;
        }
        if (costo < 0)
        {
            mostrarError("Ingrese un costo mayor o igual a cero");
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
