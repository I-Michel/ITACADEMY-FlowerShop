package Product;

public class Decoration extends Product {

    public enum Material {
        WOOD,
        PLASTIC
    }

    private final Material MATERIAL;

    public Decoration(float price, Material material) {
        super(price);
        this.MATERIAL = material;
    }

    public Decoration(float price, Material material, int id) {
        super(price, id);
        this.MATERIAL = material;
    }

    public Material getMATERIAL() {
        return MATERIAL;
    }

    @Override
    public void getCareInstructions() {
        System.out.println("Wipe gently with a wet cloth when needed.");
    }

    @Override
    public String toString() {
        return "- Decoration: ID " + this.id + " - Material: " + this.MATERIAL + " - Price: " + super.price + " €";
    }
}