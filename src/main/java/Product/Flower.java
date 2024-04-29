package Product;

public class Flower extends Product {

    private final String COLOR;

    public Flower(String color) {
        this.COLOR = color;
    }

    @Override
    public void showCareInstructions() {
        System.out.println("Clean the vase and change the water every other day.");
    }
}
