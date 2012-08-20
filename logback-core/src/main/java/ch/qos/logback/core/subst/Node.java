package ch.qos.logback.core.subst;

public class Node {

  enum Type {LITERAL, VARIABLE, VARIABLE2}

  Type type;
  Object payload;
  Object defaultPart;
  Node next;


  public Node(Type type, Object payload) {
    this.type = type;
    this.payload = payload;
  }


  public Node(Type type, Object payload, Object defaultPart) {
    this.type = type;
    this.payload = payload;
    this.defaultPart = defaultPart;
  }


  @Override
  public String toString() {
    switch (type) {
      case LITERAL:
        return "Node{" +
                "type=" + type +
                ", payload=" + payload +
                '}';
      case VARIABLE:
        StringBuilder payloadBuf = new StringBuilder();
        recursive((Node) payload, payloadBuf);
        return "Node{" +
                "type=" + type +
                ", payload=" + payloadBuf.toString() + "}";

      case VARIABLE2:
        StringBuilder payloadBuf2 = new StringBuilder();
        StringBuilder defaultPartBuf2 = new StringBuilder();
        recursive((Node) payload, payloadBuf2);
        recursive((Node) defaultPart, defaultPartBuf2);
        String r = "Node{" +
                "type=" + type +
                ", payload=" + payloadBuf2.toString();
        r += ", defaultPart=" + defaultPartBuf2.toString();
        r += '}';
        return r;
    }
    return null;
  }

  void recursive(Node n, StringBuilder sb) {
    Node c = n;
    while (c != null) {
      sb.append(c.toString()).append(" --> ");
      c = c.next;
    }
    sb.append("null ");
  }

  public void setNext(Node next) {
    this.next = next;
  }
}
