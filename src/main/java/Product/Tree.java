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
    public void showCareInstructions() {
        System.out.println("Water again when the soil is mostly dry.");
    }

    @Override
    public String toString() {
        return "- Tree (" + this.HEIGHT + "). Price: " + super.price + " €";
    }
}