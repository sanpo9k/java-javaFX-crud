package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.CartNew;
import utils.ListCart;
import utils.ListHoodie;
import utils.User;

public class Connect {
	private final String USERNAME ="root";
	private final String PASSWORD ="";
	private final String DATABASE ="ho-ohdie";
	private final String HOST ="localhost: 3306";
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection con;
	private Statement st;
	private static PreparedStatement ps;
	public static Connect connect;
	public static ResultSet rs;
	
		private Connect(){
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
				st = con.createStatement();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
		public static Connect getInstance() {
			if (connect == null) return new Connect();
		
			return connect;
			
		}
	
		public ResultSet execSelect(String query) {
			try {
				ps = con.prepareStatement(query);
				rs = st.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return rs;
		}
		
		  public void InsertUser(String query, User u) {
		      try {
		          ps = con.prepareStatement(query);
		          
		          ps.setString(1, u.getUserId());
		          ps.setString(2, u.getEmail());
		          ps.setString(3, u.getUsername());
		          ps.setString(4, u.getPassword());
		          ps.setString(5, u.getPhoneNum());
		          ps.setString(6, u.getAddress());
		          ps.setString(7, u.getGender());
		          ps.setString(8, "User");
		          
		          ps.executeUpdate();
		      } catch (SQLException e) {
		          e.printStackTrace();
		      }
		  }
		  
		
		public void closeResultSet() {
		    try {
		        if (rs != null) {
		            rs.close();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

		public void execPreparedStatement(String query, String... parameters) {
		    try {
		        ps = con.prepareStatement(query);
		        int parameterIndex = 1;
		        for (String parameter : parameters) {
		            ps.setString(parameterIndex++, parameter);
		        }
		        rs = ps.executeQuery();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		
		
		public void InsertHoodie(String query, ListHoodie listHoodie){
			try{
				ps = con.prepareStatement(query);
				
				ps.setString(1, listHoodie.getHoodieId());
				ps.setString(2, listHoodie.getHoodieName());
				ps.setDouble(3, listHoodie.getHoodiePrice());
				
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void insertCart(String query, CartNew cartnew) {
		    try {
		        ps = con.prepareStatement(query);
		        ps.setString(1, cartnew.getUserId());
		        ps.setString(2, cartnew.getHoodieId());
		        ps.setInt(3, cartnew.getQuantity()); 
		        
		        ps.executeUpdate();
			   } catch (SQLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			   }
			   
			  }

		
		
		public void execUpdate(String query) {
			try {
				st.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void UpdatePrice(String query, double newPrice, String hoodieId) {
			   try {
			    ps = con.prepareStatement(query);
			    
			    ps.setDouble(1, newPrice);
			    ps.setString(2, hoodieId);
			    
			    ps.executeUpdate();
			   } catch (SQLException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			   }
			   
			  }
		
		public void execInsert(String query) {
			try {
				ps = con.prepareStatement(query);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void execDelete(String query) {
			try {
				ps = con.prepareStatement(query);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	


