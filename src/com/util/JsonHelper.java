package com.util;

import java.lang.reflect.Array;  
import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
import java.lang.reflect.ParameterizedType;  
import java.lang.reflect.Type;  
import java.text.SimpleDateFormat;  
import java.util.ArrayList;  
import java.util.Collection;  
import java.util.Date;  
import java.util.HashMap;  
import java.util.HashSet;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Locale;  
import java.util.Map;  
import java.util.Set;  
  
import org.json.JSONArray;  
import org.json.JSONException;  
import org.json.JSONObject;  
import org.json.JSONStringer;  
  
import android.util.Log; 

public class JsonHelper {

	private static String TAG = "JsonHelper";  
	   
    public static String toJSON(Object obj) {  
        JSONStringer js = new JSONStringer();  
        serialize(js, obj);  
        return js.toString();  
    } 
    
    public static JSONStringer toJSONString(Object obj) {  
        JSONStringer js = new JSONStringer();  
        serialize(js, obj);  
        return js; 
    }  
    
    private static void serialize(JSONStringer js, Object o) {  
        if (isNull(o)) {  
            try {  
                js.value(null);  
            } catch (JSONException e) {  
                e.printStackTrace();  
            }  
            return;  
        }  
  
        Class<?> clazz = o.getClass();  
        if (isObject(clazz)) { // ����  
            serializeObject(js, o);  
        } else if (isArray(clazz)) { // ����  
            serializeArray(js, o);  
        } else if (isCollection(clazz)) { // ����  
            Collection<?> collection = (Collection<?>) o;  
            serializeCollect(js, collection);  
        }else if (isMap(clazz)) { // ����  
            HashMap<?,?> collection = (HashMap<?,?>) o;  
            serializeMap(js, collection);  
        } else { // ����ֵ  
            try {  
                js.value(o);  
            } catch (JSONException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    
    private static void serializeArray(JSONStringer js, Object array) {  
        try {  
            js.array();  
            for (int i = 0; i < Array.getLength(array); ++i) {  
                Object o = Array.get(array, i);  
                serialize(js, o);  
            }  
            js.endArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
     
    private static void serializeCollect(JSONStringer js, Collection<?> collection) {  
        try {  
            js.array();  
            for (Object o : collection) {  
                serialize(js, o);  
            }  
            js.endArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
     
    private static void serializeMap(JSONStringer js, Map<?,?> map) {  
        try {  
            js.object();  
            @SuppressWarnings("unchecked")  
            Map<String, Object> valueMap = (Map<String, Object>) map;  
            Iterator<Map.Entry<String, Object>> it = valueMap.entrySet().iterator();  
            while (it.hasNext()) {  
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();  
                js.key(entry.getKey());  
                serialize(js,entry.getValue());  
            }  
            js.endObject();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    private static void serializeObject(JSONStringer js, Object obj) {  
        try {  
            js.object();  
            Class<? extends Object> objClazz = obj.getClass();  
            Method[] methods = objClazz.getDeclaredMethods();     
            Field[] fields = objClazz.getDeclaredFields();       
            for (Field field : fields) {     
                try {     
                    String fieldType = field.getType().getSimpleName();     
                    String fieldGetName = parseMethodName(field.getName(),"get");     
                    if (!haveMethod(methods, fieldGetName)) {     
                        continue;     
                    }     
                    Method fieldGetMet = objClazz.getMethod(fieldGetName, new Class[] {});     
                    Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});     
                    String result = null;     
                    if ("Date".equals(fieldType)) {     
                        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);     
                        //result = sdf.format((Date)fieldVal);    
                    	long ticks = ((Date)fieldVal).getTime();
                    	result = String.format("/Date(%d+0800)/",ticks);
  
                    } else {     
                        if (null != fieldVal) {     
                            result = String.valueOf(fieldVal);     
                        }     
                    }     
                    js.key(field.getName());  
                    serialize(js, result);    
                } catch (Exception e) {     
                    continue;     
                }     
            }    
            js.endObject();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static boolean haveMethod(Method[] methods, String fieldMethod) {  
        for (Method met : methods) {  
            if (fieldMethod.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    public static String parseMethodName(String fieldName,String methodType) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        return methodType + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);  
    }  
      
    public static void setFieldValue(Object obj, Map<String, String> valMap) {     
        Class<?> cls = obj.getClass();     
        Method[] methods = cls.getDeclaredMethods();     
        Field[] fields = cls.getDeclaredFields();     
    
        for (Field field : fields) {     
            try {       
                String setMetodName = parseMethodName(field.getName(),"set");     
                if (!haveMethod(methods, setMetodName)) {     
                    continue;     
                }     
                Method fieldMethod = cls.getMethod(setMetodName, field     
                        .getType());     
                String value = valMap.get(field.getName());     
                if (null != value && !"".equals(value)) {     
                    String fieldType = field.getType().getSimpleName();     
                    if ("String".equals(fieldType)) {     
                        fieldMethod.invoke(obj, value);     
                    } else if ("Date".equals(fieldType)) {     
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);     
                        Date temp = sdf.parse(value);      
                        fieldMethod.invoke(obj, temp);     
                    } else if ("Integer".equals(fieldType)     
                            || "int".equals(fieldType)) {     
                        Integer intval = Integer.parseInt(value);     
                        fieldMethod.invoke(obj, intval);     
                    } else if ("Long".equalsIgnoreCase(fieldType)) {     
                        Long temp = Long.parseLong(value);     
                        fieldMethod.invoke(obj, temp);     
                    } else if ("Double".equalsIgnoreCase(fieldType)) {     
                        Double temp = Double.parseDouble(value);     
                        fieldMethod.invoke(obj, temp);     
                    } else if ("Boolean".equalsIgnoreCase(fieldType)) {     
                        Boolean temp = Boolean.parseBoolean(value);     
                        fieldMethod.invoke(obj, temp);     
                    } else {     
                        System.out.println("setFieldValue not supper type:" + fieldType);     
                    }     
                }     
            } catch (Exception e) {     
                continue;     
            }     
        }     
    
    }   
    
    public static Map<String, String> beanToMap(Object obj) {     
        Class<?> cls = obj.getClass();     
        Map<String, String> valueMap = new HashMap<String, String>();    
        Method[] methods = cls.getDeclaredMethods();     
        Field[] fields = cls.getDeclaredFields();       
        for (Field field : fields) {     
            try {     
                String fieldType = field.getType().getSimpleName();     
                String fieldGetName = parseMethodName(field.getName(),"get");     
                if (!haveMethod(methods, fieldGetName)) {     
                    continue;     
                }     
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});     
                Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});     
                String result = null;     
                if ("Date".equals(fieldType)) {     
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);     
                    result = sdf.format((Date)fieldVal);     
  
                } else {     
                    if (null != fieldVal) {     
                        result = String.valueOf(fieldVal);     
                    }     
                }     
                valueMap.put(field.getName(), result);     
            } catch (Exception e) {     
                continue;     
            }     
        }     
        return valueMap;     
    
    }     
  
    public static void setFiedlValue(Object obj,Method fieldSetMethod,String fieldType,Object value){  
             
        try {      
            if (null != value && !"".equals(value)) {      
                if ("String".equals(fieldType)) {     
                    fieldSetMethod.invoke(obj, value.toString());     
                } else if ("Date".equals(fieldType)) {     
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);     
                    Date temp = sdf.parse(value.toString());      
                    fieldSetMethod.invoke(obj, temp);     
                } else if ("Integer".equals(fieldType)     
                        || "int".equals(fieldType)) {     
                    Integer intval = Integer.parseInt(value.toString());     
                    fieldSetMethod.invoke(obj, intval);     
                } else if ("Long".equalsIgnoreCase(fieldType)) {     
                    Long temp = Long.parseLong(value.toString());     
                    fieldSetMethod.invoke(obj, temp);     
                } else if ("Double".equalsIgnoreCase(fieldType)) {     
                    Double temp = Double.parseDouble(value.toString());     
                    fieldSetMethod.invoke(obj, temp);     
                } else if ("Boolean".equalsIgnoreCase(fieldType)) {     
                    Boolean temp = Boolean.parseBoolean(value.toString());     
                    fieldSetMethod.invoke(obj, temp);     
                } else {     
                    fieldSetMethod.invoke(obj, value);   
                    Log.e(TAG, TAG  + ">>>>setFiedlValue -> not supper type" + fieldType);     
                }   
            }  
                  
        } catch (Exception e) {     
//            Log.e(TAG, TAG  + ">>>>>>>>>>set value error.",e);  
            e.printStackTrace();  
        }     
      
    }  
      
    public static <T> T parseObject(JSONObject jo, Class<T> clazz) throws JSONException {  
        if (clazz == null || isNull(jo)) {  
            return null;  
        }  
  
        T obj = newInstance(clazz);  
        if (obj == null) {  
            return null;  
        }  
        if(isMap(clazz)){   
            setField(obj,jo);  
        }else{  
              // ȡ��bean������з���     
            Method[] methods = clazz.getDeclaredMethods();     
            Field[] fields = clazz.getDeclaredFields();               
            for (Field f : fields) {  
                String setMetodName = parseMethodName(f.getName(),"set");     
                if (!haveMethod(methods, setMetodName)) {     
                    continue;     
                }                   
                try {  
                    Method fieldMethod = clazz.getMethod(setMetodName, f.getType());  
                    setField(obj,fieldMethod,f, jo);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }    
            }  
        }  
        return obj;  
    }  
      
    public static <T> T parseObject(String jsonStr, Class<T> clazz) throws JSONException {  
        if (clazz == null || jsonStr == null || jsonStr.length() == 0) {  
            return null;  
        }  
          
        JSONObject jo = null;  
        jo = new JSONObject(jsonStr);  
        if (isNull(jo)) {  
            return null;  
        }  
  
        return parseObject(jo, clazz);  
    }  
  
    public static <T> T[] parseArray(JSONArray ja, Class<T> clazz) {  
        if (clazz == null || isNull(ja)) {  
            return null;  
        }  
  
        int len = ja.length();  
  
        @SuppressWarnings("unchecked")  
        T[] array = (T[]) Array.newInstance(clazz, len);  
  
        for (int i = 0; i < len; ++i) {  
            try {  
                JSONObject jo = ja.getJSONObject(i);  
                T o = parseObject(jo, clazz);  
                array[i] = o;  
            } catch (JSONException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return array;  
    }  
  
    public static <T> T[] parseArray(String jsonStr, Class<T> clazz) {  
        if (clazz == null || jsonStr == null || jsonStr.length() == 0) {  
            return null;  
        }  
        JSONArray jo = null;  
        try {  
            jo = new JSONArray(jsonStr);  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
  
        if (isNull(jo)) {  
            return null;  
        }  
  
        return parseArray(jo, clazz);  
    }  
  
    @SuppressWarnings("unchecked")  
    public static <T> Collection<T> parseCollection(JSONArray ja, Class<?> collectionClazz,  
            Class<T> genericType) throws JSONException {  
  
        if (collectionClazz == null || genericType == null || isNull(ja)) {  
            return null;  
        }  
  
        Collection<T> collection = (Collection<T>) newInstance(collectionClazz);  
  
        for (int i = 0; i < ja.length(); ++i) {  
            try {  
                JSONObject jo = ja.getJSONObject(i);  
                T o = parseObject(jo, genericType);  
                collection.add(o);  
            } catch (JSONException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return collection;  
    }  
  
    public static <T> Collection<T> parseCollection(String jsonStr, Class<?> collectionClazz,  
            Class<T> genericType) throws JSONException {  
        if (collectionClazz == null || genericType == null || jsonStr == null  
                || jsonStr.length() == 0) {  
            return null;  
        }  
        JSONArray jo = null;  
        try {  
            //���Ϊ���飬��˴�ת��ʱ����Ҫȥ��ǰ��ļ�ֱ�Ӻ����[]�е�ֵ  
            int index = jsonStr.indexOf("[");  
            String arrayString=null;   
              
            //��ȡ������ַ�  
            if(-1!=index){  
                arrayString = jsonStr.substring(index);  
            }  
              
            //���Ϊ���飬ʹ������ת��  
            if(null!=arrayString){  
                jo = new JSONArray(arrayString);  
            }  
            else{  
                jo = new JSONArray(jsonStr);  
            }  
              
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
  
        if (isNull(jo)) {  
            return null;  
        }  
  
        return parseCollection(jo, collectionClazz, genericType);  
    }  
  
    /** 
     * ������ʹ������� 
     * @param clazz ��ʵ������� 
     * @return  ʵ����� 
     * @throws JSONException  
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    private static <T> T newInstance(Class<T> clazz) throws JSONException {  
        if (clazz == null)  
            return null;  
        T obj = null;  
        if (clazz.isInterface()) {  
            if (clazz.equals(Map.class)) {  
                obj = (T) new HashMap();  
            }else if (clazz.equals(List.class)) {  
                obj = (T) new ArrayList();  
            }else if (clazz.equals(Set.class)) {  
                obj = (T) new HashSet();  
            }else{  
                throw new JSONException("unknown interface: " + clazz);  
            }  
        }else{  
            try {  
                obj = clazz.newInstance();  
            }catch (Exception e) {  
                throw new JSONException("unknown class type: " + clazz);  
            }  
        }     
        return obj;  
    }  
      
    /** 
     * �趨Map��ֵ 
     * @param obj   ��ֵ�ֶεĶ��� 
     * @param jo    jsonʵ�� 
     */  
    private static void setField(Object obj, JSONObject jo) {  
        try {  
            @SuppressWarnings("unchecked")  
            Iterator<String> keyIter = jo.keys();  
            String key;  
            Object value;  
            @SuppressWarnings("unchecked")  
            Map<String, Object> valueMap = (Map<String, Object>) obj;  
            while (keyIter.hasNext()) {  
                key = (String) keyIter.next();  
                value = jo.get(key);                  
                valueMap.put(key, value);  
  
            }  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
    }     
      
    /** 
     * �趨�ֶε�ֵ 
     * @param obj   ��ֵ�ֶεĶ��� 
     * @param fieldSetMethod    �ֶη����� 
     * @param field �ֶ� 
     * @param jo    jsonʵ�� 
     */  
    private static void setField(Object obj, Method fieldSetMethod,Field field, JSONObject jo) {  
        String name = field.getName();  
        Class<?> clazz = field.getType();       
        try {  
            if (isArray(clazz)) { // ����  
                Class<?> c = clazz.getComponentType();  
                JSONArray ja = jo.optJSONArray(name);  
                if (!isNull(ja)) {  
                    Object array = parseArray(ja, c);  
                    setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), array);  
                }  
            } else if (isCollection(clazz)) { // ���ͼ���  
                // ��ȡ����ķ�������  
                Class<?> c = null;  
                Type gType = field.getGenericType();  
                if (gType instanceof ParameterizedType) {  
                    ParameterizedType ptype = (ParameterizedType) gType;  
                    Type[] targs = ptype.getActualTypeArguments();  
                    if (targs != null && targs.length > 0) {  
                        Type t = targs[0];  
                        c = (Class<?>) t;  
                    }  
                }  
  
                JSONArray ja = jo.optJSONArray(name);  
                if (!isNull(ja)) {  
                    Object o = parseCollection(ja, clazz, c);  
                    setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), o);  
                }  
            } else if (isSingle(clazz)) { // ֵ����  
                Object o = jo.opt(name);  
                if (o != null) {  
                    setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), o);  
                }  
            } else if (isObject(clazz)) { // ����  
                JSONObject j = jo.optJSONObject(name);  
                if (!isNull(j)) {  
                    Object o = parseObject(j, clazz);  
                    setFiedlValue(obj, fieldSetMethod, clazz.getSimpleName(), o);  
                }  
            } else if (isList(clazz)) { // �б�  
//              JSONObject j = jo.optJSONObject(name);  
//              if (!isNull(j)) {  
//                  Object o = parseObject(j, clazz);  
//                  f.set(obj, o);  
//              }  
            } else {  
                throw new Exception("unknow type!");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * �趨�ֶε�ֵ  
     * @param obj   ��ֵ�ֶεĶ��� 
     * @param field �ֶ� 
     * @param jo    jsonʵ�� 
     */  
    @SuppressWarnings("unused")  
    private static void setField(Object obj, Field field, JSONObject jo) {  
        String name = field.getName();  
        Class<?> clazz = field.getType();  
        try {  
            if (isArray(clazz)) { // ����  
                Class<?> c = clazz.getComponentType();  
                JSONArray ja = jo.optJSONArray(name);  
                if (!isNull(ja)) {  
                    Object array = parseArray(ja, c);  
                    field.set(obj, array);  
                }  
            } else if (isCollection(clazz)) { // ���ͼ���  
                // ��ȡ����ķ�������  
                Class<?> c = null;  
                Type gType = field.getGenericType();  
                if (gType instanceof ParameterizedType) {  
                    ParameterizedType ptype = (ParameterizedType) gType;  
                    Type[] targs = ptype.getActualTypeArguments();  
                    if (targs != null && targs.length > 0) {  
                        Type t = targs[0];  
                        c = (Class<?>) t;  
                    }  
                }  
                JSONArray ja = jo.optJSONArray(name);  
                if (!isNull(ja)) {  
                    Object o = parseCollection(ja, clazz, c);  
                    field.set(obj, o);  
                }  
            } else if (isSingle(clazz)) { // ֵ����  
                Object o = jo.opt(name);  
                if (o != null) {  
                    field.set(obj, o);  
                }  
            } else if (isObject(clazz)) { // ����  
                JSONObject j = jo.optJSONObject(name);  
                if (!isNull(j)) {  
                    Object o = parseObject(j, clazz);  
                    field.set(obj, o);  
                }  
            } else if (isList(clazz)) { // �б�  
                JSONObject j = jo.optJSONObject(name);  
                if (!isNull(j)) {  
                    Object o = parseObject(j, clazz);  
                    field.set(obj, o);  
                }  
            }else {  
                throw new Exception("unknow type!");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * �ж϶����Ƿ�Ϊ�� 
     * @param obj   ʵ�� 
     * @return 
     */  
    private static boolean isNull(Object obj) {  
        if (obj instanceof JSONObject) {  
            return JSONObject.NULL.equals(obj);  
        }  
        return obj == null;  
    }  
  
    /** 
     * �ж��Ƿ���ֵ����  
     * @param clazz  
     * @return 
     */  
    private static boolean isSingle(Class<?> clazz) {  
        return isBoolean(clazz) || isNumber(clazz) || isString(clazz);  
    }  
  
    /** 
     * �Ƿ񲼶�ֵ 
     * @param clazz  
     * @return 
     */  
    public static boolean isBoolean(Class<?> clazz) {  
        return (clazz != null)  
                && ((Boolean.TYPE.isAssignableFrom(clazz)) || (Boolean.class  
                        .isAssignableFrom(clazz)));  
    }  
  
    /** 
     * �Ƿ���ֵ  
     * @param clazz  
     * @return 
     */  
    public static boolean isNumber(Class<?> clazz) {  
        return (clazz != null)  
                && ((Byte.TYPE.isAssignableFrom(clazz)) || (Short.TYPE.isAssignableFrom(clazz))  
                        || (Integer.TYPE.isAssignableFrom(clazz))  
                        || (Long.TYPE.isAssignableFrom(clazz))  
                        || (Float.TYPE.isAssignableFrom(clazz))  
                        || (Double.TYPE.isAssignableFrom(clazz)) || (Number.class  
                        .isAssignableFrom(clazz)));  
    }  
  
    /** 
     * �ж��Ƿ����ַ�  
     * @param clazz  
     * @return 
     */  
    public static boolean isString(Class<?> clazz) {  
        return (clazz != null)  
                && ((String.class.isAssignableFrom(clazz))  
                        || (Character.TYPE.isAssignableFrom(clazz)) || (Character.class  
                        .isAssignableFrom(clazz)));  
    }  
  
    /** 
     * �ж��Ƿ��Ƕ��� 
     * @param clazz  
     * @return 
     */  
    private static boolean isObject(Class<?> clazz) {  
        return clazz != null && !isSingle(clazz) && !isArray(clazz) && !isCollection(clazz) && !isMap(clazz);  
    }  
  
    /** 
     * �ж��Ƿ�������  
     * @param clazz 
     * @return 
     */  
    public static boolean isArray(Class<?> clazz) {  
        return clazz != null && clazz.isArray();  
    }  
  
    /** 
     * �ж��Ƿ��Ǽ��� 
     * @param clazz 
     * @return 
     */  
    public static boolean isCollection(Class<?> clazz) {  
        return clazz != null && Collection.class.isAssignableFrom(clazz);  
    }  
          
    /** 
     * �ж��Ƿ���Map 
     * @param clazz 
     * @return 
     */  
    public static boolean isMap(Class<?> clazz) {  
        return clazz != null && Map.class.isAssignableFrom(clazz);  
    }  
      
    /** 
     * �ж��Ƿ����б�  
     * @param clazz 
     * @return 
     */  
    public static boolean isList(Class<?> clazz) {  
        return clazz != null && List.class.isAssignableFrom(clazz);  
    }  
      
}  
