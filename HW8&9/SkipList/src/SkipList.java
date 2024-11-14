import java.util.Random;

public class SkipList {

  private SkipListNode header;
  private int maxLevel;
  private int currentLevel;
  private Random random;

  public SkipList(int maxLevel) {
    this.maxLevel = maxLevel;
    this.currentLevel = 0;
    this.header = new SkipListNode(Integer.MIN_VALUE, maxLevel);
    this.random = new Random();
  }

  // Generate a random level for new nodes
  private int randomLevel() {
    int level = 0;
    while (random.nextDouble() < 0.5 && level < maxLevel) {
      level++;
    }
    return level;
  }

  // Insert a value into the skip list
  public void insert(int value) {
    SkipListNode[] update = new SkipListNode[maxLevel + 1];
    SkipListNode current = header;

    // Find the position to insert the new node
    for (int i = currentLevel; i >= 0; i--) {
      while (current.forward[i] != null && current.forward[i].value < value) {
        current = current.forward[i];
      }
      update[i] = current;
    }

    // Move to the next node
    current = current.forward[0];

    // If the value already exists, we don't insert it
    if (current == null || current.value != value) {
      int newLevel = randomLevel();

      // Update the current level of the skip list if needed
      if (newLevel > currentLevel) {
        for (int i = currentLevel + 1; i <= newLevel; i++) {
          update[i] = header;
        }
        currentLevel = newLevel;
      }

      // Create a new node and insert it
      SkipListNode newNode = new SkipListNode(value, newLevel);
      for (int i = 0; i <= newLevel; i++) {
        newNode.forward[i] = update[i].forward[i];
        update[i].forward[i] = newNode;
      }
      System.out.println("Inserted: " + value);
      printList();
    }
  }

  // Delete a value from the skip list
  public void delete(int value) {
    SkipListNode[] update = new SkipListNode[maxLevel + 1];
    SkipListNode current = header;

    // Find the position of the node to delete
    for (int i = currentLevel; i >= 0; i--) {
      while (current.forward[i] != null && current.forward[i].value < value) {
        current = current.forward[i];
      }
      update[i] = current;
    }

    // Move to the next node
    current = current.forward[0];

    // If the node to be deleted is found
    if (current != null && current.value == value) {
      for (int i = 0; i <= currentLevel; i++) {
        if (update[i].forward[i] != current) {
          break;
        }
        update[i].forward[i] = current.forward[i];
      }

      // Remove levels if necessary
      while (currentLevel > 0 && header.forward[currentLevel] == null) {
        currentLevel--;
      }
      System.out.println("Deleted: " + value);
      printList();
    }
  }

  // Lookup a value in the skip list
  public boolean lookup(int value) {
    SkipListNode current = header;

    for (int i = currentLevel; i >= 0; i--) {
      while (current.forward[i] != null && current.forward[i].value < value) {
        System.out.println("Comparing with: " + current.forward[i].value + " (going right)");
        current = current.forward[i];
      }
    }

    current = current.forward[0];

    if (current != null && current.value == value) {
      System.out.println("Found: " + value);
      return true;
    } else {
      System.out.println("Not found: " + value);
      return false;
    }
  }

  // Print the entire skip list at each level
  public void printList() {
    for (int i = 0; i <= currentLevel; i++) {
      SkipListNode node = header.forward[i];
      System.out.print("Level " + i + ": ");
      while (node != null) {
        System.out.print(node.value + " ");
        node = node.forward[i];
      }
      System.out.println();
    }
  }
}
