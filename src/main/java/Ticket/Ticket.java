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
    private addProductTicket(){};
    private removeProductTicket(){};

}


