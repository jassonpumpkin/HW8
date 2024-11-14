public class Node {

  int data;
  Color color;
  Node left, right, parent;

  Node(int data) {
    this.data = data;
    this.color = Color.RED; // New nodes are always red
    this.left = null;
    this.right = null;
    this.parent = null;
  }
}
