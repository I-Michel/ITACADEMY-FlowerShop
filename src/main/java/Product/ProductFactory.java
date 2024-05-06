package Product;
import  Product.Decoration.Material;

public class ProductFactory {

    public static Flower createFlower() {
        String color = validateText("Which is the color of the new flower?");
        float price = validateFloat("Which is the price of the new product?");

        return new Flower(price, color);
    }

    public static Tree createTree() {
        float height = validateFloat("Which is the height of the new Tree?");
        float price = validateFloat("Which is the price of the new product?");

        return new Tree(price, height);
    }

    public static Decoration createDecoration() {
        int numMaterial = 0;
        Material material = null;

        do {
            numMaterial = validateInt("Which is the material of the new decoration? Please choose an option:" +
                    "\n1. Wood.\n 2. Plastic");
            if (numMaterial == 1) {
                material = Material.WOOD;
            } else if (numMaterial == 2) {
                material = Material.PLASTIC;
            } else {
                System.out.println("Invalid option.");
            }
        } while (numMaterial != 1 && numMaterial != 2);

        float price = validateFloat("Which is the price of the new product?");

        return new Decoration(price, material);
    }
}