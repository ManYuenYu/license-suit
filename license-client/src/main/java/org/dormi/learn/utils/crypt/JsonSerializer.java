/**
 * @author : dormi330
 * @date : 2020-03-03
 * description : 文件描述
 */

package org.dormi.learn.utils.crypt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;


public class JsonSerializer {

    public final static String DEFALUT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** ObjectMapper 线程安全, 公用一个节约性能 */
    public final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        /** 在反序列化时忽略在 json 中存在但 Java 对象不存在的属性  */
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        /** 在序列化时日期格式 */
        DateFormat df = new SimpleDateFormat(DEFALUT_TIMESTAMP_FORMAT, Locale.CHINA);
        objectMapper.setDateFormat(df);

        /** 在序列化时忽略值为 null 的属性  */
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static String objectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("object -> json 异常", ex);
        }
    }

    private static String objectToPrettyJson(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("object -> json 异常", ex);
        }
    }

    /**
     * 返回一个 定制(日期/排除序列化字段)的 Gson对象, 用于进行 序列化/反序列化
     *
     * @return gosn对象
     */
    public static String prettyJson(Object obj) {
        return objectToPrettyJson(obj);
    }

    public static String toJson(Object obj) {
        return objectToJson(obj);
    }

    /** json string -> 对象 */
    public static <T> T jsonToObj(String json, Class<T> type) {
        if (null == json) {
            return null;
        }

        try {
            return objectMapper.readValue(json, type);
        } catch (IOException ex) {
            throw new RuntimeException("json -> object 异常", ex);
        }
    }

    public static <T> T jsonToObj(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException ex) {
            throw new RuntimeException("json -> object 异常", ex);
        }

    }


//    /** 范型 */
//    public static <T> List<T> getList(final Class<T[]> clazz, final String json) {
//        final T[] jsonToObject = formattedGson().fromJson(json, clazz);
//        return Arrays.asList(jsonToObject);
//    }

    /** json -> list[对象] */
    public static <T> String list2String(Collection<T> list) {
        return objectToJson(list);
    }
//
//    /** json string -> JsonObject */
//    public static JsonObject fromString(String json) {
//        return formattedGson().fromJson(json, JsonObject.class);
//    }
}
