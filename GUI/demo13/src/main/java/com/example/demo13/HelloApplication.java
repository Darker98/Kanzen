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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

import com.example.demo13.AuthenticationCaller;

public class HelloApplication extends Application {
    private MultiColumnListView<Issue> multiColumnListView;
    private List<MultiColumnListView.ListViewColumn<Issue>> columns;
    public static HelloApplication object;

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
//        List<Column> columnObjects = createColumns();
//
//     //   List<Column> columnObjects = Column.add(this);
          columns = new ArrayList<>();
//        for (Column column : columnObjects) {
            MultiColumnListView.ListViewColumn<Issue> listViewColumn = new MultiColumnListView.ListViewColumn<>();
//            Label headerLabel = createHeaderLabel(column.getName(), "column-header");
//            listViewColumn.setHeader(headerLabel);
//            columns.add(listViewColumn);
//        }

        multiColumnListView = new MultiColumnListView<>();
        multiColumnListView.setCellFactory(listView -> new IssueListCell(multiColumnListView));
//        multiColumnListView.getColumns().setAll(columns);
        multiColumnListView.setPlaceholderFrom(new Issue(Database.generateId(),"From", "Done", null));
        multiColumnListView.setPlaceholderTo(new Issue(Database.generateId(),"To", "Done", null));


        stage.initStyle(StageStyle.DECORATED.UNDECORATED);

        BorderPane loginbox = new BorderPane();
        loginbox.setPrefSize(769, 523);



        // Left side
        AnchorPane leftAnchorPane = new AnchorPane();
        leftAnchorPane.setPrefSize(401, 523);
        leftAnchorPane.setStyle("-fx-background-color: #0598ff;");

        Label titleLabel = new Label("\"Kanzen, the choice of professionals\"");
        titleLabel.setStyle("-fx-font-family: 'Aptos';");
        titleLabel.setTextFill(Color.web("#fffbfb"));
        titleLabel.setFont(Font.font("3DS Fonticon", 23));
        titleLabel.setAlignment(javafx.geometry.Pos.CENTER);
        titleLabel.setLayoutX(-1);
        titleLabel.setLayoutY(355);
        titleLabel.setPrefWidth(406);
        titleLabel.setPrefHeight(66);

        ImageView logoImageView = new ImageView();
        logoImageView.setImage(new Image("file:src/main/logo with text.png"));
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
        memberBtn.getStyleClass().add("member-btn");
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









//        multiColumnListView = new MultiColumnListView<>();
//        columns = createColumns();
//        multiColumnListView.setCellFactory(listView -> new IssueListCell(multiColumnListView));
//        multiColumnListView.getColumns().setAll(columns);
//        multiColumnListView.setPlaceholderFrom(new Issue("From", "Done"));
//        multiColumnListView.setPlaceholderTo(new Issue("To", "Done"));
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

        Menu sign_out = new Menu("Sign Out");


//        MenuBar menuBar = new MenuBar();
//        menuBar.getMenus().addAll(fileMenu, editMenu, sign_out);
//        menuBar.setStyle("-fx-background-color:#abd2f5; -fx-border-color: black;");

        // Create a HBox to hold the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setStyle("-fx-background-color:#0598ff; -fx-border-color: black;");
        buttonBox.setPrefHeight(30);



// Create buttons
        Button fileMenuButton = new Button("");
        FontAwesomeIcon file = new FontAwesomeIcon();
        file.setGlyphName("FILE");
        fileMenuButton.setStyle("-fx-background-color: #0598ff");
        //file.setFill(Color.web("#ffffff"));
        file.setSize("1.5em");
        fileMenuButton.setGraphic(file);
        fileMenuButton.setCursor(Cursor.HAND);
        fileMenuButton.setOnMouseEntered(event->{
            fileMenuButton.setStyle("-fx-background-color: #2146cc");
        });
        fileMenuButton.setOnMouseExited(event->{
            fileMenuButton.setStyle("-fx-background-color:#0598ff;");
        });


        Button editMenuButton = new Button("");
        FontAwesomeIcon edit = new FontAwesomeIcon();
        edit.setGlyphName("COGWHEEL");
        editMenuButton.setStyle("-fx-background-color: #0598ff");
       // edit.setFill(Color.web("#ffffff"));
        edit.setSize("1.5em");
        editMenuButton.setGraphic(edit);
        editMenuButton.setCursor(Cursor.HAND);
        editMenuButton.setOnMouseEntered(event->{
            editMenuButton.setStyle("-fx-background-color: #2146cc");
        });
        editMenuButton.setOnMouseExited(event->{
            editMenuButton.setStyle("-fx-background-color:#0598ff;");
        });


        Button signOutButton = new Button("");
        FontAwesomeIcon signout = new FontAwesomeIcon();
        signout.setGlyphName("COG");
        signOutButton.setStyle("-fx-background-color: #0598ff");
       // signout.setFill(Color.web("#ffffff"));
        signout.setSize("1.5em");
        signOutButton.setGraphic(signout);
        signOutButton.setCursor(Cursor.HAND);
        signOutButton.setOnMouseEntered(event->{
            signOutButton.setStyle("-fx-background-color: #2146cc");
        });
        signOutButton.setOnMouseExited(event->{
            signOutButton.setStyle("-fx-background-color:#0598ff;");
        });


        Button create_card = new Button("");
        create_card.setOnAction(e -> openAddIssueDialog());
        FontAwesomeIcon add = new FontAwesomeIcon();
        add.setGlyphName("PLUS");
        create_card.setStyle("-fx-background-color: #0598ff");
       // add.setFill(Color.web("#ffffff"));
        add.setSize("1.5em");
        create_card.setGraphic(add);
        create_card.setCursor(Cursor.HAND);
        create_card.setOnMouseEntered(event->{
            create_card.setStyle("-fx-background-color: #2146cc");
        });
        create_card.setOnMouseExited(event->{
            create_card.setStyle("-fx-background-color:#0598ff;");
        });


        Button create_column = new Button("");
        FontAwesomeIcon col = new FontAwesomeIcon();
        col.setGlyphName("BOOKMARK");
        create_column.setStyle("-fx-background-color: #0598ff");
       // col.setFill(Color.web("#ffffff"));
        col.setSize("1.5em");
        create_column.setGraphic(col);
        create_column.setOnAction(e -> addColumn());
        create_column.setCursor(Cursor.HAND);
        create_column.setOnMouseEntered(event->{
            create_column.setStyle("-fx-background-color: #2146cc");
        });
        create_column.setOnMouseExited(event->{
            create_column.setStyle("-fx-background-color:#0598ff;");
        });

        Button close = new Button("");
        close.setAlignment(Pos.TOP_RIGHT);
        FontAwesomeIcon cross = new FontAwesomeIcon();
        cross.setGlyphName("CLOSE");
        close.setStyle("-fx-background-color: #0598ff;");
       // cross.setFill(Color.web("#ffffff"));
        cross.setSize("1.5em");
        close.setGraphic(cross);
        close.setCursor(Cursor.HAND);
        close.setOnMouseEntered(event->{
            close.setStyle("-fx-background-color: #2146cc");
        });
        close.setOnMouseExited(event->{
            close.setStyle("-fx-background-color:#0598ff;");
        });
        close.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit Confirmation");
            alert.setHeaderText("Are you sure you want to exit?");
            alert.setContentText("Click OK to exit.");

            alert.showAndWait().ifPresent(response ->{
                if(response == ButtonType.OK){
                    stage.close();
                }
                else{

                }
            });

        });

        Button invite_member = new Button("");
        FontAwesomeIcon invite = new FontAwesomeIcon();
        invite.setGlyphName("CHILD");
        invite_member.setStyle("-fx-background-color: #0598ff;");
        invite.setSize("1.5em");
        invite_member.setGraphic(invite);
        invite_member.setCursor(Cursor.HAND);
        invite_member.setOnMouseEntered(event->{
            invite_member.setStyle("-fx-background-color: #2146cc");
        });
        invite_member.setOnMouseExited(event->{
            invite_member.setStyle("-fx-background-color:#0598ff;");
        });





        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Allow spacer to grow horizontally


// Add buttons to the buttonBox
        buttonBox.getChildren().addAll(fileMenuButton, editMenuButton, signOutButton, create_card, create_column, invite_member, spacer,close);

        // Set spacing between the buttons and the close button
      //  HBox.setMargin(close, new Insets(0, 150, 0, 0)); // Adjust the right margin as needed


        // VBox vbox2 = new VBox(menuBar, multiColumnListView);
       // vbox2.setPadding(new Insets(20,0,0,0));

        //Not gonna be using these buttons for now, instead added everything in the menu bar.
//        Button addButton = new Button("Add Card");
//       addButton.setOnAction(e -> openAddIssueDialog());

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
        VBox vbox = new VBox(buttonBox, multiColumnListView);
       // vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.setPadding(new Insets(0));
        multiColumnListView.setPadding(new Insets(20));

        Image kanzen_logo = new Image("file:src/main/logo with text.png");


        Scene scene = new Scene(vbox);
        Scene scene2 = new Scene(loginbox);
        memberBtn.setOnAction(event -> {
            ArrayList<String> parameters = new ArrayList<String>();
            try {
                System.out.println("Calling auth");
                AuthenticationCaller.call(parameters, "login", "Member");
                System.out.println("Auth finished");
            } catch (IllegalAccessException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No board found! Contact your manager to add you to a board.");
                return;
            }

            System.out.println(parameters.get(0));
            if (parameters.get(0) != null) {
//                for (int i = 0; i < Board.object.columns.size(); i++) {
//                    addColumn(Board.object.columns.get(i));
//                }
                initialColumns();
                initialCards();

                stage.setTitle("KanZen");
                stage.setScene(scene);
                stage.getIcons().add(kanzen_logo);
                //stage.setFullScreen(true);
                stage.setWidth(1200);
              //  multiColumnListView.setSeparatorFactory(null);

                stage.setHeight(800);

                stage.centerOnScreen();
            }
        });

        managerBtn.setOnAction(event -> {
            ArrayList<String> parameters = new ArrayList<String>();
            try {
                AuthenticationCaller.call(parameters, "login", "Manager");
            } catch (IllegalAccessException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No board found! Contact your manager to add you to a board.");
                return;
            }

            if (parameters.size() != 0) {
                if (parameters.get(0) != null) {
//                    for (int i = 0; i < Board.object.columns.size(); i++) {
//                        addColumn(Board.object.columns.get(i));
//                    }
                    initialColumns();
                    initialCards();

                    stage.setTitle("KanZen");
                    stage.setScene(scene);
                    stage.getIcons().add(kanzen_logo);
                    //stage.setFullScreen(true);
                    stage.setWidth(1200);
                    //  multiColumnListView.setSeparatorFactory(null);

                    stage.setHeight(800);

                    stage.centerOnScreen();
                }
            }
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

        String cssPath = "src/main/java/com/example/demo13/multi-column-app.css"; // Specify the correct path
        scene.getStylesheets().add(new File(cssPath).toURI().toURL().toExternalForm());
    }

    // Create initial columns
//    public List<Column> createColumns() {
//        Column col1 = new Column("Backlog", 0);
//        Column col2 = new Column("To Do", 0);
//        Column col3 = new Column("In Flight", 0);
//        Column col4 = new Column("Done", 0);
//
//
//       // col1.setHeader(createHeaderLabel("Backlog", "column-header-backlog"));
//        col1.setName("Backlog");
//        col2.setName("To Do");
//        col3.setName("In Flight");
//        col4.setName("Done");
//
//
//
//        //col2.setHeader(createHeaderLabel("To Do","column-header-todo"));
//        //col3.setHeader(createHeaderLabel("In Flight","column-header-in-progress"));
//        //col4.setHeader(createHeaderLabel("Done","column-header-done"));
//
////        col2.setItems(FXCollections.observableArrayList(new Issue("Jule", "important"), new Issue("Franz", "in-progress"), new Issue("Paul", "done"), new Issue("Orange", "todo"), new Issue("Yellow", "in-progress"), new Issue("Red", "done"), new Issue("Mango", "todo")));
////        col3.setItems(FXCollections.observableArrayList(new Issue("Armin", "todo")));
////        col4.setItems(FXCollections.observableArrayList(new Issue("Zaid", "todo")));
//
//
//        List<Column> columns = new ArrayList<>();
//        columns.add(col1);
//        columns.add(col2);
//        columns.add(col3);
//        columns.add(col4);
//
//        return columns;
//    }

    private Label createHeaderLabel(String text, String styleClass){
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        return label;

    }


    public void addColumn(Column column) {
        if(multiColumnListView.getColumns().size() >= 6){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Maximum number of Columns Reached");
            alert.setContentText("You can only have two columns.");
            alert.showAndWait();
            return;
        }

        Label headerLabel = new Label(column.getName());
        MultiColumnListView.ListViewColumn<Issue> listViewColumn = new MultiColumnListView.ListViewColumn<>();
        ColumnUI columnUI = new ColumnUI(column);
        columnUI.setHeader(headerLabel);
        columnUI.getHeader().setStyle("-fx-font-size: 40px; -fx-alignment: center; -fx-font-family:'Futura';" +
                "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #6ec5ff 10%, #f4f4f4 70%);");

        columns.add(listViewColumn);
        listViewColumn.setHeader(headerLabel);
        multiColumnListView.getColumns().add(listViewColumn);
    }

    // Add a new column to the multiColumnListView
    public void addColumn() {
        if(multiColumnListView.getColumns().size() >= 6){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Maximum number of Columns Reached");
            alert.setContentText("You can only have two columns.");
            alert.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Column");
        dialog.setHeaderText("Enter Column Title:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(title -> {
            //MultiColumnListView.ListViewColumn<Issue> newColumn = new MultiColumnListView.ListViewColumn<>();
            Column newColumn = new Column();


            //Randomly select a color from the list
            Color random_color = headerColors.get(new Random().nextInt(headerColors.size()));
            //creating a label with a specified title and assigning it a random color
            Label headerLabel = new Label(title);
            headerLabel.setText(title);
            headerLabel.setTextAlignment(TextAlignment.CENTER);

            //headerLabel.setStyle("-fx-background-color: " + toRgbString(random_color)+" ;"+ "fx-font-size: 20px; -fx-alignment: center; -fx-font-family: 'Times New Roman'; -fx-text-fill: white;");
            newColumn.setName(title);
            MultiColumnListView.ListViewColumn<Issue> listViewColumn = new MultiColumnListView.ListViewColumn<>();
            Board.object.columns.add(newColumn);
            ColumnUI columnUI = new ColumnUI(newColumn);
            columnUI.setHeader(headerLabel);
            columnUI.getHeader().setStyle("-fx-font-size: 40px; -fx-alignment: center; -fx-font-family:'Futura';" +
                    "-fx-background-color: radial-gradient(center 50% 50%, radius 100%, #6ec5ff 10%, #f4f4f4 70%);");

            listViewColumn.setHeader(headerLabel);
            multiColumnListView.getColumns().add(listViewColumn);
            columns.add(listViewColumn);

            Database.updateBoard();

            //newColumn.setHeader(headerLabel);

           // multiColumnListView.getColumns().add(newColumn);

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
    public static class Issue extends Card{
        private  String title;
        private  String status;

        private LocalDateTime createdDate;

        private LocalDate dueDate;


        public Issue (String id, String title, String status, LocalDate dueDate)  {
            this.id = id;
            this.title = title;
            this.status = status;
            this.createdDate = LocalDateTime.now();
            this.dueDate = dueDate;

        }

        public String getTitle() {
            return title;
        }

        public String getStatus() {
            return status;
        }

        public LocalDateTime getCreatedDate(){
            return  createdDate;
        }

        public String getFormattedDate(){
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy ");
            return getCreatedDate().format(formatter);
        }

        public void updateTitle(String newTitle){
            this.title = newTitle;
        }

        public LocalDate getDueDate(){
            return dueDate;
        }
    }

    // Define the IssueListCell class
    public static class IssueListCell extends MultiColumnListView.ColumnListCell<Issue> {
        private  StackPane wrapper;
         Button delete_btn = new Button("");

         Label date_label;
         Label due_date;

         Button update_btn = new Button("");

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

            Label delete_label = new Label();

            FontAwesomeIcon delete = new FontAwesomeIcon();
            delete.setGlyphName("TRASH");
            delete.setFill(Color.web("#ffffff"));
            delete.setSize("1.5em");
            delete_btn.setStyle("-fx-background-color: #ff0000;");
            delete_btn.setGraphic(delete);

            delete_label.setGraphic(delete_btn);
            delete_label.visibleProperty().bind(placeholder.not().and(emptyProperty().not()));
            delete_label.managedProperty().bind(placeholder.not().and(emptyProperty().not()));
            delete_label.setStyle("-fx-font-size: 10px;");
            delete_label.setCursor(Cursor.HAND);


           date_label = new Label();
           date_label.setStyle("-fx-font-size: 10px;");

            due_date = new Label();
            due_date.setStyle("-fx-font-size: 10px;");


            delete_btn.setOnAction(event -> {
                Issue issue = getItem();
                if(issue != null){
                    getMultiColumnListView().getColumns().forEach(column ->{
                        if(column.getItems().contains(issue)){
                            column.getItems().remove(issue);

                        }

                    });
                }
            });

            Label update_label = new Label();
            FontAwesomeIcon update = new FontAwesomeIcon();
            update.setGlyphName("PENCIL");
            update.setFill(Color.web("#ffffff"));
            update.setSize("1.0em");
            //update.setStyle("-fx-background-color: #27e868;");
            update_btn.setGraphic(update);
            update_label.setGraphic(update_btn);
            update_btn.setStyle("-fx-background-color: #27e868");
            update_btn.setOnAction(event ->{
                TextInputDialog dialog = new TextInputDialog(getItem().getTitle());
                dialog.setTitle("Update Card");
                dialog.setHeaderText("Enter New Description");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(description ->{
                   getItem().updateTitle(description);
                   updateItem(getItem(), isEmpty());
                });
            });
            update_label.visibleProperty().bind(placeholder.not().and(emptyProperty().not()));
            update_label.managedProperty().bind(placeholder.not().and(emptyProperty().not()));





            wrapper = new StackPane(content, contentPlaceholder, label );

            wrapper.getChildren().add(delete_label);
            wrapper.getChildren().add(date_label);
            wrapper.getChildren().add(update_label);
            wrapper.getChildren().add(due_date);
            wrapper.setAlignment(update_label,Pos.TOP_LEFT);
            wrapper.setAlignment(delete_label,Pos.TOP_RIGHT);
            wrapper.setAlignment(date_label, Pos.BOTTOM_LEFT);
            wrapper.setAlignment(due_date, Pos.BOTTOM_RIGHT);
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
                    date_label.setText("Created On: "+item.getFormattedDate());
                    if (item.getDueDate() != null) {
                        due_date.setText("Due Date: " + item.getDueDate().toString());
                    } else {
                        due_date.setText(""); // Clear due date label if no due date available
                    }
                }
            } else {
                setText("");
                date_label.setText("");
            }
        }
    }

    // Open a dialog to add a new issue
    private void openAddIssueDialog() {
        Dialog<Issue> dialog = new Dialog<>();

        dialog.getDialogPane().setStyle("-fx-background-color: #c7e7fc;");



        TextField titleField = new TextField();
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("todo", "in-progress", "done", "important");

        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);


        dialog.getDialogPane().setContent(new VBox(10, new Label("Title:"), titleField, new Label("Status:"), statusComboBox, new Label("Due Date:", datePicker)));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String title = titleField.getText();
                String status = statusComboBox.getValue();
                LocalDate dueDate = datePicker.getValue();

                return new Issue(Database.generateId(),title, status, dueDate);
            }
            return null;
        });

        Optional<Issue> result = dialog.showAndWait();
        result.ifPresent(issue -> {
            // Add the new issue to the first column
            Board.object.columns.get(0).cards.add(issue);
            columns.get(0).getItems().add(issue);
            Database.updateBoard();
        });
    }

    public void initialCards() {
        Issue issue;
        Card card;
        for (int i = 0; i < Board.object.columns.size(); i++) {
            for (int j = 0; j < Board.object.columns.get(i).cards.size(); j++) {
                card = Board.object.columns.get(i).cards.get(j);
                issue = new Issue(card.id, card.title, card.status, card.date);
                columns.get(i).getItems().add(issue);
            }
        }
    }

    public void initialColumns() {
        for (Column column : Board.object.columns) {
            addColumn(column);
        }
    }



    public static void main(String[] args) {
        Database.initialize();
        launch();
    }

//    public HelloApplication() {
//        //launch();
//        this.object = this;
//    }
}
