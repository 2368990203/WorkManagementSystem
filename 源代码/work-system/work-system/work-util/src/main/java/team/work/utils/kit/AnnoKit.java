package team.work.utils.kit;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.constant.Table;
import team.work.utils.convert.J;
import team.work.utils.constant.Table;
import team.work.utils.convert.J;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class AnnoKit {
    public static AnnoKit anno = null;

    public static AnnoKit getInstance() {
        if (anno == null) {
            anno = new AnnoKit();
        }
        return anno;
    }
    public int getTableId(Class cls){
        JSONObject object = J.o2j(AnnoKit.getInstance().getClassAnnotation(cls, TableName.class));
        return Table.BUSINESS.get(object.get("value"));
    }
    /**
     * 得到类上面的注解信息
     * @param scannerClass
     * @param allowInjectClass
     * @return
     */
    public Annotation getClassAnnotation(Class<?> scannerClass , Class<? extends Annotation> allowInjectClass) {

        if(!scannerClass.isAnnotationPresent(allowInjectClass)) {
            return null;
        }

        return scannerClass.getAnnotation(allowInjectClass);
    }

    /**
     * 等到方法级别注解的信息
     * @param scannerClass：需要被扫描的class文件
     * @param allowInjectClass：注解的文件
     * @return
     */
    public List<Annotation> getMethodAnnotation(Class<?> scannerClass , Class<? extends Annotation> allowInjectClass) {
        List<Annotation> annotations = new ArrayList<Annotation>();

        for(Method method : scannerClass.getDeclaredMethods()) {
            if(!method.isAnnotationPresent(allowInjectClass)) {
                continue;
            }

            annotations.add(method.getAnnotation(allowInjectClass));
        }

        return annotations;
    }

    /**
     * 使用Java反射得到注解的信息
     * @param annotation
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object getAnnotationInfo(Annotation annotation , String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if(annotation == null) {
            return null;
        }

        Method method = annotation.getClass().getDeclaredMethod(methodName, null);
        return method.invoke(annotation, null);
    }
}
