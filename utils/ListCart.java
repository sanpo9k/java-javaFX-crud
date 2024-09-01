package utils;

public class ListCart {
	private String hoodieId;
	private String hoodieName;
	private double quantity;
	private double totalPrice;
	/**
	 * @param hoodieId
	 * @param hoodieName
	 * @param quantity
	 * @param totalPrice
	 */
	public ListCart(String hoodieId, String hoodieName, double quantity, double totalPrice) {
		this.hoodieId = hoodieId;
		this.hoodieName = hoodieName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	public String getHoodieId() {
		return hoodieId;
	}
	public void setHoodieId(String hoodieId) {
		this.hoodieId = hoodieId;
	}
	public String getHoodieName() {
		return hoodieName;
	}
	public void setHoodieName(String hoodieName) {
		this.hoodieName = hoodieName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	

}
