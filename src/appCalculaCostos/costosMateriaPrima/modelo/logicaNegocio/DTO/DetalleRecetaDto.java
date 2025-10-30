/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO;

/**
 *
 * @author jose
 */
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleRecetaDto {

    private int idInsumo;
    private StringProperty nombre;
    private DoubleProperty cantidad;
    private StringProperty nombreUnidadMedida;

    public DetalleRecetaDto(int idInsumo, String nombre, double cantidad, String nombreUnidadMedida) {
        this.idInsumo = idInsumo;
        this.nombre = new SimpleStringProperty(nombre);
        this.cantidad = new SimpleDoubleProperty(cantidad);
        this.nombreUnidadMedida = new SimpleStringProperty(nombreUnidadMedida);
    }

    // idInsumo (no requiere ser Property si no se edita en tabla)
    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }

    // nombre
    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    // cantidad
    public double getCantidad() { return cantidad.get(); }
    public void setCantidad(double cantidad) { this.cantidad.set(cantidad); }
    public DoubleProperty cantidadProperty() { return cantidad; }

    // nombreUnidadMedida
    public String getNombreUnidadMedida() { return nombreUnidadMedida.get(); }
    public void setNombreUnidadMedida(String unidad) { this.nombreUnidadMedida.set(unidad); }
    public StringProperty nombreUnidadMedidaProperty() { return nombreUnidadMedida; }
}
