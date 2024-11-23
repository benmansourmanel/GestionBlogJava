package tn.esprit.controllers;

import com.sun.glass.events.KeyEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Post;
import tn.esprit.services.ServicePost;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import tn.esprit.test.mainFX;

public class DisplayPost {

    @FXML
    private TableView<Post> postTableView;

    @FXML
    private TableColumn<Post, Integer> colId;

    @FXML
    private TableColumn<Post, String> colTitle;

    @FXML
    private TableColumn<Post, String> colContent;

    @FXML
    private TableColumn<Post, String> colImage;

    @FXML
    private TableColumn<Post, String> colCreatedAt;

    @FXML
    private TableColumn<Post, String> colUpdatedAt;

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfContent;

    @FXML
    private TextField tfImagePath;

    @FXML
    private TextField tfCreatedAt;

    @FXML
    private TextField tfUpdatedAt;

    private ObservableList<Post> posts;
    private ServicePost servicePost;
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
    void Speech(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AfficherQuestionsFront.fxml");
    }
    @FXML
    void GoToBack(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayCommentBack.fxml");
    }
    @FXML
    void initialize() {
        // Create a service instance to retrieve posts data
        servicePost = new ServicePost();

        // Initialize columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colUpdatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        // Retrieve posts data from the service
        posts = FXCollections.observableArrayList(servicePost.getAll());

        // Set the posts data to the TableView
        postTableView.setItems(posts);

        // Bind the text fields to the properties of the selected post
        postTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfTitle.setText(newValue.getTitle());
                tfContent.setText(newValue.getContent());
                tfImagePath.setText(newValue.getImage());
                tfCreatedAt.setText(newValue.getCreatedAt().toString());
                tfUpdatedAt.setText(newValue.getUpdatedAt().toString());
            } else {
                tfTitle.clear();
                tfContent.clear();
                tfImagePath.clear();
                tfCreatedAt.clear();
                tfUpdatedAt.clear();
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleUpdatePost(ActionEvent event) {
        Post selectedPost = postTableView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            // Update selected post with new details from text fields
            selectedPost.setTitle(tfTitle.getText());
            selectedPost.setContent(tfContent.getText());
            selectedPost.setImage(tfImagePath.getText());


            // Call update method in service class
            servicePost.update(selectedPost);
            showAlert("Post updated successfully!");

            // Refresh table view if necessary
            refreshTableView();
        } else {
            showAlert("Error", "No Post Selected", "Please select a post to update.");
        }
    }

    private void refreshTableView() {
        // Refresh the TableView by retrieving updated data from the service
        posts.clear();
        posts.addAll(servicePost.getAll());
    }

    // METIER SEARCH

    @FXML
    private void gotoAjouter(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("AddPost.fxml"));
        Scene scene = new Scene(page1);
        Stage newStage = new Stage();
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    void handleDeletePost(ActionEvent event) {
        Post selectedPost = postTableView.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            // Call delete method in service class
            boolean deleted = servicePost.delete(selectedPost);
            if (deleted) {
                // Remove the selected post from the table view
                posts.remove(selectedPost);
                showAlert("Post deleted successfully!");
            } else {
                showAlert("Error", "Deletion Failed", "Failed to delete the selected post.");
            }
        } else {
            showAlert("Error", "No Post Selected", "Please select a post to delete.");
        }
    }

    @FXML
    void downloadPdfButtonClicked(ActionEvent event) {
        try {
            String downloadsPath = System.getProperty("user.home") + "/Downloads/";
            Document document = new Document();
            String filePath = downloadsPath + "PostList.pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            document.add(new Paragraph("List of Posts\n\n"));

            for (Post post : posts) {
                document.add(new Paragraph(post.toString()));
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

    @FXML
    void handleSavePostChanges(ActionEvent actionEvent) {
        // Implementation for saving post changes
    }
}
