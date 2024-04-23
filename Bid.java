import java.awt.*;

public class Bid {
	final public String name;
	public int Price;
	final public String bidType;

	 public int newPrice;

	public Bid(String name, String bidType, int Price) {
		this.name = name;
		this.Price = Price;
		this.bidType = bidType;
		this.newPrice = Price;
	}
// behövs det 2 bid? hur vet jag att priset resultera det nyapriset.
	public Bid(String name, String bidType, int Price, int newPrice) {
		this.name = name;
		this.Price = Price;
		this.bidType = bidType;
		this.newPrice = newPrice;
	}


	public int hashCode() {
		return 1 + 23 * Price + 31 * name.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Bid)) return false;

		Bid bid = (Bid) obj;

		// TODO: compare the objects
		return this.name.equals(bid.name) && this.Price == bid.Price;
	}

	public String toString() {
		// TODO: return a description of the bid
		if (this.bidType.equals("K") || this.bidType.equals("S")) {
			return this.name + this.bidType + this.Price;
		} else if (this.bidType.equals("NS") || this.bidType.equals("NK")) {
			return this.name + this.bidType + this.Price + this.newPrice;
		} else
			return "invalid bid type";
	}

	public int getPrice() {
		return this.Price;
	}
}

