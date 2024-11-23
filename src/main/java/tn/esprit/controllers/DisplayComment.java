package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Comment;
import tn.esprit.services.ServiceComment;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import tn.esprit.test.mainFX;

public class DisplayComment {

    @FXML
    private ListView<Comment> commentListView; // Renamed for clarity

    @FXML
    private TextField tfContent;

    @FXML
    private TextField tfPostId;

    @FXML
    private TextField tfUserId;

    private ObservableList<Comment> comments;
    private ServiceComment serviceComment;
    @FXML
    void GoToBack(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayCommentBack.fxml");
    }
    @FXML
    void GoToComment(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddComment.fxml");
    }
    @FXML
    void AddLike(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddLikes.fxml");
    }

    @FXML
    void GoToPost(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddPost.fxml");
    }

    @FXML
    void initialize() {
        // Create a service instance to retrieve comments data
        serviceComment = new ServiceComment();

        // Retrieve comments data from the service
        comments = FXCollections.observableArrayList(serviceComment.getAll());

        // Set the comments data to the ListView
        commentListView.setItems(comments);

        // Bind the text fields to the properties of the selected comment
        commentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfContent.setText(newValue.getContent());
                tfPostId.setText(String.valueOf(newValue.getPostId()));
                tfUserId.setText(String.valueOf(newValue.getUserId()));
            } else {
                tfContent.clear();
                tfPostId.clear();
                tfUserId.clear();
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleUpdateComment(ActionEvent event) {
        Comment selectedComment = commentListView.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            // Update selected comment with new details from text fields
            selectedComment.setContent(tfContent.getText());
            selectedComment.setPostId(Integer.parseInt(tfPostId.getText()));
            selectedComment.setUserId(Integer.parseInt(tfUserId.getText()));

            // Call update method in service class
            serviceComment.update(selectedComment);
            showAlert("Comment updated successfully!");

            // Refresh list view if necessary
            refreshListView();
        } else {
            showAlert("Error", "No Comment Selected", "Please select a comment to update.");
        }
    }

    private void refreshListView() {
        // Refresh the ListView by retrieving updated data from the service
        comments.clear();
        comments.addAll(serviceComment.getAll());
    }

    @FXML
    void handleDeleteComment(ActionEvent event) {
        Comment selectedComment = commentListView.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            // Call delete method in service class
            boolean deleted = serviceComment.delete(selectedComment);
            if (deleted) {
                // Remove the selected comment from the list view
                comments.remove(selectedComment);
                showAlert("Comment deleted successfully!");
            } else {
                showAlert("Error", "Deletion Failed", "Failed to delete the selected comment.");
            }
        } else {
            showAlert("Error", "No Comment Selected", "Please select a comment to delete.");
        }
    }

    @FXML
    void downloadPdfButtonClicked(ActionEvent event) {
        try {
            String downloadsPath = System.getProperty("user.home") + "/Downloads/";
            Document document = new Document();
            String filePath = downloadsPath + "CommentList.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("List of Comments\n\n"));

            for (Comment comment : comments) {
                document.add(new Paragraph(comment.toString()));
            }

            document.close();
            showAlert("PDF Downloaded Successfully!");

        } catch (Exception e) {
            showAlert("Error", "PDF Download Error", "Failed to download PDF: " + e.getMessage());
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void handleSaveCommentChanges(ActionEvent actionEvent) {
        // Implementation for saving comment changes
    }
}
