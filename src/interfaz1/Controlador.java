package interfaz1;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Controlador
{

    @FXML
    private TableView<?> tablaOpciones;

    @FXML
    private TableColumn<?, ?> colPorcentaje;

    @FXML
    public void initialize()
    {
        colPorcentaje.setText("% de Ganancia");

        for (TableColumn<?, ?> col : tablaOpciones.getColumns())
        {
            col.setReorderable(false);
        }
    }
}
