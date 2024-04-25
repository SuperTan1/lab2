import java.util.*;

// A priority queue.
public class PriorityQueue<E> {
	private ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;
	private Map<E, Integer> hashMap = new HashMap<>();

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}


	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}
     
	// Adds an item to the priority queue.
	public void add(E x) {
		heap.add(x);
		hashMap.put(x, heap.size() - 1);
			siftUp(heap.size() - 1);
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public void deleteMinimum() {
		if (size() == 0)
			throw new NoSuchElementException();
		E last = heap.remove(heap.size() -1 );
	if(heap.size() > 0) {
		heap.set(0, last);
		hashMap.put(last, 0);
		siftDown(0);
	}
	}

	public void update(E oldBid, E newBid) {
		int index = hashMap.get(oldBid); // index av gamla bid
		if(index == -1) { // finns inte med i heapen, returnera inget
			return;
		}
		heap.set(index,newBid); // sätter nya bidet till indexet
		hashMap.remove(oldBid); // ta bort gamla bidet
		hashMap.put(newBid, index); // lägger nya bidet i indexet

		if (comparator.compare(newBid, oldBid) > 0) {
			siftDown(index);
		} else {
			siftUp(index);
		}
	}
	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	private void siftUp(int index) {
		E value = heap.get(index);

		while (index > 0) {
			int parentIndex = parent(index);
			E valueParent = heap.get(parentIndex);

			if (comparator.compare(value, valueParent) < 0) {
				heap.set(index, valueParent);
				hashMap.put(valueParent, index);
				index = parentIndex;
			}
			else {
				break;
		}
		}
		heap.set(index, value);
		hashMap.put(value, index);
	}
     
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	private void siftDown(int index) {
		E value = heap.get(index);
		
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childValue = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}

			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) > 0) {
				heap.set(index, childValue);
				hashMap.put(childValue, index);
				index = child;
			} else break;
		}

		heap.set(index, value);
		hashMap.put(value, index);
	}

	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}
}
