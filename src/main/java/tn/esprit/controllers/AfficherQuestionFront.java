package tn.esprit.controllers;
import tn.esprit.models.Question;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Future;

public class AfficherQuestionFront implements Initializable {

    @FXML
    private AnchorPane nh;

    @FXML
    private VBox vbox1;

    @FXML
    private Button submitButton;

    @FXML
    private TextField textInput;

    private List<Question> questions;
    private List<Pane> questionCards = new ArrayList<>();
    private ToggleGroup toggleGroup;

    private SpeechSynthesizer synthesizer;
    private AudioConfig audioConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the display of questions
        afficherQuestions();

        // Initialize SpeechSynthesizer for text-to-speech
        audioConfig = AudioConfig.fromDefaultSpeakerOutput();
        SpeechConfig speechConfig = SpeechConfig.fromSubscription("9060f63ba0654e7b8533abfa8e407a6e", "westeurope");
        synthesizer = new SpeechSynthesizer(speechConfig);
    }

    private void afficherQuestions() {
        vbox1.getChildren().clear();

        toggleGroup = new ToggleGroup();

        if (questions != null && !questions.isEmpty()) {
            vbox1.setSpacing(50.0);
            vbox1.setPadding(new Insets(30.0));

            int counter = 1;
            for (Question question : questions) {
                Pane card = createQuestionCard(question, counter);
                questionCards.add(card);
                vbox1.getChildren().add(card);

                counter++;
            }

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(event -> handleSubmit());
            vbox1.getChildren().add(submitButton);
        }
    }

    private Pane createQuestionCard(Question question, int counter) {
        Pane card = new Pane();
        card.setPrefHeight(200.0);
        card.setMinHeight(200.0);
        card.setPrefWidth(250.0);
        card.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        Label counterLabel = new Label("Question " + counter + ": ");
        counterLabel.setLayoutX(10.0);
        counterLabel.setLayoutY(30.0);
        counterLabel.setFont(new Font(14.0));

        Label contentLabel = new Label(question.getContent());
        contentLabel.setLayoutX(10.0);
        contentLabel.setLayoutY(60.0);
        contentLabel.setFont(new Font(14.0));

        RadioButton choice1Radio = new RadioButton(question.getChoice1());
        choice1Radio.setLayoutX(10.0);
        choice1Radio.setLayoutY(90.0);
        choice1Radio.setFont(new Font(14.0));

        RadioButton choice2Radio = new RadioButton(question.getChoice2());
        choice2Radio.setLayoutX(10.0);
        choice2Radio.setLayoutY(120.0);
        choice2Radio.setFont(new Font(14.0));

        RadioButton choice3Radio = new RadioButton(question.getChoice3());
        choice3Radio.setLayoutX(10.0);
        choice3Radio.setLayoutY(150.0);
        choice3Radio.setFont(new Font(14.0));

        toggleGroup = new ToggleGroup();
        choice1Radio.setToggleGroup(toggleGroup);
        choice2Radio.setToggleGroup(toggleGroup);
        choice3Radio.setToggleGroup(toggleGroup);

        Button listenButton = new Button("Listen");
        listenButton.setLayoutX(200.0);
        listenButton.setLayoutY(30.0);
        listenButton.setOnAction(event -> readQuestion(question));

        card.getChildren().addAll(counterLabel, contentLabel, choice1Radio, choice2Radio, choice3Radio, listenButton);

        return card;
    }

    private void readQuestion(Question question) {
        String textToSpeak = "Question : " + question.getContent() + ". Choices : " + question.getChoice1() + ", " + question.getChoice2() + ", " + question.getChoice3();
        // Call text-to-speech method here
        try {
            synthesizer.SpeakTextAsync(textToSpeak);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleSubmit() {
        int totalQuestions = questions.size();
        int totalCorrectAnswers = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);
            Pane card = questionCards.get(i);
            RadioButton selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();

            if (selectedRadio == null) {
                continue;
            }

            String selectedAnswer = selectedRadio.getText();
            String correctAnswer = question.getCorrectchoice();

            if (selectedAnswer.equals(correctAnswer)) {
                highlightAnswer(card, selectedRadio, true);
                totalCorrectAnswers++;
            } else {
                RadioButton correctRadio = findRadioButton(card, correctAnswer);
                highlightAnswer(card, selectedRadio, false);
                highlightAnswer(card, correctRadio, true);
            }
        }

        double percentageCorrect = ((double) totalCorrectAnswers / totalQuestions) * 100;
        String formattedPercentage = String.format("%.2f", percentageCorrect);

        Label percentageLabel = new Label("Percentage of correct answers: " + formattedPercentage + "%");
        vbox1.getChildren().add(percentageLabel);
    }

    private void highlightAnswer(Pane card, RadioButton radioButton, boolean isCorrect) {
        Color color = isCorrect ? Color.GREEN : Color.RED;
        radioButton.setTextFill(color);
    }

    private RadioButton findRadioButton(Pane card, String answer) {
        for (javafx.scene.Node node : card.getChildren()) {
            if (node instanceof RadioButton && ((RadioButton) node).getText().equals(answer)) {
                return (RadioButton) node;
            }
        }
        return null;
    }

    @FXML
    private Button retour;

    @FXML
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/erpinterns/java_erpinterns/AfficherTestFront.fxml"));

            Pane root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Error loading view: " + ex.getMessage());
        }
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        afficherQuestions();
    }

    @FXML
    private void chatgpt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/erpinterns/java_erpinterns/chatgpt.fxml"));
            Pane root = loader.load();
            nh.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("Error loading view: " + ex.getMessage());
        }
    }

    @FXML
    private void convertToSpeech(ActionEvent event) {
        String textToSpeak = textInput.getText();
        // Call text-to-speech method here
        try {
            synthesizer.SpeakTextAsync(textToSpeak);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void stt(ActionEvent event) {
        Task<Void> recognitionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                SpeechConfig speechConfig = SpeechConfig.fromSubscription("9060f63ba0654e7b8533abfa8e407a6e", "westeurope");

                try (SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, AudioConfig.fromDefaultMicrophoneInput())) {
                    System.out.println("Speak into your microphone.");

                    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
                    SpeechRecognitionResult result = task.get();

                    if (result.getReason() == ResultReason.Canceled) {
                        System.out.println("Cancellation detected.");
                    } else if (result.getReason() == ResultReason.NoMatch) {
                        System.out.println("No speech recognized.");
                    } else {
                        String recognizedText = result.getText();
                        System.out.println("Recognized text: " + recognizedText);
                        // Update UI element with recognized text if needed
                    }
                }
                return null;
            }
        };

        recognitionTask.setOnFailed(event1 -> {
            Throwable exception = recognitionTask.getException();
            System.err.println("Speech recognition failed: " + exception.getMessage());
        });

        new Thread(recognitionTask).start();
    }
}
