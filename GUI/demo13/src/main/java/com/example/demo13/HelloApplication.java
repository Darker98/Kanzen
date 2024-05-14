package com.example.demo13;

import com.dlsc.gemsfx.MultiColumnListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.css.StyleClass;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.util.Callback;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


import java.io.File;
import java.io.IOException;
import java.util.*;

import com.example.demo13.AuthenticationCaller;

public class HelloApplication extends Application {
    private MultiColumnListView<Issue> multiColumnListView;
    private List<MultiColumnListView.ListViewColumn<Issue>> columns;

    //List of colors for the columns that user creates manually
    private final List<Color> headerColors = List.of(
            Color.web("#26d1a6"),
            Color.web("#9029ff"),
            Color.web("#c98fcc"),
            Color.web("#e8a054"),
            Color.web("0598ff"),
            Color.web("#f74343"),
            Color.web("#4c22f5")
    );

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        BorderPane loginbox = new BorderPane();
        loginbox.setPrefSize(769, 523);

        // Left side
        AnchorPane leftAnchorPane = new AnchorPane();
        leftAnchorPane.setPrefSize(401, 523);
        leftAnchorPane.setStyle("-fx-background-color: #0598ff;");

        Label titleLabel = new Label("\"KanZen, The Choice of Professionals.\"");
        titleLabel.setStyle("-fx-font-family: 'Times New Roman';");
        titleLabel.setTextFill(Color.web("#fffbfb"));
        titleLabel.setFont(Font.font("3DS Fonticon", 23));
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER);
        titleLabel.setLayoutX(-1);
        titleLabel.setLayoutY(355);
        titleLabel.setPrefWidth(406);
        titleLabel.setPrefHeight(66);

        ImageView logoImageView = new ImageView();
        logoImageView.setImage(new Image("file:GUI/demo13/src/main/logo with text.png"));
        logoImageView.setFitWidth(243);
        logoImageView.setFitHeight(251);
        logoImageView.setLayoutX(81);
        logoImageView.setLayoutY(26);
        logoImageView.setPreserveRatio(true);
        logoImageView.setPickOnBounds(true);

        leftAnchorPane.getChildren().addAll(titleLabel, logoImageView);
        loginbox.setLeft(leftAnchorPane);

        // Right side
        AnchorPane rightAnchorPane = new AnchorPane();
        rightAnchorPane.setPrefSize(381, 523);

        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setFill(Color.web("#0589ff"));
        icon.setGlyphName("GROUP");
        icon.setSize("10em");
        icon.setLayoutX(119);
        icon.setLayoutY(166);

        FontAwesomeIcon icon2 = new FontAwesomeIcon();
        icon2.setGlyphName("BRIEFCASE");
        icon2.setLayoutY(350);
        icon2.setLayoutX(85);
        icon2.setFill(Color.web("#0598ff"));
        icon2.setSize("2em");

        FontAwesomeIcon icon3 = new FontAwesomeIcon();
        icon3.setGlyphName("KEY");
        icon3.setSize("2em");
        icon3.setFill(Color.web("#0598ff"));
        icon3.setLayoutY(405);
        icon3.setLayoutX(85);

        FontAwesomeIcon close_btn = new FontAwesomeIcon();
        close_btn.setGlyphName("CLOSE");
        close_btn.setFill(Color.web("#0598ff"));
        close_btn.setSize("1.5em");
        close_btn.setCursor(javafx.scene.Cursor.HAND);
        close_btn.setLayoutX(360);
        close_btn.setLayoutY(22);


        Button memberBtn = new Button("Member");
        memberBtn.setLayoutX(124);
        memberBtn.setLayoutY(378);
        memberBtn.setPrefSize(120, 35);
        memberBtn.setFont(Font.font("ArtifaktElement-Light", 12));
        memberBtn.setCursor(javafx.scene.Cursor.HAND);
        memberBtn.setStyle("-fx-background-color:#0598ff; -fx-font-size: 14px");
        memberBtn.setTextFill(Color.web("#ffffff"));


        Button managerBtn = new Button("Manager");
        managerBtn.setLayoutX(124);
        managerBtn.setLayoutY(321);
        managerBtn.setPrefSize(120, 35);
        managerBtn.setFont(Font.font("ArtifaktElement-Light", 12));
        managerBtn.setCursor(javafx.scene.Cursor.HAND);
        managerBtn.setStyle("-fx-background-color:#0598ff; -fx-font-size: 14px");
        managerBtn.setTextFill(Color.web("#ffffff"));
        managerBtn.setId("manager_btn");

        Label userLoginLabel = new Label("User Login");
        userLoginLabel.setTextFill(Color.web("#0589ff"));
        userLoginLabel.setFont(Font.font(16));
        userLoginLabel.setLayoutX(144);
        userLoginLabel.setLayoutY(208);
        userLoginLabel.setPrefWidth(78);
        userLoginLabel.setPrefHeight(22);

        rightAnchorPane.getChildren().addAll(memberBtn, managerBtn, userLoginLabel, icon, icon2, icon3, close_btn);
        loginbox.setRight(rightAnchorPane);









        multiColumnListView = new MultiColumnListView<>();
        columns = createColumns();
        multiColumnListView.setCellFactory(listView -> new IssueListCell(multiColumnListView));
        multiColumnListView.getColumns().setAll(columns);
        multiColumnListView.setPlaceholderFrom(new Issue("From", "Done"));
        multiColumnListView.setPlaceholderTo(new Issue("To", "Done"));
        VBox.setVgrow(multiColumnListView, Priority.ALWAYS);

        // Create menu items
        MenuItem addColumnMenuItem = new MenuItem("Add Column");
        addColumnMenuItem.setOnAction(event -> addColumn());

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> {
            System.exit(0);
        });

        MenuItem addIssueMenuItem = new MenuItem("Add Card");
        addIssueMenuItem.setOnAction(event -> openAddIssueDialog());

        CheckMenuItem showHeadersMenuItem = new CheckMenuItem("Show Headers");
        showHeadersMenuItem.selectedProperty().bindBidirectional(multiColumnListView.showHeadersProperty());

        CheckMenuItem disableEditingMenuItem = new CheckMenuItem("Disable Editing");
        disableEditingMenuItem.selectedProperty().bindBidirectional(multiColumnListView.disableDragAndDropProperty());



        Menu fileMenu = new Menu("File");
        //fileMenu.getItems().addAll(new MenuItem("Exit"));
        fileMenu.getItems().add(exit);

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(addColumnMenuItem, addIssueMenuItem, showHeadersMenuItem, disableEditingMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);
        menuBar.setStyle("-fx-background-color:#abd2f5; -fx-border-color: black;");

       // VBox vbox2 = new VBox(menuBar, multiColumnListView);
       // vbox2.setPadding(new Insets(20,0,0,0));

        //Not gonna be using these buttons for now, instead added everything in the menu bar.
//        Button addButton = new Button("Add Card");
//        addButton.setOnAction(e -> openAddIssueDialog());

        Button addColumnButton = new Button("Add Column");
        addColumnButton.setOnAction(e -> addColumn());

        CheckBox showHeaders = new CheckBox("Show Headers");
        showHeaders.selectedProperty().bindBidirectional(multiColumnListView.showHeadersProperty());

        CheckBox disableDragAndDrop = new CheckBox("Disable Editing");
        disableDragAndDrop.selectedProperty().bindBidirectional(multiColumnListView.disableDragAndDropProperty());

        Callback<Integer, Node> separatorFactory = multiColumnListView.getSeparatorFactory();

        CheckBox separators = new CheckBox("Use Separators");
        separators.setSelected(false);
        separators.selectedProperty().addListener(it -> {
            if (separators.isSelected()) {
                multiColumnListView.setSeparatorFactory(separatorFactory);
            } else {
                multiColumnListView.setSeparatorFactory(null);
            }
        });


        //Login Window close action.
        close_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.close();
            }
        });


       // HBox optionsBox = new HBox(10, separators, showHeaders, disableDragAndDrop, addColumnButton);
        //optionsBox.setAlignment(Pos.CENTER_RIGHT);
        VBox vbox = new VBox(menuBar, multiColumnListView );
       // vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.setPadding(new Insets(0));
        multiColumnListView.setPadding(new Insets(20));

        Image kanzen_logo = new Image("file:C:\\Users\\Home PC\\OneDrive\\Documents\\GitHub\\Kanzen\\GUI\\demo13\\src\\main\\logo with text.png");


        Scene scene = new Scene(vbox);
        Scene scene2 = new Scene(loginbox);
        memberBtn.setOnAction(event -> {
          //  ArrayList<String> parameters = new ArrayList<String>();
           // AuthenticationCaller.call(parameters);

           // if (parameters.get(0) != null) {
                stage.setTitle("KanZen");
                stage.setScene(scene);
                stage.getIcons().add(kanzen_logo);
                stage.setWidth(1400);
                stage.setHeight(800);

                stage.centerOnScreen();
           // }
        });
        stage.setTitle("KanZen");
        stage.getTitle();
        stage.setScene(scene2);
        stage.getIcons().add(kanzen_logo);
        stage.getIcons().add(kanzen_logo);
        //stage.setWidth(1000);
       // stage.setHeight(800);
        //stage.setFullScreen(true);
        //stage.setFullScreenExitHint("");
        stage.centerOnScreen();
        stage.show();

        // Add CSS file
        //scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("multi-column-app.css")).toExternalForm());

        String cssPath = "C:\\Users\\Home PC\\OneDrive\\Documents\\GitHub\\Kanzen\\GUI\\demo13\\src\\main\\java\\com\\example\\demo13\\multi-column-app.css"; // Specify the correct path
        scene.getStylesheets().add(new File(cssPath).toURI().toURL().toExternalForm());

    }

    // Create initial columns
    private List<MultiColumnListView.ListViewColumn<Issue>> createColumns() {
        MultiColumnListView.ListViewColumn<Issue> col1 = new MultiColumnListView.ListViewColumn<>();
        MultiColumnListView.ListViewColumn<Issue> col2 = new MultiColumnListView.ListViewColumn<>();
        MultiColumnListView.ListViewColumn<Issue> col3 = new MultiColumnListView.ListViewColumn<>();
        MultiColumnListView.ListViewColumn<Issue> col4 = new MultiColumnListView.ListViewColumn<>();

        col1.setHeader(createHeaderLabel("Backlog", "column-header-backlog"));
        col2.setHeader(createHeaderLabel("To Do","column-header-todo"));
        col3.setHeader(createHeaderLabel("In Flight","column-header-in-progress"));
        col4.setHeader(createHeaderLabel("Done","column-header-done"));

        col2.setItems(FXCollections.observableArrayList(new Issue("Jule", "important"), new Issue("Franz", "in-progress"), new Issue("Paul", "done"), new Issue("Orange", "todo"), new Issue("Yellow", "in-progress"), new Issue("Red", "done"), new Issue("Mango", "todo")));
        col3.setItems(FXCollections.observableArrayList(new Issue("Armin", "todo")));
        col4.setItems(FXCollections.observableArrayList(new Issue("Zaid", "todo")));

        return List.of(col1, col2, col3, col4);
    }

    private Label createHeaderLabel(String text, String styleClass){
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;

    }

    // Add a new column to the multiColumnListView
    private void addColumn() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Column");
        dialog.setHeaderText("Enter Column Title:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(title -> {
            MultiColumnListView.ListViewColumn<Issue> newColumn = new MultiColumnListView.ListViewColumn<>();
            //Randomly select a color from the list
            Color random_color = headerColors.get(new Random().nextInt(headerColors.size()));
            //creating a label with a specified title and assigning it a random color
            Label headerLabel = new Label(title);
            headerLabel.setTextAlignment(TextAlignment.CENTER);
            headerLabel.setStyle("-fx-background-color: " + toRgbString(random_color)+" ;"+ "fx-font-size: 50px; -fx-alignment: center; -fx-font-family: 'Times New Roman'; -fx-text-fill: white;");

            newColumn.setHeader(headerLabel);
            multiColumnListView.getColumns().add(newColumn);
        });
    }

    // Helper method to convert Color to RGB string representation
    private String toRgbString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02x%02x%02x", r, g, b);
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

            getStyleClass().removeAll("todo", "in-progress", "done", "important");

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
        statusComboBox.getItems().addAll("todo", "in-progress", "done", "important");

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
