package vo;

/**
 * User: dorid
 * Date: 13-9-23
 * Time: 17:16
 */
public class TaskNode extends Node{
// ------------------------------ FIELDS ------------------------------

    private Node previous;
    private Node next;

// --------------------- GETTER / SETTER METHODS ---------------------

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        return super.toString() + " " + "TaskNode{" +
                "previous=" + previous +
                ", next=" + next +
                '}';
    }
}
