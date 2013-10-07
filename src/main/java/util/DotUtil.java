package util;

import vo.Node;
import vo.SequenceFlow;

import java.util.List;

/**
 * User: dorid
 * Date: 13-9-24
 * Time: 17:08
 */
public class DotUtil {

    public static String generateDot(List<Node> tasks, List<SequenceFlow> sequenceFlows) {
        StringBuffer sb = new StringBuffer();
        for (Node task : tasks) {
            sb.append(getDotForNode(task));
        }

        for (SequenceFlow flow : sequenceFlows) {
            sb.append(getDotForLine(flow));
        }
        return sb.toString();
    }

    private static String getDotForLine(SequenceFlow flow) {
        return flow.toString();
    }

    public static String getDotForNode(Node node) {
        StringBuffer sb = new StringBuffer();
        sb.append(node);
        return sb.toString();
    }
}
