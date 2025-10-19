package interfaz3;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controlador
{
    @FXML
    private TableView<?> tablaInsumos;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colTipo;
    @FXML
    private TableColumn<?, ?> colUnidad;

    @FXML
    private TableView<?> tablaIngredientes;
    @FXML
    private TableColumn<?, ?> colIdIng;
    @FXML
    private TableColumn<?, ?> colNombreIng;
    @FXML
    private TableColumn<?, ?> colCantidadIng;
    @FXML
    private TableColumn<?, ?> colCostoIng;

    @FXML
    public void initialize()
    {
        // Configurar encabezados personalizados
        colCostoIng.setText("Costo ($)");
        colCantidadIng.setText("Cantidad");
        colNombreIng.setText("Ingrediente");

        // Desactivar reordenamiento de columnas
        for (TableColumn<?, ?> col : tablaInsumos.getColumns())
        {
            col.setReorderable(false);
        }
        for (TableColumn<?, ?> col : tablaIngredientes.getColumns())
        {
            col.setReorderable(false);
        }
    }
}
