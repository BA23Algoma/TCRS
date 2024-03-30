package application;

import userInterface.*;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start (Stage primaryStage) {
		
		
		UserInteraction userInteraction = new UserInteraction(primaryStage);
		userInteraction.setButtonActions();

		
		
		primaryStage.setTitle("TrafficWatch");	
		primaryStage.setScene(userInteraction.createLoginScene());
	
		primaryStage.show();	
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
}
