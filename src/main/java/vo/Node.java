package vo;

/**
 * User: dori
 * Date: 13-9-23
 * Time: 下午9:51
 */
public class Node {
// ------------------------------ FIELDS ------------------------------

    private String name;
    private String id;
    
    private String x;
    private String y;
    private String weight;
    private String height;
    
    private String style;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", weight='" + weight + '\'' +
                ", height='" + height + '\'' +
                ", style='" + style + '\'' +
                '}';
    }
}
