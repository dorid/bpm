package vo;

/**
 * User: dorid
 * Date: 13-9-23
 * Time: 17:17
 */
public class SequenceFlow {
// ------------------------------ FIELDS ------------------------------

    private String name;
    private String id;

    private String sourceId;
    private String targetId;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        return "SequenceFlow{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", targetId='" + targetId + '\'' +
                '}';
    }
}
