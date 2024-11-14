public class RedBlackTree {

  private Node root;
  private Node nullNode;

  public RedBlackTree() {
    nullNode = new Node(0);
    nullNode.color = Color.BLACK;
    root = nullNode;
  }

  //InOrder traversal from sepecific node
  private void inOrder(Node node) {
    if (node != nullNode) {
      inOrder(node.left);
      System.out.print(node.data + " ");
      inOrder(node.right);
    }
  }

  public void sort() {
    this.inOrder(root);
  }

  public Node search(int key) {
    return searchTreeHelper(this.root, key);
  }

  private Node searchTreeHelper(Node node, int key) {
    if (node == nullNode || key == node.data) {
      return node;
    }
    if (key < node.data) {
      return searchTreeHelper(node.left, key);
    }
    return searchTreeHelper(node.right, key);
  }

  public Node minimum(Node node) {
    while (node.left != nullNode) {
      node = node.left;
    }
    return node;
  }

  public Node maximum(Node node) {
    while (node.right != nullNode) {
      node = node.right;
    }
    return node;
  }

  public Node successor(Node x) {
    if (x.right != nullNode) {
      return minimum(x.right);
    }

    Node y = x.parent;
    while (y != nullNode && x == y.right) {
      x = y;
      y = y.parent;
    }
    return y;
  }

  public Node predecessor(Node x) {
    if (x.left != nullNode) {
      return maximum(x.left);
    }

    Node y = x.parent;
    while (y != nullNode && x == y.left) {
      x = y;
      y = y.parent;
    }
    return y;
  }

  public void insert(int key) {
    Node node = new Node(key);
    node.parent = null;
    node.data = key;
    node.left = nullNode;
    node.right = nullNode;

    Node y = null;
    Node x = this.root;

    // Find the correct place for the new node
    while (x != nullNode) {
      y = x;
      if (node.data < x.data) {
        x = x.left;
      } else {
        x = x.right;
      }
    }

    node.parent = y;
    if (y == null) {
      root = node;
    } else if (node.data < y.data) {
      y.left = node;
    } else {
      y.right = node;
    }

    // Fix the red-black properties
    fixInsert(node);
  }

  private void fixInsert(Node k) {
    Node u;
    while (k.parent != null && k.parent.color == Color.RED) {
      if (k.parent == k.parent.parent.left) {
        u = k.parent.parent.right;
        if (u.color == Color.RED) {
          k.parent.color = Color.BLACK;
          u.color = Color.BLACK;
          k.parent.parent.color = Color.RED;
          k = k.parent.parent;
        } else {
          if (k == k.parent.right) {
            k = k.parent;
            leftRotate(k);
          }
          k.parent.color = Color.BLACK;
          k.parent.parent.color = Color.RED;
          rightRotate(k.parent.parent);
        }
      } else {
        u = k.parent.parent.left;
        if (u.color == Color.RED) {
          k.parent.color = Color.BLACK;
          u.color = Color.BLACK;
          k.parent.parent.color = Color.RED;
          k = k.parent.parent;
        } else {
          if (k == k.parent.left) {
            k = k.parent;
            rightRotate(k);
          }
          k.parent.color = Color.BLACK;
          k.parent.parent.color = Color.RED;
          leftRotate(k.parent.parent);
        }
      }
      if (k == root) {
        break;
      }
    }
    root.color = Color.BLACK;
  }

  private void leftRotate(Node x) {
    Node y = x.right;
    x.right = y.left;
    if (y.left != nullNode) {
      y.left.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == null) {
      this.root = y;
    } else if (x == x.parent.left) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
  }

  private void rightRotate(Node x) {
    Node y = x.left;
    x.left = y.right;
    if (y.right != nullNode) {
      y.right.parent = x;
    }
    y.parent = x.parent;
    if (x.parent == null) {
      this.root = y;
    } else if (x == x.parent.right) {
      x.parent.right = y;
    } else {
      x.parent.left = y;
    }
    y.right = x;
    x.parent = y;
  }
  public void delete(int data) {
    deleteNodeHelper(this.root, data);
  }
  private void deleteNodeHelper(Node node, int key) {
    Node z = nullNode;
    Node x, y;

    // Find the node to delete
    while (node != nullNode) {
      if (node.data == key) {
        z = node;
      }

      if (node.data <= key) {
        node = node.right;
      } else {
        node = node.left;
      }
    }

    if (z == nullNode) {
      System.out.println("Couldn't find key in the tree");
      return;
    }

    // Node with only one child
    if (z.left == nullNode) {
      x = z.right;
      rbTransplant(z, z.right);
    } else if (z.right == nullNode) {
      x = z.left;
      rbTransplant(z, z.left);
    } else {
      // Node with two children
      y = minimum(z.right);
      x = y.right;

      if (y.parent == z) {
        x.parent = y;
      } else {
        rbTransplant(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }

      rbTransplant(z, y);
      y.left = z.left;
      y.left.parent = y;
      y.color = z.color;
    }
  }
  private void rbTransplant(Node u, Node v) {
    if (u.parent == null) {
      root = v;
    } else if (u == u.parent.left) {
      u.parent.left = v;
    } else {
      u.parent.right = v;
    }
    v.parent = u.parent;
  }
  public int height(Node node) {
    if (node == nullNode) {
      return 0;
    } else {
      int leftHeight = height(node.left);
      int rightHeight = height(node.right);
      return Math.max(leftHeight, rightHeight) + 1;
    }
  }
  public void printHeight() {
    System.out.println("Height of the tree: " + height(root));
  }
  public void printTree() {
    printTreeHelper(this.root, "", true);
  }
  private void printTreeHelper(Node node, String prefix, boolean isLeft) {
    if (node != nullNode) {
      System.out.print(prefix + (isLeft ? "├── " : "└── "));
      System.out.println(node.data + "(" + node.color + ")");
      printTreeHelper(node.left, prefix + (isLeft ? "│   " : "    "), true);
      printTreeHelper(node.right, prefix + (isLeft ? "│   " : "    "), false);
    }
  }
}
