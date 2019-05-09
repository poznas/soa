package com.agh.soa.lab7;

import static java.util.Objects.nonNull;
import static java.util.Optional.of;

import java.util.Collection;
import java.util.LinkedList;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LibraryContext {

  @Getter
  private static final Collection<LibraryConsumer> CONSUMERS = new LinkedList<>();

  public static void registerConsumer(@NotNull LibraryConsumer consumer) {
    of(consumer).filter(x -> nonNull(x.getReaderId())).ifPresent(CONSUMERS::add);
  }

  public static void removeConsumer(@NotNull Long readerId) {
    CONSUMERS.removeIf(x -> x.getReaderId().equals(readerId));
  }

}
