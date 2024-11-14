import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class HashTable {

  private ListNode[] table;
  private int maxSize;

  public HashTable(int maxSize) {
    this.maxSize = maxSize;
    this.table = new ListNode[maxSize];
  }

  private int hashFunction(String key) {
    // Simple hashing function: sum of ASCII values modulo the table size
    int hashIndex = 0;
    for (char c : key.toCharArray()) {
      hashIndex += c;
    }
    return hashIndex % maxSize;
  }

  public void insert(String key, int value) {
    int index = hashFunction(key);
    ListNode newNode = new ListNode(key, value);

    if (table[index] == null) {
      table[index] = newNode;
    } else {
      ListNode currentNode = table[index];
      while (currentNode != null) {
        if (currentNode.key.equals(key)) {
          currentNode.value = value;
          return;
        }
        if (currentNode.next == null) {
          break;
        }
        currentNode = currentNode.next;
      }
      currentNode.next = newNode;
    }
  }

  public void delete(String key) {
    int index = hashFunction(key);
    ListNode currentNode = table[index];
    ListNode previousNode = null;

    while (currentNode != null) {
      if (currentNode.key.equals(key)) {
        if (previousNode != null) {
          previousNode.next = currentNode.next;
        } else {
          table[index] = currentNode.next;
        }
        return;
      }
      previousNode = currentNode;
      currentNode = currentNode.next;
    }
  }

  public Integer find(String key) {
    int index = hashFunction(key);
    ListNode currentNode = table[index];

    while (currentNode != null) {
      if (currentNode.key.equals(key)) {
        return currentNode.value;
      }
      currentNode = currentNode.next;
    }
    return null;
  }

  public void increase(String key) {
    int index = hashFunction(key);
    ListNode current = table[index];

    while (current != null) {
      if (current.key.equals(key)) {
        current.value++;
        return;
      }
      current = current.next;
    }
    insert(key, 1);
  }

  public List<String> listAllKeys() {
    List<String> keys = new ArrayList<>();
    for (ListNode node : table) {
      ListNode current = node;
      while (current != null) {
        keys.add(current.key + ": " + current.value);
        current = current.next;
      }
    }
    return keys;
  }

  public void outputToFile(String filename) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
      for (String entry : listAllKeys()) {
        writer.write(entry);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public TreeMap<Integer, Integer> collisionHistogram() {
    TreeMap<Integer, Integer> histogram = new TreeMap<>();
    for (ListNode node : table) {
      int length = 0;
      ListNode current = node;
      while (current != null) {
        length++;
        current = current.next;
      }
      if (length > 0) {
        histogram.put(length, histogram.getOrDefault(length, 0) + 1);
      }
    }
    return histogram;
  }
}

