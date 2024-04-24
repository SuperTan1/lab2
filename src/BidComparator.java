import java.util.Comparator;

public class BidComparator {


    public static class BuyComparator implements Comparator<Bid> {
 // sänkning 4,3,2,1 högsta till minsta
    @Override
    public int compare(Bid bid1, Bid bid2) {
        return Integer.compare(bid2.getPrice(), bid1.getPrice());
    }
}

    public static class SellComparator implements Comparator<Bid> {
 // ökning 1,2,3,4 minsta till hösta
    @Override
    public int compare(Bid bid1, Bid bid2) {

        return Integer.compare(bid1.getPrice(), bid2.getPrice());
    }
}
}
