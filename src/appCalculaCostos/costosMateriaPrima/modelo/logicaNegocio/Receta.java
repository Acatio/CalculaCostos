package appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio;

import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.Gramo;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.Kilo;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.Litro;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.Mililitro;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.Pieza;
import appCalculaCostos.costosMateriaPrima.modelo.UnidadesMedida.UnidadMedida;
import appCalculaCostos.costosMateriaPrima.modelo.daos.InsumoDaoImpl;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.InsumoException;
import appCalculaCostos.costosMateriaPrima.modelo.excepciones.NoPosibleConversion;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.DetalleRecetaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.MateriaPrimaDto;
import appCalculaCostos.costosMateriaPrima.modelo.logicaNegocio.DTO.RecetaDto;
import conexion.implementaciones.ConexionSQL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Receta extends Insumo
{

    private List<DetalleReceta> ingredientes;

    public Receta()
    {
        ingredientes = new ArrayList<>();
    }

    public Receta(int id, String nombre, double cantidadProducida, UnidadMedida unidadDeMedida)
    {
        super(id, nombre, cantidadProducida, unidadDeMedida, TipoInsumo.RECETA);
        ingredientes = new ArrayList<>();
    }

    public Receta(String nombre, double cantidadProducida, UnidadMedida unidadDeMedida)
    {
        super(nombre, cantidadProducida, unidadDeMedida, TipoInsumo.RECETA);
        ingredientes = new ArrayList<>();
    }

    public List<DetalleReceta> getIngredientes()
    {
        return ingredientes;
    }

    public void setIngredientes(List<DetalleReceta> ingredientes)
    {
        if (ingredientes == null)
        {
            throw new IllegalArgumentException("la lista de ingredientes no es valida");
        }
        this.ingredientes = ingredientes;
    }

    @Override
    public double calcularCostoTotal() throws NoPosibleConversion
    {
        double total = 0;
        for (DetalleReceta detalle : ingredientes)
        {
                total += detalle.getMonto();
        }
        return total;
    }

    public void agregarIngrediente(DetalleReceta detalle)
    {
        ingredientes.add(detalle);
    }

    public void mostrarReceta()
    {
        System.out.println("++++ RECETA: " + super.getNombre() + " ++++");
        for (DetalleReceta detalle : ingredientes)
        {
            try
            {
                var insumo = detalle.getInsumo();
                System.out.println("Ingrediente: " + insumo.getNombre() + " cantidad: " + detalle.getCantidad() + " " + detalle.getUnidadMedida().getSimbolo() + " costo: " + detalle.getMonto());
            } catch (NoPosibleConversion ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args)
    {
        UnidadMedida kilo = new Kilo();
        UnidadMedida gramo = new Gramo();
        UnidadMedida litro = new Litro();
        UnidadMedida mililitro = new Mililitro();
        UnidadMedida pieza = new Pieza();

        Insumo i1 = new MateriaPrima("Harina", 25, kilo, 800);
        Insumo i2 = new MateriaPrima("Huevo", 16, pieza, 50);
        Insumo i3 = new MateriaPrima("sal", 1, kilo, 23);
        Insumo i4 = new MateriaPrima("agua", 1, litro, 25);

        Receta masa = new Receta("masa", 8, kilo);
        Receta pure = new Receta("pure", 4, litro);
        DetalleReceta d1 = new DetalleReceta(i1, 4, kilo);
        DetalleReceta d2 = new DetalleReceta(i2, 4, pieza);
        DetalleReceta d3 = new DetalleReceta(i3, 32, gramo);
        DetalleReceta d4 = new DetalleReceta(i4, 2, litro);
        masa.agregarIngrediente(d1);
        masa.agregarIngrediente(d2);
        masa.agregarIngrediente(d3);
        masa.agregarIngrediente(d4);
        pure.agregarIngrediente(d4);
        pure.agregarIngrediente(d4);
        DetalleReceta d5 = new DetalleReceta(pure, 2, litro);
        masa.agregarIngrediente(d5);
        masa.mostrarReceta();
        MateriaPrimaService mps = new MateriaPrimaService(new InsumoDaoImpl(new ConexionSQL()));
        MateriaPrimaDto mp1=new MateriaPrimaDto("Harina", 25, kilo.getNombre(), TipoInsumo.MATERIA_PRIMA, 800);
        ArrayList<DetalleRecetaDto> detalles=new ArrayList<>();
        
        DetalleRecetaDto detalle=new DetalleRecetaDto(4, "a",4, kilo.getNombre());
        detalles.add(detalle);
        
        RecetaDto dtoReceta=new RecetaDto( "masa", 4, kilo.getNombre(), TipoInsumo.RECETA, detalles);
        try
        {
          //  mps.guardarMateriaPrima(mp1);
            mps.guardarReceta(dtoReceta);
        } catch (InsumoException ex)
        {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }
}
