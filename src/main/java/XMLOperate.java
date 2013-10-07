import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.DotUtil;
import util.NodeUtil;
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
        List<Node> nodes = new ArrayList<Node>();
        XMLOperate operate = new XMLOperate();
        List<SequenceFlow> sequenceFlows = new ArrayList<SequenceFlow>();
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
        if (process != null && process.size() > 0) {
            List<Element> elements = process.get(0).elements();
            //解析点
            for (Element element : elements) {
                Node node = NodeUtil.parse(element);
                if (node != null) {
                    nodes.add(node);
                }
            }
            //线
            for (Element element : elements) {
                if (element.getName().equals("sequenceFlow")) {
                    sequenceFlows.add(operate.getSequence(element, nodes));
                }
            }

        }
        Map<String, Point> points = getPoints(root);


        //设置各节点的坐标
        setNodePos(nodes, points);
        operate.printTask(nodes);

        //取空白节点
        List<Node> blankNodes = operate.getBlankNode(root, sequenceFlows);
        nodes.addAll(blankNodes);
/*

        //把各节点连接起来
        operate.arrange(nodes, sequenceFlows);
        //插入空节点
        operate.insert(nodes, blankNodes);

        SequenceFlow startFlow = operate.findFlowById(sequenceFlows, startFlowName);
        //根据起始节点，生成DOT文件
        */

        String dot = DotUtil.generateDot(nodes, sequenceFlows);

        return dot;
    }



    private List<Node> getBlankNode(Element root, List<SequenceFlow> sequenceFlows) {
        List<Node> blankNodes = new ArrayList<Node>();

        Element bpmnDiagram = root.element("BPMNDiagram");
        Element bpmnPlane = bpmnDiagram.element("BPMNPlane");

        List<Element> elements = bpmnPlane.elements();
        for (Element element : elements) {
            if (element.getName().equals("BPMNEdge")) {

                String flowName = element.attributeValue("bpmnElement");
                SequenceFlow flow = findFlowById(sequenceFlows, flowName);

                List<Element> points = element.elements();
                if (points.size() > 2) {
                    for (int i = 1; i < points.size() - 1; i++) {
                        Element point = points.get(i);
                        String x = point.attributeValue("x");
                        String y = "-" + point.attributeValue("y");

                        //point
                        Point blankPoint = new Point();
                        blankPoint.setX(x + "");
                        blankPoint.setY(y + "");
                        blankPoint.setH("0");
                        blankPoint.setW("0");

                        BlankNode blankNode = new BlankNode();
                        blankNode.setId(flowName + "_" + i);
                        blankNode.setPos(blankPoint);

                        blankNodes.add(blankNode);

                        SequenceFlow blankFlow = new SequenceFlow();
                        sequenceFlows.add(blankFlow);
                        blankFlow.setId(flowName + "_" + i);
                        if (i == 1) {
                            blankFlow.setTargetRef(flow.getTargetRef());
                            blankFlow.setSourceRef(blankNode);
                            flow.setTargetRef(blankNode);
                        }else{
                            flow = findFlowById(sequenceFlows, (flowName + "_" + (i-1)));
                            blankFlow.setTargetRef(flow.getTargetRef());
                            blankFlow.setSourceRef(blankNode);
                            flow.setTargetRef(blankNode);
                        }
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
                String w = bounds.attributeValue("width");
                String h = bounds.attributeValue("height");


                Point point = new Point();
                point.setX(x);
                point.setY(y);
                point.setW(w);
                point.setH(h);

                points.put(key, point);
            }

        }
        return points;
    }



    private SequenceFlow getSequence(Element element, List<Node> nodes) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");
        String sourceRef = element.attributeValue("sourceRef");
        String targetRef = element.attributeValue("targetRef");

        SequenceFlow sequenceFlow = new SequenceFlow();
        sequenceFlow.setId(id);
        sequenceFlow.setName(name);
        Node source = findNodeById(nodes, sourceRef);
        sequenceFlow.setSourceRef(source);

        Node target = findNodeById(nodes, targetRef);
        sequenceFlow.setTargetRef(target);

        return sequenceFlow;
    }

    private Node findNodeById(List<Node> tasks, String id) {
        if (id == null) {
            return null;
        }
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

}