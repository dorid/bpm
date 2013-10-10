import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import newp.XMLOperate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class Test {
  
 public static void main(String[] args) throws Exception {
     //模板路径
     String dir = Test.class.getClass().getResource("/").getPath();

     Configuration cfg = new Configuration();

     //加载freemarker模板文件
     cfg.setDirectoryForTemplateLoading(new File(dir));

     //设置对象包装器
     cfg.setObjectWrapper(new DefaultObjectWrapper());

     //设计异常处理器
     cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);

     //定义并设置数据
     Map<String, String> data = new HashMap<String, String>();
//     data.put("dot", XMLOperate.generateDot());

     //获取指定模板文件
     Template template = cfg.getTemplate("template.ftl");

     //定义输入文件，默认生成在工程根目录
     Writer out = new OutputStreamWriter(new FileOutputStream("test.dot"));

     //最后开始生成
     template.process(data, out);

     Runtime.getRuntime().exec("D:\\graphviz-2.34\\release\\bin\\dot test.dot -Tpng -o test.png -Kfdp");
 }
}  