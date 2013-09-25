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
        List<Node> blankNodes = getBlankNode(root);
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

        //设置各节点的坐标
        setNodePos(tasks, points);
        //把各节点连接起来
        operate.arrange(tasks, sequenceFlows);
        //插入空节点
        operate.insert(tasks, blankNodes);

        SequenceFlow startFlow = operate.findFlowById(sequenceFlows, startFlowName);
        //根据起始节点，生成DOT文件
        String dot = DotUtil.generateDot(tasks, startFlow.getTargetId());

        return dot;
    }

    private void insert(List<Node> tasks, List<Node> newNodes) {
        tasks.addAll(newNodes);
        for (Node newNode : newNodes) {
            Node previous = findNodeById(tasks, newNode.getPrevious().get(0).getId());
            Node next = findNodeById(tasks, newNode.getNext().get(0).getId());

            if (previous != null && next != null) {
                previous.getNext().remove(next);
                previous.getNext().add(newNode);

                newNode.getNext().clear();
                newNode.getPrevious().clear();
                newNode.getNext().add(next);
                newNode.getLineLabel().put(next, previous.getLineLabel().get(next));
            }
        }
    }

    private static List<Node> getBlankNode(Element root) {
        List<Node> blankNodes = new ArrayList<Node>();

        Element bpmnDiagram = root.element("BPMNDiagram");
        Element bpmnPlane = bpmnDiagram.element("BPMNPlane");

        List<Element> elements = bpmnPlane.elements();
        for (Element element : elements) {
            if (element.getName().equals("BPMNEdge")) {

                String sourceElement = element.attributeValue("sourceElement");
                sourceElement = sourceElement.substring(sourceElement.indexOf("_") + 1);

                String targetElement = element.attributeValue("targetElement");
                targetElement = targetElement.substring(targetElement.indexOf("_") + 1);

                List<Element> points = element.elements();
                if (points.size() > 2) {
                    for (int i = 1; i < points.size() - 1; i++) {
                        Element point = points.get(i);
                        String x = point.attributeValue("x");
                        String y = "-" + point.attributeValue("y");
                        double xd = new Float(x) - 50;
                        double yd = new Float(y) + 20;

                        //point
                        Point blankPoint = new Point();
                        blankPoint.setX(xd + "");
                        blankPoint.setY(yd + "");
                        //previous
                        Node previous = new Node();
                        previous.setId(sourceElement);
                        //next
                        Node next = new Node();
                        next.setId(targetElement);


                        Node blankNode = new Node();
                        blankNode.setId(sourceElement + "__" + targetElement);
                        blankNode.setWidth("0.01");
                        blankNode.setHeight("0.01");
                        blankNode.setPos(blankPoint);
                        blankNode.setBlank(true);

                        blankNode.getPrevious().add(previous);
                        blankNode.getNext().add(next);

                        blankNodes.add(blankNode);
                    }
                }
            }
        }
        return blankNodes;
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
                sourceNode.getLineLabel().put(targetNode, flow.getName());
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