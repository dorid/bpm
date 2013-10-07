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



    private boolean isDraw = false;
    private boolean isBlank = false;

    private String _getId() {
        if (id == null) {
            return null;
        }
        return id.replace("-", "_");
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getHeight() {
        Point point = getPos();
        if (point != null) {
            String h = point.getH();
            return new Double(h)/72 + "";
        }

        return "0";
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
        if (name.length() > getWidth()) {
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShape() {
        return "box";
    }



    public String getStyle() {

        return style;
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

    public Double getWidth() {
        Point point = getPos();
        if (point != null) {
            return new Double(point.getW()) / 72;
        }
        return 0.0;
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
        if (pos == null) {
            return "";
        }
        if ("diamond".equals(shape)) {
            double x = ((new Float(pos.getX())) / 72);
            float y = new Float(pos.getY()) - new Float(pos.getH()) / 2;
//            Float y = new Float(pos.getY()) / 72;
            return "pos=\"" + x + "," + y + "!\",";
        }

        return pos.toString();
    }

// ------------------------ CANONICAL METHODS ------------------------

    public String drawWrap() {
        StringBuffer sb = new StringBuffer();
        sb.append(_getId() + "_");
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

    public String getLabel() {
        String label = this.getName();
        if (label != null && label.length() > 8) {
//            label = label.substring(0, 8) + "\\n" + label.substring(8);
        }
        return label;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (!isBlank()) {
            //sb.append(drawWrap());
        }
        sb.append(_getId());
        sb.append("[");

        sb.append("label=\"" + getLabel() + "\",");
        sb.append("xlabel=\"" + getXlabel() + "\",");
        sb.append("width=\"" + this.getWidth() + "\",");
        sb.append("height=\"" + this.getHeight() + "\",");
        sb.append("shape=\"" + getShape() + "\",");
        sb.append(getPosString());
        sb.append("]");
        sb.append("\n");

        return sb.toString();
    }

    public String getXlabel() {
        return "";
    }
}
