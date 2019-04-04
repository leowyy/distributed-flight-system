package common;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.beans.*;
import java.lang.reflect.Method;


/**
 * Created by signapoop on 1/4/19.
 */
public class Utils {

    public static byte[] marshal(Object obj) throws UnsupportedEncodingException {
        List message = new ArrayList();

        //marshallObject(obj, res);

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field: fields) {
            try {
                appendMessage(message, field.getName());

                Object o = field.get(obj);
                String type = field.getGenericType().getTypeName().split("[<>]")[0];
//                System.out.println(field.getName());
//                System.out.println(o);
//                System.out.println(type);

                switch (type) {
                    case "java.lang.String":
                        appendMessage(message,(String) o);
                        break;
                    case "java.lang.Integer":
                    case "int":
                        appendMessage(message,(int) o);
                        break;
                    case "java.lang.Float":
                    case "float":
                        appendMessage(message,(float) o);
                        break;
                    case "int[]":
                        appendMessage(message,(int[]) o);
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Utils.byteUnboxing(message);
    }

    public static Object unmarshal(byte[] b, Class<?> clazz) {
        Object obj;
        try {
            obj = clazz.getConstructor().newInstance();
        } catch (Exception e) {
            System.out.printf("Empty constructor not defined for {}\n", clazz);
            e.printStackTrace();
            return null;
        }
        int ptr = 0;
        do {
            int sourceLength = unmarshalInteger(b, ptr);
            ptr += Constants.INT_SIZE;

            String propertyName = unmarshalString(b, ptr, ptr+sourceLength);
            ptr += sourceLength;

            PropertyDescriptor propDetails = null;
            try {
                propDetails = new PropertyDescriptor(propertyName, clazz);
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            String type = propDetails.getPropertyType().toString();
            Method setter = propDetails.getWriteMethod();
            try {
                switch (type) {
                    case "java.lang.String":
                        sourceLength = unmarshalInteger(b, ptr);
                        ptr += Constants.INT_SIZE;

                        String stringValue = unmarshalString(b, ptr, ptr+sourceLength);
                        ptr += sourceLength;

                        setter.invoke(obj, stringValue);
                        break;
                    case "java.lang.Integer":
                    case "int":
                        int intValue = unmarshalMsgInteger(b, ptr);
                        ptr += Constants.INT_SIZE + Constants.INT_SIZE;

                        setter.invoke(obj, intValue);
                        break;
                    case "java.lang.Float":
                    case "float":
                        float floatValue = unmarshalMsgFloat(b, ptr);
                        ptr += Constants.INT_SIZE + Constants.FLOAT_SIZE;

                        setter.invoke(obj, floatValue);
                        break;
                    case "int[]":
                        int len = Utils.unmarshalInteger(b, ptr);
                        ptr += Constants.INT_SIZE;
                        int[] intArrValue = unmarshalIntArray(b, ptr, ptr+len);
                        setter.invoke(obj, intArrValue);
                        ptr += len;
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } while (ptr < b.length);

        return obj;
    }


    /**
     * Marshaling int into bytes
     * @param x {@code int}
     * @return {@code byte[]} marshaled integer
     * @since 1.9
     */
    public static byte[] marshal(int x){
        return new byte[]{
            (byte)(x >> 24),
            (byte)(x >> 16),
            (byte)(x >> 8),
            (byte)(x >> 0)
        };
    }

    /**
     * Unmarshaling bytes into int
     * @param b {@code byte[]}
     * @param start {@code int} start unmarshal index
     * @return {@code int} unmarshaled int
     * @since 1.9
     */
    public static int unmarshalInteger(byte[] b, int start){
        return b[start] << 24 | (b[start+1] & 0xFF) << 16 | (b[start+2] & 0xFF) << 8 | (b[start+3] & 0xFF);
    }

    /**
     * Unmarshaling bytes into int, with header
     * @param b {@code byte[]}
     * @param start {@code int} start index of int header
     * @return {@code float} unmarshaled int
     * @since 1.9
     */
    public static int unmarshalMsgInteger(byte[] b, int start){
        return Utils.unmarshalInteger(b, start+Constants.INT_SIZE);
    }

    /**
     * Marshaling float into bytes
     * @param f {@code float}
     * @return {@code byte[]} marshaled float
     * @since 1.9
     */
    public static byte[] marshal(float f){
        return ByteBuffer.allocate(Constants.FLOAT_SIZE).putFloat(f).array();
    }

    /**
     * Unmarshaling bytes into float
     * @param b {@code byte[]}
     * @param start {@code float} start unmarshal index
     * @return {@code float} unmarshaled float
     * @since 1.9
     */
    public static float unmarshalFloat(byte[] b, int start){
        byte[] content = new byte[]{
                b[start], b[start+1], b[start+2], b[start+3]
        };
        return ByteBuffer.wrap(content).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    /**
     * Unmarshaling bytes into float, with header
     * @param b {@code byte[]}
     * @param start {@code int} start index of float header
     * @return {@code float} unmarshaled float
     * @since 1.9
     */
    public static float unmarshalMsgFloat(byte[] b, int start){
        return Utils.unmarshalFloat(b, start+Constants.INT_SIZE);
    }

    /**
     * Marshaling String into bytes
     * @param s {@code String}
     * @return {@code byte[]} marshaled string
     * @throws UnsupportedEncodingExceptions
     * @since 1.9
     */
    public static byte[] marshal(String s) throws UnsupportedEncodingException{
        byte[] ret = new byte[s.length()];
        for(int i = 0; i < s.length(); i++) {
            ret[i] = (byte)s.charAt(i);
        }
        return ret;
    }

    /**
     * Unmarshaling bytes into String
     * @param b {@code byte[]}
     * @param start {@code int} start unmarshal index
     * @param end {@code int} end unmarshal index
     * @return {@code String} unmarshaled string
     * @since 1.9
     */
    public static String unmarshalString(byte[] b, int start, int end){
        char[] c = new char[end - start];
        for(int i = start; i < end; i++) {
            c[i-start] = (char)(b[i]);
        }
        return new String(c);
    }

    /**
     * Unmarshaling bytes into string, with header
     * @param b {@code byte[]}
     * @param start {@code int} start index of string header
     * @return {@code String} unmarshaled string
     * @since 1.9
     */
    public static String unmarshalMsgString(byte[] b, int start){
        int len = Utils.unmarshalInteger(b, start);
        int resStart = start + Constants.INT_SIZE;
        return Utils.unmarshalString(b, resStart, resStart+len);
    }

    public static byte[] marshal(int[] array) throws UnsupportedEncodingException{
        byte[] ret = new byte[array.length * Constants.INT_SIZE];
        for(int i = 0; i < array.length; i++) {
            byte[] num = marshal(array[i]);
            for(int j = 0; j < Constants.INT_SIZE; j++)
                ret[i*Constants.INT_SIZE + j] = num[j];
        }
        return ret;
    }

    public static int[] unmarshalIntArray(byte[] b, int start, int end){
        int[] array = new int[(end - start)/Constants.INT_SIZE];
        for(int i = 0; i < array.length; i++) {
            int startIndex = start + i* Constants.INT_SIZE;
            array[i] = unmarshalInteger(Arrays.copyOfRange(b, startIndex, startIndex+Constants.INT_SIZE),0);
        }
        return array;
    }

    public static int[] unmarshalMsgIntArray(byte[] b, int start){
        int len = Utils.unmarshalInteger(b, start);
        int resStart = start + Constants.INT_SIZE;
        return Utils.unmarshalIntArray(b, resStart, resStart+len);
    }

    /**
     * Convert array of byte into array of Byte (Class)
     * @param b {@code byte[]}
     * @return {@code Byte[]} Bytes object
     * @since 1.9
     */
    public static Byte[] byteBoxing(byte[] b){
        Byte[] ret = new Byte[b.length];
        for(int i = 0; i < b.length; i++)
            ret[i] = Byte.valueOf(b[i]);
        return ret;
    }

    /**
     * Convert array of Byte (Class) into array of byte
     * @param b {@code Byte[]}
     * @return {@code byte[]} bytes
     * @since 1.9
     */
    public static byte[] byteUnboxing(Byte[] b){
        byte[] ret = new byte[b.length];
        for(int i = 0; i < b.length; i++)
            ret[i] = b[i].byteValue();
        return ret;
    }

    /**
     * Convert list into array of byte
     * @param list {@code List}
     * @return {@code byte[]} converted list
     * @since 1.9
     */
    public static byte[] byteUnboxing(List list){
        return Utils.byteUnboxing((Byte[])list.toArray(new Byte[list.size()]));
    }

    /**
     * Append int to list (as bytes) with the header length
     * @param list {@code List} original list
     * @param x {@code int} int to be appended
     * @return {@code void}
     * @throws UnsupportedEncodingException
     * @since 1.9
     */
    public static void appendMessage(List list, int x)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                Constants.INT_SIZE
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                x
        ))));
    }

    /**
     * Append float to list (as bytes) with the header length
     * @param list {@code List} original list
     * @param f {@code float} float to be appended
     * @return {@code void}
     * @throws UnsupportedEncodingException
     * @since 1.9
     */
    public static void appendMessage(List list, float f)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                Constants.FLOAT_SIZE
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                f
        ))));
    }

    /**
     * Append String to list (as bytes) with the header length
     * @param list {@code List} original list
     * @param s {@code String} String to be appended
     * @return {@code void}
     * @throws UnsupportedEncodingException
     * @since 1.9
     */
    public static void appendMessage(List list, String s)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                s.length()
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                s
        ))));
    }

    public static void appendMessage(List list, int[] array)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                Constants.INT_SIZE*array.length
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                array
        ))));
    }

    /**
     * Append int to list (as bytes) without the header length
     * @param list {@code List} original list
     * @param x {@code int} int to be appended
     * @return {@code void}
     * @throws UnsupportedEncodingException
     * @since 1.9
     */
    public static void append(List list, int x)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                x
        ))));
    }

    /**
     * Append float to list (as bytes) without the header length
     * @param list {@code List} original list
     * @param f {@code float} float to be appended
     * @return {@code void}
     * @throws UnsupportedEncodingException
     * @since 1.9
     */
    public static void append(List list, float f)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                f
        ))));
    }

    /**
     * Append String to list (as bytes) without the header length
     * @param list {@code List} original list
     * @param s {@code String} String to be appended
     * @return {@code void}
     * @throws UnsupportedEncodingException
     * @since 1.9
     */
    public static void append(List list, String s)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                s
        ))));
    }

    public static void append(List list, int[] array)throws UnsupportedEncodingException{
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                array
        ))));
    }

    public static class testtest{
        public int prop1;
        public int[] prop2;
        public String prop3;
        public float prop4;

        public testtest(int prop1, int[] prop2, String prop3, float prop4) {
            this.prop1 = prop1;
            this.prop2 = prop2;
            this.prop3 = prop3;
            this.prop4 = prop4;
        }



        public void setProp1(int prop1) {
            this.prop1 = prop1;
        }

        public int getProp1() {
            return this.prop1;
        }
    }

    public static void main(String[] args) throws IOException, IntrospectionException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        int prop1 = 1;
        int[] prop2 = {1, 2, 3};
        String prop3 = "hello";
        float prop4 = 1.2f;

        Object test = new testtest(prop1,prop2,prop3,prop4);

//        BeanInfo beanInfo = Introspector.getBeanInfo(testtest.class);

//        testtest instance = (testtest) Beans.instantiate(
//                Utils.class.getClassLoader(),
//                beanInfo.getBeanDescriptor()
//                        .getBeanClass()
//                        .getName());

        Method setter = new PropertyDescriptor("prop1", testtest.class).getWriteMethod();
        setter.invoke(test, 5);
        ((testtest) test).getProp1();
        marshal(test);

//        System.out.println(Arrays.toString(marshal(1521331231)));
//        System.out.println(Arrays.toString(byteBoxing(marshal(5))));
    }
}
