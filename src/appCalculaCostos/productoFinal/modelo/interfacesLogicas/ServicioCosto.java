/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package appCalculaCostos.productoFinal.modelo.interfacesLogicas;

import appCalculaCostos.productoFinal.modelo.logicaNegocio.entidades.CostoDeModulo;

/**
 *
 * @author jose
 * @param <T>
 */
public abstract class ServicioCosto<T extends ICostoDatosEntrada>
{

    protected final int id; // Usamos final para inmutabilidad
    protected final String nombre; // Usamos final para inmutabilidad

    // Constructor Único para forzar la validación de ambos campos
    public ServicioCosto(int id, String nombre)
    {
        if (id < 1)
        {
            throw new IllegalArgumentException("El ID del costo debe ser mayor a cero.");
        }
        if (nombre == null || nombre.isBlank())
        {
            throw new IllegalArgumentException("El nombre del tipo de costo no es válido.");
        }
        this.id = id;
        this.nombre = nombre;
    }

    // --- Getters (Propiedades Inmutables) ---
    public int getIdTipoCosto()
    {
        return id;
    }

    public String getNombreCosto()
    {
        return nombre;
    }

    // --- Contrato de Lógica ---
    /**
     * Genera la entidad de dominio CostoDeModulo a partir de los datos de
     * entrada.
     *
     * @param datosEntrada Una lista o DTO que contiene los datos crudos de la
     * UI.
     * @return El objeto de dominio CostoDeModulo final.
     */
    public abstract CostoDeModulo generarCostoDeModulo(Object datosEntrada);

}
