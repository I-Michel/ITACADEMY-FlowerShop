package Product;

public class Flower extends Product {

    private final String COLOR;

    public Flower(float price, String color) {
        super(price);
        this.COLOR = color;
    }

    public Flower(float price, String color, int id) {
        super(price, id);
        this.COLOR = color;
    }

    public String getCOLOR() {
        return COLOR;
    }

    @Override
    public void getCareInstructions() {
        System.out.println("Clean the vase and change the water every other day.");
    }

    @Override
    public String toString() {
        return "- Flower: ID " + this.id + " - Color: " + this.COLOR + " - Price: " + super.price + " €";
    }
}