package util;

import constant.NodeType;
import newp.XMLOperate;
import org.dom4j.Element;
import vo.Node;
import vo.SequenceFlow;
import vo.task.ScirptTask;
import vo.task.TaskNode;
import vo.event.EndEvent;
import vo.gateway.EventBasedGateway;
import vo.gateway.ExclusiveGateway;
import vo.gateway.InclusiveGateway;
import vo.gateway.ParallelGateway;
import vo.event.ErrorEvent;
import vo.event.StartEvent;
import vo.process.SubProcess;
import vo.task.UserTask;

import java.util.List;

/**
 * User: dorid
 * Date: 13-10-7
 * Time: 14:05
 */
public class NodeUtil {
    public static Node parse(Element element, List<Node> nodes, List<SequenceFlow> sequenceFlows) {
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
        if (NodeType.isSubProcess(element)) {
            return getSubProcess(element, nodes, sequenceFlows);
        }
        if ("boundaryEvent".equals(element.getName())) {
            return getStartEvent(element);
        }
        return null;
    }

    private static Node getSubProcess(Element element, List<Node> nodes, List<SequenceFlow> sequenceFlows) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        SubProcess subProcess = new SubProcess();
        subProcess.setId(id);
        subProcess.setName(name);

        nodes.add(subProcess);

        //解析子流程
        List<Element> elements = element.elements();
        //解析子节点
        for (Element nodeEletemnt : elements) {
            Node node = NodeUtil.parse(nodeEletemnt, nodes, sequenceFlows);
            if (node != null) {
                node.setParent(subProcess);
                nodes.add(node);
            }
        }
        //线
        XMLOperate operate = new XMLOperate();
        for (Element nodeEletemnt : elements) {
            if (nodeEletemnt.getName().equals("sequenceFlow")) {
//                sequenceFlows.add(operate.getSequence(nodeEletemnt, nodes));
            }
        }

        return null;
    }

    private static Node getEndEvent(Element element) {
        EndEvent endEvent = new EndEvent();
        getCommonProp(endEvent, element);
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
        Node node = new StartEvent();
        getCommonProp(node, element);

        List<Element> childElements = element.elements();
        for (Element childElement : childElements) {
            if ("errorEventDefinition".equals(childElement.getName())) {
                node = new ErrorEvent();
            }
        }

        return node;
    }

    public static Node getTaskNode(Element element) {
        String name = element.getName();

        Node task = new TaskNode();
        if (name.equals(NodeType.SCRIPT_TASK)) {
            task = new ScirptTask();
        }
        if (name.equals(NodeType.USER_TASK)) {
            task = new UserTask();
        }

        getCommonProp(task, element);

        return task;
    }

    private static void getCommonProp(Node node, Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");
        String fillcolor = element.attributeValue("bgcolor");
        String bordercolor = element.attributeValue("bordercolor");

        node.setId(id);
        node.setName(name);
        node.setFillColor(fillcolor);
        node.setBorderColor(bordercolor);

    }
}
