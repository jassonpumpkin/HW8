import java.util.Random;

public class BinomialHeap {

  private BinomialNode head;

  public BinomialHeap() {
    this.head = null;
  }

  public void makeHeap() {
    head = null;
  }

  public void insert(int key) {
    BinomialHeap newHeap = new BinomialHeap();
    newHeap.head = new BinomialNode(key);
    this.head = union(newHeap).head;
  }

  public int minimum() {
    if (head == null) {
      throw new IllegalStateException("Heap is empty");
    }
    BinomialNode minNode = head;
    BinomialNode curr = head.sibling;
    int minKey = minNode.key;

    while (curr != null) {
      if (curr.key < minKey) {
        minKey = curr.key;
        minNode = curr;
      }
      curr = curr.sibling;
    }
    return minKey;
  }

  public int extractMin() {
    if (head == null) {
      throw new IllegalStateException("Heap is empty");
    }

    BinomialNode minNode = head;
    BinomialNode minPrev = null;
    BinomialNode curr = head;
    int minKey = minNode.key;

    while (curr.sibling != null) {
      if (curr.sibling.key < minKey) {
        minKey = curr.sibling.key;
        minPrev = curr;
        minNode = curr.sibling;
      }
      curr = curr.sibling;
    }

    if (minPrev != null) {
      minPrev.sibling = minNode.sibling;
    } else {
      head = minNode.sibling;
    }

    BinomialHeap newHeap = new BinomialHeap();
    newHeap.head = reverse(minNode.child);
    head = union(newHeap).head;

    return minKey;
  }

  public void decreaseKey(BinomialNode node, int newKey) {
    if (newKey > node.key) {
      throw new IllegalArgumentException("New key is greater than current key");
    }
    node.key = newKey;
    BinomialNode parent = node.parent;
    if (parent != null && node.key < parent.key) {
      cut(node, parent);
      insert(node.key);
    }
  }

  public void delete(BinomialNode node) {
    decreaseKey(node, Integer.MIN_VALUE);
    extractMin();
  }

  public BinomialHeap union(BinomialHeap other) {
    BinomialHeap newHeap = new BinomialHeap();
    newHeap.head = merge(this.head, other.head);
    if (newHeap.head == null) {
      return newHeap;
    }

    BinomialNode prev = null;
    BinomialNode curr = newHeap.head;
    BinomialNode next = curr.sibling;

    while (next != null) {
      if (curr.degree != next.degree) {
        prev = curr;
        curr = next;
      } else {
        if (curr.key <= next.key) {
          curr.sibling = next.sibling;
          link(next, curr);
        } else {
          if (prev == null) {
            newHeap.head = next;
          } else {
            prev.sibling = next;
          }
          link(curr, next);
          curr = next;
        }
      }
      next = curr.sibling;
    }
    return newHeap;
  }

  private void link(BinomialNode y, BinomialNode z) {
    y.parent = z;
    y.sibling = z.child;
    z.child = y;
    z.degree++;
  }

  private BinomialNode merge(BinomialNode h1, BinomialNode h2) {
    if (h1 == null) {
      return h2;
    }
    if (h2 == null) {
      return h1;
    }

    BinomialNode head = null;
    if (h1.degree <= h2.degree) {
      head = h1;
      h1 = h1.sibling;
    } else {
      head = h2;
      h2 = h2.sibling;
    }
    BinomialNode current = head;

    while (h1 != null && h2 != null) {
      if (h1.degree <= h2.degree) {
        current.sibling = h1;
        h1 = h1.sibling;
      } else {
        current.sibling = h2;
        h2 = h2.sibling;
      }
      current = current.sibling;
    }
    current.sibling = (h1 != null) ? h1 : h2;
    return head;
  }

  private BinomialNode reverse(BinomialNode node) {
    BinomialNode prev = null;
    while (node != null) {
      BinomialNode next = node.sibling;
      node.sibling = prev;
      prev = node;
      node = next;
    }
    return prev;
  }

  private void cut(BinomialNode node, BinomialNode parent) {
    if (parent.child == node) {
      parent.child = node.sibling;
    } else {
      BinomialNode curr = parent.child;
      while (curr.sibling != node) {
        curr = curr.sibling;
      }
      curr.sibling = node.sibling;
    }
    parent.degree--;
    node.sibling = null;
  }


}
