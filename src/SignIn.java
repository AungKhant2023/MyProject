import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SignIn {

    static Scanner scanner = new Scanner(System.in);

    public static void signIn() {
        System.out.println("\n\n##### Sign In Page #####\n");
        String phone, password;
        boolean loop = false;
        int option = 0;
        String[] temp = {"sign", "in"};

        do {
            System.out.print("Enter phone number to log in: ");
            phone = scanner.next();

            System.out.print("Enter password: ");
            password = scanner.next();

            if (UtilityDB.checkUserLogIn(phone, password))
                break;
            else
                System.out.println("Credential Error!");

        } while (true);

        String username = checkSignInData(phone, password);
        if (username != null) {
            System.out.println("\n\n+++ You are signed in\n" +
                    "Welcome " + username + "...");

            do {
                System.out.print("""
                                        
                        Choose an option to process:
                        1: To-Do List
                        2: View Transfer History
                        0: Sign Out
                        Enter your choice: """);

                try {
                    loop = true;
                    option = scanner.nextInt();
                    switch (option) {
                        case 1:
                            todoList(phone);
                            break;
                        case 2:
                            viewHistory(phone);
                            break;
                        case 0:
                            Home.main(temp);
                            loop = false;
                            break;
                        default:
                            System.out.println("\nOption must only be a number: 1, 2, 0.");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input option...");
                    System.out.println(e.getStackTrace() + e.getLocalizedMessage());
                    scanner = new Scanner(System.in);
                    loop = true;
                }
            } while (loop);
        }
    }

    private static void todoList(String phone) {
        UtilityDB.todoList();
    }

    private static void viewHistory(String phone) {
        UtilityDB.showHistory();
    }


    private static String checkSignInData(String phone, String password) {
        ResultSet singleData = UtilityDB.retrieveData(phone);
        while (true) {
            try {
                if (!singleData.next())
                    return null;
                else {
                    String phoneDb = singleData.getString("phone");
                    String passwordDb = singleData.getString("password");
                    String nameDb = singleData.getString("name");

                    if (phone.equals(phoneDb) && password.equals(passwordDb))
                        return nameDb;
                }
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                return null;
            }
        }
    }
}


