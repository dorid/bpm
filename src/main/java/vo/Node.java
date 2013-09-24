package vo;

import java.util.ArrayList;
import java.util.List;

/**
 * User: dori
 * Date: 13-9-23
 * Time: 下午9:51
 */
public class Node {
// ------------------------------ FIELDS ------------------------------

    private String name;
    private String id;


    private Point pos;
    private String width;
    private String height;
    
    private String style;
    private String shape;

    private List<Node> previous = new ArrayList<Node>();
    private List<Node> next = new ArrayList<Node>();

    private boolean isDraw = false;

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

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

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

    public String getStyle() {

        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public String getWidth() {
        if ("diamond".equals(shape)) {
            return "0.5";
        }
        if (name != null) {
            return name.length() / 10.0 + "";
        }
        return "";
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Point getPos() {
        return pos;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.id);
        sb.append("[");
        String label = this.getName();
        if (label != null && label.length() > 8) {
//            label = label.substring(0, 8) + "\\n" + label.substring(8);
        }
        if ("diamond".equals(shape)) {
            label = "X";
        }
        sb.append("label=\"" + label + "\",");
        sb.append("width=\"" + this.getWidth() + "\",");
        sb.append("shape=\"" + this.shape + "\",");
        sb.append(pos.toString());
        sb.append("]");
        sb.append("\n");

        return sb.toString();
    }
}
