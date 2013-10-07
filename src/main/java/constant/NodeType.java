package constant;

import org.dom4j.Element;

/**
 * User: dorid
 * Date: 13-9-23
 * Time: 17:19
 */
public class NodeType {
    public static final String TASK = "task";
    public static final String START_EVENT = "startEvent";
    public static final String END_EVENT = "endEvent";
    public static final String SCRIPT_TASK = "scriptTask";
    public static final String SEQUENCE = "sequenceFlow";

    public static final String GATEWAY = "|exclusiveGateway|eventBasedGateway|parallelGateway|inclusiveGateway|";

    public static boolean isTask(Element element) {
        if (NodeType.TASK.equals(element.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isSequence(Element element) {
        if (NodeType.SEQUENCE.equals(element.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isStartEvent(Element element) {
        if (NodeType.START_EVENT.equals(element.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isGateWay(Element element) {
        if (NodeType.GATEWAY.contains(element.getName())) {
            return true;
        }
        return false;
    }

    public static boolean isEndEvent(Element element) {
        if (NodeType.END_EVENT.equals(element.getName())) {
            return true;
        }
        return false;    }
}
