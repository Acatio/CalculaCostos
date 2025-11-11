/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package appCalculaCostos.vista.interfaz2;

import appCalculaCostos.productoFinal.modelo.exepciones.ProductoFinalException;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalCreacionDTO;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalDatosDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.servicios.ServicioProductoFinal;
import appCalculaCostos.vista.interfaz1.ControladorPf;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jose
 */
public class InterfazProductoController
{

    private boolean modoEdicion = false;
    ControladorPf controladorPrincipal;
    private ServicioProductoFinal productoService;
    ProductoFinalDatosDto productoEditable;

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
            if (modoEdicion)
            {
                var nombre = txtNombre.getText();
                var porcentajeTxt = txtPorcentajeGanancia.getText();
                var cantVendidaTxt = txtCantidadVendida.getText();
                var precioVentaTxt = txtPrecioVenta.getText();

                // Convertir textos a double de forma segura
                double ganancia = porcentajeTxt.isEmpty() ? 0.0 : Double.parseDouble(porcentajeTxt);
                double cantidadVendida = cantVendidaTxt.isEmpty() ? 0.0 : Double.parseDouble(cantVendidaTxt);
                double precioV = precioVentaTxt.isEmpty() ? 0.0 : Double.parseDouble(precioVentaTxt);

                // Crear DTO con los valores convertidos
                ProductoFinalDatosDto productoEditado = new ProductoFinalDatosDto(
                        productoEditable.getId(),
                        nombre,
                        ganancia,
                        precioV,
                        -1,
                        cantidadVendida
                );

                // Aquí podrías llamar al servicio para actualizar
                productoService.actualizar(productoEditado);
                mostrarMensajeExito("Producto actualizado");
                productoService.actualizarPorcentajeDeGanancia(productoEditado.getId());
            } else
            {
                var nuevo = crearPfDto();
                productoService.guardarNuevoProductoSinCostos(nuevo);
                mostrarMensajeExito("Producto guardado");
                limpiarCampos();
            }
            controladorPrincipal.actualizarVista();
        } catch (ProductoFinalException ex)
        {
            mostrarMensajeError(ex.getMessage());
        } catch (NumberFormatException ex)
        {
            mostrarMensajeError("la ganancia, precio de venta, y cantidad vendida deben ser numeros");
        }

    }

    public void mostrarMensajeExito(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();

    }

    public void mostrarMensajeError(String mensaje)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
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

    public void setPrincipalController(ControladorPf controller)
    {
        this.controladorPrincipal = controller;
    }

    public void setProductoService(ServicioProductoFinal service)
    {
        this.productoService = service;
    }

    public boolean isModoEdicion()
    {
        return modoEdicion;
    }

    public void setModoEdicion(boolean modoEdicion)
    {
        this.modoEdicion = modoEdicion;
    }

    public ProductoFinalDatosDto getProducto()
    {
        return productoEditable;
    }

    public void setProducto(ProductoFinalDatosDto ProductoEditable)
    {
        this.productoEditable = ProductoEditable;
        if (ProductoEditable != null)
        {
            modoEdicion = true;
            cargarDatosProducto();
        } else
        {
            modoEdicion = false;
            limpiarCampos();
        }
    }

    private void cargarDatosProducto()
    {
        txtNombre.setText(productoEditable.getNombre());
        txtPorcentajeGanancia.setText(String.valueOf(productoEditable.getPorcentajeGanancia()));
        txtCantidadVendida.setText(String.valueOf(productoEditable.getCantidadVendida()));
        txtPrecioVenta.setText(String.valueOf(productoEditable.getPrecioVenta()));

    }

    private void limpiarCampos()
    {
        txtNombre.clear();
        txtPorcentajeGanancia.clear();
        txtCantidadVendida.clear();
        txtPrecioVenta.clear();
    }

}
