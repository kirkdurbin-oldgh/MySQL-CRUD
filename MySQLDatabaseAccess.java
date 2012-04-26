/*
 * Kirk Durbin 
 * MySQL CRUD
 */
package mysqldatabaseaccess;

import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Kirk Durbin
 */
public class MySQLDatabaseAccess {
        
    public static void testConnection() {
        String prompt=null;
       
        // Prompt for password to access MySQL                
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);

        try {
            System.out.println("Please enter the password: ");
            prompt = buffRead.readLine();
        }
        catch (IOException err) {
            System.out.println("An error occured reading buffer.");
        }
        
        // Open connection to database
        
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "jdbc";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = prompt;
        
        try {
            conn = DriverManager.getConnection(url+dbName,userName,password);
            System.out.println("Connected to database: " + dbName);
            conn.close();
            System.out.println("Disconnected from database: " + dbName);
        }
        catch (Exception e) {
            System.out.println("An error has occured connecting to database.");
            e.printStackTrace();
        }
  }
    
    public static String getDBName() {
        
        /* This code is not used.
         * Will be used in the future to make the CRUD more universal
         */
        
        String dbName = null;
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        try {
            System.out.print("Enter the name of the database to edit: ");
            dbName = buffRead.readLine().toString();
        }
        catch (IOException err) {
            System.out.println("An I/O error has occured.");
        }
        
        return dbName;
    }
    public static String getDBPass() {
        String dbPass = null;
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        try {
            System.out.print("Enter the password for the database: ");
            dbPass = buffRead.readLine().toString();
        }
        catch (IOException err) {
            System.out.println("An I/O error has occured.");
        }
        
        return dbPass;
    }
    
    public static Connection openConnection() {
        
        String prompt=null;
       
        // Prompt for password to access MySQL                
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        // Open connection to database
        
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "jdbc";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = getDBPass();
        
        try {
            conn = DriverManager.getConnection(url+dbName,userName,password);
            System.out.println("Connection Successful!");
        }
        catch (Exception e) {
            System.out.println("An error has occured connecting to database.");
            e.printStackTrace();
        }
        
        return conn;
    }
    
    public static void closeConnection(Connection conn){
        System.out.println("Disconnected Successfully!");
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQLDatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createRecord() {
        
        String year = null;
        String make = null;
        String model = null;
        
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        try {
            System.out.print("Enter the year: ");
            year = buffRead.readLine().toString();
            System.out.println("");
            
            System.out.print("Enter the make: ");
            make = buffRead.readLine().toString();
            System.out.println("");
            
            System.out.print("Enter the model: ");
            model = buffRead.readLine().toString();
            System.out.println("");            
        }
        catch (IOException err) {
            System.out.println("An I/O error occurred.");
        }
        
        Connection conn = openConnection();
        
        try {
            Statement st = conn.createStatement();
            int val = st.executeUpdate("INSERT INTO car (`year`, `make`, `model`) VALUES ('" + year + "', '" + make + "', '" + model + "')");
            System.out.println("1 row affected.");
       
        }
        catch (SQLException s) {
            System.out.println("SQL code failed to execute.");
        }
        
        finally {
            closeConnection(conn);
        }
    }
    
    public static void retrieveRecords() {
        Connection conn = openConnection();
        
        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM car");
            System.out.println("ID" + "\t\t" + "Year" + "\t\t" + "Make" + "\t\t" + "Model");
            
            while (res.next()){
                int id = res.getInt("id");
                String year = res.getString("year");
                String make = res.getString("make");
                String model = res.getString("model");
                System.out.println(id + "\t\t" + year + "\t\t" + make + "\t\t" + model);
            }
        }
        catch (SQLException s) {
            System.out.println("SQL code failed to execute.");
        }        
        closeConnection(conn);
        
        }
    
    public static void updateRecord() {
        
        String recNum = null;

        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        try {
            System.out.print("Enter record number to update: ");
            recNum = buffRead.readLine().toString();
            System.out.println();
        }
        catch (IOException err) {
            System.out.println("An I/O error occurred.");
        }
        
        Connection conn = openConnection();
        
        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM car WHERE id = " + recNum);
            System.out.println("ID" + "\t\t" + "Year" + "\t\t" + "Make" + "\t\t" + "Model");
            
            while (res.next()) {
                String newYear = null;
                String newMake = null;
                String newModel = null;
                
                int id = res.getInt("id");
                String year = res.getString("year");
                String make = res.getString("make");
                String model = res.getString("model");
                System.out.println(id + "\t\t" + year + "\t\t" + make + "\t\t" + model);
                System.out.println("");
                
                System.out.print("Enter the year: (" + year + ")");
                newYear = buffRead.readLine().toString();
                if ( newYear.isEmpty()) {
                    newYear = year;
                }
                
                System.out.print("Enter the make: (" + make + ")");
                newMake = buffRead.readLine().toString();
                if (newMake.isEmpty()) {
                    newMake = make;
                }
                
                System.out.print("Enter the model: (" + model + ")");
                newModel = buffRead.readLine().toString();
                if (newModel.isEmpty()) {
                    newModel = model;
                }
                
                Statement st2 = conn.createStatement();
                int val = st2.executeUpdate("UPDATE car SET year='" + newYear + "', make='" + newMake + "', model='" + newModel + "' WHERE id='" + recNum + "'");
                System.out.println("1 row affected.");
            }
        }
        
        catch (SQLException s) {
            System.out.println("SQL code failed to execute.");
        } 
        catch (IOException err) {
            System.out.println("An I/O error has occurred.");
        }
        
        finally {
            closeConnection(conn);
        }
    }

    public static void deleteRecord() {
        
        String recNum = null;
       
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        try {
            System.out.print("Enter record number to delete: ");
            recNum = buffRead.readLine().toString();
            System.out.println();
        }
        catch (IOException err) {
            System.out.println("An I/O error occurred.");
        }
        
        Connection conn = openConnection();
        
        try {
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM car WHERE id = " + recNum);
            System.out.println("ID" + "\t\t" + "Year" + "\t\t" + "Make" + "\t\t" + "Model");
            
            while (res.next()) {
                String newYear = null;
                String newMake = null;
                String newModel = null;
                String finalAnswer = null;
                
                int id = res.getInt("id");
                String year = res.getString("year");
                String make = res.getString("make");
                String model = res.getString("model");
                System.out.println(id + "\t\t" + year + "\t\t" + make + "\t\t" + model);
                System.out.println("");
                
                System.out.print("Are you sure you want to delete this record?: ");
                finalAnswer = buffRead.readLine().toString().toLowerCase();
                if( "y".equals(finalAnswer) || "yes".equals(finalAnswer)){
                    Statement st2 = conn.createStatement();
                    int val = st2.executeUpdate("DELETE FROM car WHERE id = " + id);
                    System.out.println("1 row affected.");
                }
                else {
                    System.out.println("Database not affected.");
                }
            }
        }
        
        catch (SQLException s) {
            System.out.println("SQL code failed to execute.");
        } 
        catch (IOException err) {
            System.out.println("An I/O error has occurred.");
        }
        
        finally {
            closeConnection(conn);
        }
    }
       
    public static void about() {
        System.out.println("        MySQL CRUD");
        System.out.println(" Written by: Kirk Durbin");
        System.out.println("    www.kirkdurbin.com");
        System.out.println("Bugs: kirkmdurbin@gmail.com");
    }
      
    public static String mainMenu() {
        String userChoice = null;
        
        InputStreamReader istream = new InputStreamReader(System.in);
        BufferedReader buffRead = new BufferedReader(istream);
        
        System.out.println("");
        System.out.println("+---------------------+");
        System.out.println("CRUD Main Menu");
        System.out.println("");
        System.out.println("(T)est Connection");
        System.out.println("(C)reate a record");
        System.out.println("(R)etrieve a record");
        System.out.println("(U)pdate a record");
        System.out.println("(D)elete a record");
        System.out.println("(A)bout");
        System.out.println("(Q)uit");
        System.out.println("+---------------------+");
        
        try {
            System.out.println("");
            System.out.print("CRUD >> ");
            userChoice = buffRead.readLine().toString().toLowerCase();
           
            if ("t".equalsIgnoreCase(userChoice)) {
                testConnection();
            }
            else if ("c".equalsIgnoreCase(userChoice)) {
                createRecord();
            }
            else if ("r".equalsIgnoreCase(userChoice)) {
                retrieveRecords();
            }
            else if ("u".equalsIgnoreCase(userChoice)) {
                updateRecord();
            }
            else if ("d".equalsIgnoreCase(userChoice)) {
                deleteRecord();
            }
            else if ("a".equalsIgnoreCase(userChoice)) {
                about();
            }
            else if ("q".equalsIgnoreCase(userChoice)) {
                System.exit(0);
            }
            else {
                System.out.println("The item you selected is invalid.");
            }    
        }
        catch (IOException err) {
            System.out.println("An error occured during input.");
        }
        
        return userChoice;
    }
    
    public static void execute() {
        String userChoice = null;

        while (!"q".equalsIgnoreCase(userChoice)) {
            mainMenu();
        }
    }
    
    public static void main(String[] args) {
        execute();
    
    }
}