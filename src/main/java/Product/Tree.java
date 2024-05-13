package Product;

public class Tree extends Product {

    private final float HEIGHT;

    public Tree(float price, float height) {
        super(price);
        this.HEIGHT = height;
    }

    public float getHEIGHT() {
        return HEIGHT;
    }

    @Override
    public void getCareInstructions() {
        System.out.println("Water again when the soil is mostly dry.");
    }

    @Override
    public String toString() {
        return "- Tree: ID " + this.id + " - Height: " + this.HEIGHT + " - Price: " + super.price + " €";
    }
}