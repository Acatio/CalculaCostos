package appCalculaCostos.vista.interfaz1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        try
        {
            // 1. Carga del FXML
            // Usamos la ruta absoluta: /view/opciones.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/appCalculaCostos/vista/interfaz1/vistaPF.fxml"));
            Scene scene = new Scene(loader.load());

            // 2. Carga del Estilo CSS
            // El error de "No resources specified" a menudo está aquí. 
            // Asegúrate de que existe un archivo 'estilo.css' dentro de la carpeta 'styles' 
            // en la raíz de tus fuentes.
            String cssPath = "estilo.css";

            // Verifica que el recurso exista antes de intentar cargarlo
            if (getClass().getResource(cssPath) != null)
            {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            } else
            {
                System.err.println("Advertencia: No se pudo encontrar el archivo CSS en la ruta: " + cssPath);
                // Si el CSS no existe, la aplicación debe seguir cargando la interfaz.
            }

            // 3. Mostrar la ventana
            stage.setTitle("Proyecto JavaFX - Opciones");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e)
        {
            // Imprime la traza completa para un mejor diagnóstico si el error persiste.
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
