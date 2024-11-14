import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws IOException {
    int[] sizes = {30, 300, 1000};
    String filename = "input.txt";
    for (int size : sizes) {

      HashTable ht = new HashTable(size); // Adjust size as needed
      try {
        // Read all text from the file
        String textToProcess = new String(Files.readAllBytes(Paths.get(filename)));
        String[] text = preprocessingText(textToProcess);
        for (String word : text) {
          if (!word.isEmpty()) {
            ht.increase(word);
          }
        }
      } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
        return;
      }

      // Output results to a file
      ht.outputToFile("maxHeap_" + size + ".txt");
//      System.out.println("All keys: " + ht.listAllKeys());
      System.out.println("Collision histogram: " + ht.collisionHistogram());

    }
  }

  public static String[] preprocessingText(String text) {
    String[] words = text.toLowerCase().replaceAll("[^a-z ]", "").split("\\s+");
    return words;
  }
}
