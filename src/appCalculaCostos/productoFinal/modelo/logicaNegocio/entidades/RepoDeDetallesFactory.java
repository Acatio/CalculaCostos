/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades;

import java.util.HashMap;
import java.util.Map;
import appCalculaCostos.productoFinal.modelo.interfacesLogicas.IRepositorioDetalles;

/**
 *
 * @author jose
 */
public class RepoDeDetallesFactory
{

    // El corazón de la fábrica: Mapea la CLASE del módulo de costo a su REPOSITORIO.
    private final Map<Class<? extends CostoDeModulo>, IRepositorioDetalles> mapaRepositorios;

    /**
     * Constructor: Configura el mapa con las implementaciones concretas. Solo
     * necesita actualizarse aquí al añadir un nuevo módulo.
     */
    public RepoDeDetallesFactory()
    {
        this.mapaRepositorios = new HashMap<>();

        // --- Registro de Módulos Fijos ---
        // Al agregar un nuevo módulo (ej., CostoFletes), se añade una sola línea aquí:
//        mapaRepositorios.put(CostoMateriaPrima.class, new RepoMateriaPrima());
//        mapaRepositorios.put(CostoManoObra.class, new RepoManoObra());
    }

    /**
     * Devuelve el repositorio especializado para guardar el tipo de
     * CostoDeModulo dado.
     *
     * @param costo La instancia de CostoDeModulo a persistir.
     * @return El repositorio especializado que sabe cómo guardar sus detalles.
     */
    public IRepositorioDetalles crearRepo(CostoDeModulo costo)
    {

        // Obtiene la clase concreta (ej., CostoMateriaPrima.class)
        Class<? extends CostoDeModulo> tipoCosto = costo.getClass();

        if (mapaRepositorios.containsKey(tipoCosto))
        {
            return mapaRepositorios.get(tipoCosto);
        } else
        {
            // Esto asegura que la transacción falle si un módulo no está configurado.
            throw new IllegalArgumentException("ERROR: No se encontró un repositorio para el tipo de costo: " + tipoCosto.getSimpleName());
        }
    }
}
