package apps;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import connect.Connect;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.CartNew;
import utils.ListCart;
import utils.ListCurrent;
import utils.ListHoodie;

public class HomeUser {
	
	private Stage stage;
	private Scene scene;
	private BorderPane border;
	
	private MenuBar menuBar;
	private String userID;
	private Menu accountMenu, userMenu;
	private MenuItem logOutMenuItem, homeMenuItem, cartMenuItem, historyMenuItem;

	private ArrayList<ListHoodie> arrayHoodie = new ArrayList<>();
	private ListView<ListHoodie> viewHoodie = new ListView<>();
	private ObservableList<ListCart> cartItems = FXCollections.observableArrayList();
	private Label hoohdieTitleLbl, hoodieDetailLbl, hoodieDetailLbl2, hoodieIdLbl, nameLbl, priceLbl, quantityLbl, totalPriceLbl, selectItemLbl;
	private Spinner<Integer> quantitySp; 
	private Button cartBtn;
	
	Connect connect = Connect.getInstance();
	private ListCurrent listCurrent;

	
	private void initialize() {		
		stage = new Stage();
		border = new BorderPane();
		
		menuBar = new MenuBar();
		accountMenu = new Menu("Account");
		userMenu = new Menu("User");
		
		logOutMenuItem = new MenuItem("Logout");
		homeMenuItem = new MenuItem("Home");
		cartMenuItem = new MenuItem("Cart");
		historyMenuItem = new MenuItem("History");
		
		menuBar.getMenus().addAll(accountMenu, userMenu);
		accountMenu.getItems().add(logOutMenuItem);
		userMenu.getItems().addAll(homeMenuItem, cartMenuItem, historyMenuItem);
		
		
		//LABEL STYLING
		Font idFont = Font.font("arial", FontWeight.BOLD, FontPosture.ITALIC, 24);
		Font titleFont = Font.font("constantia", FontWeight.BOLD, FontPosture.ITALIC, 24);
		
		//String format
		
		//LABEL INIT
		hoohdieTitleLbl = new Label("hO-Ohdie");
		hoohdieTitleLbl.setFont(titleFont);
		hoodieDetailLbl = new Label("Hoodie's Detail");
		hoodieDetailLbl.setFont(idFont);
		hoodieDetailLbl2 = new Label("Hoodie's Detail");
		hoodieDetailLbl2.setFont(idFont);
		hoodieIdLbl = new Label("Hoodie ID: ");
		nameLbl = new Label("Name: ");
		priceLbl = new Label("Price: ");
		quantityLbl = new Label("Quantity: " );
		totalPriceLbl = new Label("Total Price: ");
		
		selectItemLbl = new Label("Select an item from the list...");
		
		quantitySp = new Spinner<>(1,10,1);
		cartBtn = new Button("Add to Cart");
		
		border.setTop(menuBar);
		
	}
	
	private void styling() {
		GridPane grid = new GridPane();
		Insets insets = new Insets(10,10,10,10);
		
		HBox hbox = new HBox(5);
		hbox.getChildren().addAll(quantitySp, totalPriceLbl);
		
		VBox vbox = new VBox(5);
		vbox.getChildren().add(hoodieDetailLbl);
		vbox.getChildren().add(hoodieIdLbl);
		vbox.getChildren().add(nameLbl);
		vbox.getChildren().add(priceLbl);
		vbox.getChildren().add(quantityLbl);
		vbox.getChildren().addAll(hbox);
		vbox.getChildren().add(cartBtn);
		
		VBox vbox2 = new VBox(5);
		vbox2.getChildren().add(hoodieDetailLbl2);
		vbox2.getChildren().add(selectItemLbl);
		
		grid.add(hoohdieTitleLbl, 0, 0);
		grid.add(viewHoodie, 0, 1);
		grid.add(vbox, 3, 1);
		grid.add(vbox2, 3, 1);
		
		grid.setPadding(insets);
		grid.setVgap(10);
		grid.setHgap(10);
		
		vbox.setVisible(false);
		
	    viewHoodie.getSelectionModel().selectedItemProperty().addListener(
	               (observable, oldValue, newValue) -> {
	                    if (newValue != null) {
	                        updateHoodieDetails(newValue);
	                        vbox.setVisible(true);
	                        vbox2.setVisible(false);
	                    } else {
	                        clearHoodieDetails();
	                    }
	                });
	    quantitySp.valueProperty().addListener(this::updateTotalPrice);

		border.setCenter(grid);
		scene = new Scene(border, 600, 500);
	}
	
	 private void keyHandler() {
		 historyMenuItem.setOnAction(e ->{
			 new MenuHistory(stage);
		 });
	        cartBtn.setOnAction(e -> {
	            addHoodieToCart();
	        });
	        cartMenuItem.setOnAction(e -> {
	           new MenuCart(stage, listCurrent, cartItems);
	        });
	        logOutMenuItem.setOnAction(e -> {
	            try {
	                new Main().start(stage);
	            } catch (Exception e1) {
	                e1.printStackTrace();
	            }
	        });
	        
	    }
	 
	 private void insertCart(String userID, String hoodieID, int quantity) {
		    Connect con = Connect.getInstance();
		    
		    String query = "INSERT INTO cart (UserID, HoodieID, Quantity) VALUES (?, ?, ?)";
		    
		    CartNew cartNew = new CartNew(userID, hoodieID, quantity);
		    con.insertCart(query, cartNew);
		}



	 private void addHoodieToCart() {
		    ListHoodie selectedHoodie = viewHoodie.getSelectionModel().getSelectedItem();
		    if (selectedHoodie != null) {
		        String hoodieId = selectedHoodie.getHoodieId();
		        String hoodieName = selectedHoodie.getHoodieName();
		        double hoodiePrice = selectedHoodie.getHoodiePrice();
		        int quantity = quantitySp.getValue();
		        double totalHoodiePrice = hoodiePrice * quantity;

		        ListCart listCart = new ListCart(hoodieId, hoodieName, quantitySp.getValue(), totalHoodiePrice);		        cartItems.add(listCart);

		        insertCart(userID, hoodieId, quantity);

		        System.out.println("Cart Contents:");
		        for (ListCart cartItem : cartItems) {
		            System.out.println("Hoodie ID: " + cartItem.getHoodieId() +
		                    ", Hoodie Name: " + cartItem.getHoodieName() +
		                    ", Quantity: " + cartItem.getQuantity() +
		                    ", Total Price: " + cartItem.getTotalPrice());
		        }

		        alert("Success", "Message", hoodieId + " - " + hoodieName + " - " + quantity + "x" + " added");
		    } else {
		        alert("Error", "Message", "Please select a hoodie before adding to cart.");
		    }
		}

	
    private void updateHoodieDetails(ListHoodie hoodie) {
        hoodieIdLbl.setText("Hoodie ID: " + hoodie.getHoodieId());
        nameLbl.setText("Name: " + hoodie.getHoodieName());
        priceLbl.setText("Price: $" + hoodie.getHoodiePrice());
        updateTotalPrice(null, null, quantitySp.getValue());
    }

    private void clearHoodieDetails() {
        hoodieIdLbl.setText("Hoodie ID: ");
        nameLbl.setText("Name: ");
        priceLbl.setText("Price: ");
        updateTotalPrice(null, null, quantitySp.getValue());
    }

    private void updateTotalPrice(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        double totalPrice = 0;
        if (!priceLbl.getText().equals("Price: ")) {
            double hoodiePrice = Double.parseDouble(priceLbl.getText().substring(8));
            totalPrice = hoodiePrice * quantitySp.getValue();
        }
        totalPriceLbl.setText("Total Price: $" + totalPrice);
    }
	

    private void alert(String setTitleA, String setHeaderA, String setContentA) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(setTitleA);
        alert.setHeaderText(setHeaderA);
        alert.setContentText(setContentA);
        alert.showAndWait();
    }
    
    private void getDataHoohdie() {
        Connect connect = Connect.getInstance();
        ResultSet rs = connect.execSelect("SELECT * FROM hoodie");

        try {
            // Clear the arrayHoodie only once before populating it
            arrayHoodie.clear();

            while (rs.next()) {
                String hoodieId = rs.getString("HoodieID");
                String hoodieName = rs.getString("HoodieName");
                Double hoodiePrice = rs.getDouble("HoodiePrice");

                ListHoodie listhoodie = new ListHoodie(hoodieId, hoodieName, hoodiePrice);
                arrayHoodie.add(listhoodie);
            }

            viewHoodie.setItems(FXCollections.observableArrayList(arrayHoodie));

            // Close the ResultSet and Statement
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
    public HomeUser(Stage stage, ListCurrent listCurrent, String userID) {
        this.userID = userID;
        initialize();
        styling();
        getDataHoohdie();
        keyHandler();
        stage.setResizable(false);
        this.listCurrent = listCurrent;
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.setTitle("hO-Ohdie");
        this.stage.show();
        
        System.out.println("User ID: " + userID); // Print the userID

    }
}