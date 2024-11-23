package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Likes;
import tn.esprit.services.ServiceLikes;
import tn.esprit.test.mainFX;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class DisplayLikesBack {

    @FXML
    private ListView<Likes> likesListView;

    @FXML
    private TextField tfPostId;

    @FXML
    private TextField tfUserId;

    @FXML
    private TextField tfIsLiked;

    private ObservableList<Likes> likesList;
    private ServiceLikes serviceLikes;

    @FXML
    void GoToLike(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayLikesBack.fxml");
    }

    @FXML
    void GoToPost(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayPostBack.fxml");
    }
    @FXML
    void GoToComment(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayCommentBack.fxml");
    }
    @FXML
    void GoToFront(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayComment.fxml");
    }
    @FXML
    void initialize() {
        // Create a service instance to retrieve likes data
        serviceLikes = new ServiceLikes();

        // Retrieve likes data from the service
        likesList = FXCollections.observableArrayList(serviceLikes.getAll());

        // Set the likes data to the ListView
        likesListView.setItems(likesList);

        // Bind the text fields to the properties of the selected like
        likesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfPostId.setText(String.valueOf(newValue.getPostId()));
                tfUserId.setText(String.valueOf(newValue.getUserId()));
                tfIsLiked.setText(String.valueOf(newValue.isLiked()));
            } else {
                tfPostId.clear();
                tfUserId.clear();
                tfIsLiked.clear();
            }
        });
    }

    @FXML
    void handleUpdateLike(ActionEvent event) {
        Likes selectedLike = likesListView.getSelectionModel().getSelectedItem();
        if (selectedLike != null) {
            // Update selected like with new details from text fields
            selectedLike.setPostId(Integer.parseInt(tfPostId.getText()));
            selectedLike.setUserId(Integer.parseInt(tfUserId.getText()));
            selectedLike.setLiked(Boolean.parseBoolean(tfIsLiked.getText())); // Convert text to boolean

            // Call update method in service class
            serviceLikes.update(selectedLike);
            showAlert("Like updated successfully!");

            // Refresh list view if necessary
            refreshListView();
        } else {
            showAlert("Error", "No Like Selected", "Please select a like to update.");
        }
    }

    private void refreshListView() {
        // Refresh the ListView by retrieving updated data from the service
        likesList.clear();
        likesList.addAll(serviceLikes.getAll());
    }

    @FXML
    void handleDeleteLike(ActionEvent event) {
        Likes selectedLike = likesListView.getSelectionModel().getSelectedItem();
        if (selectedLike != null) {
            // Call delete method in service class
            boolean deleted = serviceLikes.delete(selectedLike);
            if (deleted) {
                // Remove the selected like from the list view
                likesList.remove(selectedLike);
                showAlert("Like deleted successfully!");
            } else {
                showAlert("Error", "Deletion Failed", "Failed to delete the selected like.");
            }
        } else {
            showAlert("Error", "No Like Selected", "Please select a like to delete.");
        }
    }

    @FXML
    void downloadPdfButtonClicked(ActionEvent event) {
        try {
            String downloadsPath = System.getProperty("user.home") + "/Downloads/";
            Document document = new Document();
            String filePath = downloadsPath + "LikesList.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("List of Likes\n\n"));

            for (Likes like : likesList) {
                document.add(new Paragraph(like.toString()));
            }

            document.close();
            showAlert("PDF Downloaded Successfully!");

        } catch (Exception e) {
            showAlert("Error", "PDF Download Error", "Failed to download PDF: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void handleSaveLikeChanges(ActionEvent actionEvent) {

    }
}
