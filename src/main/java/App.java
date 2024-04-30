import static Validation.Validation.validateInt;

public class App {
    public static void start(){
        int option = 0;
        System.out.println("Welcome!");

        do {
            System.out.println("0. Close app. \n1. Create flower shop. \n2. Add product. \n3. Remove product. \n" +
                    "4. Show stock details. \n5. Show product stock. \n6. Show total stock \n7. Show stock value. \n8. Generate ticket \n" +
                    "9. Display purchases. \n10. Show total profit \n11. Generate JSON from ticket \n12. Read ticket from JSON");

                    validateInt();
            switch (option){
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
                    //displayPurchases();
                    break;
                case 10:
                    //showProfit();
                    break;
                case 11:
                    //generateJSON();
                    break;
                case 12:
                    //readJSON();
                    break;
                default:
                    System.out.println("Please choose one of the options.");
            }
        } while (option != 0);
    }

    public static void addProductStock(){
        int option2 = 0;
        do{
            System.out.println("Which product do you want to add?\n 1. Tree \n 2. Flower\n 3. Decoration");
            validateInt();
            switch (option2){
                case 1:
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
