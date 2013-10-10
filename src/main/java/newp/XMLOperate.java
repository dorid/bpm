package newp;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.DotUtil;
import util.NodeUtil;
import vo.*;

import java.io.InputStream;
import java.util.*;

public class XMLOperate {
// --------------------------- main() method ---------------------------

    /**
     * 解析XML数据
     */
    public static void main(String[] args) {
        generateDot();
//        operate.printSequence(sequenceFlows);
    }

    public static void generateDot() {
        XMLOperate operate = new XMLOperate();
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        List<SequenceFlow> sequenceFlows = new ArrayList<SequenceFlow>();
        Document doc = null;
        try {
            SAXReader reader = new SAXReader();
            InputStream in = XMLOperate.class.getClassLoader().getResourceAsStream("mrp_pomt_req_release_flow.xml");

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
                Map<String, Object> node = elementToMap(element);
                nodes.add(node);
            }
        }

        for (Map<String, Object> node : nodes) {
            System.out.println(node);
        }
    }


    private static Map<String, Object> elementToMap(Element element) {
        List<Attribute> attributes = element.attributes();
        Map<String, Object> node = new LinkedHashMap<String, Object>();
        node.put(Constant.NODE_NAME, element.getName());
        node.put(Constant.NODE_TXT, element.getText().trim());

        for (Attribute attribute : attributes) {
            String name = attribute.getName();
            String value = attribute.getValue();
            node.put(name, value);
        }

        List<Element> childEles = element.elements();
        for (Element child : childEles) {
            Map<String, Object> childNode = elementToMap(child);
            Object childList = node.get(Constant.NODE_CHILD);
            if (childList == null) {
                node.put(Constant.NODE_CHILD, new ArrayList<Map<String, Object>>());
            }
            ((List) node.get(Constant.NODE_CHILD)).add(childNode);
        }
        return node;
    }


}