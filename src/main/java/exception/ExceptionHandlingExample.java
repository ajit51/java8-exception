package exception;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ExceptionHandlingExample {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("10", "20", "xy");
        List<Integer> list1 = Arrays.asList(1, 0);
        //list1.forEach(handleGenericException(s -> System.out.println(10 / s), ArithmeticException.class));
        //list.forEach(handleGenericException(s -> System.out.println(Integer.parseInt(s)), NumberFormatException.class));
        //list.forEach(handleExceptionIfAny(s -> System.out.println(Integer.parseInt(s))));
        /*List<Integer> intList = list.stream().map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(intList);*/

        //Handle exception for checkedException
        List<Integer> list2 = Arrays.asList(10, 20);
        list2.forEach(handleCheckedExceptionConsumer(s -> {
            Thread.sleep(s);
            System.out.println(s);
        }));
    }

    //approach - 1
    public static void printList(String s) {
        try {
            System.out.println(Integer.parseInt(s));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    //approach - 2
    public static Consumer<String> handleExceptionIfAny(Consumer<String> payload) {
        return obj -> {
            try {
                payload.accept(obj);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        };
    }

    //approach - 3
    static <Target, ExObj extends Exception> Consumer<Target> handleGenericException
    (Consumer<Target> targetConsumer, Class<ExObj> exObjClass) {
        return obj -> {
            try {
                targetConsumer.accept(obj);
            } catch (Exception ex) {
                try {
                    ExObj exObj = exObjClass.cast(ex);
                    System.out.println("Exception: " + ex.getMessage());
                } catch (ClassCastException ecx) {
                    throw ecx;
                }
            }
        };
    }

    static <Target> Consumer<Target> handleCheckedExceptionConsumer(CheckedExceptionHandlerConsumer<Target, Exception> handlerConsumer){
        return obj -> {
            try {
                handlerConsumer.accept(obj);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
