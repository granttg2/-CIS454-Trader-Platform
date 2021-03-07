package org.jfx.Trader_Platform;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;

import java.io.IOException;

import org.backend.Trader_Platform.APIKeys;

public class APIPrompt {
	
	@FXML private Button buttonClose;
	@FXML private TextField apiEnterBoxMarket;
	
	@FXML
	public void closeButtonAction() {
		Platform.exit();
	}
	
	@FXML //Takes in the entered api key and tries to switch to the main screen
	public void getMarketKey(KeyEvent event) {
		if(event.getCode().equals(KeyCode.ENTER)) {
			App.setKeys(new APIKeys(apiEnterBoxMarket.getText()));
			
			try {
				App.setRoot("Main_Page");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
