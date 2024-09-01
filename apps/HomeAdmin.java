package apps;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import connect.Connect;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import utils.ListHoodie;

public class HomeAdmin {
 
  private Stage stage;
  private Scene sc;
  private BorderPane bp;
  private GridPane gp;
  
  private MenuBar mb;
  private Menu maccount, madmin;
  private MenuItem miLogout, mieditProduct;
  
  private Label lblEditProductTitle, lblUpdateDelete, lblSelect, lblInsertHoodie, lblHoodieId, lblNameUD, lblpriceUD, lblNameI, lblPriceI;
  private TextField tfPriceUD, tfNameI, tfPriceI;
  private Button btnUpdatePrice, btnDeleteHoodie, btnInsert;
  
  private TableView<ListHoodie> table;
  private TableColumn<ListHoodie, String> hoodieIDcol;
  private TableColumn<ListHoodie, String> hoodieNamecol;
  private TableColumn<ListHoodie, Double> hoodiePricecol;
  private VBox vb;
  
  private ArrayList<ListHoodie> hoodieList = new ArrayList<>();
  private int nextHoodieIndex = 1;

  
  private void showSelectedProductDetails() {
     table.setOnMouseClicked(e -> {
         ListHoodie selectedHoodie = table.getSelectionModel().getSelectedItem();
         if (selectedHoodie != null) {
             lblHoodieId.setText("Hoodie ID: " + String.valueOf(selectedHoodie.getHoodieId()));
             lblNameUD.setText("Hoodie Name: " + (selectedHoodie.getHoodieName()));
             tfPriceUD.setText(String.valueOf(selectedHoodie.getHoodiePrice()));
             
         }
     });
 }


  private void getData() {
      ArrayList<ListHoodie> hoodieList = new ArrayList<>();
      Connect con = Connect.getInstance();

      table = new TableView<>();
      hoodieIDcol = new TableColumn<>("Hoodie ID");
      hoodieNamecol = new TableColumn<>("Hoodie Name");
      hoodiePricecol = new TableColumn<>("Hoodie Price");
      table.getColumns().addAll(hoodieIDcol, hoodieNamecol, hoodiePricecol);
         
      ResultSet rs = con.execSelect("SELECT * FROM hoodie");

      try {
          while (rs.next()) {
              String hoodieId = rs.getString("HoodieID");
              String hoodieName = rs.getString("HoodieName");
              Double hoodiePrice = rs.getDouble("HoodiePrice");

              ListHoodie listHoodie = new ListHoodie(hoodieId, hoodieName, hoodiePrice);
              hoodieList.add(listHoodie);
          }

          table.getItems().clear();
          table.getItems().addAll(hoodieList);
      } catch (SQLException e) {
          e.printStackTrace();
      }

      hoodieIDcol.setCellValueFactory(new PropertyValueFactory<>("hoodieId"));
      hoodieNamecol.setCellValueFactory(new PropertyValueFactory<>("hoodieName"));
      hoodiePricecol.setCellValueFactory(new PropertyValueFactory<>("hoodiePrice"));

      table.setMaxHeight(700);
      hoodieIDcol.setMinWidth(70);
      hoodieNamecol.setMinWidth(70);
      hoodiePricecol.setMinWidth(70);

      vb = new VBox();
      vb.getChildren().add(table);
      vb.setPadding(new Insets(20, 20, 20, 20));
      
      showSelectedProductDetails();

  }

 
 private void initialized() {
  stage = new Stage();
  bp = new BorderPane();
  gp = new GridPane();
  
  mb = new MenuBar();
  maccount = new Menu("Account");
  madmin = new Menu("Admin");
  miLogout = new MenuItem("Logout");
  mieditProduct = new MenuItem("Edit Product");
  
  mb.getMenus().addAll(maccount, madmin);
  
  maccount.getItems().add(miLogout);
  madmin.getItems().add(mieditProduct);
  
  Font idFont = Font.font("italic", FontWeight.BOLD, FontPosture.ITALIC, 24);
  Font titleFont = Font.font("arial", FontWeight.BOLD, FontPosture.ITALIC, 24);
  
  lblEditProductTitle = new Label("Edit Product");
  lblEditProductTitle.setFont(titleFont);

  lblUpdateDelete = new Label("Update & Delete Hoodie(s)");
  lblUpdateDelete.setFont(idFont);

//  lblSelect = new Label("Select an item from the table");
  lblInsertHoodie = new Label("Insert Hoodie");
  lblInsertHoodie.setFont(idFont);

  lblHoodieId = new Label("Hoodie ID: ");
  lblNameUD = new Label("Name: ");
  lblpriceUD = new Label("Price: ");
  lblNameI = new Label("Name: ");
  lblPriceI = new Label("Price: ");
  
  tfPriceUD = new TextField();
  tfNameI = new TextField();
  tfPriceI = new TextField();
  
  btnUpdatePrice = new Button("Update Price");
  btnDeleteHoodie = new Button("Delete Hoodie");
  btnInsert = new Button("Insert");
   
 }
 
 private String generateHoodieID() {
     String prefix = "HO";
     String index = String.format("%03d", nextHoodieIndex);
     String hoodieId = prefix + index;

     while (isHoodieIDExists(hoodieId)) {
         nextHoodieIndex++;
         index = String.format("%03d", nextHoodieIndex);
         hoodieId = prefix + index;
     }

     return hoodieId;
 }

 private boolean isHoodieIDExists(String hoodieId) {
     for (ListHoodie hoodie : hoodieList) {
         if (hoodie.getHoodieId().equals(hoodieId)) {
             return true;
         }
     }

     Connect con = Connect.getInstance();
     ResultSet rs = con.execSelect("SELECT HoodieID FROM hoodie WHERE HoodieID = '" + hoodieId + "'");

     try {
         return rs.next(); 
     } catch (SQLException e) {
         e.printStackTrace();
         return false; 
     }
 }

 private void insertHoodie(String lh, String n, Double p) {
     String hoodieId = generateHoodieID();
     ListHoodie listHoodie = new ListHoodie(hoodieId, tfNameI.getText(), Double.parseDouble(tfPriceI.getText()));

   
     Connect con = Connect.getInstance();
     con.InsertHoodie("INSERT INTO hoodie (HoodieID, HoodieName, HoodiePrice) VALUES (?, ?, ?)", listHoodie);
     hoodieList.add(listHoodie);

     getData();
 }
 
 private void deleteSelectedHoodie() {
     ListHoodie selectedHoodie = table.getSelectionModel().getSelectedItem();
     if (selectedHoodie != null) {
         String hoodieId = selectedHoodie.getHoodieId();
         
         Connect con = Connect.getInstance();
         
         String query = "DELETE FROM hoodie WHERE HoodieID = '" + hoodieId + "'";
         con.execDelete(query);
         
         hoodieList.remove(selectedHoodie);
         getData();
     }
 }
 
 private void updatePrice() {
  ListHoodie selectedHoodie = table.getSelectionModel().getSelectedItem();
  if (selectedHoodie != null) {
  double newPrice = Double.parseDouble(tfPriceUD.getText());
  selectedHoodie.setHoodiePrice(newPrice);
  
  Connect con = Connect.getInstance();
     
     String query = "UPDATE hoodie SET HoodiePrice = ? WHERE HoodieID = ?";
     con.UpdatePrice(query, newPrice, query);
     
     getData();

 }
 }
 
 private void styling() {
  Insets insets = new Insets(10,10,10,10);
  
  HBox hbox = new HBox(5);
  hbox.getChildren().addAll(btnUpdatePrice, btnDeleteHoodie);
  
  VBox vbox1 = new VBox(20);
  vbox1.getChildren().addAll(lblNameI, lblPriceI);
  
  
  VBox vbox2 = new VBox(10);
  vbox2.getChildren().addAll(tfNameI, tfPriceI, btnInsert);
  btnInsert.setMinWidth(200);
  
  HBox hbox1 = new HBox(5);
  hbox1.getChildren().addAll(vbox1, vbox2);
  
  HBox hbox3 = new HBox(5);
  hbox3.getChildren().addAll(lblpriceUD, tfPriceUD);
  
  VBox vbox = new VBox(5);
  vbox.getChildren().add(lblUpdateDelete);
  vbox.getChildren().add(lblHoodieId);
  vbox.getChildren().add(lblNameUD);
  vbox.getChildren().add(hbox3);
  vbox.getChildren().add(hbox);
  vbox.getChildren().add(lblInsertHoodie);
  vbox.getChildren().add(hbox1);
  
  gp.add(lblEditProductTitle, 0, 0);
  gp.add(vb, 0, 1);
  gp.add(vbox, 3, 1);
  
  
  gp.setPadding(insets);
  gp.setVgap(25);
  gp.setHgap(25);
  
  bp.setTop(mb);
  
  bp.setCenter(gp);
  sc = new Scene(bp, 700, 750);
  
 }
 
 private void keyHandling() {
  miLogout.setOnAction(e -> {
   try {
    new Main().start(stage);
   } catch (Exception e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
   }
  });
  
  btnInsert.setOnAction(e -> {
     insertHoodie(tfNameI.getText(), null, Double.parseDouble(tfPriceI.getText()));
 });

  btnDeleteHoodie.setOnAction(e -> {
      deleteSelectedHoodie();
  });
  
  btnUpdatePrice.setOnAction(e -> {
   updatePrice();
  });
 }
 
 public HomeAdmin(Stage stage2) {
  initialized();
  getData();
  styling();
  keyHandling();
  stage.setResizable(false);
  this.stage = stage2;
  this.stage.setScene(sc);
  this.stage.setTitle("hO-Ohdie");
  this.stage.show();
 }

}