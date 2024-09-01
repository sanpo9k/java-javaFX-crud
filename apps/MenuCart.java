package apps;

import java.sql.ResultSet;
import java.sql.SQLException;

import connect.Connect;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.CartNew;
import utils.ListCart;
import utils.ListCurrent;
import utils.ListHoodie;
import utils.User;

public class MenuCart {
	private Scene scene;
	private Stage stage;
	private BorderPane border;
	private GridPane grid;
	
	private MenuBar menuBar;
	private Menu accountMenu, userMenu;
	private MenuItem logOutMenuItem, homeMenuItem, cartMenuItem, historyMenuItem;
	
	private Label userCartLbl, hoodiesDetailLbl, hoodieIdLbl, nameLbl, priceLbl, quantityLbl, totalPriceLbl,
	contactInformationLbl, emailLbl, phoneLbl, addressLbl, cartsTotalPrice;
	
	private TableView<ListCart> cartTable = new TableView<>();
    private TableColumn<ListCart, String> hoodieIdCol;
    private TableColumn<ListCart, String> hoodieNameCol;
    private TableColumn<ListCart, Double> quantityCol;
    private TableColumn<ListCart, Double> totalPriceCol;
    private ObservableList<ListCart> cartData = FXCollections.observableArrayList();

	
	private Button removeBtn, checkoutBtn;
	
	private ListCurrent listCurrent;
	
	private void initialize() {
		stage = new Stage();
		border = new BorderPane();
		grid = new GridPane();
				
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
		
		userCartLbl = new Label("Dummy's cart");
		
		hoodiesDetailLbl = new Label("Hoodie's Detail");
		hoodieIdLbl = new Label("Hoodie ID: ");
		nameLbl = new Label("Name: ");
		priceLbl = new Label("Price: ");
		quantityLbl = new Label("Quantity: ");
		totalPriceLbl = new Label("Total Price: ");
		removeBtn = new Button("Remove from cart");
		
		contactInformationLbl = new Label("Contact Information");
		emailLbl = new Label("Email		: dummy@hoohdie.com");
		phoneLbl = new Label("Phone		: +6212345678901");
		addressLbl = new Label("Address		: Jl. Dummy");
		cartsTotalPrice = new Label("Cart's Total Price: ");
		checkoutBtn = new Button("Checkout");
	}
	
	private void styling() {
		Font CartTitle = Font.font("serif", FontWeight.BOLD, FontPosture.ITALIC, 28);
		userCartLbl.setFont(CartTitle);
		
		Font fontTitle = Font.font(" ", FontWeight.BOLD, FontPosture.ITALIC, 24);
		hoodiesDetailLbl.setFont(fontTitle);
		contactInformationLbl.setFont(fontTitle);
		cartsTotalPrice.setFont(fontTitle);
		
		totalPriceLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 15;");

		
		Insets insets = new Insets(15,15,15,15);
		VBox vBox = new VBox();
		
		grid.add(userCartLbl, 0, 0);
		grid.add(cartTable, 0, 1);
		
		grid.add(vBox, 2, 1);
		grid.add(checkoutBtn, 3, 2);
		
		vBox.getChildren().addAll(hoodiesDetailLbl, hoodieIdLbl, nameLbl, priceLbl, quantityLbl, totalPriceLbl,removeBtn,
				contactInformationLbl, emailLbl, phoneLbl, addressLbl, cartsTotalPrice);
		
		grid.setPadding(insets);
		grid.setVgap(10);
		grid.setHgap(10);
		
		
		
		border.setTop(menuBar);
		border.setCenter(grid);
			
		scene = new Scene(border, 700, 600);
	}
	
	  private void initializeTable() {
				
		  		cartTable = new TableView<>();
			    hoodieIdCol = new TableColumn<>("Hoodie ID");
			    hoodieNameCol = new TableColumn<>("Hoodie Name");
			    quantityCol = new TableColumn<>("Quantity");
			    totalPriceCol = new TableColumn<>("Total Price");
			   
			    Connect connect = Connect.getInstance();
			    ResultSet rs = connect.execSelect("SELECT * FROM hoodie");

			    try {
			        cartData.clear();

			        while (rs.next()) {
			            String hoodieId = rs.getString("HoodieID");
			            String hoodieName = rs.getString("HoodieName");
			            double hoodieprice = rs.getDouble("HoodiePrice");
			            
			        
			        }

			        rs.close();
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
			    
			    
			    hoodieIdCol.setCellValueFactory(new PropertyValueFactory<>("hoodieId"));
			    hoodieNameCol.setCellValueFactory(new PropertyValueFactory<>("hoodieName"));
			    quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			    totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

			    cartTable.getColumns().addAll(hoodieIdCol, hoodieNameCol, quantityCol, totalPriceCol);
			    cartTable.setItems(cartData);


	        cartTable.getColumns().addAll(hoodieIdCol, hoodieNameCol, quantityCol, totalPriceCol);
	        cartTable.setItems(cartData);
	        
	        cartTable.setMaxHeight(700);
	        hoodieIdCol.setMinWidth(70);
	        hoodieNameCol.setMinWidth(70);
	        quantityLbl.setMinWidth(70);
	        totalPriceLbl.setMinWidth(70);
	    }
	  
	  public void updateCartData(ListCart cartItem) {
	        cartData.add(cartItem);
	    }
	
	private void keyHandler() {
		logOutMenuItem.setOnAction(e ->{
			try {
				new Main().start(stage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
			homeMenuItem.setOnAction(e1 -> {
				new HomeUser(stage, listCurrent, null);
			});
			
			historyMenuItem.setOnAction(e2 ->{
				new MenuHistory(stage);
			});
			
			checkoutBtn.setOnAction(e3-> {
				showAlert("Sucess", "Message" , "Succesfully made a transaction");
			});
	}
	
	private void showAlert(String title, String header, String content) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.setContentText(content);
	    alert.show();
	}
	
	private void getCart() {
	    Connect connect = Connect.getInstance();
	    ResultSet rs = connect.execSelect("SELECT * FROM cart WHERE UserID = '" + listCurrent.getUserID() + "'");

	    try {
	        cartData.clear();

	        while (rs.next()) {
	            String userID = rs.getString("UserID");
	            String hoodieID = rs.getString("HoodieID");
	            int quantity = rs.getInt("Quantity");

	        }

	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void getUserData() {
	    Connect connect = Connect.getInstance();
	    ResultSet rs = connect.execSelect("SELECT Email, PhoneNumber, Address FROM hoodie");

	    try {
	        cartData.clear();

	        while (rs.next()) {
	            String hoodieID = rs.getString("HoodieID");
	            String hoodieName = rs.getString("HoodieName");
	            double hoodiePrice = rs.getDouble("HoodiePrice");
	            
	  
	        }

	        rs.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void getHoodieData() {
	    Connect connect = Connect.getInstance();
	    ResultSet rs = connect.execSelect("SELECT * FROM user WHERE UserID = '" + listCurrent.getUserID() + "'");

	}
	
	private void showSelectedProductDetails() {
	    cartTable.setOnMouseClicked(e -> {
	        ListCart selectedHoodie = cartTable.getSelectionModel().getSelectedItem();
	        if (selectedHoodie != null) {
	            hoodieIdLbl.setText("Hoodie ID: " + selectedHoodie.getHoodieId());
	            nameLbl.setText("Hoodie Name: " + selectedHoodie.getHoodieName());
	            quantityLbl.setText("Quantity: " + selectedHoodie.getQuantity());
	            priceLbl.setText("Price: " + selectedHoodie.getTotalPrice());
	        }
	    });
	}
	
	private void updateCartLabel() {
        userCartLbl.setText(listCurrent.getUsernameUser() + "'s cart");
	}
	
	  public MenuCart(Stage stage, ListCurrent listCurrent,ObservableList<ListCart> cartData) {
	        initialize();
	        showSelectedProductDetails();
	        styling();
	        keyHandler();
	        this.stage = stage;
	        this.listCurrent = listCurrent;
	        this.cartData = cartData;
	        initializeTable();
	        updateCartLabel();
	        this.stage.setScene(scene);
	        this.stage.setTitle("hO-Ohdie");
	        this.stage.show();
	    }
}