package Ticket;

import Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Serializar
public class Ticket {
    private LocalDateTime dateTime;
    private float price;
    private ArrayList<Product> productList;

    public Ticket(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        productList =  new ArrayList<Product>();
        price=calculateTotalPrice();
    }
    private float calculateTotalPrice(){
        price=0;
        for (Product producto:productList){
           price=price+producto.getPrice();
        }
        return price;
    }
    private void addProductTicket(Product product){
        productList.add(product);
    };
    private void removeProductTicket( int i){
        productList.remove(i);

    };

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public float getPrice(){

        return price;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product.Product> productList) {
        this.productList = productList;
    }

    public void setPrice(float price){
        this.price= price;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

