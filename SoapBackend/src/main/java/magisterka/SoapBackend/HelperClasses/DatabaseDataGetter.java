package magisterka.SoapBackend.HelperClasses;


import java.sql.*;

public class DatabaseDataGetter {
    static public PairOfTexts getTexts(String textsLength, String textsType, String dbmsType){
        switch (dbmsType){
            case "mysql":
                return getTextsFromMySQL(textsLength, textsType);
            case "mssql":
                return getTextsFromMsSQL(textsLength, textsType);
            case "oracledb":
                return getTextsFromOracleDB(textsLength, textsType);
            default:
                System.out.println("Nieprawidłowy typ bazy danych");
                return new PairOfTexts("a","a");
        }
    }

    private static PairOfTexts getTextsFromOracleDB(String textsLength, String textsType) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String connectionString = "jdbc:oracle:thin:@localhost:1521:orcl";
            Connection conn = DriverManager.getConnection(connectionString,"sys as sysdba","Orac.001464");
            Statement stmt = conn.createStatement();
            String sql = String.format("select * from teksty_%s", textsLength);
            ResultSet rl = stmt.executeQuery(sql);
            rl.next();
            String text1 = rl.getString(String.format("tekst_%s", textsType));
            rl.next();
            String text2 = rl.getString(String.format("tekst_%s", textsType));
            return new PairOfTexts(text1, text2);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("En SQL Error Occured");
            return new PairOfTexts("a", "a");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static PairOfTexts getTextsFromMsSQL(String textsLength, String textsType) {
        try {
            String connectionString = "jdbc:sqlserver://AREKSLAPTOP\\SQLEXPRESS;databaseName=baza_tekstow;encrypt=true;trustServerCertificate=true";
            Connection conn = DriverManager.getConnection(connectionString,"ns","Mssq.001464");
            Statement stmt = conn.createStatement();
            String sql = String.format("select tekst_%s from teksty_%s", textsType, textsLength);
            ResultSet rl = stmt.executeQuery(sql);
            rl.next();
            String text1 = rl.getString(String.format("tekst_%s", textsType));
            rl.next();
            String text2 = rl.getString(String.format("tekst_%s", textsType));
            return new PairOfTexts(text1, text2);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("En SQL Error Occured");
            return new PairOfTexts("a", "a");
        }
    }

    private static PairOfTexts getTextsFromMySQL(String textsLength, String textsType){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://localhost:3306/baza_tekstow?serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(connectionString,"root", "Mysq.001464");
            Statement stmt = conn.createStatement();
            String sql = String.format("select tekst_%s from baza_tekstow.teksty_%s", textsType, textsLength);
            ResultSet rl = stmt.executeQuery(sql);
            rl.next();
            String text1 = rl.getString(String.format("tekst_%s", textsType));
            rl.next();
            String text2 = rl.getString(String.format("tekst_%s", textsType));
            return new PairOfTexts(text1, text2);
        } catch (SQLException e) {
            System.out.println("En SQL Error Occured");
            return new PairOfTexts("a", "a");
        } catch (ClassNotFoundException e) {
            System.out.println("En ClassNotFoundException Error Occured");
            return new PairOfTexts("b", "b");
        }
    }
}
