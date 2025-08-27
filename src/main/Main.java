/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import conexion.ConexionSQL;
import controller.ControladorInsumo;
import datos.insumoDao.InsumoDaoImpl;
import vista.vistaConsola.VistaConsola;
import datos.insumoDao.IInsumoDAO;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vista.IvistaInsumos;

/**
 *
 * @author jose
 */
public class Main extends Application
{

//        IInsumoDAO insumoDao = new InsumoDaoImpl(new ConexionSQL());
//        IvistaInsumos vistaInsumos = new VistaConsola();
//        ControladorInsumo controladorMP = new ControladorInsumo(insumoDao, vistaInsumos);
//        try
//        {
//            controladorMP.iniciarApp();
//        } catch (Exception ex)
//        {
//           vistaInsumos.mostrarMensajeError(ex.getMessage());
//        }
    @Override
    public void start(Stage stage) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/view/VtnInsumos.fxml"));
        Scene scene = new Scene(loader.load());

        scene.getStylesheets().add(getClass().getResource("/styles/formulario.css").toExternalForm());

        stage.setTitle("Proyecto JavaFX - Ejemplo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
