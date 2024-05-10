package Ticket;

import Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static Validation.Validation.validateInt;

//Serializar
public class Ticket {
    private LocalDateTime dateTime;
    private float price;
    private ArrayList<Product> productList;


    private Ticket(LocalDateTime dateTime) {
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

        productList.add(product);
        //restar en stock
    };
    private void removeProductTicket(Ticket actualTicket){
        System.out.println(actualTicket.toString());
        //validation producto en el ticket
        int iRemove=validateInt("Which is the Index of the product you want to remove?");

        if (iRemove>0||iRemove<actualTicket.getProductList().size()){
            actualTicket.getProductList().remove(iRemove);
        }
        else { System.out.println("No corresponde con ningun núm del indice ");}//traducir
        //remove
        //add a stock

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
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            S+= i + ".   " + product + "\n";
        }
        S+="Total " + price + "€\n";
        return S;

    }
}