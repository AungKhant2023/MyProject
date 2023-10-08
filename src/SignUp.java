import java.util.InputMismatchException;
import java.util.Scanner;

public class SignUp {
    private static String patternPw = "[A-Za-z]*[A-Z]+[A-Za-z]*[1-9]+[,<>.';:!@#$%^&*)(_\\-+=]";
    private static String patternPhone = "09[0-9]{7,9}";

    public static void signUp() {
        String name;
        String password;
        String phone;
        int age = 0;

        boolean loop = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n##### Sign Up Page #####\n");

        System.out.print("Enter your fullName : ");
        name = sc.nextLine();

        do {
            System.out.print("Enter your password : ");
            password = sc.next();
            if (!password.matches(patternPw)) {
                System.out.println("\nYour password must have at least" +
                        "\n one upper, lower, numbers and special character : ");
            }
        } while (!password.matches(patternPw));

        do {
            System.out.print("Enter your mobile number : ");
            phone = sc.next();
            if (UtilityDB.checkPhNumber(phone)) {
                System.out.println("\nPhone number already exit.");
            } else if (!phone.matches(patternPhone)) {
                System.out.println("\nPhone number is invalid format.");
            }
        } while (UtilityDB.checkPhNumber(phone) || !phone.matches(patternPhone));

        do {
            try {
                loop = false;
                System.out.print("Enter your age : ");
                age = sc.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Input must be number : " +
                        "eg : 19\n");

                sc = new Scanner(System.in);
                loop = true;
            }
        } while (loop);

        boolean accountstatus = UtilityDB.registerUser(name, password, phone, age);

        if (accountstatus){
            System.out.println("""
                    
                    ***\t**********\t***
                    Account created successfully
                    ***\t**********\t***
                    """);
            String temp[] = {"Helo","home"};
            Home.main(temp);
        }
        else {
            System.out.println("""

                    xxx\txxxxxxxxxx\txxx
                    Fail to create the account
                    xxx\t\txxx\t\txxx
                    """);
            loop = true;
        }
    }

}


