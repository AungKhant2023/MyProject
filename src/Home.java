import java.util.Scanner;

public class Home {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean loop = false;
        int option = 0;

        System.out.println("""
                Welcome""");
        do{
            System.out.print("""
                
                choose the option ...\s
                1. Sign Up
                2. Sign In
                0. exit     : \s""");

            try {
                loop = false;
                option = sc.nextByte();

                switch (option){
                    case 1 : SignUp.signUp();break;
                    case 2 : SignIn.signIn();break;
                    case 0 : System.exit(0); break;
                    default:
                }

            } catch (Exception e) {
                System.out.println("Invalid input...");
                sc = new Scanner(System.in);
                loop = true;
            }
        }while (loop);
    }
}
