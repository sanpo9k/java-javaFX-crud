package apps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connect.Connect;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import utils.User;


public class RegisterPage {
 Stage stage;
 Scene sc;
 BorderPane bp;
 GridPane gp;
 
 Label lblRegist, lblemail, lblusername, lblpassword, lblconfirmPass, lblphoneNum, lblgender, lbladdress;
 TextField tfEmail, tfUsername, tfphoneNum;
 PasswordField pfpassword, pfconfirmPass;
 RadioButton male, female;
 TextArea taAddress;
 CheckBox termCondi;
 Button registbtn;
 ToggleGroup toggle;
 
 MenuBar mb;
 Menu mRegist;
 MenuItem miLogin;

 private ArrayList<User> userList = new ArrayList<>();
 private int nextUserIndex = 1;
 
 private void initalized() {
  Font registFont = Font.font("verdana",FontWeight.BOLD, FontPosture.ITALIC, 40);
  
  lblRegist = new Label("Register");
  lblRegist.setFont(registFont);
  gp.add(lblRegist, 0, 0);
  
  lblemail = new Label("Email");
  gp.add(lblemail, 0, 1);
  lblemail.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
  
  tfEmail = new TextField();
  tfEmail.setPromptText("Input an email");
  gp.add(tfEmail, 0, 2, 3, 1);
  
  
  lblusername = new Label("Username");
  gp.add(lblusername, 0, 3);
  lblusername.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
  
  tfUsername = new TextField();
  tfUsername.setPromptText("Input an unique username");
  gp.add(tfUsername, 0, 4, 3, 1);
  
  
  lblpassword = new Label("Password");
  gp.add(lblpassword, 0, 5);
  lblpassword.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
  
  pfpassword = new PasswordField();
  pfpassword.setPromptText("Input password");
  gp.add(pfpassword, 0, 6, 3, 1);

  
  lblconfirmPass = new Label("Confirm Password");
  gp.add(lblconfirmPass, 0, 7);
  lblconfirmPass.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

  pfconfirmPass = new PasswordField();
  pfconfirmPass.setPromptText("Confirm password");
  gp.add(pfconfirmPass, 0, 8, 3, 1);
  
  
  lblphoneNum = new Label("Phone Number");
  gp.add(lblphoneNum, 0, 9);
  lblphoneNum.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

  tfphoneNum = new TextField();
  tfphoneNum.setPromptText("Example: +62123456789901");
  gp.add(tfphoneNum, 0, 10, 3, 1);

  
  lblgender = new Label("Gender");
  gp.add(lblgender, 0, 11);
  lblgender.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

  
  male = new RadioButton("Male");
  gp.add(male, 0, 12);
  female = new RadioButton("Female");
  gp.add(female, 1, 12);
  
  toggle = new ToggleGroup();
  male.setToggleGroup(toggle);
  female.setToggleGroup(toggle);

  
  lbladdress = new Label("Address");
  gp.add(lbladdress, 0, 13);
  lbladdress.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");
  
  taAddress = new TextArea();
  taAddress.setPromptText("Input Address");
  gp.add(taAddress, 0, 14, 3, 1);

  
  termCondi = new CheckBox("I Agree to Term & Conditions");
  gp.add(termCondi, 0, 15);
  
  registbtn = new Button("Register");
  gp.add(registbtn, 2, 16);
  registbtn.setMinWidth(175);
  
  mb = new MenuBar();
  mRegist = new Menu("Register");
  miLogin = new MenuItem("Login");
  mRegist.getItems().add(miLogin);
  
  mb.getMenus().add(mRegist);
  
  
  bp.setCenter(gp);
  

  
  //gp.setGridLinesVisible(true);

 }
 
 private void keyHandling() {
  miLogin.setOnAction(e -> {
   try {
    new Main().start(stage);
   } catch (Exception e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
   }
  });
  
  registbtn.setOnAction(e -> {
   String email = tfEmail.getText();
   String username = tfUsername.getText();
   String password = pfpassword.getText();
   String confirmPass = pfconfirmPass.getText();
   String phoneNum = tfphoneNum.getText();
   String gender = "";
   if (male.isSelected()) {
    gender = "Male";
   } else if (female.isSelected()) {
    gender = "Female";
   }
   String address = taAddress.getText();
   boolean termsChecked = termCondi.isSelected();
   
   
   if (!email.endsWith("@hoohdie.com")) {
    alert("Error", "Error", "Email must end with '@hoohdie.com'");
   } else if (usernameAlreadyExists(username)) {
    alert("Error", "Error", "Username must be unique");
   } else if (password.length() < 5) {
    alert("Error", "Error", "Password must contain minimal 5 characters");
   } else if (!confirmPass.equals(password)) {
    alert("Error", "Error", "Confirm Password must be the same as Password.");
   } else if (phoneNum.length() != 14 && !phoneNum.startsWith("+62")) {
    alert("Error", "Error", "Phone number must be 14 characters and start with '+62'");
   } else if (gender.isEmpty()) {
    alert("Error", "Error", "Gender must be seleted");
   } else if (address.isEmpty()) {
    alert("Error", "Error", "Address must be filled");
   } else if (!termsChecked) {
    alert("Error", "Error", "CheckBox must be checked");
   } else {
    insertUser(email, username, password, phoneNum, address, gender);
    try {
     new Main().start(stage);
    } catch (Exception e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
    }
   }
  
  });
 }
 
 
  private String generateUserID() {
      String prefix = "US";
      String index = String.format("%03d", nextUserIndex);
      String userId = prefix + index;
      
      while (usernameAlreadyExists(userId)) {
       nextUserIndex++;
       index = String.format("%03d", nextUserIndex);
       userId = prefix + index;
      }
  return userId;
      
  }
 
  private void insertUser(String email, String username, String password, String phoneNum, String address, String gender) {
	  Connect con = Connect.getInstance();

      String userId = generateUserID();
      String role = "User";

      String genderValue = (gender.equals("Male")) ? "Male" : "Female";

      User newUser = new User(userId, tfEmail.getText(), tfUsername.getText(), pfpassword.getText(), tfphoneNum.getText(), taAddress.getText(), genderValue);

      con.InsertUser("INSERT INTO user (userId, email, username, password, phoneNumber, address, gender, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", newUser);
      
      userList.add(newUser);
  }

  
 private boolean usernameAlreadyExists(String userId) {
  for (User user : userList) {
   if (user.getUserId().equals(userId)) {
    return true;
   }
  }
  
  Connect con = Connect.getInstance();
        String query = "SELECT * FROM user WHERE userId = '" + userId + "'";
        ResultSet rs = con.execSelect(query);

        try {
            return rs.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false; 
        }
    }
 
 
 private void alert(String setTitileA, String setHeaderA, String setContentA) {
  Alert alert = new Alert(AlertType.ERROR);
  alert.setTitle(setContentA);
  alert.setHeaderText(setHeaderA);
  alert.setContentText(setContentA);
  alert.show();
 }
 
 private void styling() {
  gp.setAlignment(Pos.CENTER);
  bp.setTop(mb);
  
  BorderPane.setAlignment(gp, Pos.CENTER);
  
  gp.setHgap(5);
  gp.setVgap(5);
 }

 
 public RegisterPage (Stage stage) {
  bp = new BorderPane();
  gp = new GridPane();
  
  initalized();
  styling();
  keyHandling();
  
  this.stage = stage;
  this.stage.setScene(new Scene(bp, 700, 750));
  this.stage.show();
 }
}