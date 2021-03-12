package org.jfx.Trader_Platform;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import org.backend.Trader_Platform.APIKeys;
import org.backend.Trader_Platform.CoinParser;
import org.backend.Trader_Platform.TickerList;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static APIKeys keys;
    private static TickerList tickerList;

    @Override
    public void start(Stage stage) throws IOException {
    	Parent root = loadFXML("API_Prompt");
    	this.stage = stage;
        scene = new Scene(root, 256, 332);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Trader Platform");
        stage.show();
    }
    
    //Takes the name of an fxml resource file and loads it into the program
    private static Parent loadFXML(String fxml) throws IOException{
    	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    	return fxmlLoader.load();
    }
    
    private static void loadCoinTickers() {
    	tickerList = new TickerList();
    	CoinParser.parseCurrencyTickers(tickerList);
    }
    
    
    //Changes the window shown to the user
    public static void setRoot(String fxml) throws IOException {
    	Stage newStage = new Stage();
    	newStage.initStyle(StageStyle.UTILITY);
    	newStage.setResizable(false);
    	newStage.setScene(new Scene(loadFXML(fxml)));
    	newStage.setTitle("Trader Platform");
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
	
	public static TickerList getTickerList() {
		return tickerList;
	}

	public static void setTickerList(TickerList tickerList) {
		App.tickerList = tickerList;
	}
	
    public static void main(String[] args) {
    	loadCoinTickers();
    	//for(String s: tickerList.getTickerList()) {System.out.println(s);}
        launch();
    }

}