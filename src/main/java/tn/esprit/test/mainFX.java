package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFX extends Application {
    private  static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainFX.primaryStage=primaryStage;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/DisplayComment.fxml"));
        try {
            Parent root =loader.load();
            Scene scene =new Scene(root);

            primaryStage.setTitle("Comment management");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());        }

    }
    public static FXMLLoader loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(mainFX.class.getResource(fxmlFileName));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        return loader;
    }
}
