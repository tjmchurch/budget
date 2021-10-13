package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DetailData {
    int id;
    SimpleIntegerProperty date;
    SimpleStringProperty store;
    SimpleDoubleProperty amount;
    SimpleStringProperty category;

    public DetailData(int id, int date, String store, double amount, String category) {
        this.id = id;
        this.date = new SimpleIntegerProperty(date);
        this.store = new SimpleStringProperty(store);
        this.amount = new SimpleDoubleProperty(amount);
        this.category = new SimpleStringProperty(category);
    }

    public String getStore() {
        return store.get();
    }

    public SimpleStringProperty storeProperty() {
        return store;
    }

    public void setStore(String store) {
        this.store.set(store);
    }

    public Double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount.set(amount);
    }

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public int getDate() {
        return date.get();
    }

    public SimpleIntegerProperty dateProperty() {
        return date;
    }

    public void setDate(int date) {
        this.date.set(date);
    }

    public int getId(){return id;}
}
