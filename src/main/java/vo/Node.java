package vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<Node, String> lineLabel = new HashMap<Node, String>();

    private boolean isDraw = false;
    private boolean isBlank = false;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getHeight() {
        if (height == null) {
            return "0.5";
        }
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
        if (name == null) {
            name = "";
        }
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

    public Map<Node, String> getLineLabel() {
        return lineLabel;
    }

    public void setLineLabel(Map<Node, String> lineLabel) {
        this.lineLabel = lineLabel;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public boolean isBlank() {
        return isBlank;
    }

    public void setBlank(boolean blank) {
        isBlank = blank;
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
        return "0.01";
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

    public String getPosString() {
        if ("diamond".equals(shape)) {
            //TODO why -0.3? not understand
            double x = ((new Float(pos.getX())) / 92) - 0.3;
            Float y = new Float(pos.getY()) / 92;
            return "pos=\"" + x + "," + y + "!\",";
        }

        return pos.toString();
    }

// ------------------------ CANONICAL METHODS ------------------------

    public String drawWrap() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.id + "_");
        sb.append("[");
        sb.append("label=\"\",");
        sb.append("xlabel=\"tttttt\",");
        sb.append("width=\"" + (new Float(this.getWidth()) + 0.2) + "\",");
        sb.append("height=\"" + (new Float(this.getHeight()) + 0.2) + "\",");
        sb.append("style=\"dotted\",");
        sb.append(getPosString());
        sb.append("]\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (!isBlank()) {
            sb.append(drawWrap());
        }
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
        sb.append("height=\"" + this.getHeight() + "\",");
        sb.append("shape=\"" + this.shape + "\",");
        sb.append(getPosString());
        sb.append("]");
        sb.append("\n");

        return sb.toString();
    }
}
