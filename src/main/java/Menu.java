import static Validation.Validation.validateInt;

public class Menu {
    public static void start() {
        int option = 0;
        System.out.println("Welcome! Please choose an option.");

        do {
            option = validateInt("0. Close app. \n1. Create flower shop. \n2. Add product. \n3. Remove product. \n" +
                    "4. Show stock details. \n5. Show product stock. \n6. Show total stock \n7. Show stock value. \n8. Generate ticket \n" +
                    "9. Show ticket.\n10. Display purchases. \n11. Show total profit \n12. Generate JSON from ticket \n13. Read ticket from JSON");

            switch (option) {
                case 1:
                    //createFlowerShop();
                    break;
                case 2:
                    addProductStock();
                    break;
                case 3:
                    //removeProductStock();
                    break;
                case 4:
                    //showStock();
                    break;
                case 5:
                    //calculateProductStock();
                    break;
                case 6:
                    //calculateTotalStock();
                    break;
                case 7:
                    //calculateTotalValue();
                    break;
                case 8:
                    //generateTicket();
                    break;
                case 9:
                    //showTicket();
                case 10:
                    //displayPurchases();
                    break;
                case 11:
                    //showProfit();
                    break;
                case 12:
                    //generateJSON();
                    break;
                case 13:
                    //readJSON();
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 0);
    }

    public static void addProductStock() {
        int option2 = 0;

        do {
            option2 = validateInt("Which product do you want to add?\n 1. Tree \n 2. Flower\n 3. Decoration");

            switch (option2) {
                case 1:
                    System.out.println("How many do you want to add?");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option2 != 0);
    }
}
