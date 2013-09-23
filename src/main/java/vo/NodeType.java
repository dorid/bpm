package vo;

import org.dom4j.Element;

/**
 * User: dorid
 * Date: 13-9-23
 * Time: 17:19
 */
public class NodeType {
    public static final String TASK = "task";
    public static final String SCRIPT_TASK = "scriptTask";
    public static final String SEQUENCE = "sequenceFlow";

    public static boolean isTask(Element element) {
        if (NodeType.TASK.equals(element.getName()) ||
                NodeType.SCRIPT_TASK.equals(element.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isSequence(Element element) {
        if (NodeType.SEQUENCE.equals(element.getName())
                ) {
            return true;
        }
        return false;
    }
}
