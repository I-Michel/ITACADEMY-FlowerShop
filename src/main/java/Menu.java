import Connection.*;
import Connection.MySQL.*;
import Connection.MongoDB.*;
import FlowerShop.FlowerShop;
import Product.*;
import java.io.*;
import java.sql.*;

import Ticket.*;

import static Validation.Validation.*;

public class Menu {
    public static void start() {

        FlowerShop flowerShop = FlowerShop.connectFlowerShop();
        DataBase db = flowerShop.getDb();
        Ticket ticket =new Ticket();
        int option = 0;
        System.out.println("Welcome to " + flowerShop.getName() + " Flower Shop! Please choose an option:");

        do {
            option = validateInt("""
                    1. Add product.\s
                    2. Remove product.\s
                    3. Show stock details.\s
                    4. Calculate total stock.\s
                    5. Calculate stock value.\s
                    6. Generate ticket.\s
                    7. Show ticket.\s
                    8. Display purchases.\s
                    9. Show total profit.\s
                    10. Generate JSON from ticket.\s
                    11. Read ticket from JSON.\s
                    12. Close app.""");

            switch (option) {
                case 1:
                    FlowerShop.addOptions(db);
                    break;
                case 2:
                    FlowerShop.removeOptions(db);
                    break;
                case 3:
                    db.showStock(db);
                    break;
                case 4:
                    db.calculateStock(db);
                    break;
                case 5:
                    db.calculateTotalValue(db);
                    break;
                case 6:
                    //generateTicket();
                    break;
                case 7:
                    ticket= TicketFun.generateTicket(db);
                    break;
                case 8:
                    //showTicket();
                case 9:
                    TicketFun.displayPurchases(TicketFun.getTickets(db));
                    break;
                case 10:
                    TicketFun.showProfit(TicketFun.getTickets(db));
                    break;
                case 11:
                    generateJSON(ticket,"ticket");
                    break;
                case 12:
                    readJSON("ticket");
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 12);
    }

    public static void generateJSON(Ticket ticket, String name) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(name + ".ser");
            ObjectOutputStream objectOutPutStream = new ObjectOutputStream(fileOutputStream);
            objectOutPutStream.writeObject(ticket);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void readJSON(String name) {

        try {
            FileInputStream Archivo = new FileInputStream(name + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(Archivo);
            Ticket ticket = (Ticket) objectInputStream.readObject();

            System.out.println(ticket);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}