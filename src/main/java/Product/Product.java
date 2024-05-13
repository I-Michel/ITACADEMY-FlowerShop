package Product;

public abstract class Product {

    int id;
    float price;

    public Product(float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public abstract void getCareInstructions();
}
