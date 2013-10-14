package newp;

import freemarker.template.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.DotUtil;
import util.NodeUtil;
import vo.*;

import java.io.*;
import java.util.*;

public class XMLOperate {
// --------------------------- main() method ---------------------------

    /**
     * 解析XML数据
     */
    public static void main(String[] args) throws IOException, TemplateException {
        Map<String, Object> rootMap = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = generateDot();
        rootMap.put("nodeList", mapList);


        //模板路径
        String dir = XMLOperate.class.getClass().getResource("/").getPath();

        Configuration cfg = new Configuration();

        //加载freemarker模板文件
        cfg.setDirectoryForTemplateLoading(new File(dir));

        //设置对象包装器
        cfg.setObjectWrapper(new DefaultObjectWrapper());

        //设计异常处理器
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);


        //获取指定模板文件
        Template template = cfg.getTemplate("template.ftl");

        //定义输入文件，默认生成在工程根目录
        Writer out = new OutputStreamWriter(new FileOutputStream("test.dot"));

        //最后开始生成
        template.process(rootMap, out);

        Runtime.getRuntime().exec("D:\\graphviz-2.34\\release\\bin\\dot test.dot -Tpng -o test.png -Kfdp");
    }

    public static List<Map<String, Object>> generateDot() {
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
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
        List<Element> elementList = new ArrayList<Element>();
        List<Element> process = root.elements("process");
        for (Element proces : process) {
            elementList.addAll(proces.elements());
        }



        Element bpmnDiagram = root.element("BPMNDiagram");
        if (bpmnDiagram != null) {
            Element bpmnPlane = bpmnDiagram.element("BPMNPlane");
            if (bpmnPlane != null) {
                elementList.addAll(bpmnPlane.elements());
            }
        }

        if (elementList != null) {
            //解析点
            for (Element element : elementList) {
                Map<String, Object> node = elementToMap(element);
                nodes.add(node);
            }
        }

        for (Map<String, Object> node : nodes) {
            System.out.println(node);
        }

        return nodes;
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