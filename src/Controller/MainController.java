package Controller;


import Model.DetailData;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;



import java.net.URL;
import java.sql.SQLException;
import java.util.*;


//Not sure I want to extend a UI controller, I just didnt Like all the object in this code
public class MainController extends MainControllerUI implements Initializable {

    //TODO Should Query table to get the years then add one for the next year
    List<String> years = Arrays.asList("2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025");
    List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    List<String> months31 = Arrays.asList("January", "March", "May", "July", "August", "October", "December");
    List<String> days = Arrays.asList("1", "2", "3", "4", "5", "6", "7","8","9","10","11", "12", "13", "14", "15", "16", "17","18","19","20","21", "22", "23", "24", "25", "26", "27","28");
    //TODO Should Query table to get the category
    List<String> categories = Arrays.asList("Dining", "Housing","Investments");
    DataController dataController = new DataController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        dataController.connect();

        expenseMonthCB.getItems().addAll(months);
        expenseYearCB.getItems().addAll(years);
        incomeMonthCB.getItems().addAll(months);
        incomeYearCB.getItems().addAll(years);
        MDMonth.getItems().addAll(months);
        MDYear.getItems().addAll(years);
        MDExpenseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        MDExpenseStore.setCellValueFactory(new PropertyValueFactory<>("store"));
        MDExpenseAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        MDExpenseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        MDIncomeDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        MDIncomeSource.setCellValueFactory(new PropertyValueFactory<>("store"));
        MDIncomeAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        MDIncomeCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        Set<String> stores = dataController.getStores();
        if (!(stores == null || stores.isEmpty())){
            //uncommetn for StoreAutoCompleteTextField
            expenseStoreTF.addEntries(stores);
        }
//TODO add ability to add categories, will come with budget side of app
        List<String> expenseCategoies = dataController.getExpenseCategories();
        if(expenseCategoies == null){
            expenseCategory.getItems().add("Add Categories");
        }else {
            expenseCategory.getItems().addAll(expenseCategoies);
        }

        //////testing code
        //expenseStoreTF.addEntry("test");
        //dataController.addExpense(2020,"March",2,"Walmart",19.59,"Groceries",null);
        //Set<DetailData> test =  dataController.getMonthlyExpenses(2020,"March");
        //MDExpenseTable.getItems().addAll(test);
        //MDIncomeTable.getItems().addAll(test);
        //System.out.println(MDMonth.getValue());
    }

    /**
     * Called when the window is closed.
     */
    public void shutdown(){
        try {
            this.dataController.getConn().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Called when the Submit button in add->Add expense is pressed
     * This method checks the expense data fields for valid data and saves the record to the month_expenses table
     */
    public void onExpenseSubmit(){
        //if any info missing return
        if (expenseYearCB.getValue() == null || expenseMonthCB.getValue() == null || expenseDateCB.getValue() == null || expenseStoreTF.getText().isEmpty() || expenseAmountTF.getText().isEmpty()){
            //TODO send alert
            System.out.println("Fill out info");
            return;
        }else{
            //TODO May want to check for stores that a just a capital letter different
            if(!expenseStoreTF.contains(expenseStoreTF.getText())){
                expenseStoreTF.addEntry(expenseStoreTF.getText());
            }
            //for testing
            dataController.addExpense(expenseYearCB.getValue().toString(), expenseMonthCB.getValue().toString(), expenseDateCB.getValue().toString(), expenseStoreTF.getText(), expenseAmountTF.getText(),"dining out");
            //Actual if I Add in reasons
            //dataController.addExpense(expenseYearCB.getValue().toString(), expenseMonthCB.getValue().toString(), expenseDateCB.getValue().toString(), expenseStoreTF.getText(), expenseAmountTF.getText(),expenseCategory.getValue().toString());
            System.out.println(expenseMonthCB.getValue() + " " + expenseDateCB.getValue() + " " + expenseYearCB.getValue() + " at "+ expenseStoreTF.getText() + " for " + expenseAmountTF.getText());
            expenseStoreTF.setText("");
            expenseAmountTF.setText("");
        }
    }

    /**
     * Called when the expense Month dropdown is selected
     * This method looks at the current month and year selected to determines the dates to show in the date Dropdown
     */
    public void onExpenseMonthSelected(){
        expenseDateCB.getItems().clear();
        expenseDateCB.getItems().addAll(days);
        //month is February
        if (expenseMonthCB.getValue().toString().compareTo("February") == 0 ){
            //Leap Year
            if (expenseYearCB.getValue() != null && Integer.parseInt(expenseYearCB.getValue().toString())%4 == 0) {
                expenseDateCB.getItems().add("29");
            }
        }else{
            expenseDateCB.getItems().addAll("29","30");
        }
        if (months31.contains(expenseMonthCB.getValue().toString())){
            expenseDateCB.getItems().add("31");
        }
    }

    /**
     * Called when the expense year dropdown is selected
     * This method looks to see is the selected month is February, then determines how many days to show in the date dropdown
     * No other month needs looked at because year to year they all have the same amount of days
     */
    public void onExpenseYearSelected(){
        if (expenseMonthCB.getValue() != null && expenseMonthCB.getValue().toString().compareTo("February") == 0 ){
            expenseDateCB.getItems().clear();
            expenseDateCB.getItems().addAll(days);
            //Leap Year
            if (Integer.parseInt(expenseYearCB.getValue().toString())%4 == 0) {
                expenseDateCB.getItems().add("29");
            }
        }
    }

    /**
     * Called when the income Month dropdown is selected
     * This method looks at the current month and year selected to determines the dates to show in the date Dropdown
     */
    public void onIncomeMonthSelected(){
        incomeDateCB.getItems().clear();
        incomeDateCB.getItems().addAll(days);
        //month is February
        if (incomeMonthCB.getValue().toString().compareTo("February") == 0 ){
            //Leap Year
            if (incomeYearCB.getValue() != null && Integer.parseInt(incomeYearCB.getValue().toString())%4 == 0) {
                incomeDateCB.getItems().add("29");
            }
        }else{
            incomeDateCB.getItems().addAll("29","30");
        }
        if (months31.contains(incomeMonthCB.getValue().toString())){
            incomeDateCB.getItems().add("31");
        }
    }

    /**
     * Called when the expense year dropdown is selected
     * This method looks to see is the selected month is February, then determines how many days to show in the date dropdown
     * No other month needs looked at because year to year they all have the same amount of days
     */
    public void onIncomeYearSelected(){
        if (incomeMonthCB.getValue() != null && incomeMonthCB.getValue().toString().compareTo("February") == 0 ){
            incomeDateCB.getItems().clear();
            incomeDateCB.getItems().addAll(days);
            //Leap Year
            if (Integer.parseInt(incomeYearCB.getValue().toString())%4 == 0) {
                incomeDateCB.getItems().add("29");
            }
        }
    }

    /**
     * Called when the Submit button in add->Add income is pressed
     * This method checks the income data fields for valid data and saves the record to the month_income table
     */
    public void onIncomeSubmit(){
        //if any info missing return
        if (incomeYearCB.getValue() == null || incomeMonthCB.getValue() == null || incomeDateCB.getValue() == null || incomeSourceTF.getText().isEmpty() || incomeAmountTF.getText().isEmpty()){
            //TODO send alert
            System.out.println("Fill out info");
            return;
        }else{
            //for testing
            dataController.addIncome(incomeYearCB.getValue().toString(), incomeMonthCB.getValue().toString(), incomeDateCB.getValue().toString(), incomeSourceTF.getText(), incomeAmountTF.getText(),"Employer");
            //Actual
            //dataController.addExpense(incomeYearCB.getValue().toString(), incomeMonthCB.getValue().toString(), incomeDateCB.getValue().toString(), incomeSourceTF.getText(), incomeAmountTF.getText(),incomeCategory.getValue().toString());
            System.out.println(incomeMonthCB.getValue() + " " + incomeDateCB.getValue() + " " + incomeYearCB.getValue() + " at "+ incomeSourceTF.getText() + " for " + incomeAmountTF.getText());
            expenseStoreTF.setText("");
            expenseAmountTF.setText("");
        }
    }

    /**
     * Called when the Submit button in Monthly Details is pressed
     * This method checks the year and Month for valid data then queries the table and populates the tables
     */
    public void onMDSubmitPressed() {
        if (MDMonth.getValue() == null || MDYear.getValue() == null) {
            //TODO Add Alert
        } else {
            Set<DetailData> monthlyExpenses = dataController.getMonthlyExpenses(Integer.parseInt(MDYear.getValue().toString()), MDMonth.getValue().toString());
            MDExpenseTable.getItems().clear();
            MDExpenseTable.getItems().addAll(monthlyExpenses);
            MDExpenseTable.sort();
            Set<DetailData> monthlyIncome = dataController.getMonthlyIncome(Integer.parseInt(MDYear.getValue().toString()), MDMonth.getValue().toString());
            MDIncomeTable.getItems().clear();
            MDIncomeTable.getItems().addAll(monthlyIncome);
            MDIncomeTable.sort();
        }
    }

    /**
     * Called when the expense Remove Row button is pressed in the Monthly Details tab
     * This method removes the selected row from the db and updates the table
     */
    public void onMDIncomeRemove(){
        DetailData row = MDIncomeTable.getSelectionModel().getSelectedItem();
        dataController.deleteIncome(row.getId());
        Set<DetailData> monthlyIncome = dataController.getMonthlyIncome(Integer.parseInt(MDYear.getValue().toString()), MDMonth.getValue().toString());
        MDIncomeTable.getItems().clear();
        MDIncomeTable.getItems().addAll(monthlyIncome);
        MDIncomeTable.sort();
    }

    /**
     * Called when the income Remove Row button is pressed in the Monthly Details tab
     * This method removes the selected row from the db and updates the table
     */
    public void onMDExpenseRemove(){
        DetailData row = MDExpenseTable.getSelectionModel().getSelectedItem();
        dataController.deleteExpense(row.getId());
        Set<DetailData> monthlyExpenses = dataController.getMonthlyExpenses(Integer.parseInt(MDYear.getValue().toString()), MDMonth.getValue().toString());
        MDExpenseTable.getItems().clear();
        MDExpenseTable.getItems().addAll(monthlyExpenses);
        MDExpenseTable.sort();
    }
}

