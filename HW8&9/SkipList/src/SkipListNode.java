public class SkipListNode {
  int value;
  SkipListNode[] forward;

  public SkipListNode(int value, int level) {
    this.value = value;
    this.forward = new SkipListNode[level + 1];
  }
}
