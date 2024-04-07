package application;

import userInterface.*;
import databaseManagement.DatabaseManager;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
	
	private UserInteraction userInteraction;
	
	@Override
	public void start (Stage primaryStage) {
		
		
		userInteraction = new UserInteraction(primaryStage);
		userInteraction.setButtonActions();
		
		
		
		
		primaryStage.setScene(userInteraction.createLoginScene());
		
		
		primaryStage.show();	
		
		
		
	}
	
	@Override
	public void stop () {
		userInteraction.disconnectFromDatabase();
		userInteraction.getSceneStack().clear();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
