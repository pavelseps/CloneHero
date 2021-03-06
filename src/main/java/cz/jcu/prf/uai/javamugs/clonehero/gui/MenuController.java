package cz.jcu.prf.uai.javamugs.clonehero.gui;

import cz.jcu.prf.uai.javamugs.clonehero.logic.Game;
import cz.jcu.prf.uai.javamugs.clonehero.logic.Parser;
import cz.jcu.prf.uai.javamugs.clonehero.logic.PressChart;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class MenuController {
    private Stage stage;
    private EditorController editorController;
    private GameController gameController;
    public Label difficultyLabel;
    public Slider speedSlider;
    public Slider difficultySlider;
    public Button exitButton;
    public FileChooser fileChooser = new FileChooser();
    public BorderPane rootContainer;

    /**
     * Method to be called on start, after initialization
     */
    public void start() {
        rootContainer.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("/splash.jpg").toString()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null)));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        this.stage = (Stage) rootContainer.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });
        difficultyLabel.textProperty().bind(
                Bindings.format("%.0f", difficultySlider.valueProperty())
        );
    }

    /**
     * Method to be called on play button click
     * @param event click event
     */
    public void playButtonAction(ActionEvent event) {
        fileChooser.setTitle("Select song");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
        File songFile = fileChooser.showOpenDialog(stage);
        if (songFile == null) {
            return;
        }
        String songURIstring = songFile.toURI().toString();

        fileChooser.setTitle("Select PressChart file");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PressChart file", "*.prc"));
        File pressChartFile = fileChooser.showOpenDialog(stage);
        if (pressChartFile == null) {
            return;
        }
        String pressChartPath = pressChartFile.getAbsolutePath();

        Parser parser = new Parser();
        int timeOffset = (int) speedSlider.getValue();

        PressChart pressChart;
        try {
            pressChart = parser.parseFile(pressChartPath, timeOffset);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Clone Hero");
            alert.setHeaderText("Error while parsing the file!");
            alert.setContentText(ex.getMessage());
            alert.show();
            return;
        }

        int difficulty = (int) difficultySlider.getValue();

        Game game = new Game(timeOffset, (byte) difficulty, pressChart);

        openGameWindow(game, songURIstring); //TODO put method under logic
    }

    /**
     * Creates Game window
     * @param game game core
     * @param songURIstring path to the song
     */
    private void openGameWindow(Game game, String songURIstring) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Game.fxml"));
            Parent root = loader.load();
            gameController = (GameController) loader.getController();
            gameController.setGame(game);
            gameController.setSongURIstring(songURIstring);
            Stage gameStage = new Stage();
            gameStage.setTitle("Clone Hero");
            gameStage.setScene(new Scene(root));
            gameStage.setResizable(false);
            gameStage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
            gameStage.show();
            gameController.start();
            //((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to be called on editor button click
     * @param event click event
     */
    public void editorButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Editor.fxml"));
            Parent root = loader.load();
            editorController = (EditorController) loader.getController();
            fileChooser.setTitle("Select song");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
            File songFile = fileChooser.showOpenDialog(stage);
            if (songFile == null) {
                return;
            }
            Stage editorStage = new Stage();
            editorStage.setTitle("Clone Hero Editor");
            editorStage.setScene(new Scene(root));
            editorStage.setResizable(false);
            editorStage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
            editorStage.show();
            editorController.setSongPath(songFile.toURI().toString());

            editorController.start();
            //((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to be called on exit button click
     * @param event click event
     */
    public void exitButtonAction(ActionEvent event) {
        stage.close();
    }
}

