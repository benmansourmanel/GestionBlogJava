package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class chatgpt {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInput;

    private Map<String, String> responses;

    @FXML
    private AnchorPane nh;

    public void initialize() {
        // Initialiser les réponses du chatbot
        responses = new HashMap<>();
        responses.put("Bonjour", "Bonjour ! Comment puis-je vous aider ?");
        responses.put("Comment ça va ?", "Je suis juste un programme, je n'ai pas d'émotions !");
        responses.put("Au revoir", "Au revoir ! N'hésitez pas à revenir si vous avez d'autres questions.");

        // Ajouter les questions et réponses supplémentaires
        responses.put("La liaison tardive est essentielle pour assurer?", "(b) le polymorphisme");
        responses.put("Quand la surcharge de méthode est-elle déterminée?", "(B) Au moment de la compilation");
        responses.put("Comment ça s’appelle si un objet a son propre cycle de vie?", "(D) Association");
        responses.put("Comment s’appelle-t-on dans le cas où l’objet d’une classe mère est détruit donc l’objet d’une classe fille sera détruit également?", "(B) Composition");
        responses.put("Quels keywords sont utilisés pour spécifier la visibilité des propriétés et des méthodes ?", "(B) private");
        responses.put("Quels sont les séparateurs utilisés par la méthode GET pour ajouter les données à l'URL ?", "(C) '?' Pour séparer l'adresse et '&' pour les données");
        responses.put("En quoi consiste la surcharge en PHP Orienté Objet ?", "(B) à redéfinir les méthodes et attributs d'une classe mère");
        responses.put("Pour envoyer un email au format HTML en PHP il faut?", "(D) créer un header au content-type : text/html");
        responses.put("Comment obtenir l'identifiant unique d'une session ?", "(B) session_id()");
        responses.put("Quelle directive de httpd.conf interdit de lister un dossier ?", "(A) Options -indexes");
    }

    @FXML
    private void sendMessage() {
        String message = userInput.getText().trim();
        if (!message.isEmpty()) {
            // Afficher le message de l'utilisateur
            chatArea.appendText("Vous : " + message + "\n");
            // Obtenir la réponse du chatbot
            String response = getResponse(message);
            // Afficher la réponse du chatbot
            chatArea.appendText("ChatGPT : " + response + "\n");
            // Effacer le champ de saisie utilisateur
            userInput.clear();
        }
    }

    private String getResponse(String message) {
        // Vérifier si le message de l'utilisateur correspond à une réponse pré-enregistrée
        String response = responses.getOrDefault(message, "Désolé, je ne comprends pas.");
        return response;
    }
    @FXML
    void returnToAfficherQuestion(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTestFront.fxml"));
            Pane root = loader.load();
            nh.getChildren().setAll(root);

        } catch (IOException ex) {
            System.out.println("Erreur lors du chargement de la vue : " + ex.getMessage());
        }
    }
}


