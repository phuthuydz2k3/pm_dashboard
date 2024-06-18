package org.example.project_manager_dashboard.views.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.project_manager_dashboard.controllers.ProductsController;
import org.example.project_manager_dashboard.models.*;
import javafx.scene.control.DatePicker;


import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ProductFormView implements Initializable {

    @FXML
    private TextField priceTextField;
    @FXML
    private TextField availableTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField imageURLTextField;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextField weightTextField;
    @FXML
    private CheckBox rushDeliveryCheckBox;

    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

    @FXML
    private VBox extraFieldsVBox;

    @FXML
    private ImageView imageView;

    private ProductsController productsController;

    private Runnable onMediaAddedCallback;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productsController = ProductsController.getProductsController();
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateExtraFields(newVal));
    }

    private void updateExtraFields(String category) {
        extraFieldsVBox.getChildren().clear();
        System.out.println(category);
        switch (category) {
            case "Book":
                addBookFields();
                break;
            case "CD":
                addCDFields();
                break;
            case "DVD":
                addDVDFields();
                break;
            case "LP":
                addLPFields();
                break;
        }
    }

    private void addBookFields() {
        TextField authorTextField = new TextField();
        authorTextField.setPromptText("Enter author");
        authorTextField.setId("authorTextField");

        TextField coverTypeTextField = new TextField();
        coverTypeTextField.setPromptText("Enter cover type");
        coverTypeTextField.setId("coverTypeTextField");

        TextField publisherTextField = new TextField();
        publisherTextField.setPromptText("Enter publisher");
        publisherTextField.setId("publisherTextField");

        DatePicker publishDatePicker = new DatePicker();
        publishDatePicker.setPromptText("Enter publish date");
        publishDatePicker.setId("publishDatePicker");

        TextField numOfPagesTextField = new TextField();
        numOfPagesTextField.setPromptText("Enter number of pages");
        numOfPagesTextField.setId("numOfPagesTextField");

        TextField languageTextField = new TextField();
        languageTextField.setPromptText("Enter language");
        languageTextField.setId("languageTextField");

        TextField bookCategoryTextField = new TextField();
        bookCategoryTextField.setPromptText("Enter book category");
        bookCategoryTextField.setId("bookCategoryTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Author"), authorTextField,
                new Label("Cover Type"), coverTypeTextField,
                new Label("Publisher"), publisherTextField,
                new Label("Publish Date"), publishDatePicker,
                new Label("Number of Pages"), numOfPagesTextField,
                new Label("Language"), languageTextField,
                new Label("Book Category"), bookCategoryTextField
        );
    }


    private void addCDFields() {
        TextField artistTextField = new TextField();
        artistTextField.setPromptText("Enter artist");
        artistTextField.setId("artistTextField");

        DatePicker releasedDatePickerCD = new DatePicker();
        releasedDatePickerCD.setPromptText("Enter released date");
        releasedDatePickerCD.setId("releasedDatePickerCD");

        TextField recordLabelTextField = new TextField();
        recordLabelTextField.setPromptText("Enter record label");
        recordLabelTextField.setId("recordLabelTextFieldCD");

        TextField musicTypeTextField = new TextField();
        musicTypeTextField.setPromptText("Enter music type");
        musicTypeTextField.setId("musicTypeTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Artist"), artistTextField,
                new Label("Released Date"), releasedDatePickerCD,
                new Label("Record Label"), recordLabelTextField,
                new Label("Music Type"), musicTypeTextField
        );
    }



    private void addDVDFields() {
        TextField discTypeTextField = new TextField();
        discTypeTextField.setPromptText("Enter disc type");
        discTypeTextField.setId("discTypeTextField");

        TextField directorTextField = new TextField();
        directorTextField.setPromptText("Enter director");
        directorTextField.setId("directorTextField");

        TextField studioTextField = new TextField();
        studioTextField.setPromptText("Enter studio");
        studioTextField.setId("studioTextField");

        DatePicker releasedDatePickerDVD = new DatePicker();
        releasedDatePickerDVD.setPromptText("Enter released date");
        releasedDatePickerDVD.setId("releasedDatePickerDVD");

        TextField subtitleTextField = new TextField();
        subtitleTextField.setPromptText("Enter subtitle");
        subtitleTextField.setId("subtitleTextField");

        TextField runtimeTextField = new TextField();
        runtimeTextField.setPromptText("Enter runtime");
        runtimeTextField.setId("runtimeTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Disc Type"), discTypeTextField,
                new Label("Director"), directorTextField,
                new Label("Studio"), studioTextField,
                new Label("Released Date"), releasedDatePickerDVD,
                new Label("Subtitle"), subtitleTextField,
                new Label("Runtime"), runtimeTextField
        );
    }


    private void addLPFields() {
        TextField artistTextField = new TextField();
        artistTextField.setPromptText("Enter artist");
        artistTextField.setId("artistTextFieldLP");

        TextField releasedDateTextField = new TextField();
        releasedDateTextField.setPromptText("Enter released date");
        releasedDateTextField.setId("releasedDateTextFieldLP");

        TextField recordLabelTextField = new TextField();
        recordLabelTextField.setPromptText("Enter record label");
        recordLabelTextField.setId("recordLabelTextFieldLP");

        TextField musicTypeTextField = new TextField();
        musicTypeTextField.setPromptText("Enter music type");
        musicTypeTextField.setId("musicTypeTextFieldLP");

        extraFieldsVBox.getChildren().addAll(
                new Label("Artist"), artistTextField,
                new Label("Released Date"), releasedDateTextField,
                new Label("Record Label"), recordLabelTextField,
                new Label("Music Type"), musicTypeTextField
        );
    }


    @FXML
    private void saveMedia() {
        try {
            Double price = Double.parseDouble(priceTextField.getText());
            Integer available = Integer.parseInt(availableTextField.getText());
            String name = nameTextField.getText();
            String imageURL = imageURLTextField.getText();
            String category = categoryChoiceBox.getValue().toLowerCase();
            Double weight = Double.parseDouble(weightTextField.getText());
            Short rushDelivery = rushDeliveryCheckBox.isSelected() ? (short) 1 : (short) 0;

            // Validate inputs
            if (name.isEmpty() || category == null || category.isEmpty()) {
                showAlert("Validation Error", "Please fill all required fields.");
                return;
            }

            // Create Product object
            Product product;
            switch (category) {
                case "book":
                    String author = ((TextField) extraFieldsVBox.lookup("#authorTextField")).getText();
                    String coverType = ((TextField) extraFieldsVBox.lookup("#coverTypeTextField")).getText();
                    String publisher = ((TextField) extraFieldsVBox.lookup("#publisherTextField")).getText();
                    LocalDate publishDateLocal = ((DatePicker) extraFieldsVBox.lookup("#publishDatePicker")).getValue();
                    Date publishDate = null;
                    if (publishDateLocal != null) {
                        publishDate = java.sql.Date.valueOf(publishDateLocal);
                    }
                    Integer numOfPages = Integer.parseInt(((TextField) extraFieldsVBox.lookup("#numOfPagesTextField")).getText());
                    String language = ((TextField) extraFieldsVBox.lookup("#languageTextField")).getText();
                    String bookCategory = ((TextField) extraFieldsVBox.lookup("#bookCategoryTextField")).getText();
                    product = Book.builder()
                            .price(price)
                            .available(available)
                            .name(name)
                            .imageURL(imageURL)
                            .category(category)
                            .weight(weight)
                            .supportRushDelivery(rushDelivery)
                            .author(author)
                            .coverType(coverType)
                            .publisher(publisher)
                            .publishDate(publishDate)
//                            .numOfPages(numOfPages)
//                            .language(language)
//                            .bookCategory(bookCategory)
                            .build();
                    break;
                case "cd":
                    String artist = ((TextField) extraFieldsVBox.lookup("#artistTextField")).getText();
                    LocalDate releasedDateLocalCD = ((DatePicker) extraFieldsVBox.lookup("#releasedDatePickerCD")).getValue();
                    Date releasedDateCD = null;
                    if (releasedDateLocalCD != null) {
                        releasedDateCD = java.sql.Date.valueOf(releasedDateLocalCD);
                    }
                    String recordLabel = ((TextField) extraFieldsVBox.lookup("#recordLabelTextFieldCD")).getText();
                    String musicType = ((TextField) extraFieldsVBox.lookup("#musicTypeTextField")).getText();
                    product = CD.builder()
                            .price(price)
                            .available(available)
                            .name(name)
                            .imageURL(imageURL)
                            .category(category)
                            .weight(weight)
                            .supportRushDelivery(rushDelivery)
                            .artist(artist)
                            .releasedDate(releasedDateCD)
                            .recordLabel(recordLabel)
                            .musicType(musicType)
                            .build();
                    break;
                case "dvd":
                    String discType = ((TextField) extraFieldsVBox.lookup("#discTypeTextField")).getText();
                    String director = ((TextField) extraFieldsVBox.lookup("#directorTextField")).getText();
                    String studio = ((TextField) extraFieldsVBox.lookup("#studioTextField")).getText();
//                    String releasedDateDVD = ((TextField) extraFieldsVBox.lookup("#releasedDateTextField")).getText();
                    String subtitle = ((TextField) extraFieldsVBox.lookup("#subtitleTextField")).getText();
                    String runtime = ((TextField) extraFieldsVBox.lookup("#runtimeTextField")).getText();
                    product = DVD.builder()
                            .price(price)
                            .available(available)
                            .name(name)
                            .imageURL(imageURL)
                            .category(category)
                            .weight(weight)
                            .supportRushDelivery(rushDelivery)
                            .discType(discType)
                            .director(director)
                            .studio(studio)
//                            .releasedDate(releasedDateDVD)
                            .subtitle(subtitle)
                            .runtime(runtime)
                            .build();
                    break;
                case "LP":
//                    String artistLP = ((TextField) extraFieldsVBox.lookup("#artistTextField")).getText();
//                    String releasedDateLP = ((TextField) extraFieldsVBox.lookup("#releasedDateTextFieldLP")).getText();
//                    String recordLabelLP = ((TextField) extraFieldsVBox.lookup("#recordLabelTextFieldLP")).getText();
//                    String musicTypeLP = ((TextField) extraFieldsVBox.lookup("#musicTypeTextField")).getText();
//                    product = LP.builder()
//                            .price(price)
//                            .available(available)
//                            .name(name)
//                            .imageURL(imageURL)
//                            .category(category)
//                            .weight(weight)
//                            .supportRushDelivery(rushDelivery)
//                            .artist(artistLP)
//                            .releasedDate(releasedDateLP)
//                            .recordLabel(recordLabelLP)
//                            .musicType(musicTypeLP)
//                            .build();
//                    break;
                default:
                    showAlert("Validation Error", "Invalid category selected.");
                    return;
            }


            // Save product using ProductsView
            Boolean result = productsController.addProduct(product);
            showResultAddMedia(result);

            // Close the form
            Stage stage = (Stage) saveBtn.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter valid numbers for price, available quantity, and weight.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while saving the media. Please try again.");
            e.printStackTrace();
        }
    }

    private void showResultAddMedia(Boolean result) {
        if (result) {
            showAlert("Success", "Product added successfully.");
            if (onMediaAddedCallback != null) {
                onMediaAddedCallback.run();
            }
        } else {
            showAlert("Error", "Failed to add media.");
        }
    }

    public void setOnMediaAdded(Runnable callback) {
        this.onMediaAddedCallback = callback;
    }

    @FXML
    private void cancelSaveMedia() {
        // Close the form
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onDragOver(DragEvent event) {
        if (event.getGestureSource() != imageURLTextField &&
                event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    private void onDragDropped(DragEvent event) {
        boolean success = false;
        if (event.getDragboard().hasFiles()) {
            success = true;
            File file = event.getDragboard().getFiles().get(0);
            String imagePath = file.toURI().toString();
            imageURLTextField.setText(imagePath);
            imageView.setImage(new Image(imagePath));
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void saveImage() {
        String imageURL = imageURLTextField.getText();
        if (!imageURL.isEmpty()) {
            try {
                Image image = new Image(imageURL);
                imageView.setImage(image);
            } catch (Exception e) {
                showAlert("Error", "Failed to load image from URL.");
                e.printStackTrace();
            }
        } else {
            showAlert("Validation Error", "Please enter an image URL.");
        }
    }
}
