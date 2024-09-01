package utils;

public class ListHoodie {
	private String hoodieId;
	private String hoodieName;
	private double hoodiePrice;

	
	public ListHoodie(String hoodieID, String hoodieName, double hoodiePrice) {
		this.hoodieId = hoodieID;
		this.hoodieName = hoodieName;
		this.hoodiePrice = hoodiePrice;
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
	public double getHoodiePrice() {
		return hoodiePrice;
	}
	public void setHoodiePrice(double hoodiePrice) {
		this.hoodiePrice = hoodiePrice;
	}
	
    @Override
    public String toString() {
        return getHoodieId() + "\s" + getHoodieName() + "\s" + getHoodiePrice();
    }

}