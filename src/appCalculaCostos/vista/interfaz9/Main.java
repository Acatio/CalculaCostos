package appCalculaCostos.vista.interfaz9;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz9/interfazCostoFijo.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());
        stage.setTitle("Costo Fijo");
        stage.setScene(scene);
        ControladorAltaCosto c= loader.getController();
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
