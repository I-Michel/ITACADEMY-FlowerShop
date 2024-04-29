package Product;

public class ProductFactory {

    public Flower createFlower(String color) {
        return new Flower(color);
    }

    public Tree createTree(float height) {
        return new Tree(height);
    }

    public Decoration createDecoration(Product.Decoration.Material material) {
        return new Decoration(material);
    }
}