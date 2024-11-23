package tn.esprit.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import tn.esprit.models.Post;
import tn.esprit.services.ServicePost;
import tn.esprit.test.mainFX;

public class AddPost {

    private ServicePost postService = new ServicePost();

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfContent;

    @FXML
    private TextField tfImagePath;

    @FXML
    private DatePicker dpCreatedAt;

    @FXML
    private DatePicker dpUpdatedAt;

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnUploadImage;


    @FXML
    void GoToComment(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddComment.fxml");
    }
    @FXML
    void DisplayPost(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayPost.fxml");
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
    void GoToBack(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayCommentBack.fxml");
    }
    @FXML
    void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            tfImagePath.setText(file.getAbsolutePath());

            try {
                Image image = new Image(new FileInputStream(file));
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void addPost(ActionEvent event) {
        // Check if any of the fields are empty
        if (tfTitle.getText().isEmpty() || tfContent.getText().isEmpty() || tfImagePath.getText().isEmpty() ||
                dpCreatedAt.getValue() == null || dpUpdatedAt.getValue() == null) {
            // Show error message
            showAlert("Please fill in all fields!");
        } else {
            try {
                // All fields are filled, proceed to add the post
                Post post = new Post();
                post.setTitle(tfTitle.getText());
                post.setContent(tfContent.getText());
                post.setImage(tfImagePath.getText());
                post.setCreatedAt(dpCreatedAt.getValue()); // Set the date from DatePicker
                post.setUpdatedAt(dpUpdatedAt.getValue()); // Set the date from DatePicker

                postService.add(post);

                // Show success message
                showAlert("Post added successfully!");

                // Clear fields after adding the post
                clearFields();

            } catch (Exception e) {
                // Handle exceptions (e.g., logging, showing error message)
                showAlert("An error occurred while adding the post.");
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        tfTitle.clear();
        tfContent.clear();
        tfImagePath.clear();
        dpCreatedAt.setValue(null);
        dpUpdatedAt.setValue(null);
        imageView.setImage(null); // Clear the displayed image
    }

}
