package com.example.demo13;

import com.dlsc.gemsfx.MultiColumnListView;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HelloApplication extends Application {
    private MultiColumnListView<Issue> multiColumnListView;
    private List<MultiColumnListView.ListViewColumn<Issue>> columns;

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        // Create menu items
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> System.exit(0));

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(exitMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);

        multiColumnListView = new MultiColumnListView<>();
        columns = createColumns();
        multiColumnListView.setCellFactory(listView -> new IssueListCell(multiColumnListView));
        multiColumnListView.getColumns().setAll(columns);
        multiColumnListView.setPlaceholderFrom(new Issue("From", "Done"));
        multiColumnListView.setPlaceholderTo(new Issue("To", "Done"));
        VBox.setVgrow(multiColumnListView, Priority.ALWAYS);

        Button addButton = new Button("Add Issue");
        addButton.setOnAction(e -> openAddIssueDialog());

        Button addColumnButton = new Button("Add Column");
        addColumnButton.setOnAction(e -> addColumn());

        CheckBox showHeaders = new CheckBox("Show Headers");
        showHeaders.selectedProperty().bindBidirectional(multiColumnListView.showHeadersProperty());

        CheckBox disableDragAndDrop = new CheckBox("Disable Editing");
        disableDragAndDrop.selectedProperty().bindBidirectional(multiColumnListView.disableDragAndDropProperty());

        Callback<Integer, Node> separatorFactory = multiColumnListView.getSeparatorFactory();

        CheckBox separators = new CheckBox("Use Separators");
        separators.setSelected(true);
        separators.selectedProperty().addListener(it -> {
            if (separators.isSelected()) {
                multiColumnListView.setSeparatorFactory(separatorFactory);
            } else {
                multiColumnListView.setSeparatorFactory(null);
            }
        });

        HBox optionsBox = new HBox(10, separators, showHeaders, disableDragAndDrop, addColumnButton);
        optionsBox.setAlignment(Pos.CENTER_RIGHT);
        VBox vbox = new VBox(menuBar, multiColumnListView, addButton, optionsBox);
        vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox);
        stage.setTitle("KanZen");
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(750);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");

        stage.centerOnScreen();
        stage.show();

        // Add CSS file
        //scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("multi-column-app.css")).toExternalForm());

        String cssPath = "C:\\Users\\Home PC\\IdeaProjects\\demo13\\src\\main\\java\\com\\example\\demo13\\multi-column-app.css"; // Specify the correct path
        scene.getStylesheets().add(new File(cssPath).toURI().toURL().toExternalForm());

    }

    // Create initial columns
    private List<MultiColumnListView.ListViewColumn<Issue>> createColumns() {
        MultiColumnListView.ListViewColumn<Issue> col1 = new MultiColumnListView.ListViewColumn<>();
        MultiColumnListView.ListViewColumn<Issue> col2 = new MultiColumnListView.ListViewColumn<>();
        MultiColumnListView.ListViewColumn<Issue> col3 = new MultiColumnListView.ListViewColumn<>();
        MultiColumnListView.ListViewColumn<Issue> col4 = new MultiColumnListView.ListViewColumn<>();

        col1.setHeader(new Label("Backlog"));
        col2.setHeader(new Label("To DO"));
        col3.setHeader(new Label("In Flight"));
        col4.setHeader(new Label("Done"));

        col2.setItems(FXCollections.observableArrayList(new Issue("Jule", "todo"), new Issue("Franz", "in-progress"), new Issue("Paul", "done"), new Issue("Orange", "todo"), new Issue("Yellow", "in-progress"), new Issue("Red", "done"), new Issue("Mango", "todo")));
        col3.setItems(FXCollections.observableArrayList(new Issue("Armin", "todo")));
        col4.setItems(FXCollections.observableArrayList(new Issue("Zaid", "todo")));

        return List.of(col1, col2, col3, col4);
    }

    // Add a new column to the multiColumnListView
    private void addColumn() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Column");
        dialog.setHeaderText("Enter Column Title:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(title -> {
            MultiColumnListView.ListViewColumn<Issue> newColumn = new MultiColumnListView.ListViewColumn<>();
            newColumn.setHeader(new Label(title));
            multiColumnListView.getColumns().add(newColumn);
        });
    }

    // Define the Issue class
    public static class Issue {
        private final String title;
        private final String status;

        public Issue(String title, String status) {
            this.title = title;
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }
    }

    // Define the IssueListCell class
    public static class IssueListCell extends MultiColumnListView.ColumnListCell<Issue> {
        private final StackPane wrapper;

        public IssueListCell(MultiColumnListView<Issue> multiColumnListView) {
            super(multiColumnListView);
            getStyleClass().add("issue-list-cell");

            VBox content = new VBox();
            content.getStyleClass().add("content");
            content.visibleProperty().bind(placeholder.not().and(emptyProperty().not()));
            content.managedProperty().bind(placeholder.not().and(emptyProperty().not()));

            VBox contentPlaceholder = new VBox();
            contentPlaceholder.getStyleClass().add("placeholder");
            contentPlaceholder.visibleProperty().bind(placeholder);
            contentPlaceholder.managedProperty().bind(placeholder);

            Label label = new Label();
            label.textProperty().bind(textProperty());

            wrapper = new StackPane(content, contentPlaceholder, label);
            setGraphic(wrapper);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }

        @Override
        protected Node getSnapshotNode() {
            return wrapper;
        }

        private final BooleanProperty placeholder = new SimpleBooleanProperty(this, "placeholder", false);

        @Override
        protected void updateItem(Issue item, boolean empty) {
            super.updateItem(item, empty);
            placeholder.set(false);

            getStyleClass().removeAll("todo", "in-progress", "done");

            if (item != null && !empty) {
                if (item == getMultiColumnListView().getPlaceholderFrom()) {
                    placeholder.set(true);
                    setText("From");
                } else if (item == getMultiColumnListView().getPlaceholderTo()) {
                    placeholder.set(true);
                    setText("To");
                } else {
                    setText(item.getTitle());
                    getStyleClass().add(item.getStatus());
                }
            } else {
                setText("");
            }
        }
    }

    // Open a dialog to add a new issue
    private void openAddIssueDialog() {
        Dialog<Issue> dialog = new Dialog<>();
        dialog.setTitle("Add Issue");

        TextField titleField = new TextField();
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("todo", "in-progress", "done");

        dialog.getDialogPane().setContent(new VBox(10, new Label("Title:"), titleField, new Label("Status:"), statusComboBox));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String title = titleField.getText();
                String status = statusComboBox.getValue();
                return new Issue(title, status);
            }
            return null;
        });

        Optional<Issue> result = dialog.showAndWait();
        result.ifPresent(issue -> {
            // Add the new issue to the first column
            columns.get(0).getItems().add(issue);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
