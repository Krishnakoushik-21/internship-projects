package com.studentmanagement.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Reusable generic helpers. These generic methods (GenericUtils.&lt;T&gt;) are
 * genuinely used by StudentService for filtering, sorting, mapping and searching,
 * so this is NOT just {@code List<Student>} usage.
 *
 * @param <T> the element type this sorter instance works with
 */
public class GenericUtils<T> {

    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public List<T> sortedCopy(Comparator<T> comparator) {
        return items.stream().sorted(comparator).collect(Collectors.toList());
    }

    public Optional<T> findFirst(Predicate<T> predicate) {
        return items.stream().filter(predicate).findFirst();
    }

    // ---------- Static reusable generic methods (used by StudentService) ----------

    public static <T> List<T> filter(List<T> source, Predicate<T> predicate) {
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T> List<T> sorted(List<T> source, Comparator<T> comparator) {
        return source.stream().sorted(comparator).collect(Collectors.toList());
    }

    public static <T> void sortInPlace(List<T> list, Comparator<T> comparator) {
        list.sort(comparator);
    }

    public static <T> Optional<T> findFirst(List<T> source, Predicate<T> predicate) {
        return source.stream().filter(predicate).findFirst();
    }

    public static <T, R> List<R> map(List<T> source, Function<T, R> mapper) {
        return source.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T> long countIf(List<T> source, Predicate<T> predicate) {
        return source.stream().filter(predicate).count();
    }

    public static <T> void forEach(List<T> source, Consumer<T> action) {
        source.forEach(action);
    }
}
