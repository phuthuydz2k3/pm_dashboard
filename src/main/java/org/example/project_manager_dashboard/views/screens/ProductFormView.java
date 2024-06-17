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

import java.io.File;
import java.net.URL;
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

        TextField publishDateTextField = new TextField();
        publishDateTextField.setPromptText("Enter publish date");
        publishDateTextField.setId("publishDateTextField");

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
                new Label("Publish Date"), publishDateTextField,
                new Label("Number of Pages"), numOfPagesTextField,
                new Label("Language"), languageTextField,
                new Label("Book Category"), bookCategoryTextField
        );
    }


    private void addCDFields() {
        TextField artistTextField = new TextField();
        artistTextField.setPromptText("Enter artist");
        artistTextField.setId("artistTextField");

        TextField releasedDateTextField = new TextField();
        releasedDateTextField.setPromptText("Enter released date");
        releasedDateTextField.setId("releasedDateTextFieldCD");

        TextField recordLabelTextField = new TextField();
        recordLabelTextField.setPromptText("Enter record label");
        recordLabelTextField.setId("recordLabelTextFieldCD");

        TextField musicTypeTextField = new TextField();
        musicTypeTextField.setPromptText("Enter music type");
        musicTypeTextField.setId("musicTypeTextField");

        extraFieldsVBox.getChildren().addAll(
                new Label("Artist"), artistTextField,
                new Label("Released Date"), releasedDateTextField,
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

        TextField releasedDateTextField = new TextField();
        releasedDateTextField.setPromptText("Enter released date");
        releasedDateTextField.setId("releasedDateTextFieldDVD");

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
                new Label("Released Date"), releasedDateTextField,
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
            Float price = Float.parseFloat(priceTextField.getText());
            Integer available = Integer.parseInt(availableTextField.getText());
            String name = nameTextField.getText();
            String imageURL = imageURLTextField.getText();
            String category = categoryChoiceBox.getValue();
            Float weight = Float.parseFloat(weightTextField.getText());
            Short rushDelivery = rushDeliveryCheckBox.isSelected() ? (short) 1 : (short) 0;

            // Validate inputs
            if (name.isEmpty() || category == null || category.isEmpty()) {
                showAlert("Validation Error", "Please fill all required fields.");
                return;
            }

            // Create Media object
            Media media;
            switch (category) {
                case "Book":
                    String author = ((TextField) extraFieldsVBox.lookup("#authorTextField")).getText();
                    String coverType = ((TextField) extraFieldsVBox.lookup("#coverTypeTextField")).getText();
                    String publisher = ((TextField) extraFieldsVBox.lookup("#publisherTextField")).getText();
                    String publishDate = ((TextField) extraFieldsVBox.lookup("#publishDateTextField")).getText();
                    Integer numOfPages = Integer.parseInt(((TextField) extraFieldsVBox.lookup("#numOfPagesTextField")).getText());
                    String language = ((TextField) extraFieldsVBox.lookup("#languageTextField")).getText();
                    String bookCategory = ((TextField) extraFieldsVBox.lookup("#bookCategoryTextField")).getText();
                    media = Book.builder()
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
                            .numOfPages(numOfPages)
                            .language(language)
                            .bookCategory(bookCategory)
                            .build();
                    break;
                case "CD":
                    String artist = ((TextField) extraFieldsVBox.lookup("#artistTextField")).getText();
                    String releasedDateCD = ((TextField) extraFieldsVBox.lookup("#releasedDateTextFieldCD")).getText();
                    String recordLabel = ((TextField) extraFieldsVBox.lookup("#recordLabelTextFieldCD")).getText();
                    String musicType = ((TextField) extraFieldsVBox.lookup("#musicTypeTextField")).getText();
                    media = CD.builder()
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
                case "DVD":
                    String discType = ((TextField) extraFieldsVBox.lookup("#discTypeTextField")).getText();
                    String director = ((TextField) extraFieldsVBox.lookup("#directorTextField")).getText();
                    String studio = ((TextField) extraFieldsVBox.lookup("#studioTextField")).getText();
                    String releasedDateDVD = ((TextField) extraFieldsVBox.lookup("#releasedDateTextField")).getText();
                    String subtitle = ((TextField) extraFieldsVBox.lookup("#subtitleTextField")).getText();
                    String runtime = ((TextField) extraFieldsVBox.lookup("#runtimeTextField")).getText();
                    media = DVD.builder()
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
                            .releasedDate(releasedDateDVD)
                            .subtitle(subtitle)
                            .runtime(runtime)
                            .build();
                    break;
                case "LP":
                    String artistLP = ((TextField) extraFieldsVBox.lookup("#artistTextField")).getText();
                    String releasedDateLP = ((TextField) extraFieldsVBox.lookup("#releasedDateTextFieldLP")).getText();
                    String recordLabelLP = ((TextField) extraFieldsVBox.lookup("#recordLabelTextFieldLP")).getText();
                    String musicTypeLP = ((TextField) extraFieldsVBox.lookup("#musicTypeTextField")).getText();
                    media = LP.builder()
                            .price(price)
                            .available(available)
                            .name(name)
                            .imageURL(imageURL)
                            .category(category)
                            .weight(weight)
                            .supportRushDelivery(rushDelivery)
                            .artist(artistLP)
                            .releasedDate(releasedDateLP)
                            .recordLabel(recordLabelLP)
                            .musicType(musicTypeLP)
                            .build();
                    break;
                default:
                    showAlert("Validation Error", "Invalid category selected.");
                    return;
            }


            // Save media using ProductsView
            Boolean result = productsController.addMedia(media);
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
            showAlert("Success", "Media added successfully.");
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
    private void  browseImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            imageURLTextField.setText(imagePath);
        }
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
}
