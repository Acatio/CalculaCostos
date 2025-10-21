package interfaz4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("interfazProducto.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("estilo.css").toExternalForm());
        stage.setTitle("Registrar Producto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
