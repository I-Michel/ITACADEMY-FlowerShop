package Product;

public abstract class Product {

    int id;
    float price;

    public Product(float price) {
        this.price = price;
    }

    public Product(float price, int id) {
        this.price = price;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public abstract void getCareInstructions();
}