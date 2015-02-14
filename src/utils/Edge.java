package utils;

public class Edge {
    private Integer head;
    private Integer tail;

    public Integer getHead() {
        return head;
    }

    public Integer getTail() {
        return tail;
    }

    public Edge(Integer head, Integer tail) {
        this.head = head;
        this.tail = tail;
    }
}
