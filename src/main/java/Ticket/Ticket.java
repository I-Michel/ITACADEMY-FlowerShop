package Ticket;

import Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Serializar
public class Ticket {
    private LocalDateTime dateTime;
    private float price;
    private ArrayList<Product> productList;
    private

    public Ticket(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        productList =  new ArrayList<Product>();
        price=calculateTotalPrice();
    }
    public float calculateTotalPrice(){
        price=0;
        for (Product producto:productList){
           price=price+producto.getPrice();
        }
        return price;
    }
    public void addProductTicket(Product product){
        //validation producto existe

        //validation hay stock
        if()

        productList.add(product);
        //restar en stock
    };
    private void removeProductTicket( int cantidad){
        //validation producto en el ticket
        //validation productos suficientes a eliminar
        //remove
        //sumar a stock

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

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public void setPrice(float price){
        this.price= price;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    @Override
    public String toString() {
        String S="";
        S="*****Ticket*****\n" +
                "Date: " + dateTime + "\n" +
                "Products:\n";
        for (Product producto : productList) {
            S+= producto + "\n";

        }
        S+="Total " + price + "€\n";
        return S;
    }
}

