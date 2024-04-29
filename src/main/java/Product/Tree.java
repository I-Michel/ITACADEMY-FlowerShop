package Product;

public class Tree extends Product {

    private final float HEIGHT;

    public Tree(float height) {
        this.HEIGHT = height;
    }

    @Override
    public void showCareInstructions() {
        System.out.println("Water again when the soil is mostly dry.");
    }
}