package com.example.kanban_board;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

enum Importance {
    HIGH, NORMAL, LOW;
}

public class HelloController {
    @FXML
    BorderPane borderPane;
    @FXML
    Button addbtn;
    @FXML
    Button deletebtn; // Added delete button
    @FXML
    GridPane grid;
    @FXML
    GridPane grid1;
    int rowCount = 0;
    int columnCount = 0;
    boolean useGrid1 = false; // Flag to indicate whether to use grid1

    public void initialize() {
        // Initialize the grid pane with some initial content if needed
        // For example, you can add some default cards here.
    }

    @FXML
    public void handleAddButtonAction() {
        showAddCardDialog(grid);
    }

    @FXML
    public void handleAddButtonActionForGrid1() {
        showAddCardDialog(grid1);
    }

    private void showAddCardDialog(GridPane targetGrid) {
        // Create the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Add New Card");

        // Create the card details section
        GridPane cardDetailsGrid = new GridPane();
        cardDetailsGrid.setHgap(10);
        cardDetailsGrid.setVgap(10);
        cardDetailsGrid.setPadding(new Insets(20));

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        ChoiceBox<String> importanceChoice = new ChoiceBox<>();
        importanceChoice.getItems().addAll(Importance.HIGH.toString(), Importance.NORMAL.toString(), Importance.LOW.toString());
        importanceChoice.setValue(Importance.NORMAL.toString());

        cardDetailsGrid.add(new Label("Title:"), 0, 0);
        cardDetailsGrid.add(titleField, 1, 0);
        cardDetailsGrid.add(new Label("Description:"), 0, 1);
        cardDetailsGrid.add(descriptionArea, 1, 1);
        cardDetailsGrid.add(new Label("Importance:"), 0, 2);
        cardDetailsGrid.add(importanceChoice, 1, 2);

        dialog.getDialogPane().setContent(cardDetailsGrid);

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == createButtonType) {
                addGreenCard(targetGrid, titleField.getText(), descriptionArea.getText(), Importance.valueOf(importanceChoice.getValue()));
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void addGreenCard(GridPane targetGrid, String title, String description, Importance importance) {
        // Create a new green card (pane) dynamically
        Pane greencard = new Pane();
        greencard.setPrefSize(206, 150); // Set preferred size as per your requirement

        // Set color based on importance
        String backgroundColor;
        switch (importance) {
            case HIGH:
                backgroundColor = "indianred"; // Very important tasks represented by red
                break;
            case NORMAL:
                backgroundColor = "lightgoldenrodyellow"; // Normal tasks represented by yellow
                break;
            case LOW:
                backgroundColor = "lightgreen"; // Not so important tasks represented by green
                break;
            default:
                backgroundColor = "lightgreen"; // Default color
        }
        greencard.setStyle("-fx-border-color: gray; -fx-background-color: " + backgroundColor + "; -fx-border-color: black;");

        // Create and add components to the green card
        TextField titleField = new TextField(title);
        titleField.setPrefSize(215, 31);
        titleField.setLayoutY(-1);
        titleField.setStyle("-fx-background-color: " + backgroundColor + "; -fx-font-size: 14px; -fx-font-weight: bold;-fx-border-color: black;");
        TextArea descriptionArea = new TextArea(description);
        descriptionArea.setPrefSize(215, 87);
        descriptionArea.setLayoutY(30);
        descriptionArea.setStyle("-fx-font-size: 12px; -fx-border-color: black;");
        CheckBox checkBox = new CheckBox("Completed");
        checkBox.setLayoutX(10);
        checkBox.setLayoutY(122);

        // Add components to the green card
        greencard.getChildren().addAll(titleField, descriptionArea, checkBox);

        // Make the green card draggable
        greencard.setOnDragDetected(event -> {
            greencard.startFullDrag();
            event.consume();
        });

        // Define drag and drop behavior
        targetGrid.setOnDragOver(event -> {
            if (event.getGestureSource() != greencard && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            }
        });

        // Handle drop event
        targetGrid.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                // Move the card to the new position
                targetGrid.getChildren().remove(greencard);
                targetGrid.add(greencard, GridPane.getColumnIndex(event.getPickResult().getIntersectedNode()), GridPane.getRowIndex(event.getPickResult().getIntersectedNode()));
                success = true;
                event.setDropCompleted(success);
            }
            event.consume();
        });

        // Create context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem moveMenuItem = new MenuItem("Move");
        moveMenuItem.setOnAction(event -> {
            greencard.startFullDrag();
        });
        contextMenu.getItems().add(moveMenuItem);
        greencard.setOnContextMenuRequested(event -> contextMenu.show(greencard, event.getScreenX(), event.getScreenY()));

        // Add the card to the grid pane
        targetGrid.add(greencard, columnCount, rowCount);

        // Update row and column counts
        columnCount++;
        if (columnCount >= targetGrid.getColumnCount()) {
            columnCount = 0;
            rowCount++;
        }

        // Remove focus from text input fields after user input
        titleField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                greencard.requestFocus(); // Set focus to another node to remove input bar
            }
        });
        descriptionArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                greencard.requestFocus(); // Set focus to another node to remove input bar
            }
        });
    }

    private void showUpdateCardDialog(GridPane targetGrid, Pane greencard) {
        // Get current values
        TextField titleField = null;
        TextArea descriptionArea = null;
        String title = "";
        String description = "";
        for (Node node : greencard.getChildren()) {
            if (node instanceof TextField) {
                titleField = (TextField) node;
                title = titleField.getText();
            } else if (node instanceof TextArea) {
                descriptionArea = (TextArea) node;
                description = descriptionArea.getText();
            }
        }
        // Create the dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Update Card");

        // Create the card details section
        GridPane cardDetailsGrid = new GridPane();
        cardDetailsGrid.setHgap(10);
        cardDetailsGrid.setVgap(10);
        cardDetailsGrid.setPadding(new Insets(20));

        TextField newTitleField = new TextField(title);
        newTitleField.setPromptText("Title");
        TextArea newDescriptionArea = new TextArea(description);
        newDescriptionArea.setPromptText("Description");
        ChoiceBox<String> importanceChoice = new ChoiceBox<>();
        importanceChoice.getItems().addAll("High", "Normal", "Low");
        importanceChoice.setValue("Normal");

        cardDetailsGrid.add(new Label("Title:"), 0, 0);
        cardDetailsGrid.add(newTitleField, 1, 0);
        cardDetailsGrid.add(new Label("Description:"), 0, 1);
        cardDetailsGrid.add(newDescriptionArea, 1, 1);
        cardDetailsGrid.add(new Label("Importance:"), 0, 2);
        cardDetailsGrid.add(importanceChoice, 1, 2);

        dialog.getDialogPane().setContent(cardDetailsGrid);

        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == updateButtonType) {
                updateGreenCard(targetGrid, greencard, newTitleField.getText(), newDescriptionArea.getText(), importanceChoice.getValue());
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void updateGreenCard(GridPane targetGrid, Pane greencard, String title, String description, String importance) {
        // Update the card details
        TextField titleField = null;
        TextArea descriptionArea = null;
        for (Node node : greencard.getChildren()) {
            if (node instanceof TextField) {
                titleField = (TextField) node;
            } else if (node instanceof TextArea) {
                descriptionArea = (TextArea) node;
            }
        }
        if (titleField != null) {
            titleField.setText(title);
            // Update text field color based on importance
            switch (importance.toLowerCase()) {
                case "high":
                    titleField.setStyle("-fx-background-color: indianred; -fx-border-color : black");
                    break;
                case "normal":
                    titleField.setStyle("-fx-background-color: lightgoldenrodyellow; -fx-border-color : black");
                    break;
                case "low":
                    titleField.setStyle("-fx-background-color: lightgreen; -fx-border-color : black");
                    break;
                default:
                    titleField.setStyle("-fx-background-color: lightgreen;");
            }
        }
        if (descriptionArea != null) {
            descriptionArea.setText(description);
        }

        // Set color based on importance for the entire card
        String backgroundColor;
        switch (importance.toLowerCase()) {
            case "high":
                backgroundColor = "indianred"; // Very important tasks represented by red
                break;
            case "normal":
                backgroundColor = "lightgoldenrodyellow"; // Normal tasks represented by yellow
                break;
            case "low":
                backgroundColor = "lightgreen"; // Not so important tasks represented by green
                break;
            default:
                backgroundColor = "lightgreen"; // Default color
        }
        greencard.setStyle("-fx-border-color: gray; -fx-background-color: " + backgroundColor + "; -fx-border-color: black;");
    }
}
