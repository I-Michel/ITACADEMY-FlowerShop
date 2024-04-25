package FlowerShop.Validations;

import java.util.Scanner;

public class ValidateInt {

    private boolean ok = false;
    Scanner sc = new Scanner(System.in);

    public void validateInt(){
        do {
            try {
                sc.nextInt();
                ok = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        } while (!ok);
    }
}
