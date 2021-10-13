package Controller;

import Model.DetailData;
import Model.StoreAutoCompleteTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public  class MainControllerUI {
    //add Tab
    @FXML public StoreAutoCompleteTextField expenseStoreTF;
    @FXML public TextField expenseAmountTF;
    @FXML public ChoiceBox expenseYearCB;
    @FXML public ChoiceBox expenseMonthCB;
    @FXML public ChoiceBox expenseDateCB;
    @FXML public ChoiceBox expenseCategory;

    @FXML public TextField incomeSourceTF;
    @FXML public TextField incomeAmountTF;
    @FXML public ChoiceBox incomeYearCB;
    @FXML public ChoiceBox incomeMonthCB;
    @FXML public ChoiceBox incomeDateCB;
    @FXML public ChoiceBox incomeCategory;

    //Monthly Details tab
    @FXML public ChoiceBox MDYear;
    @FXML public ChoiceBox MDMonth;
    @FXML public Button MDSubmit;
    @FXML public TableView<DetailData> MDExpenseTable;
    @FXML public TableView<DetailData> MDIncomeTable;

    @FXML public TableColumn<DetailData,String> MDExpenseDate;
    @FXML public TableColumn<DetailData,String> MDExpenseStore;
    @FXML public TableColumn<DetailData,String> MDExpenseAmount;
    @FXML public TableColumn<DetailData,String> MDExpenseCategory;
    @FXML public TableColumn<DetailData,String> MDIncomeDate;
    @FXML public TableColumn<DetailData,String> MDIncomeSource;
    @FXML public TableColumn<DetailData,String> MDIncomeAmount;
    @FXML public TableColumn<DetailData,String> MDIncomeCategory;
}
