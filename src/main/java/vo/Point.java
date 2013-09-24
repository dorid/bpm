package vo;

/**
 * User: dori
 * Date: 13-9-24
 * Time: 下午8:47
 */
public class Point {
    private String x;
    private String y;

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

    @Override
    public String toString() {
        Float x = new Float(this.getX()) / 92;
        Float y = new Float(this.getY()) / 92;
        return "pos=\"" + x + "," + y + "!\",";
    }
}
