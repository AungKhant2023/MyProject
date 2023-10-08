import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class UtilityDB {
    static String url = "jdbc:mysql://localhost:3306/todolist_db";
    static String dbuser = "root";
    static String dbpassword = "";

    public static void todoList() {
        String task;
        String description;

        boolean loop = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n##### Insert Task #####\n");

        System.out.print("Enter your Task : ");
        task = sc.nextLine();

        System.out.print("Enter your Description :  ");
        description = sc.nextLine();

        LocalDateTime timeNow = LocalDateTime.now();
        Timestamp todoDate = Timestamp.valueOf(timeNow);
        boolean ListEntry = UtilityDB.entryList(task, description, todoDate);

        if (ListEntry){
            System.out.println("""

                    ***\t**********\t***
                    Successfully
                    ***\t**********\t***
                    """);
            String temp[] = {"Helo","home"};
            Home.main(temp);
        }
        else {
            System.out.println("""

                    xxx\txxxxxxxxxx\txxx
                    Fail to entry
                    xxx\t\txxx\t\txxx
                    """);
            loop = true;
        }

    }

    private static boolean checkDbData(String column, String inputData) {
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users;");

            String dbData;
            while (resultSet.next()) {
                if (column.equals("age")) {
                    int dbDataInt = resultSet.getInt(column);
                    dbData = Integer.toString(dbDataInt);
                } else {
                    dbData = resultSet.getString(column);
                }

                if (dbData.equals(inputData)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean entryList(String task, String description, Timestamp todoDate) {
        try (Connection connection = DriverManager.getConnection(url, dbuser, dbpassword)) {
            String query = "INSERT INTO todos (task, description, todoDate) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, task);
                preparedStatement.setString(2, description);
                preparedStatement.setTimestamp(3, todoDate);
                int status = preparedStatement.executeUpdate();
                return status == 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public static boolean checkPhNumber(String inpPhone) {
        return checkDbData("phone", inpPhone);
    }

    public static boolean checkUserLogIn(String phone, String password) {

        if (!checkPhNumber(phone))
            return false;
        ResultSet userinfo = retrieveData(phone);
        while (true) {
            try {
                if (!userinfo.next()) break;
                String dbPassword = userinfo.getString("password");
                return password.equals(dbPassword);
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
                return false;
            }
        }
        return false;

    }

    public static boolean registerUser(String name, String password, String phone, int age) {
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            int status = statement.executeUpdate(
                    " insert into users (name, password, phone, age) " +
                            "values (\""
                            + name + "\", \""
                            + password + "\", \""
                            + phone + "\","
                            + age + ");");

            return status == 1;

        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public static ResultSet retrieveData(String phone) {
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users where phone = " + phone);
            return resultSet;
        } catch (SQLException e) {
            return null;
        }
    }
    public static void showHistory() {
        try {
            Connection connection = DriverManager.getConnection(url, dbuser, dbpassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT task, description, todoDate FROM todos");

            if (!resultSet.next()) {
                System.out.println("No todos data found.");
            } else {
                System.out.println("Task              Description              Time");
                do {
                    String task = resultSet.getString("task");
                    String description = resultSet.getString("description");
                    String todoDate = resultSet.getString("todoDate");

                    String formatStr = "%-20s %-30s %-30s";
                    System.out.println(String.format(formatStr, task, description, todoDate));
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}

