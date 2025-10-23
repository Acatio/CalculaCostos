/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package appCalculaCostos.vista.interfaz2;

import appCalculaCostos.productoFinal.modelo.daos.ProductoFinalDaoImpl;
import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.vista.interfaz1.Controlador;
import conexion.implementaciones.ConexionSQL;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jose
 */
public class InterfazProductoController
{

    Controlador controladorPrincipal;
    private ServicioProductoFinal productoService;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtPorcentajeGanancia;
    @FXML
    private TextField txtPrecioVenta;
    @FXML
    private TextField txtCantidadVendida;
    @FXML
    private Button btnSiguiente;

    @FXML
    public void onActionAceptar()
    {
        try
        {
            var nuevo = crearPfDto();
            productoService.guardarNuevoProductoSinCostos(nuevo);
            controladorPrincipal.actualizarVista();
            mostrarMensajeExito("Producto guardado");
//            Stage stage = (Stage) btnSiguiente.getScene().getWindow();
//            stage.close();
        } catch (ProductoFinalException ex)
        {
            System.out.println("Error: No se pudo guardar el producto");
        } catch (NumberFormatException ex)
        {
            System.out.println("Error: ingrese campos validos");
        }

    }

    public void mostrarMensajeExito(String mensaje)
    {
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setContentText(mensaje);
        alert.showAndWait();
        
    }

    public ProductoFinalCreacionDTO crearPfDto()
    {
        var nombre = txtNombre.getText();
        Double porcentajeGanancia = Double.valueOf(txtPorcentajeGanancia.getText());
        Double precioVenta = Double.valueOf(txtPrecioVenta.getText());
        var cantidadVendida = Integer.parseInt(txtCantidadVendida.getText());
        if (porcentajeGanancia == 0)
        {
            porcentajeGanancia = null;
        }
        if (precioVenta == 0)
        {
            precioVenta = null;
        }
        ProductoFinalCreacionDTO nuevo = new ProductoFinalCreacionDTO(nombre, cantidadVendida, porcentajeGanancia, precioVenta, new ArrayList<>());
        return nuevo;
    }

    public void setPrincipalController(Controlador controller)
    {
        this.controladorPrincipal = controller;
    }

    public void setProductoService(ServicioProductoFinal service)
    {
        this.productoService = service;
    }

}
