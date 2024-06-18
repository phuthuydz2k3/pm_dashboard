package org.example.project_manager_dashboard.views.screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.project_manager_dashboard.controllers.ProductController;
import org.example.project_manager_dashboard.models.Book;
import org.example.project_manager_dashboard.models.CD;
import org.example.project_manager_dashboard.models.DVD;
import org.example.project_manager_dashboard.models.Product;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProductDetailView implements Initializable {

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productCategoryField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productStockField;

    @FXML
    private TextField productWeightField;

    @FXML
    private TextField productRushDeliveryField;

    @FXML
    private AnchorPane productSpecificDetailsPane;

    @FXML
    private Button updateProductBtn, confirmUpdateBtn, cancelUpdateBtn, deleteProductBtn;

    @FXML
    private ImageView productImageView;

    private Product product;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelUpdateBtn.setVisible(false);
        confirmUpdateBtn.setVisible(false);
        if (product != null) {
            setProductDetails(product);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
        System.out.println(product.toString());
        if (productNameField != null) {
            setProductDetails(product);
        }
    }

    public void setProductDetails(Product product) {
        productNameField.setText(product.getName());
        productCategoryField.setText(product.getCategory());
        productPriceField.setText(product.getPrice().toString());
        productStockField.setText(product.getAvailable().toString());
        productWeightField.setText(product.getWeight().toString());
        productRushDeliveryField.setText(product.getSupportRushDelivery() == 1 ? "Yes" : "No");
        // Load and set the image
        String imageURL = product.getImageURL();
        System.out.println(imageURL);
        if (imageURL != null && !imageURL.isEmpty()) {
            try {
                Image image = new Image(Objects.requireNonNull(getClass().getResource(imageURL)).toExternalForm());
                productImageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        loadSpecificDetails(product);
    }

    private void loadSpecificDetails(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader();
            VBox pane;
            if (product instanceof Book) {
                loader.setLocation(getClass().getResource("/fxml/bookDetail.fxml"));
                pane = loader.load();
                Book book = (Book) product;
                ((TextField) pane.lookup("#bookAuthorField")).setText(book.getAuthor());
                ((TextField) pane.lookup("#bookCoverTypeField")).setText(book.getCoverType());
                ((TextField) pane.lookup("#bookPublisherField")).setText(book.getPublisher());
                ((TextField) pane.lookup("#bookPublishDateField")).setText(book.getPublishDate().toString());
            } else if (product instanceof CD) {
                loader.setLocation(getClass().getResource("/fxml/cdDetail.fxml"));
                pane = loader.load();
                CD cd = (CD) product;
                ((TextField) pane.lookup("#cdArtistField")).setText(cd.getArtist());
                ((TextField) pane.lookup("#cdReleasedDateField")).setText(cd.getReleasedDate().toString());
                ((TextField) pane.lookup("#cdRecordLabelField")).setText(cd.getRecordLabel());
                ((TextField) pane.lookup("#cdMusicTypeField")).setText(cd.getMusicType());
            } else if (product instanceof DVD) {
                loader.setLocation(getClass().getResource("/fxml/dvdDetail.fxml"));
                pane = loader.load();
                DVD dvd = (DVD) product;
                ((TextField) pane.lookup("#dvdDiscTypeField")).setText(dvd.getDiscType());
                ((TextField) pane.lookup("#dvdDirectorField")).setText(dvd.getDirector());
                ((TextField) pane.lookup("#dvdStudioField")).setText(dvd.getStudio());
                ((TextField) pane.lookup("#dvdSubtitleField")).setText(dvd.getSubtitle());
                ((TextField) pane.lookup("#dvdRuntimeField")).setText(dvd.getRuntime());
            } else {
                // Handle other product types if needed
                return;
            }
            productSpecificDetailsPane.getChildren().setAll(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateProduct() {
        if (product != null) {
            updateProductBtn.setVisible(false);
            deleteProductBtn.setVisible(false);
            cancelUpdateBtn.setVisible(true);
            confirmUpdateBtn.setVisible(true);

            makeAllTextFieldsEditable(true);
        }
    }

    private void makeAllTextFieldsEditable(Boolean editable) {
        productNameField.setEditable(editable);
//        productCategoryField.setEditable(editable);
        productPriceField.setEditable(editable);
        productStockField.setEditable(editable);
        productWeightField.setEditable(editable);
        productRushDeliveryField.setEditable(editable);

        VBox pane = (VBox) productSpecificDetailsPane.getChildren().get(0);
        for (TextField textField : pane.getChildren().filtered(node -> node instanceof TextField).toArray(new TextField[0])) {
            textField.setEditable(editable);
        }
    }

    @FXML
    private void deleteProduct() {
        if (product != null) {
            boolean deleted = ProductController.getProductController().deleteProduct(product.getProductId());
            if (deleted) {
                // Optionally, update UI or show success message
                System.out.println("Product deleted successfully.");
                // Clear fields or handle UI state as needed after deletion
            } else {
                // Handle deletion failure
                System.out.println("Failed to delete product.");
            }
        }
    }

    @FXML
    private void confirmUpdate() {
        if (product != null) {
            Product oldProduct = createProductCopy(product);

            try {
                product.setName(productNameField.getText());
                product.setCategory(productCategoryField.getText());
                product.setPrice(Double.parseDouble(productPriceField.getText()));
                product.setAvailable(Integer.parseInt(productStockField.getText()));
                product.setWeight(Double.parseDouble(productWeightField.getText()));
                product.setSupportRushDelivery((short) (productRushDeliveryField.getText().equalsIgnoreCase("Yes") ? 1 : 0));

                VBox pane = (VBox) productSpecificDetailsPane.getChildren().get(0);
                if (product instanceof Book) {
                    Book book = (Book) product;
                    book.setAuthor(((TextField) pane.lookup("#bookAuthorField")).getText());
                    book.setCoverType(((TextField) pane.lookup("#bookCoverTypeField")).getText());
                    book.setPublisher(((TextField) pane.lookup("#bookPublisherField")).getText());
                    book.setPublishDate(java.sql.Date.valueOf(((TextField) pane.lookup("#bookPublishDateField")).getText()));
                } else if (product instanceof CD) {
                    CD cd = (CD) product;
                    cd.setArtist(((TextField) pane.lookup("#cdArtistField")).getText());
                    cd.setReleasedDate(java.sql.Date.valueOf(((TextField) pane.lookup("#cdReleasedDateField")).getText()));
                    cd.setRecordLabel(((TextField) pane.lookup("#cdRecordLabelField")).getText());
                    cd.setMusicType(((TextField) pane.lookup("#cdMusicTypeField")).getText());
                } else if (product instanceof DVD) {
                    DVD dvd = (DVD) product;
                    dvd.setDiscType(((TextField) pane.lookup("#dvdDiscTypeField")).getText());
                    dvd.setDirector(((TextField) pane.lookup("#dvdDirectorField")).getText());
                    dvd.setStudio(((TextField) pane.lookup("#dvdStudioField")).getText());
                    dvd.setSubtitle(((TextField) pane.lookup("#dvdSubtitleField")).getText());
                    dvd.setRuntime(((TextField) pane.lookup("#dvdRuntimeField")).getText());
                }

                boolean updated = ProductController.getProductController().updateProduct(product);
                if (updated) {
                    System.out.println("Product updated successfully.");
                } else {
                    product = oldProduct;  // Revert to old product details
                    System.out.println("Failed to update product. Reverting to previous state.");
                }
            } catch (Exception e) {
                product = oldProduct;  // Revert to old product details
                System.out.println("An error occurred while updating the product. Reverting to previous state.");
            }

            updateProductBtn.setVisible(true);
            deleteProductBtn.setVisible(true);
            cancelUpdateBtn.setVisible(false);
            confirmUpdateBtn.setVisible(false);

            makeAllTextFieldsEditable(false);
        }
    }

    private Product createProductCopy(Product product) {
        if (product instanceof Book) {
            Book book = (Book) product;
            return Book.builder()
                    .productId(book.getProductId())
                    .name(book.getName())
                    .category(book.getCategory())
                    .price(book.getPrice())
                    .available(book.getAvailable())
                    .weight(book.getWeight())
                    .supportRushDelivery(book.getSupportRushDelivery())
                    .author(book.getAuthor())
                    .coverType(book.getCoverType())
                    .publisher(book.getPublisher())
                    .publishDate(book.getPublishDate())
                    .build();
        } else if (product instanceof CD) {
            CD cd = (CD) product;
            return CD.builder()
                    .productId(cd.getProductId())
                    .name(cd.getName())
                    .category(cd.getCategory())
                    .price(cd.getPrice())
                    .available(cd.getAvailable())
                    .weight(cd.getWeight())
                    .supportRushDelivery(cd.getSupportRushDelivery())
                    .artist(cd.getArtist())
                    .releasedDate(cd.getReleasedDate())
                    .recordLabel(cd.getRecordLabel())
                    .musicType(cd.getMusicType())
                    .build();
        } else if (product instanceof DVD) {
            DVD dvd = (DVD) product;
            return DVD.builder()
                    .productId(dvd.getProductId())
                    .name(dvd.getName())
                    .category(dvd.getCategory())
                    .price(dvd.getPrice())
                    .available(dvd.getAvailable())
                    .weight(dvd.getWeight())
                    .supportRushDelivery(dvd.getSupportRushDelivery())
                    .discType(dvd.getDiscType())
                    .director(dvd.getDirector())
                    .studio(dvd.getStudio())
                    .subtitle(dvd.getSubtitle())
                    .runtime(dvd.getRuntime())
                    .build();
        } else {
            return Product.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .category(product.getCategory())
                    .price(product.getPrice())
                    .available(product.getAvailable())
                    .weight(product.getWeight())
                    .supportRushDelivery(product.getSupportRushDelivery())
                    .build();
        }
    }

    @FXML
    private void cancelUpdate() {
        updateProductBtn.setVisible(true);
        deleteProductBtn.setVisible(true);
        cancelUpdateBtn.setVisible(false);
        confirmUpdateBtn.setVisible(false);

        makeAllTextFieldsEditable(false);
        loadSpecificDetails(product);
    }
}