import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class MySQLConnect {
    // create singleton instance
    private static Connection mysqlConn = null;

    static {

        String url;
        String dbName;
        String user;
        String password;
        // read from dbConnect.txt
        File dbFile = new File("Assignment/src/dbConnect");
        try {
            Scanner scan = new Scanner(dbFile); // use scanner to read lines from dbConnect.txt
            url = scan.nextLine();
            dbName = scan.nextLine();
            user = scan.nextLine();
            password = scan.nextLine();
            //System.out.println(url+"\n"+dbName+"\n"+user+"\n"+password+"\n");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            mysqlConn = DriverManager.getConnection(url + dbName, user, password);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();}
    }
    public static Connection getMysqlConnection()
    {return mysqlConn;}

}