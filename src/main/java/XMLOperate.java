import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import constant.NodeType;
import vo.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLOperate {
// --------------------------- main() method ---------------------------

    /**
     * 解析XML数据
     */
    public static void main(String[] args) {
        generateDot();
//        operate.printSequence(sequenceFlows);
    }

    public static String generateDot() {
        List<Node> tasks = new ArrayList<Node>();
        List<SequenceFlow> sequenceFlows = new ArrayList<SequenceFlow>();
        String startFlowName = "";

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
        Map<String, Point> points = getPoints(root);
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

                if (element.getName().equals("startEvent")) {
                    List<Element> startList = element.elements();
                    if (startList.size() > 0) {
                        Element start = startList.get(0);
                        startFlowName = start.getText();
                    }
                }
            }
        }

        setNodePos(tasks, points);
        operate.arrange(tasks, sequenceFlows);

        SequenceFlow startFlow = operate.findFlowById(sequenceFlows, startFlowName);
//        operate.printTask(tasks, startFlow.getTargetId(), 0);
        String dot = DotUtil.generateDot(tasks, startFlow.getTargetId());

        return dot;
    }

    private static void setNodePos(List<Node> tasks, Map<String, Point> points) {
        for (Node task : tasks) {
            Point point = points.get(task.getId());
            if (point != null) {
                task.setPos(point);
            }
        }
    }

    private static Map<String, Point> getPoints(Element root) {

        Map<String, Point> points = new HashMap<String, Point>();

        Element bpmnDiagram = root.element("BPMNDiagram");
        Element bpmnPlane = bpmnDiagram.element("BPMNPlane");

        List<Element> elements = bpmnPlane.elements();
        for (Element element : elements) {
            if (element.getName().equals("BPMNShape")) {
                String key = element.attributeValue("bpmnElement");

                Element bounds = element.element("Bounds");
                String x = bounds.attributeValue("x");
                String y = "-" + bounds.attributeValue("y");


                Point point = new Point();
                point.setX(x);
                point.setY(y);

                points.put(key, point);
            }

        }
        return points;
    }

    public TaskNode getTask(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        TaskNode task = new TaskNode();
        task.setName(name);
        task.setId(id);
        task.setShape("box");

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
        decisionNode.setShape("diamond");
        return decisionNode;
    }

    private void arrange(List<Node> tasks, List<SequenceFlow> sequenceFlows) {
        for (SequenceFlow flow : sequenceFlows) {
            Node sourceNode = findNodeById(tasks, flow.getSourceId());
            Node targetNode = findNodeById(tasks, flow.getTargetId());

            if (sourceNode != null && targetNode != null) {
                sourceNode.getNext().add(targetNode);
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

    public void printTask(List<Node> tasks, String tastId, int level) {
        int count = level;
        for (Node task : tasks) {
            if (task.getId().equals(tastId)) {
                count = count + 1;
                for (int i = 0; i < level; i++) {
                    System.out.print("    ");
                }
                System.out.println(task);
                List<Node> list = task.getNext();
                for (Node node : list) {
                    printTask(tasks, node.getId(), count);
                }
            }
        }
    }

    public void printSequence(List<SequenceFlow> sequenceFlows) {
        for (SequenceFlow sequenceFlow : sequenceFlows) {
            System.out.println(sequenceFlow);
        }
    }
}