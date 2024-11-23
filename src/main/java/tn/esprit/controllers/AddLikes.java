package tn.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tn.esprit.models.Likes;
import tn.esprit.services.ServiceLikes;
import tn.esprit.test.mainFX;

public class AddLikes {

    private ServiceLikes sl = new ServiceLikes();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfPostId;

    @FXML
    private TextField tfUserId;

    @FXML
    private TextField tfIsLiked;
    @FXML
    void GoToBack(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayCommentBack.fxml");
    }
    @FXML
    void addLike(ActionEvent event) throws IOException {
        // Check if any of the fields are empty
        if (tfPostId.getText().isEmpty() || tfUserId.getText().isEmpty() || tfIsLiked.getText().isEmpty()) {
            // Show error message
            showAlert("Please fill in all fields!", AlertType.ERROR);
        } else {
            try {
                // All fields are filled, proceed to add the like
                Likes like = new Likes();
                like.setPostId(Integer.parseInt(tfPostId.getText()));
                like.setUserId(Integer.parseInt(tfUserId.getText()));
                like.setLiked(Boolean.parseBoolean(tfIsLiked.getText())); // Convert text to boolean

                sl.add(like);

                // Show success message
                showAlert("Like added successfully!", AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                // Handle number format exceptions
                showAlert("Post ID and User ID must be valid integers.", AlertType.ERROR);
            } catch (IllegalArgumentException e) {
                // Handle boolean parsing exceptions
                showAlert("IsLiked must be 'true' or 'false'.", AlertType.ERROR);
            }
        }
    }

    @FXML
    void GoToPost(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddPost.fxml");
    }

    @FXML
    void GoToLikes(ActionEvent event) throws IOException {
        mainFX.loadFXML("/DisplayLikes.fxml");
    }
    @FXML
    void AddLike(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddLikes.fxml");
    }
    @FXML
    void GoToComment(ActionEvent event) throws IOException {
        mainFX.loadFXML("/AddComment.fxml");
    }

    @FXML
    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == AlertType.ERROR ? "Error" : "Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
