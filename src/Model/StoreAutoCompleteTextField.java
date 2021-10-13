package Model;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.*;

import java.util.*;

public class StoreAutoCompleteTextField extends TextField
{
    /** The existing autocomplete entries. */
    private final SortedSet<String> entries;
    /** The popup used to select an entry. */
    private ContextMenu entriesPopup;

    /** Construct a new AutoCompleteTextField. */
    public StoreAutoCompleteTextField() {
        super();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getText() != null) {
                    if (getText().length() == 0) {
                        entriesPopup.hide();
                    } else {
                        LinkedList<String> searchResult = new LinkedList<>();
                        //TODO make the search case Insensitive
                        entries.forEach(store -> {
                            if (subset(store,getText())){
                                searchResult.add(store);
                            }
                        });
                        searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                        if (entries.size() > 0) {
                            populatePopup(searchResult);
                            if (!entriesPopup.isShowing()) {
                                entriesPopup.show(StoreAutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                            }
                        } else {
                            entriesPopup.hide();
                        }
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

    }

    public boolean subset(String store,String currentStore){
        return store.toLowerCase().startsWith(currentStore);
    }

    /**
     * Get the existing set of autocomplete entries.
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() { return entries; }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++)
        {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    public void addEntries(Set<String> entries){
        this.entries.addAll(entries);
    }
    public void addEntry(String entry){
        this.entries.add(entry);
    }
    public boolean contains(String entry){
        return entries.contains(entry);
    }
}
