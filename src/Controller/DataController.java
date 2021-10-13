package Controller;

import Model.DetailData;

import java.sql.*;
import java.util.*;

public class DataController {

    private static Connection conn = null;
    private String Add_Expense_SQL = "INSERT INTO Month_Expense (Year,Month,Date,Store,Cost,Category,Item) Values(?,?,?,?,?,?,?);";
    private String Get_Expense_By_Month_Year_SQL = "Select ID, Date, Store, Cost, Category FROM Month_Expense Where Month = ? AND Year = ?;";
    private String GET_DISTINCT_STORE_SQL = "SELECT DISTINCT STORE FROM MONTH_EXPENSE";
    private String Get_Distinct_EXPENSE_Categories_SQL = "SELECT DISTINCT Name FROM Expense_Category";
    private String DELETE_EXPENSE_SQL = "DELETE FROM MONTH_EXPENSE WHERE id = ?;";

    private String ADD_INCOME_SQL = "INSERT INTO Month_Income (Year,Month,Date,Source,Cost,Category,Item) Values(?,?,?,?,?,?,?);";
    private String GET_INCOME_BY_MONTH_YEAR_SQL = "Select ID, Date, Source, Cost, Category FROM Month_Income Where Month = ? AND Year = ?;";
    private String GET_DISTINCT_INCOME_CATEGORIES_SQL = "SELECT DISTINCT Name FROM Income_Category";
    private String DELETE_INCOME_SQL = "DELETE FROM MONTH_INCOME WHERE id = ?;";

    /////Open in command line
    //sqlite3
    //.open F:/SQLite/DB/budgetjava.db
    // Select * FROM Month_Expense;

    public void connect() {

        try {
            // db parameters
            //TODO: Prompt user for storage location
            String url = "jdbc:sqlite:/F:\\SQLite\\DB\\budgetjava.db";
            // create a connection to the database

            String Expense_Category_SQL = "CREATE TABLE Expense_Category (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOY NULL, EXAMPLE TEXT);";
            String Month_Expense_SQL = "CREATE TABLE Month_Expense (ID INTEGER PRIMARY KEY AUTOINCREMENT,Year INT NOT NULL,Month TEXT NOT NULL,Date INT NOT NULL,Store TEXT NOT NULL, Cost REAL NOT NULL,Category TEXT NOT NULL,Item TEXT);";
            String Income_Category_SQL = "CREATE TABLE income_Category (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOY NULL, EXAMPLE TEXT);";
            String Month_Income_SQL = "CREATE TABLE Month_income (ID INTEGER PRIMARY KEY AUTOINCREMENT,Year INT NOT NULL,Month TEXT NOT NULL,Date INT NOT NULL,Source TEXT NOT NULL, Cost REAL NOT NULL,Category TEXT NOT NULL,Item TEXT);";
            conn = DriverManager.getConnection(url);

            Statement s = conn.createStatement();
            //ResultSet r =  s.executeQuery(Month_Expense_SQL);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("try to make DB");
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public void addExpense(String year, String month,String date, String store, String cost, String category){
        this.addExpense(year, month, date, store, cost, category,"");
    }

    public void addExpense(String year, String month,String date, String store, String cost, String category,String item){
        try {
            PreparedStatement statement  = conn.prepareStatement(Add_Expense_SQL);
            statement.setString(1,year);
            statement.setString(2,month);
            statement.setString(3,date);
            statement.setString(4,store);
            statement.setString(5,cost);
            statement.setString(6,category);
            statement.setString(7,item);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Set<DetailData> getMonthlyExpenses(int year, String month){
        try {
            PreparedStatement statement  = conn.prepareStatement(Get_Expense_By_Month_Year_SQL);
            statement.setString(1,month);
            statement.setInt(2,year);
            ResultSet rs = statement.executeQuery();

            return this.translateResultSetToExpenseDetailData(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Set<String> getStores(){
        try {
            PreparedStatement statement  = conn.prepareStatement(GET_DISTINCT_STORE_SQL);
            ResultSet rs = statement.executeQuery();
            return this.translateResultSetToSet(rs,"Store");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public List<String> getExpenseCategories(){
        try {
            PreparedStatement statement  = conn.prepareStatement(Get_Distinct_EXPENSE_Categories_SQL);
            ResultSet rs = statement.executeQuery();
            return this.translateResultSetToList(rs,"Name");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void deleteExpense(int id){
        try {
            PreparedStatement statement  = conn.prepareStatement(DELETE_EXPENSE_SQL);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addIncome(String year, String month,String date, String source, String cost, String category){
        this.addIncome(year, month, date, source, cost, category,"");
    }

    public void addIncome(String year, String month,String date, String source, String cost, String category,String item){
        try {
            PreparedStatement statement  = conn.prepareStatement(ADD_INCOME_SQL);
            statement.setString(1,year);
            statement.setString(2,month);
            statement.setString(3,date);
            statement.setString(4,source);
            statement.setString(5,cost);
            statement.setString(6,category);
            statement.setString(7,item);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Set<DetailData> getMonthlyIncome(int year, String month){
        try {
            PreparedStatement statement  = conn.prepareStatement(GET_INCOME_BY_MONTH_YEAR_SQL);
            statement.setString(1,month);
            statement.setInt(2,year);
            ResultSet rs = statement.executeQuery();

            return this.translateResultSetToIncomeDetailData(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void deleteIncome(int id){
        try {
            PreparedStatement statement  = conn.prepareStatement(DELETE_INCOME_SQL);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    ////////////////////////////////////////////////Data Controller Helper Methods//////////////////////////////////////
    private Set<DetailData> translateResultSetToExpenseDetailData(ResultSet resultSet) {
        Set<DetailData> dataSet = new HashSet<>();
        try {
            while (resultSet.next()) {
                dataSet.add(new DetailData(resultSet.getInt("id"),
                        resultSet.getInt("date"),
                        resultSet.getString("store"),
                        resultSet.getDouble("cost"),
                        resultSet.getString("category")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
            return dataSet;
    }

    private Set<DetailData> translateResultSetToIncomeDetailData(ResultSet resultSet) {
        Set<DetailData> dataSet = new HashSet<>();
        try {
            while (resultSet.next()) {
                dataSet.add(new DetailData(resultSet.getInt("id"),
                        resultSet.getInt("date"),
                        resultSet.getString("source"),
                        resultSet.getDouble("cost"),
                        resultSet.getString("category")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return dataSet;
    }

    private Set<String> translateResultSetToSet(ResultSet resultSet, String columnName) {
        Set<String> dataSet = new HashSet<>();
        try {
            while (resultSet.next()) {
                dataSet.add(resultSet.getString(columnName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return dataSet;
    }

    private List<String> translateResultSetToList(ResultSet resultSet, String columnName) {
        List<String> dataSet = new ArrayList<>();
        try {
            while (resultSet.next()) {
                dataSet.add(resultSet.getString(columnName));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return dataSet;
    }
}
