package org.openjfx.Trader_Platform;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import org.backend.Trader_Platform.APIKeys;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static APIKeys keys;

    @Override
    public void start(Stage stage) throws IOException {
    	Parent root = loadFXML("API_Prompt");
    	this.stage = stage;
        scene = new Scene(root, 256, 303);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    //Takes the name of an fxml resource file and loads it into the program
    private static Parent loadFXML(String fxml) throws IOException{
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	return fxmlLoader.load();
    }
    
    //Changes the window shown to the user
    public static void setRoot(String fxml) throws IOException {
    	Stage newStage = new Stage();
    	newStage.initStyle(StageStyle.UTILITY);
    	newStage.setResizable(false);
    	newStage.setScene(new Scene(loadFXML(fxml)));
    	newStage.show();
    	stage.close();
    	stage = newStage;
    }
    
	public static APIKeys getKeys() {
		return keys;
	}

	public static void setKeys(APIKeys keys) {
		App.keys = keys;
	}
    
    public static void main(String[] args) {
        launch();
    }

}