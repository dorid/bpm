package vo;

/**
 * User: dorid
 * Date: 13-9-23
 * Time: 17:16
 */
public class Task {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SequenceFlow getIn() {
        return in;
    }

    public void setIn(SequenceFlow in) {
        this.in = in;
    }

    public SequenceFlow getOut() {
        return out;
    }

    public void setOut(SequenceFlow out) {
        this.out = out;
    }

    private SequenceFlow in;
    private SequenceFlow out;
}
