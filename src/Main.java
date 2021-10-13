import Controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //For the budget page, I want categoies of want needs and savings/investing/debt repayment
//Budget tab todo
    //Functionality
        //save updates to table
        //Determine the table structures
        //Add add and remove button and functionality
    //UI
        //Figure out dynamic sizing
        //Whats going on the right, button for getMonthAndYearBudget
        //Add that the month and year to copy over values from other tabs


////Missing Features for Add and Monthly Details
//Categories update when store is selected
//Data validation on the inserts
//Populate Categories for Income
//Organize the UI
    //align the remove row.
//Organize code
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene/main.fxml"));
        Parent root;
        try {
            root = loader.load();
            //loader.setClassLoader(getClass().getClassLoader());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MainController mainController = loader.getController();

        Scene scene = new Scene(root);
        primaryStage.setOnHidden(e -> {
            mainController.shutdown();
            Platform.exit();
        });
        primaryStage.setTitle("Budget");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(473.0);
        primaryStage.setMinWidth(707.0);
        primaryStage.setMaxHeight(473.0);
        primaryStage.setMaxWidth(707.0);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}