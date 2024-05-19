import Connection.*;
import FlowerShop.FlowerShop;
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
                    7. Show tickets.\s
                    8. Show total profit.\s
                    9. Generate JSON from ticket.\s
                    10. Read ticket from JSON.\s
                    11. Close app.""");

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
                    ticket= TicketFunc.generateTicket(db);
                    break;
                case 7:
                    TicketFunc.displayPurchases(TicketFunc.getTickets(db));
                    break;
                case 8:
                    TicketFunc.displayPurchases(TicketFunc.getTickets(db));
                    break;
                case 9:
                    TicketFunc.showProfit(TicketFunc.getTickets(db));
                    break;
                case 10:
                    FlowerShop.generateJSON(ticket,"ticket");
                    break;
                case 11:
                    FlowerShop.readJSON("ticket");
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 11);
    }
}