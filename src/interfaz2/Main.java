package interfaz2;

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
            // 1. Carga del FXML: Asumo que 'interfazProducto.fxml' está en la carpeta 'interfaz2' o 'interfaz'
            // NOTA: Si 'interfazProducto.fxml' está en un paquete llamado 'view', cámbialo a: "/view/interfazProducto.fxml"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfazProducto.fxml"));
            Scene scene = new Scene(loader.load());

            // 2. Carga del Estilo CSS (CORREGIDO)
            // Usamos la ruta absoluta: /styles/estilo.css
            String cssPath = "estilo.css"; 
            
            // Si tu archivo CSS está en la misma carpeta que el FXML (interfaz2), usa:
            // String cssPath = "estilo.css"; 
            
            // Verifica y carga el recurso CSS
            if (getClass().getResource(cssPath) != null) {
                scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
                System.out.println("CSS cargado correctamente desde: " + cssPath);
            } else {
                System.err.println("Advertencia: No se pudo encontrar el archivo CSS en la ruta: " + cssPath);
            }
            
            // 3. Mostrar la ventana
            stage.setTitle("Proyecto JavaFX - Opciones");
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