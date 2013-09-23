import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import vo.NodeType;
import vo.SequenceFlow;
import vo.Task;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLOperate {



    /**
     * 解析XML数据
     */
    public static void main(String[] args) {
        List<Task> tasks = new ArrayList<Task>();
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

        operate.printTask(tasks);
    }

    private SequenceFlow getSequence(Element element) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public Task getTask(Element element) {
        String name = element.attributeValue("name");
        String id = element.attributeValue("id");

        Task task = new Task();
        task.setName(name);
        task.setId(id);

        return task;
    }

    public void printTask(List<Task> tasks) {
        for (Task task : tasks) {
            System.out.println("name=" + task.getName());
            System.out.println("id=" + task.getId());
        }
    }
}