package apps;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utils.ListCurrent;

public class MenuHistory {
	private Stage stage;
	private Scene scene;
	private BorderPane border;
	private GridPane grid;
	
	private MenuBar menuBar;
	private Menu accountMenu, userMenu;
	private MenuItem logOutMenuItem, homeMenuItem, cartMenuItem, historyMenuItem;
	
	private Label transactionLbl, transactionDetailLbl, totalPriceLbl;
	private ListCurrent listCurrent;
	
	private TableView<String> transcationTable = new TableView<>();
	private TableView<String> transcationDetailTable = new TableView<>();
	
	
	public void initialize() {
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
		
		Font titleFont = Font.font("arial", FontWeight.BOLD, FontPosture.ITALIC, 24);
		Font priceFont = Font.font("Helvetica", FontWeight.BOLD, 18);
		
		
		transactionLbl = new Label("dummy's Transaction(s)");
		transactionLbl.setFont(titleFont);
		transactionDetailLbl = new Label("dummy's Transaction Detail(s)");
		transactionDetailLbl.setFont(titleFont);
		totalPriceLbl = new Label("Total Price: ");
		totalPriceLbl.setFont(priceFont);
	}
	
	private void styling() {
		  Insets insets = new Insets(10,10,10,10);
		  
		  VBox vbox1 = new VBox();
		  vbox1.getChildren().addAll(transactionLbl, transcationTable);
		  VBox vbox2 = new VBox();
		  vbox2.getChildren().addAll(transactionDetailLbl, transcationDetailTable);
		  
		  grid = new GridPane();
		  
		  grid.setPadding(insets);
		  grid.setVgap(25);
		  grid.setHgap(25);
		  
		  grid.add(vbox1, 1, 2);
		  grid.add(vbox2, 2, 2);
		  grid.add(totalPriceLbl, 2, 3);
		  
		  border.setTop(menuBar);
		  border.setCenter(grid);
		  
		  scene = new Scene(border, 750, 750);
	}
	
	private void keyHandling() {
	    logOutMenuItem.setOnAction(e -> {
	        try {
	            new Main().start(stage);
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	    });

	    homeMenuItem.setOnAction(e1 -> {
	        new HomeUser(stage, listCurrent, null);
	    });

	    cartMenuItem.setOnAction(e2 -> {
	        new MenuHistory(stage);
	    });
	}
	
	
	public MenuHistory(Stage stage) {
		initialize();
		styling();
		keyHandling();
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.setTitle("hO-Ohdie");
		this.stage.show();	
	}
	
	
	
}
 