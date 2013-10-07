package vo;

/**
 * User: dori
 * Date: 13-9-24
 * Time: 下午8:47
 */
public class Point {
    private String x;
    private String y;
    private String w;
    private String h;

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

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    @Override
    public String toString() {
        Float aFloat1 = new Float(this.getX()) + new Float(getW())/2;
        Float x = aFloat1 / 72;
        Float aFloat = new Float(this.getY()) - new Float(getH())/2;
        Float y = aFloat / 72;
        return "pos=\"" + x + "," + y + "!\",";
    }
}
