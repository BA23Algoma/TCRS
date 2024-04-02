package userInterface;

import java.util.Stack;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;


public class SceneNavigation extends VisualDesign {


	protected Stack<CreateScene> sceneStack = new Stack<>();
	
	public Stack<CreateScene> getSceneStack() {
        return sceneStack;
    }
	
	class CreateScene {
			
		String methodName;
	    String parameter1;
	    String parameter2;

	    public CreateScene(String methodName) {
	    	this.methodName = methodName;
	    }
	    public CreateScene(String methodName, String parameter1) {
	        this.methodName = methodName;
	        this.parameter1 = parameter1;
	    }

	    public CreateScene (String methodName, String parameter1, String parameter2) {
	        this.methodName = methodName;
	        this.parameter1 = parameter1;
	        this.parameter2 = parameter2;
	    }

	}

	public Scene createLoginScene() {
	
	styleVisualComponents();
	pfPassword.clear();
	tfUsernameLogin.clear();
	cbAgencyLogin.setValue(null);
  
	VBox loginTop = new VBox (lbTrafficWatch, lbAppDescription, cbAgencyLogin, tfUsernameLogin, pfPassword);	
	loginTop.setAlignment(Pos.CENTER);
	loginTop.setSpacing(30);
	loginTop.setPadding(new Insets(50));
	
	
	VBox loginBottom = new VBox (btLogin, lbLoginError);
	loginBottom.setAlignment(Pos.CENTER);
	loginBottom.setSpacing(30);
	loginBottom.setPadding(new Insets(20));
	
	VBox login = new VBox (loginTop, loginBottom);
	
	HBox exit = new HBox (btExit);
	exit.setAlignment(Pos.CENTER_RIGHT);
	exit.setPadding(new Insets(50));
	
	BorderPane pane = new BorderPane();
	pane.setStyle("-fx-background-color: white;");
	pane.setCenter(login);
	pane.setTop(exit);
	pane.setBottom(bottomRectangle);
	
	bottomRectangle.widthProperty().bind(pane.widthProperty());
	Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
	 Scene scene = new Scene (pane,bounds.getWidth(), bounds.getHeight());
	 scene.getStylesheets().add(getClass().getResource("/Resources/Styles.css").toExternalForm());
	 sceneStack.push(new CreateScene("CreateLoginScene"));
	    
	 return scene;
}

	public Scene createOptionScene(String centerContent) {
	
	styleVisualComponents();
	setAllFieldsEditable();
	
	HBox backLogoutHBox = new HBox(btBack, btLogout);
	backLogoutHBox.setSpacing(40);
	backLogoutHBox.setAlignment(Pos.CENTER_RIGHT);
	backLogoutHBox.setPadding(new Insets(50));

    VBox centerContentVBox=null;

    switch (centerContent) {
        case "Manage/Report Provincial":
            centerContentVBox = new VBox(lbChoose, btManageRecordsProvincial, btGenerateReportProvincial);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(100));
            centerContentVBox.setPadding(new Insets(100, 0, 300, 0));
            
            break;
        case "Manage/Report Municipal":
            centerContentVBox = new VBox(lbChoose, btManageRecordsMunicipal, btGenerateReportMunicipal);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(100));
            centerContentVBox.setPadding(new Insets(100, 0, 300, 0));
           
            break;

        case "Vehicle/Driver":
            centerContentVBox = new VBox(lbManage, btVehicleRecords, btDriverRecords);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbManage.setPadding(new Insets(100));
            centerContentVBox.setPadding(new Insets(100, 0, 300, 0));
            break;

        case "Vehicle/Driver Citations":
            centerContentVBox = new VBox(lbManage, btVehicleCitations, btDriverCitations);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbManage.setPadding(new Insets(100));
            centerContentVBox.setPadding(new Insets(100, 0, 300, 0));
            break;

        case "Vehicle/Driver Warrants":
            centerContentVBox = new VBox(lbManage, btVehicleWarrants, btDriverWarrants);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbManage.setPadding(new Insets(100));
            centerContentVBox.setPadding(new Insets(100, 0, 300, 0));
            break;

        case "Citation Summary/Outstanding Warrants":
            centerContentVBox = new VBox(lbReport, btCitationSummary, btOutstandingWarrants);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbReport.setPadding(new Insets(100));
            centerContentVBox.setPadding(new Insets(100, 0, 300, 0));
            break;
              			                                        
        case "Enter/Edit/Delete Vehicle Record":
            centerContentVBox = new VBox(lbChoose, btEnterVehRecord, btViewEditVehRecord, btDeleteVehRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Driver Record":
            centerContentVBox = new VBox(lbChoose, btEnterDriRecord, btViewEditDriRecord, btDeleteDriRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Officer Record":
            centerContentVBox = new VBox(lbChoose, btEnterOffRecord, btViewEditOffRecord, btDeleteOffRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Vehicle Citation Record":
            centerContentVBox = new VBox(lbChoose, btEnterVehCitRecord, btViewEditVehCitRecord, btDeleteVehCitRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;           
        case "Enter/Edit/Delete Driver Citation Record":
            centerContentVBox = new VBox(lbChoose, btEnterDriCitRecord, btViewEditDriCitRecord, btDeleteDriCitRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Vehicle Warrant Record":
            centerContentVBox = new VBox(lbChoose, btEnterVehWarRecord, btViewEditVehWarRecord, btDeleteVehWarRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Driver Warrant Record":
            centerContentVBox = new VBox(lbChoose, btEnterDriWarRecord, btViewEditDriWarRecord, btDeleteDriWarRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Traffic School Record":
            centerContentVBox = new VBox(lbChoose, btEnterTrafficSchoolRecord, btViewEditTrafficSchoolRecord, btDeleteTrafficSchoolRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
        case "Enter/Edit/Delete Account":
            centerContentVBox = new VBox(lbChoose, btEnterAccount, btViewEditAccount, btDeleteAccount);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbChoose.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;

        case "Vehicle/Driver/Driving Record Report":
            centerContentVBox = new VBox(lbReport, btVehicleInfo, btDriverInfo, btDrivingRecord);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbReport.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;

        case "Citations/Warrants/Officers/Traffic School":
            centerContentVBox = new VBox(lbManage, btCitations, btWarrants, btOfficers, btTrafficSchool);
            centerContentVBox.setAlignment(Pos.CENTER);
            centerContentVBox.setSpacing(30);
            lbManage.setPadding(new Insets(80));
            centerContentVBox.setPadding(new Insets(50, 0, 400, 0));
            break;
    }

    BorderPane pane = new BorderPane();
    pane.setStyle("-fx-background-color: white;");
    pane.setTop(backLogoutHBox);
    pane.setCenter(centerContentVBox);
    
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
	 Scene scene = new Scene (pane, bounds.getWidth(), bounds.getHeight());
	 scene.getStylesheets().add(getClass().getResource("/Resources/Styles.css").toExternalForm());
    sceneStack.push(new CreateScene("CreateOptionScene", centerContent));
    
    return scene;
}

	public Scene createSearchScene(String centerContent, String toEditOrDelete) {
	
	
	styleVisualComponents();
	setAllFieldsEditable();
	reverseAutofill();
	
	HBox backLogoutHBox = new HBox(btBack, btLogout);
	backLogoutHBox.setSpacing(40);
	backLogoutHBox.setAlignment(Pos.CENTER_RIGHT);
	backLogoutHBox.setPadding(new Insets(50));
	
	StackPane feedbackStackSearch = new StackPane(lbEmptyFields, lbWrongFormat, lbNoRecord);
	
	

	VBox centerContentVBox=null;

	switch (centerContent) {
		case ("Enter License Driver"):
			centerContentVBox = new VBox(lbEnterLic, tfLic, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;
        
		case ("Enter License Driver Report"):
			centerContentVBox = new VBox(lbEnterLic, tfLic, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;
        
		case ("Enter VIN Vehicle"):
			centerContentVBox = new VBox(lbEnterVin, tfVin, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        
        break;
        
		case ("Enter VIN Vehicle Report"):
			centerContentVBox = new VBox(lbEnterVin, tfVin, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        
        break;
        
		case ("Enter Warrant ID Vehicle"):
			centerContentVBox = new VBox(lbEnterWarID, tfEnterWarID, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;
        
		case ("Enter Warrant ID Driver"):
			centerContentVBox = new VBox(lbEnterWarID, tfEnterWarID, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;
        
		case ("Enter Citation ID Vehicle"):
			centerContentVBox = new VBox(lbEnterCitID, tfEnterCitID, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;
        
		case ("Enter Citation ID Driver"):
			centerContentVBox = new VBox(lbEnterCitID, tfEnterCitID, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;
        
		case ("Enter Citation ID Traffic School"):
			centerContentVBox = new VBox(lbEnterCitID, tfEnterCitID, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
        	
        break;        
        
		case ("Enter Account ID"):
			centerContentVBox = new VBox(lbEnterAcc, tfEnterAcc, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
      
        break;
        
		case ("Enter Badge"):
			centerContentVBox = new VBox(lbEnterBadge, tfBadge, btSearch, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setSpacing(60);
        	centerContentVBox.setPadding(new Insets(150, 0, 300, 0));
       
        break;
        
		case ("Enter Driving Record"):
			VBox btVBox = new VBox (btSearch);
			btVBox.setAlignment(Pos.CENTER);
			centerContentVBox = new VBox(lbEnterLic, tfLic, lbStartDate, tfStartDate, lbEndDate, tfEndDate, btVBox, feedbackStackSearch);
        	centerContentVBox.setAlignment(Pos.CENTER);
        	centerContentVBox.setPadding(new Insets(0, 0, 200, 0));
        	lbEnterLic.getStyleClass().remove("big-label");
        	lbEnterLic.getStyleClass().add("medium-label");
        	lbEnterLic.setPadding(new Insets(60,0,30,0));
        	lbStartDate.setPadding(new Insets(60,0,30,0));
        	lbEndDate.setPadding(new Insets(60,0,30,0));
        	btVBox.setPadding(new Insets(60,0,0,0));
        	feedbackStackSearch.setPadding(new Insets(60,0,30,0));
       
        break;  
	    }
	
	BorderPane pane = new BorderPane();
	pane.setStyle("-fx-background-color: white;");
	pane.setTop(backLogoutHBox);
	pane.setCenter(centerContentVBox);
	
	Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
	 Scene scene = new Scene (pane,bounds.getWidth(), bounds.getHeight());
	 scene.getStylesheets().add(getClass().getResource("/Resources/Styles.css").toExternalForm());
	    sceneStack.push(new CreateScene("CreateSearchScene", centerContent, toEditOrDelete));
	    
	    return scene;
	}
	public Scene createDataScene(String prompt, String fields) {
	
	styleVisualComponents();
	setAllFieldsEditable();
	reverseAutofill();
	
	HBox backLogoutHBox = new HBox(btBack, btLogout);
	backLogoutHBox.setSpacing(40);
	backLogoutHBox.setAlignment(Pos.CENTER_RIGHT);
	backLogoutHBox.setPadding(new Insets(50));
	
	HBox submitClearHBox = new HBox(btSubmit, btClear);
	submitClearHBox.setSpacing(40);
	submitClearHBox.setAlignment(Pos.CENTER_LEFT);
	
	HBox deleteHBox = new HBox(btDelete);
	
	
	StackPane feedbackStackSubmit = new StackPane(lbEmptyFields, lbWrongFormat, lbSuccessText);
	
	feedbackStackSubmit.setAlignment(Pos.CENTER_RIGHT);


	
	HBox submitClearFeedbackHBox = new HBox(submitClearHBox, feedbackStackSubmit);
	submitClearFeedbackHBox.setPadding(new Insets(50));
	submitClearFeedbackHBox.setSpacing(40);
	

	VBox promptVBox=null;
	VBox labelsVBox=null;
	VBox fieldsVBox=null;	
	HBox bottomHBox = null;
	
	switch (prompt) {
	
		case("Enter"):
			promptVBox = new VBox(lbEnter);
			promptVBox.setPadding(new Insets(0,0,0,50));
			bottomHBox = submitClearFeedbackHBox;
			setPrimaryKeyFieldsUneditable();
			break;
		case("View/Edit"):
			setPrimaryKeyFieldsUneditable();
			promptVBox = new VBox(lbEdit);
			promptVBox.setPadding(new Insets(0,0,0,50));
			bottomHBox = submitClearFeedbackHBox;
			break;
		case("Filter"):
			promptVBox = new VBox(lbFilter);
			promptVBox.setPadding(new Insets(0,0,0,50));
			bottomHBox = submitClearFeedbackHBox;
			break;
		case("Delete"):
			setAllFieldsUneditable();
			promptVBox = new VBox(lbDelete);
			promptVBox.setPadding(new Insets(0,0,0,50));
			StackPane feedbackStackDelete = new StackPane(lbSuccessText);
			feedbackStackDelete.setAlignment(Pos.CENTER_RIGHT);
			HBox deleteFeedbackHBox = new HBox(deleteHBox, feedbackStackDelete);
			deleteFeedbackHBox.setPadding(new Insets(50));
			bottomHBox = deleteFeedbackHBox;
			break;
	}
	
	switch (fields) {
		case("Vehicle Info"):
			
			labelsVBox = new VBox(lbVin, lbPlate, lbMake, lbModel, lbYear, lbRegStat);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfVin, tfPlate, tfMake, tfModel, tfYear, cbRegStat);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			
			break;
			
		case("Driver Info"):
			
			labelsVBox = new VBox(lbFirst, lbLast, lbLic, lbPlate, lbPoints, lbLicStat);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfFirst, tfLast, tfLic, tfPlate, tfPoints, cbLicStat);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			
			break;
			
		case("Officer Info"):
			labelsVBox = new VBox(lbBadge, lbLast, lbFirst);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfBadge, tfFirst, tfLast);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;
		case("Account Info"):
			labelsVBox = new VBox(lbAccID, lbAgency, lbFirst, lbLast, lbUsername, lbPassword);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfAccID, cbAgency, tfFirst, tfLast, tfUsername, tfPassword);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;
			
		case("Vehicle Citation Info"):
			labelsVBox = new VBox(lbCitID, lbDate, lbVin, lbPlate, lbReason, lbFine, lbPaid, lbIssuingOff);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfCitID, tfDate, tfVin, tfPlate, cbReasonVeh, tfFine, cbPaid, tfIssuingOff);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			
			break;
			
		case("Driver Citation Info"):
			labelsVBox = new VBox(lbCitID, lbDate, lbLic,  lbReason, lbFine, lbPaid, lbIssuingOff, lbReportable);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfCitID, tfDate, tfLic,  cbReasonDriv, tfFine, cbPaid, tfIssuingOff, cbReportable);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;
		case("Vehicle Warrant Info"):
			labelsVBox = new VBox(lbWarID, lbDate, lbVin,  lbPlate, lbReason, lbOutstanding);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfWarID, tfDate, tfVin,  tfPlate, tfReason, cbOutstanding);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;
		case("Driver Warrant Info"):
			labelsVBox = new VBox(lbWarID, lbDate, lbLic,  lbFirst, lbLast, lbReason, lbOutstanding);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfWarID, tfDate, tfLic,  tfFirst, tfLast, tfReason, cbOutstanding);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;
			
		case("Traffic School Info"):
			labelsVBox = new VBox(lbCitID, lbSess1, lbSess2, lbSess3, lbSess4, lbSess1Att, lbSess2Att, lbSess3Att, lbSess4Att);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfEnterCitID, tfSess1, tfSess2, tfSess3, tfSess4, cbSessAtt1, cbSessAtt2, cbSessAtt3, cbSessAtt4);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;
		
		case("Citation Summary"):
			lbStartDate.setText("Start Date (DD/MM/YYYY):");
			lbEndDate.setText("End Date (DD/MM/YYYY):");
			labelsVBox = new VBox(lbIssuingOff, lbStartDate, lbEndDate, lbReason, lbPaid);
			labelsVBox.setSpacing(60);
			labelsVBox.setAlignment(Pos.TOP_RIGHT);
			labelsVBox.setPadding(new Insets(20));
			
			fieldsVBox = new VBox(tfIssuingOff,  tfStartDate, tfEndDate, cbReasonDrivVeh, cbPaid);
			fieldsVBox.setSpacing(30);
			fieldsVBox.setAlignment(Pos.TOP_LEFT);
			break;			
	}
	
	HBox labelsAndFieldsHBox=new HBox(labelsVBox, fieldsVBox);
	labelsAndFieldsHBox.setSpacing(30);
	labelsAndFieldsHBox.setPadding(new Insets(50, 200, 0, 0));
	
	BorderPane pane = new BorderPane();
	pane.setStyle("-fx-background-color: white;");
	pane.setTop(backLogoutHBox);
	pane.setLeft(promptVBox);
	pane.setRight(labelsAndFieldsHBox);
	pane.setBottom(bottomHBox);
	
	Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
	 Scene scene = new Scene (pane,bounds.getWidth(), bounds.getHeight());
	 scene.getStylesheets().add(getClass().getResource("/Resources/Styles.css").toExternalForm());
	    sceneStack.push(new CreateScene("CreateDataScene", prompt, fields));
	    return scene;
}

	public Scene createReportScene() {
		
	reverseAutofill();
	styleVisualComponents();
	setAllFieldsEditable();
	Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds(); 
	HBox backLogoutHBox = new HBox(btBack, btLogout);
	backLogoutHBox.setSpacing(40);
	backLogoutHBox.setAlignment(Pos.CENTER_RIGHT);
	backLogoutHBox.setPadding(new Insets(50,50,5,50));
	
	ScrollPane reportScroll = new ScrollPane(taReport);
	reportScroll.setStyle("-fx-background-color: white;");
	reportScroll.setFitToWidth(true);
    reportScroll.setFitToHeight(true);
    reportScroll.setPrefViewportWidth(bounds.getWidth()); 
    reportScroll.setPrefViewportHeight(bounds.getHeight());
    reportScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
	reportScroll.setPadding(new Insets(20));
	BorderPane pane = new BorderPane();
	pane.setStyle("-fx-background-color: white;");
	pane.setTop(backLogoutHBox);
	pane.setCenter(reportScroll);
	
	
	 Scene scene = new Scene (pane,bounds.getWidth(), bounds.getHeight());
	 scene.getStylesheets().add(getClass().getResource("/Resources/Styles.css").toExternalForm());
	    sceneStack.push(new CreateScene("CreateReportScene"));
	    return scene;
	
}
	
}

	
