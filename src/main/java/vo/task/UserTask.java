package vo.task;

import vo.Node;
import vo.Point;

/**
 * User: dorid
 * Date: 13-10-10
 * Time: 14:29
 */
public class UserTask extends Node {

    @Override
    public String getInnerNode() {
        Point pos = getPos();
        String x = pos.getX();
        String y = pos.getY();
        String w = pos.getW();
        String h = pos.getH();
        float innerX = (new Float(x) + new Float(w) / 10)/72;
        float innerY = (new Float(y) - new Float(h) / 10)/72;

        return "\n_N12 [shape=\"circle\", style=\"filled,rounded\", color=\"none\", fillcolor=\"none\", fontcolor=\"#ffffff\", fontsize=\"5\", " +
                "label=<<TABLE BORDER=\"0\" CELLBORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\"><TR><TD><IMG SCALE=\"false\" src=\"D:\\idea\\bpm\\src\\main\\resources\\img\\user.jpg\"></IMG></TD></TR></TABLE>>, " +
                "width=\"0.09375\", height=\"0.09375\"," +
                " pos=\"" + innerX +"," + innerY + "!\"]";
    }
}
