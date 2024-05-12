package Ticket;

import Product.Product;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static Validation.Validation.validateInt;

//Serializar
public class Ticket {
    private int ticketId;
    private Date date;
    private float price;
    private HashMap<Product,Integer> productList;


    private Ticket(int ticketId, Date date) {
        this.ticketId = ticketId;
        this.date = date;
        productList =  new HashMap<Product,Integer>();
        price=calculateTotalPrice();
    }
    public float calculateTotalPrice(){
        price=0;
        for (Map.Entry <Product,Integer > product:productList.entrySet()){
            Product prod = product.getKey();
            Integer quant = product.getValue();
            price=price+(prod.getPrice()*quant);
        }
        return price;
    }
    public void addProductTicket(Product product){
        //validation producto existe

        //validation hay stock


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

    public Date getDate() {
        return date;
    }

    public float getPrice(){

        return price;
    }

    public  HashMap<Product,Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<Product,Integer> productList) {
        this.productList = productList;
    }

    public void setPrice(float price){
        this.price= price;
    }

    public void setDateTime(Date date) {
        this.date = date;
    }
    public Timestamp tsdate(){
    Timestamp tsdate=new Timestamp(date.getTime());
                return tsdate;}
    @Override
    public String toString() {
        String S="";
        int i=0;
        S="*****Ticket*****\n" +
                "Date: " + date + "\n" +
                "Products:\n";
        for (Map.Entry <Product,Integer > product:productList.entrySet()){
            Product prod = product.getKey();
            Integer quant = product.getValue();
            S+= i + ".   " + prod +"         x"+quant+"\n";
            i=i+1;
        }
        S+="Total " + price + "€\n";
        return S;

    }


    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }
}