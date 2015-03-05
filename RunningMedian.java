import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  This class represents a two-heap structure for calculating the running 
 *  median for a stream of numbers.
 */
public class RunningMedian {
	private PriorityQueue<Integer> minHeap; // store numbers larger than the median
	private PriorityQueue<Integer> maxHeap; // store numbers smaller than the median
	private int totalSize;  // total size of numbers
	
    /**
     *  Construct the two-heap structure, where the minHeap stores numbers larger
     *  than the median in ascending order and the maxHeap stores numbers smaller
     *  than the median in descending order.
     */
	public RunningMedian() {
		totalSize = 0;
		minHeap = new PriorityQueue<Integer>();
		maxHeap = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
			@Override
			public int compare(Integer n1, Integer n2) {
				return n2 - n1;
			}
		});
	}
	
    /**
     *  Add a number to the stream.
     */
	public void addNumber(int val) {
		maxHeap.add(val);
		if (totalSize % 2 != 0) {
			minHeap.add(maxHeap.poll());
		} else {
			if (!minHeap.isEmpty() && maxHeap.peek() > minHeap.peek()) {
				int tmp1 = maxHeap.poll();
				int tmp2 = minHeap.poll();
				maxHeap.add(tmp2);
				minHeap.add(tmp1);
			}
		}
		totalSize++;
	}
	
    /**
     *  Return the median of current stream.
     */
	public double getMedian() {
		if (totalSize % 2 != 0) {
			return (double) (maxHeap.peek());
		} else {
			return (maxHeap.peek() + minHeap.peek()) / 2.0;
		}
	}
	
}
