package interfaz3; // Asumo que esta es la ruta de tu Main

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        try {
            // Carga del FXML: Ajusta la ruta si 'interfazMateriaPrima.fxml' está en otro paquete
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfazMateriaPrima.fxml"));
            Scene scene = new Scene(loader.load());

            // Carga del Estilo CSS
            String cssPath = "estilo.css"; 
            
            if (getClass().getResource(cssPath) != null) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            } else {
                System.err.println("Advertencia: No se pudo encontrar el archivo CSS en la ruta: " + cssPath);
            }
            
            stage.setTitle("Proyecto JavaFX - Costo Materia Prima");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}