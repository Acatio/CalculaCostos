package appCalculaCostos.vista.manoObra;

import appCalculaCostos.vista.interfaz10.ControladorAgregarCostoF;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalAsignarCostoDto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/manoObra/interfazAgregarCostoMo.fxml"));
        Scene scene = new Scene(loader.load());
        ControladorAgregarCostoMo controlador=loader.getController();
        controlador.setProductoDto(new ProductoFinalAsignarCostoDto(32, "Pizza mediana"));
        scene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());
        stage.setTitle("Registrar Receta");
        stage.setScene(scene);
        stage.setResizable(false); // para mantener el diseño fijo
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
