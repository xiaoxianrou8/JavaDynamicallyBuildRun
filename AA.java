import java.io.File;
import java.io.StringWriter;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Date;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class AA {
    public static void main(String[] args) {
        File jf = new File("C:\\Users\\22382\\Desktop\\temp\\Test.java");
        String outFolder = "C:\\Users\\22382\\Desktop\\temp\\output";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fm = compiler.getStandardFileManager(collector, null, null);
        
        Iterable<? extends JavaFileObject> files = fm.getJavaFileObjects(jf);
        if (files == null) {
            return;
        }
        StringWriter sw = new StringWriter();
       
        // 编译目的地设置
        Iterable options = Arrays.asList("-d", outFolder);
        // 通过 JavaCompiler 对象取得编译 Task
        JavaCompiler.CompilationTask task = compiler.getTask(sw, fm, null, options, null, files);
        // 调用 call 命令执行编译，如果不成功输出错误信息
        if (!task.call()) {
            String failedMsg = sw.toString();
            System.out.println("Build Error:" + failedMsg);
        }

        File classFile = new File(outFolder);
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            add.invoke(classLoader, new Object[] { classFile.toURI().toURL() });
            //要加载那个类
            Class c = classLoader.loadClass("Test");
            Object o = c.newInstance();
            //param1,方法名
            //param2参数
            Method m = c.getDeclaredMethod("RunTest",null);
            //传参数执行
            System.out.println(m.invoke(o));
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }
}