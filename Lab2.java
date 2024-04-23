
import java.io.*;
import java.util.*;

public class Lab2 {
	static PriorityQueue<Bid> buy_pq = new PriorityQueue<>(new BidComparator.BuyComparator());
	static PriorityQueue<Bid> sell_pq = new PriorityQueue<>(new BidComparator.SellComparator());
	public static String pureMain(String[] commands) {
		// TODO: declaration of two priority queues

		StringBuilder sb = new StringBuilder();



		for(int line_no=0;line_no<commands.length;line_no++){
			String line = commands[line_no];
			if( line.equals("") )continue;

			String[] parts = line.split("\\s+");
			if( parts.length != 3 && parts.length != 4)  // måste vara längd 3 eller 4, Felix K 10
				throw new RuntimeException("line " + line_no + ": " + parts.length + " words");
			String name = parts[0]; // namnet
			if( name.charAt(0) == '\0' ) // ser till att namnet inte är invalid
				throw new RuntimeException("line " + line_no + ": invalid name");
			String action = parts[1]; // vilken action som sker
			int price;
			try {
				price = Integer.parseInt(parts[2]); // priset
			} catch(NumberFormatException e){
				throw new RuntimeException(
						"line " + line_no + ": invalid price"); // se till att priset inte är invalid
			}

			if( action.equals("K") ) {
			buy_pq.add(new Bid(name, "K", price));
			}
			else if( action.equals("S") ) {
				sell_pq.add(new Bid(name, "S", price));
			}
			else if( action.equals("NK") ){
				// TODO: update existing buy bid. use parts[3].
				int newPriceBid = Integer.parseInt(parts[3]); // nya priset

				for (Bid buyBid : buy_pq.getHeap()) { // leta rätt på köparens bid
					if(buyBid.name.equals(name)) { // om namnen är lika, skapa ny bid med ny pris
						Bid newBid = new Bid(buyBid.name, "NK", buyBid.Price, newPriceBid); // kanske behöver bara den första bidden vet ej
						newBid.Price = newPriceBid; // priset är det nya priset
						buy_pq.update(buyBid, newBid); // uppdatera priorteringslistan
					}
				}
			}
			else if( action.equals("NS") ) {
				// TODO: update existing sell bid. use parts[3].
				int newPriceBid = Integer.parseInt(parts[3]); // nya priset

				for (Bid buyBid : sell_pq.getHeap()) { // leta rätt på köparens bid
					if (buyBid.name.equals(name)) { // om namnen är lika, skapa ny bid med ny pris
						Bid newBid = new Bid(buyBid.name, "NK", buyBid.Price, newPriceBid); // kanske behöver bara den första bidden vet ej
						newBid.Price = newPriceBid; // priset är det nya priset
						sell_pq.update(buyBid, newBid); // uppdatera priorteringslistan
					} else {
						throw new RuntimeException(
								"line " + line_no + ": invalid action");
					}
				}
			}
			if( sell_pq.size() == 0 || buy_pq.size() == 0 )continue;

			Bid sellPriority = sell_pq.peek();
			Bid buyPriority = buy_pq.peek();

			if(sellPriority.Price <= buyPriority.Price) {
				sell_pq.deleteMinimum();
				buy_pq.deleteMinimum();
			}

			sb.append("Transaction: " + buyPriority.name + " buys a share from " + sellPriority.name + "for " + sellPriority.Price);
			// TODO:
			// compare the bids of highest priority from each of
			// each priority queues.
			// if the lowest seller price is lower than or equal to
			// the highest buyer price, then remove one bid from
			// each priority queue and add a description of the
			// transaction to the output.


		}

		sb.append("Order book:\n");

		sb.append("Sellers: ");
		// TODO: print remaining sellers.
		//       can remove from priority queue until it is empty.
		for (int i = 0; i < sell_pq.size(); i++) {
			sb.append(sell_pq.getHeap().get(i));
		}

		sb.append("Buyers: ");
		// TODO: print remaining buyers
		//       can remove from priority queue until it is empty.
		for (int i = 0; i < sell_pq.size(); i++) {
			sb.append(buy_pq.getHeap().get(i));
		}

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		final BufferedReader actions;
		if( args.length != 1 ){
			actions = new BufferedReader(new InputStreamReader(System.in));
		} else {
			actions = new BufferedReader(new FileReader(args[0]));
		}

		List<String> lines = new LinkedList<String>();
		while(true){
			String line = actions.readLine();
			if( line == null)break;
			lines.add(line);
		}
		actions.close();

		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}

	private String showHeap() {
		StringBuilder sb = new StringBuilder();
		sb.append("buyers: ");
		for (Bid b : buy_pq.getHeap()) {
			sb.append(b.name).append(" ").append(b.Price).append(", ");
		}
		sb.append(": sellers: ");
		for (Bid b : sell_pq.getHeap()) {
			sb.append(b.name).append(" ").append(b.Price).append(", ");
		}
		return sb.toString();
	}

	private boolean sellInvariant() {
		int i = 0;
		boolean invariant = true;

		if (sell_pq.getHeap().isEmpty()) { // om den är tom = true;
			return invariant;
		}
		else
			while (sell_pq.leftChild(i) < sell_pq.getHeap().size()) { // stannar vid första lövet som kommer
				boolean rightChildExist = (i % 2 == 0); // en bool värde för om det finns höger child
				if(sell_pq.parent(i) > sell_pq.leftChild(i)) { // om parent större än child, returnera falsk, break
					invariant = false;
					break;
				}
				if(rightChildExist && sell_pq.parent(i) > sell_pq.rightChild(i)) { //kollar om höger barnet finns och om parent är
					invariant = false;
					break; // större än child, returerna falsk och break
				}
				i++;
			}

			return invariant;
	}
}


