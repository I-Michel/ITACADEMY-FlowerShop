package Product;
import  Product.Decoration.Material;

public class ProductFactory {

    public static Flower createFlower(float price, String color) {
        return new Flower(price, color);
    }

    public static Tree createTree(float price, float height) {
        return new Tree(price, height);
    }

    public static Decoration createDecoration(float price, Material material) {
        return new Decoration(price, material);
    }
}