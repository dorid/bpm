import vo.Node;

import java.util.List;

/**
 * User: dorid
 * Date: 13-9-24
 * Time: 17:08
 */
public class DotUtil {

    public static String generateDot(List<Node> tasks, String tastId) {
        StringBuffer sb = new StringBuffer();
        for (Node task : tasks) {
            if (task.getId().equals(tastId)) {
                sb.append(getDotForNode(task));
                List<Node> list = task.getNext();
                for (Node node : list) {
                    if (!node.isDraw()) {
                        sb.append(generateDot(tasks, node.getId()));
                    }
                }
            }
        }
        return sb.toString();
    }

    public static String getDotForNode(Node node) {
        node.setDraw(true);
        StringBuffer sb = new StringBuffer();
        String name = node.getName();
        if (name == null) {
            name = node.getId();
        }
        sb.append(node.getId() + "[label=\"" + name + "\"];\n");
        List<Node> next = node.getNext();
        for (Node nextNode : next) {
            sb.append(node.getId() + "->" + nextNode.getId() + ";\n");
        }
        return sb.toString();
    }
}
