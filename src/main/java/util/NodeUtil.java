package util;

import constant.NodeType;
import org.dom4j.Element;
import vo.Node;
import vo.TaskNode;
import vo.event.EndEvent;
import vo.gateway.EventBasedGateway;
import vo.gateway.ExclusiveGateway;
import vo.gateway.InclusiveGateway;
import vo.gateway.ParallelGateway;
import vo.event.ErrorEvent;
import vo.event.StartEvent;

import java.util.List;

/**
 * User: dorid
 * Date: 13-10-7
 * Time: 14:05
 */
public class NodeUtil {
    public static Node parse(Element element) {
        if (NodeType.isTask(element)) {
            return getTaskNode(element);
        }
        if (NodeType.isGateWay(element)) {
            return getGateWay(element);
        }
        if (NodeType.isStartEvent(element)) {
            return getStartEvent(element);
        }
        if (NodeType.isEndEvent(element)) {
            return getEndEvent(element);
        }
        return null;
    }

    private static Node getEndEvent(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        EndEvent endEvent = new EndEvent();
        endEvent.setId(id);
        endEvent.setName(name);

        return endEvent;
    }

    private static Node getGateWay(Element element) {
        String name = element.getName();
        String id = element.attributeValue("id");
        String label = element.attributeValue("name");
        Node node = null;
        if (name.equals("exclusiveGateway")) {
            node = new ExclusiveGateway();
        }
        if (name.equals("eventBasedGateway")) {
            node = new EventBasedGateway();
        }
        if (name.equals("parallelGateway")) {
            node = new ParallelGateway();
        }
        if (name.equals("inclusiveGateway")) {
            node = new InclusiveGateway();
        }

        if (node != null) {
            node.setId(id);
            node.setName(label);
        }
        return node;
    }

    private static Node getStartEvent(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");
        String incoming = null;
        String outgoing = null;

        Node node = new StartEvent();

        List<Element> childElements = element.elements();
        for (Element childElement : childElements) {

            if ("errorEventDefinition".equals(childElement.getName())) {
                node = new ErrorEvent();
            }
        }

        node.setId(id);
        node.setName(name);


        return node;
    }

    public static TaskNode getTaskNode(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        TaskNode task = new TaskNode();
        task.setName(name);
        task.setId(id);


        return task;
    }
}
