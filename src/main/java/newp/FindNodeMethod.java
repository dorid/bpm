package newp;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindNodeMethod implements TemplateMethodModel {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List args) throws TemplateModelException {
        List<Map<String, Object>> nodes = (List<Map<String, Object>>) args.get(0);
        String attrName = args.get(1).toString();
        String attrValue = args.get(2).toString();

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> node : nodes) {
            Object value = node.get(attrName);
            if (value != null) {
                if (value.equals(attrValue)) {
                    data.add(node);
                }
            }
        }
        return data;
    }
}  