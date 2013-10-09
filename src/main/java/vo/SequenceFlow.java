package vo;

/**
 * User: dorid
 * Date: 13-9-23
 * Time: 17:17
 */
public class SequenceFlow {
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
// ------------------------------ FIELDS ------------------------------

    private String name;
    private String id;
    private String dir = "forward";

    private Node sourceRef;
    private Node targetRef;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        return "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(Node sourceRef) {
        this.sourceRef = sourceRef;
    }

    public Node getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(Node targetRef) {
        this.targetRef = targetRef;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(sourceRef.getId().replace("-", "_") + "->" + targetRef.getId().replace("-", "_"));
        sb.append("[");
        sb.append("label=\"" + getName() + "\",");
        sb.append("dir=\"" + getDir() + "\"");
        sb.append("]");
        sb.append("\n");
        return sb.toString();
    }
}
