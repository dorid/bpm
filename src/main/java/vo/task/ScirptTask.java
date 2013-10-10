package vo.task;

import vo.Node;
import vo.Point;

/**
 * User: dorid
 * Date: 13-10-10
 * Time: 14:29
 */
public class ScirptTask extends Node {

    @Override
    public String getInnerNode() {

        TaskInnerNode innerNode = new TaskInnerNode(this,"D:\\idea\\bpm\\src\\main\\resources\\img\\task\\script.jpg");

        return innerNode.toString();
    }
}
