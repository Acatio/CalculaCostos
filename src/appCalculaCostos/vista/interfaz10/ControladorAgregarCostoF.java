package appCalculaCostos.vista.interfaz10;

import appCalculaCostos.costosFijos.Modelo.Excepciones.CostoFijoException;
import appCalculaCostos.costosFijos.Modelo.Servicio.CostosFijosService;
import appCalculaCostos.costosFijos.Modelo.dao.CostoFijoRepoImpl;
import appCalculaCostos.costosFijos.Modelo.dto.PonderacionDto;
import appCalculaCostos.productoFinal.modelo.logicaNegocio.DTO.ProductoFinalAsignarCostoDto;
import appCalculaCostos.vista.interfaz1.ControladorPf;
import conexion.implementaciones.ConexionSQL;
import conexion.interfacesLogicas.IConexion;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ControladorAgregarCostoF
{

    private final IConexion conexion = new ConexionSQL();
    private final CostosFijosService servicioCostoFijo = new CostosFijosService(new CostoFijoRepoImpl(conexion));
    private ProductoFinalAsignarCostoDto productoDto;
    private PonderacionDto ponderacion;
    ControladorPf controladorP;

    @FXML
    private Label lblNombre;

    @FXML
    private ComboBox<String> cmbTamanio;
    @FXML
    private ComboBox<String> cmbTiempo;
    @FXML
    private ComboBox<String> cmbRecursos;

// Mapa de pesos
    private final Map<String, Integer> pesosTamanio = new LinkedHashMap<>()
    {
        {
            put("El mas pequeño", 1);
            put("Pequeño", 2);
            put("Mediano", 3);
            put("Grande", 4);
            put("El mas grande", 5);
        }
    };

    private final Map<String, Integer> pesosTiempo = new LinkedHashMap<>()
    {
        {
            put("El menos tardado", 1);
            put("Poco", 2);
            put("Medio", 3);
            put("Mucho", 4);
            put("El mas tardado", 5);
        }
    };

    private final Map<String, Integer> pesosRecursos = new LinkedHashMap<>()
    {
        {
            put("El que menos ocupa", 1);
            put("Pocos", 2);
            put("Medio", 3);
            put("Muchos", 4);
            put("El que mas ocupa", 5);
        }
    };

    @FXML
    public void initialize()
    {
        cmbTamanio.getItems().addAll(pesosTamanio.keySet());
        cmbTiempo.getItems().addAll(pesosTiempo.keySet());
        cmbRecursos.getItems().addAll(pesosRecursos.keySet());
    }

    @FXML
    private void onGuardar()
    {
        try
        {
            // Verificar si falta alguna selección
            if (cmbTamanio.getValue() == null
                    || cmbTiempo.getValue() == null
                    || cmbRecursos.getValue() == null)
            {
                mostrarMensajeError("Debe seleccionar todos los campos antes de guardar.");
                return; // Detiene la ejecución
            }

            // Obtener los valores numéricos correspondientes
            int tamanio = pesosTamanio.getOrDefault(cmbTamanio.getValue(), 0);
            int tiempo = pesosTiempo.getOrDefault(cmbTiempo.getValue(), 0);
            int recursos = pesosRecursos.getOrDefault(cmbRecursos.getValue(), 0);

            PonderacionDto dto = new PonderacionDto(ponderacion.getIdProducto(), tamanio, tiempo, recursos);
            System.out.println(ponderacion.getIdProducto());
            if (ponderacion.getTamanio() == 0 && ponderacion.getCantidadRecursosUsados() == 0 && ponderacion.getTiempoPreparacion() == 0)
            {
                servicioCostoFijo.guardarPonderacion(dto);

                mostrarMensajeExito("Datos guardados con éxito.");
                Stage stage = (Stage) cmbRecursos.getScene().getWindow();
                stage.close();
            } else
            {
                servicioCostoFijo.editarPonderacion(dto);
                mostrarMensajeExito("Datos actualizados con éxito.");
            }

        } catch (CostoFijoException ex)
        {
            mostrarMensajeError(ex.getMessage());
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

    public ProductoFinalAsignarCostoDto getProductoDto()
    {
        return productoDto;
    }

    public void setProductoDto(ProductoFinalAsignarCostoDto productoDto)
    {
        this.productoDto = productoDto;
        lblNombre.setText(productoDto.getNombre());

    }

    public ControladorPf getControladorP()
    {
        return controladorP;
    }

    public void setControladorP(ControladorPf controladorP)
    {
        this.controladorP = controladorP;
    }

    public void setPonderacion(PonderacionDto ponderacion)
    {
        this.ponderacion = ponderacion;

        if (ponderacion.getIdProducto() != 0)
        {
            // Buscar las claves correspondientes a los valores (pesos) guardados
            String tamanioSeleccionado = obtenerClavePorValor(pesosTamanio, ponderacion.getTamanio());
            String tiempoSeleccionado = obtenerClavePorValor(pesosTiempo, ponderacion.getTiempoPreparacion());
            String recursosSeleccionado = obtenerClavePorValor(pesosRecursos, ponderacion.getCantidadRecursosUsados());

            // Asignar las selecciones a los ComboBox
            cmbTamanio.getSelectionModel().select(tamanioSeleccionado);
            cmbTiempo.getSelectionModel().select(tiempoSeleccionado);
            cmbRecursos.getSelectionModel().select(recursosSeleccionado);
        }

    }

    private String obtenerClavePorValor(Map<String, Integer> mapa, float valor)
    {
        for (Map.Entry<String, Integer> entry : mapa.entrySet())
        {
            // Usamos == si los valores son exactos (1.0, 2.0, 3.0)
            if (entry.getValue() == (int) valor)
            {
                return entry.getKey();
            }
        }
        return null; // No se encontró coincidencia
    }

}
