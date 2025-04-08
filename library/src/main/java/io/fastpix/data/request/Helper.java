package io.fastpix.data.request;

import org.json.JSONException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

import org.json.JSONException;
import java.util.*;
import java.util.concurrent.Callable;

public class Helper {
    public Helper() {
    }

    public static <ValueType> ValueType lazyGet(ValueType value, Callable<ValueType> supplier) throws Exception {
        return value != null ? value : supplier.call();
    }

    public static <ValueType> ValueType let(ValueType value, Consumer<ValueType> consumer) throws JSONException {
        if (value != null) {
            consumer.apply(value);
            return value;
        } else {
            return null;
        }
    }

    public static <ElementType> ElementType firstOrNull(Collection<ElementType> collection, Function<ElementType, Boolean> condition) {
        for (ElementType element : collection) {
            if (Boolean.TRUE.equals(condition.apply(element))) {
                return element;
            }
        }
        return null;
    }

    public static <ElementType> List<ElementType> filter(List<ElementType> list, Function<ElementType, Boolean> condition) {
        List<ElementType> filteredList = new ArrayList<>();
        for (ElementType element : list) {
            if (Boolean.TRUE.equals(condition.apply(element))) {
                filteredList.add(element);
            }
        }
        return filteredList;
    }

    public static <ValueType> boolean noneOf(ValueType value, ValueType... values) {
        for (ValueType element : values) {
            if (value.equals(element)) {
                return false;
            }
        }
        return true;
    }

    public static <ValueType> boolean oneOf(ValueType value, ValueType... values) {
        for (ValueType element : values) {
            if (value.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public interface Function1<InputType, ReturnType> {
        ReturnType accept(InputType input);
    }

    public interface Consumer<ValueType> {
        void apply(ValueType value) throws JSONException;
    }
}


