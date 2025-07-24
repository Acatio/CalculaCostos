/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paquete1;

/**
 *
 * @author jose
 */
public class Main
{

    public static void main(String[] args)
    {
        MateriaPrima p1 = new MateriaPrima("Sal", 1000, 12, "gramos");
        MateriaPrima p2 = new MateriaPrima("Azúcar", 1000, 24, "gramos");
        MateriaPrima p3 = new MateriaPrima("Huevo", 16, 50, "pieza");
        MateriaPrima p4 = new MateriaPrima("Levadura", 400, 28, "gramos");
        MateriaPrima p5 = new MateriaPrima("Aceite", 900, 28, "mililitros");
        MateriaPrima p6 = new MateriaPrima("Harina", 25, 440, "kilos");

        // Receta de masa
        Receta masa = new Receta("Masa", 4, "kilos");
        masa.agregarIngrediente(p1, 38);
        masa.agregarIngrediente(p2, 38);
        masa.agregarIngrediente(p3, 8);
        masa.agregarIngrediente(p4, 35);
        masa.agregarIngrediente(p5, 900);
        masa.agregarIngrediente(p6, 4);

        // Mostrar receta de masa
        masa.mostrarReceta();
        System.out.println("Costo total de masa: $" + masa.calcularCostoTotal());
        System.out.println("Costo por kilo de masa: $" + masa.calcularCostoPorUnidad());

        // Receta de pizza que usa masa
        Receta pizza = new Receta("Pizza", 2, "piezas");
        pizza.agregarIngrediente(masa, 2); // 2 kg de masa
        pizza.agregarIngrediente(p3, 2);   // 2 huevos más

        pizza.mostrarReceta();
        System.out.println("Costo total de pizza: $" + pizza.calcularCostoTotal());
        System.out.println("Costo por pieza de pizza: $" + pizza.calcularCostoPorUnidad());
    }
}
