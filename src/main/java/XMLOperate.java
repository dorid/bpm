import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import constant.NodeType;
import vo.DecisionNode;
import vo.Node;
import vo.SequenceFlow;
import vo.TaskNode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLOperate {
// --------------------------- main() method ---------------------------

    /**
     * 解析XML数据
     */
    public static void main(String[] args) {
        List<Node> tasks = new ArrayList<Node>();
        List<SequenceFlow> sequenceFlows = new ArrayList<SequenceFlow>();

        XMLOperate operate = new XMLOperate();
        Document doc = null;
        try {
            SAXReader reader = new SAXReader();
            InputStream in = XMLOperate.class.getResourceAsStream("mrp_pomt_req_release_flow.xml");
            doc = reader.read(in);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
        //指向根节点
        Element root = doc.getRootElement();
        List<Element> process = root.elements("process");
        for (Element node : process) {
            List<Element> elements = node.elements();
            for (Element element : elements) {
                if (NodeType.isTask(element)) {
                    tasks.add(operate.getTask(element));
                }

                if (NodeType.isSequence(element)) {
                    sequenceFlows.add(operate.getSequence(element));
                }

                if (NodeType.isDecision(element)) {
                    tasks.add(operate.getDecision(element));
                }
            }




/*
            System.out.println(order.element("customer").elementText("no"));
            System.out.println(order.element("customer").elementText("name"));
            List<Element> products = order.element("products").elements("product");
            for (Element p : products) {
                System.out.println("----" + p.elementText("name"));
                System.out.println("----" + p.elementText("type"));
                System.out.println("----" + p.elementText("quantity"));
            }
            System.out.println(order.element("address").elementText("address-start"));
            System.out.println(order.element("address").elementText("address-end"));
            System.out.println(order.elementTextTrim("date"));
            System.out.println("");*/
        }

        operate.arrange(tasks, sequenceFlows);
        operate.printTask(tasks);
        System.out.println("=========================================");
        operate.printSequence(sequenceFlows);
    }

    public TaskNode getTask(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        TaskNode task = new TaskNode();
        task.setName(name);
        task.setId(id);

        return task;
    }

    private SequenceFlow getSequence(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");
        String sourceRef = element.attributeValue("sourceRef");
        String targetRef = element.attributeValue("targetRef");

        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(id);
        sequenceFlow.setName(name);
        sequenceFlow.setSourceId(sourceRef);
        sequenceFlow.setTargetId(targetRef);

        return sequenceFlow;
    }

    private Node getDecision(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        DecisionNode decisionNode = new DecisionNode();
        decisionNode.setId(id);
        decisionNode.setName(name);

        List<Element> elements = element.elements();
        for (Element element1 : elements) {
            Node node = new Node();
            node.setId(element1.getText());
            /*if ("incoming".equals(element1.getName())) {
                decisionNode.getPrevious().add(node);
            }*/
            if ("outgoing".equals(element1.getName())) {
                decisionNode.getNext().add(node);
            }
        }

        return decisionNode;
    }

    private void arrange(List<Node> tasks, List<SequenceFlow> sequenceFlows) {
        for (SequenceFlow flow : sequenceFlows) {
            Node sourceNode = findNodeById(tasks, flow.getSourceId());

            if (sourceNode != null) {

                if (sourceNode instanceof TaskNode) {
                    Node targetNode = findNodeById(tasks, flow.getTargetId());
                    ((TaskNode) sourceNode).setNext(targetNode);
                }
                if (sourceNode instanceof DecisionNode) {
                    List<Node> next = ((DecisionNode) sourceNode).getNext();
                    List<Node> newNext = new ArrayList<Node>();
                    for (Node node : next) {
                        SequenceFlow flowById = findFlowById(sequenceFlows, node.getId());
                        if (flowById != null) {
                            Node nodeById = findNodeById(tasks, flowById.getId());
                            if (nodeById != null) {
                                newNext.add(nodeById);
                            }

                        }
                    }
                    ((DecisionNode) sourceNode).getNext().clear();
                    ((DecisionNode) sourceNode).getNext().addAll(newNext);
                }
            }
        }
    }

    private Node findNodeById(List<Node> tasks, String id) {
        for (Node task : tasks) {
            if (id.equals(task.getId())) {
                return task;
            }
        }
        return null;
    }

    private SequenceFlow findFlowById(List<SequenceFlow> flows, String id) {
        for (SequenceFlow flow : flows) {
            if (id.equals(flow.getId())) {
                return flow;
            }
        }
        return null;
    }

    public void printTask(List<Node> tasks) {
        for (Node task : tasks) {
            System.out.println(task);
        }
    }

    public void printSequence(List<SequenceFlow> sequenceFlows) {
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            System.out.println(sequenceFlow);
        }
    }
}