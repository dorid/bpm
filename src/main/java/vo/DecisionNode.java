package vo;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dori
 * Date: 13-9-23
 * Time: 下午9:56
 */
public class DecisionNode extends Node {
// ------------------------------ FIELDS ------------------------------

    private List<Node> previous = new ArrayList<Node>();
    private List<Node> next = new ArrayList<Node>();

// --------------------- GETTER / SETTER METHODS ---------------------

    public List<Node> getPrevious() {
        return previous;
    }

    public void setPrevious(List<Node> previous) {
        this.previous = previous;
    }

    public List<Node> getNext() {
        return next;
    }

    public void setNext(List<Node> next) {
        this.next = next;
    }


// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        return "DecisionNode{" +
                "previous=" + previous +
                ", next=" + next +
                '}';
    }
}
