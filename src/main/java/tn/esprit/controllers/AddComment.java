package tn.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tn.esprit.models.Comment;
import tn.esprit.services.ServiceComment;
import tn.esprit.test.mainFX;

public class AddComment {

    private ServiceComment sc = new ServiceComment();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfContent;

    @FXML
    private TextField tfPostId;

    @FXML
    private TextField tfUserId;
    @FXML
    void DisplayComment(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayComment.fxml");
    }
    @FXML
    void GoToBack(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayCommentBack.fxml");
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
    void GoToComment(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddComment.fxml");
    }

    @FXML
    void addComment(ActionEvent event) throws IOException {
        // Check if any of the fields are empty
        if (tfContent.getText().isEmpty() || tfPostId.getText().isEmpty() || tfUserId.getText().isEmpty()) {
            // Show error message
            showAlert("Please fill in all fields!");
        } else {
            // All fields are filled, proceed to add the comment
            Comment comment = new Comment();
            comment.setContent(tfContent.getText());
            comment.setPostId(Integer.parseInt(tfPostId.getText()));
            comment.setUserId(Integer.parseInt(tfUserId.getText()));
            comment.setCreatedAt(LocalDate.now()); // Set the current date as the created_at date

            sc.add(comment);

            // Show success message
            showAlert("Comment added successfully!");
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
}
