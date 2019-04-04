package common;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by signapoop on 1/4/19.
 */
public class Utils {

    public static byte[] marshal(Object obj) {
        List message = new ArrayList();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {

                Object o = field.get(obj);
                String type = field.getGenericType().getTypeName().split("[<>]")[0];

                switch (type) {
                    case "java.lang.String":
                        appendMessage(message, (String) o);
                        break;
                    case "java.lang.Integer":
                    case "int":
                        appendMessage(message, (int) o);
                        break;
                    case "java.lang.Float":
                    case "float":
                        appendMessage(message, (float) o);
                        break;
                    case "int[]":
                        appendMessage(message, (int[]) o);
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return Utils.byteUnboxing(message);
    }

    public static Object unmarshal(byte[] b, Object obj) {
        int ptr = 0;

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {

            String type = field.getGenericType().getTypeName().split("[<>]")[0];

            int sourceLength = unmarshalInteger(b, ptr);
            ptr += Constants.INT_SIZE;

            switch (type) {
                case "java.lang.String":
                    String stringValue = unmarshalString(b, ptr, ptr + sourceLength);
                    ptr += sourceLength;
                    set(obj, field.getName(), stringValue);
                    break;
                case "java.lang.Integer":
                case "int":
                    int intValue = unmarshalInteger(b, ptr);
                    ptr += sourceLength;
                    set(obj, field.getName(), intValue);
                    break;
                case "java.lang.Float":
                case "float":
                    float floatValue = unmarshalFloat(b, ptr);
                    ptr += sourceLength;
                    set(obj, field.getName(), floatValue);
                    break;
                case "int[]":
                    int[] intArrValue = unmarshalIntArray(b, ptr, ptr + sourceLength);
                    ptr += sourceLength;
                    set(obj, field.getName(), intArrValue);
                    break;
            }
        }
        return obj;
    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    public static byte[] marshal(int x) {
        return new byte[]{
                (byte) (x >> 24),
                (byte) (x >> 16),
                (byte) (x >> 8),
                (byte) (x >> 0)
        };
    }

    public static int unmarshalInteger(byte[] b, int start) {
        return b[start] << 24 | (b[start + 1] & 0xFF) << 16 | (b[start + 2] & 0xFF) << 8 | (b[start + 3] & 0xFF);
    }

    public static byte[] marshal(float f) {
        return ByteBuffer.allocate(Constants.FLOAT_SIZE).putFloat(f).array();
    }

    public static float unmarshalFloat(byte[] b, int start) {
        byte[] content = new byte[]{
                b[start], b[start + 1], b[start + 2], b[start + 3]
        };
        return ByteBuffer.wrap(content).order(ByteOrder.BIG_ENDIAN).getFloat();
    }

    public static byte[] marshal(String s) {
        byte[] ret = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            ret[i] = (byte) s.charAt(i);
        }
        return ret;
    }

    public static String unmarshalString(byte[] b, int start, int end) {
        char[] c = new char[end - start];
        for (int i = start; i < end; i++) {
            c[i - start] = (char) (b[i]);
        }
        return new String(c);
    }

    public static byte[] marshal(int[] array) {
        byte[] ret = new byte[array.length * Constants.INT_SIZE];
        for (int i = 0; i < array.length; i++) {
            byte[] num = marshal(array[i]);
            for (int j = 0; j < Constants.INT_SIZE; j++)
                ret[i * Constants.INT_SIZE + j] = num[j];
        }
        return ret;
    }

    public static int[] unmarshalIntArray(byte[] b, int start, int end) {
        int[] array = new int[(end - start) / Constants.INT_SIZE];
        for (int i = 0; i < array.length; i++) {
            int startIndex = start + i * Constants.INT_SIZE;
            array[i] = unmarshalInteger(Arrays.copyOfRange(b, startIndex, startIndex + Constants.INT_SIZE), 0);
        }
        return array;
    }

    public static Byte[] byteBoxing(byte[] b) {
        Byte[] ret = new Byte[b.length];
        for (int i = 0; i < b.length; i++)
            ret[i] = Byte.valueOf(b[i]);
        return ret;
    }

    public static byte[] byteUnboxing(Byte[] b) {
        byte[] ret = new byte[b.length];
        for (int i = 0; i < b.length; i++)
            ret[i] = b[i].byteValue();
        return ret;
    }

    public static byte[] byteUnboxing(List list) {
        return Utils.byteUnboxing((Byte[]) list.toArray(new Byte[list.size()]));
    }

    public static void appendMessage(List list, int x) {
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                Constants.INT_SIZE
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                x
        ))));
    }

    public static void appendMessage(List list, float f) {
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                Constants.FLOAT_SIZE
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                f
        ))));
    }

    public static void appendMessage(List list, String s) {
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                s.length()
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                s
        ))));
    }

    public static void appendMessage(List list, int[] array) {
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                Constants.INT_SIZE * array.length
        ))));

        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                array
        ))));
    }

    public static void append(List list, int x) {
        list.addAll(Arrays.asList(Utils.byteBoxing(Utils.marshal(
                x
        ))));
    }
}
