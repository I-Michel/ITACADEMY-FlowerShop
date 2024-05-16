package Product;

public abstract class Product {

    float price;

    public Product(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public abstract void getCareInstructions();
}
