package FlowerShop;

public class FlowerShop {

    private static FlowerShop instance;
    private String name;

    private FlowerShop() {
        this.name = "Pipiflower";
    }

    public static FlowerShop getInstance() {
        if(instance == null){
            instance = new FlowerShop();
        }
        return instance;
    }

    // Método que pregunte BBDD
}
