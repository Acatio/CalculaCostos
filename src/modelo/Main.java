/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author jose
 */
public class Main
{

    public static void main(String[] args)
    {
        MateriaPrima p1 = new MateriaPrima(1,"Sal", 1000,"gramos", 12);
        MateriaPrima p2 = new MateriaPrima(2,"Azúcar", 1000,"gramos", 24);
        MateriaPrima p3 = new MateriaPrima(3,"Huevo", 16,"pieza" ,50);
        MateriaPrima p4 = new MateriaPrima(4,"Levadura", 400,"gramos", 28);
        MateriaPrima p5 = new MateriaPrima(5,"Aceite", 900, "mililitros",28 );
        MateriaPrima p6 = new MateriaPrima(6,"Harina", 25,"kilos", 440);

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
        System.out.println("Costo por kilo de masa: $" + masa.getCostoPorUnidad());

        // Receta de pizza que usa masa
        Receta pizza = new Receta("Pizza", 2, "piezas");
        pizza.agregarIngrediente(masa, 2); // 2 kg de masa
        pizza.agregarIngrediente(p3, 2);   // 2 huevos más

        pizza.mostrarReceta();
        System.out.println("Costo total de pizza: $" + pizza.calcularCostoTotal());
        System.out.println("Costo por pieza de pizza: $" + pizza.getCostoPorUnidad());
    }
}
