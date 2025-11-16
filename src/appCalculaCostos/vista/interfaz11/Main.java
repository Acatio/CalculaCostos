package appCalculaCostos.vista.interfaz11;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz11/interfazResumenCostos.fxml"));
        Scene scene = new Scene(loader.load());
        ControladorResumenCostos controlador=loader.getController();
        controlador.setProductoDto(new ProductoFinalAsignarCostoDto(27, "Producto Prueba"));
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
