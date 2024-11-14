import java.util.Random;

public class Main {

  public static void main(String[] args) {

    BinomialHeap heap = new BinomialHeap();
    Random random = new Random();
    // Insert random integers into the heap
    for (int i = 0; i < 10; i++) {
      heap.insert(random.nextInt(100));
    }

    System.out.println("Minimum: " + heap.minimum());
    System.out.println("Extracting Minimum: " + heap.extractMin());
    System.out.println("New Minimum: " + heap.minimum());

  }
}