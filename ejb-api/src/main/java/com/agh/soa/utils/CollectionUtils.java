package com.agh.soa.utils;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectionUtils {

  public static <T, E> List<T> mapList(Collection<E> collection, Function<E, T> function) {
    return safeStream(collection).map(function).collect(toList());
  }

  public static <E> List<E> filterList(Collection<E> collection, Predicate<E> predicate) {
    return safeStream(collection).filter(predicate).collect(toList());
  }

  private static <E> Stream<E> safeStream(Collection<E> collection) {
    return ofNullable(collection).orElse(emptyList()).stream();
  }
}
