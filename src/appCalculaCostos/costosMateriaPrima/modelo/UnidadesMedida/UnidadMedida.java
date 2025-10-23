/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida;

import appCalculaCostos.costosMateriaPrima.modelo.excepciones.NoPosibleConversion;
import java.util.Objects;

/**
 *
 * @author jose
 */
public abstract class UnidadMedida
{

    private final String nombre;
    private final String simbolo;
  
    public UnidadMedida(String nombre, String simbolo)
    {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getSimbolo()
    {
        return simbolo;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final UnidadMedida other = (UnidadMedida) obj;
        if (!Objects.equals(this.nombre, other.nombre))
        {
            return false;
        }
        return Objects.equals(this.simbolo, other.simbolo);
    }
    
    public abstract double aOtraUnidad(UnidadMedida unidadDestino, double cantidad) throws NoPosibleConversion;
   
}
