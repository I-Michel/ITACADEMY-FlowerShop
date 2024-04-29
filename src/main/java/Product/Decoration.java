package Product;

public class Decoration extends Product {

    public enum Material {
        WOOD,
        PLASTIC
    }

    private Material material;

    public Decoration(Material material) {
        this.material = material;
    }


    @Override
    public void showCareInstructions() {
        System.out.println("Wipe gently with a wet cloth when needed.");
    }
}