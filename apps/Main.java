package apps;

import java.sql.Connection;


import java.sql.SQLException;

import java.util.ArrayList;

import apps.RegisterPage;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import connect.Connect;
import utils.ListCurrent;
import utils.ListHoodie;
import utils.User;

public class Main extends Application{
	
	Stage stage;
	Scene scene;
	GridPane gp;
	BorderPane bp;
	
	MenuBar mb;
	Menu mLogin;
	MenuItem miRegist;
	
	Label login, usn, pass;
	TextField tfUsn;
	PasswordField pf;
	Button loginbtn;
	
	Connect connect = Connect.getInstance();
	ArrayList<String> usernameList = new ArrayList<>();
	ArrayList<String> passwordList = new ArrayList<>();
	private ListCurrent Lis;
	private String loggedInUserID;

	
	private void initialized() {
		Font loginFont = Font.font("verdana",FontWeight.BOLD, FontPosture.ITALIC, 40);
		
		login = new Label("Login");
		login.setFont(loginFont);
		
		usn = new Label("Username: ");
		pass = new Label("Password: ");
		loginbtn = new Button("Login");
		loginbtn.setMinWidth(250);
		
		tfUsn = new TextField();
		tfUsn.setPromptText("Input usssername");
		pf = new PasswordField();
		pf.setPromptText("Input pasword");
		
		mb = new MenuBar();
		mLogin = new Menu("Login");
		miRegist = new MenuItem("Register");
		mLogin.getItems().add(miRegist);
		
		
		bp = new BorderPane();
		gp = new GridPane();
		
		scene = new Scene(bp, 700, 750);
	}
	
	private void keyHandling() {
	    loginbtn.setOnAction(e -> {
	        String enteredUsername = tfUsn.getText();
	        String enteredPassword = pf.getText();

	        if (enteredUsername.equals("admin") && enteredPassword.equals("admin")) {
	            // Handle admin login
	            new HomeAdmin(stage);
	        } else {
	            // Check if the entered credentials match any records in the database
	            boolean isValidCredentials = checkCredentials(enteredUsername, enteredPassword);

	            if (isValidCredentials) {
	                ListCurrent listCurrent = new ListCurrent(loggedInUserID, enteredUsername);
	                new HomeUser(stage, listCurrent, loggedInUserID);
	            } else {
	                alert("Error", "Error", "Wrong Credentials");
	            }
	        }
	    });

	    miRegist.setOnAction(E -> {
	        new RegisterPage(stage);
	    });
	}
	
	private boolean checkCredentials(String enteredUsername, String enteredPassword) {
	    String query = "SELECT * FROM user WHERE Username = ? AND Password = ?";
	    connect.execPreparedStatement(query, enteredUsername, enteredPassword);

	    try {
	        if (connect.rs.next()) {
	            loggedInUserID = connect.rs.getString("UserID");

	            if (enteredUsername.equals(connect.rs.getString("Username"))) {
	                System.out.println("Logged-in User ID: " + loggedInUserID);
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        connect.closeResultSet();
	    }

	    return false;
	}

	
	private void alert(String setTitileA, String setHeaderA, String setContentA) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(setContentA);
		alert.setHeaderText(setHeaderA);
		alert.setContentText(setContentA);
		alert.show();	
	}

	private void addComponent() {
		
		gp.add(login, 1, 0);
		gp.add(usn, 0, 1);
		gp.add(pass, 0, 2);
		gp.add(tfUsn, 1, 1);
		gp.add(pf, 1, 2);
		gp.add(loginbtn, 0, 3, 2, 1);
		
		bp.setCenter(gp);
		
		gp.setVgap(10);
		gp.setHgap(10);
		
		mb.getMenus().add(mLogin);			
	}
	
	private void styling() {
		gp.setAlignment(Pos.CENTER); 
		bp.setTop(mb); 
		
		BorderPane.setAlignment(gp, Pos.CENTER); 
	        
		gp.setVgap(10);
		gp.setHgap(10);
	}
	
	private void printDataHoodie() {
		String query = "SELECT * FROM user";
		connect.execSelect(query);
		
		try {
			while(connect.rs.next()) {
				String UserID = connect.rs.getString("UserID");
				String usernameDb = connect.rs.getString("Username");
				String passwordDb = connect.rs.getString("Password");
				
				usernameList.add(usernameDb);
				passwordList.add(passwordDb);
				
				System.out.println(usernameList);
				System.out.println(passwordList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		initialized();
		keyHandling();
		addComponent();
		styling();
		printDataHoodie();
		primaryStage.setTitle("hO-Ohdie");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

}
