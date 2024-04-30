package Product;

public class ProductFactory {

    public Flower createFlower(float price, String color) {
        return new Flower(price, color);
    }

    public Tree createTree(float price, float height) {
        return new Tree(price, height);
    }

    public Decoration createDecoration(float price, Product.Decoration.Material material) {
        return new Decoration(price, material);
    }
}