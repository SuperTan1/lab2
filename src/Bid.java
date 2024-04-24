import java.awt.*;

public class Bid {
	final public String name;
	final public int Price;


	public Bid(String name, int Price) {
		this.name = name;
		this.Price = Price;
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
		return "" + this.name + " " + this.Price;
	}
	public int getPrice() {
		return this.Price;
	}
}

