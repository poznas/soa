package com.agh.soa.utils;

import java.lang.reflect.InvocationTargetException;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import org.apache.commons.beanutils.BeanUtils;

@Log
@UtilityClass
public class ObjectUtils {

  public static void copyProperties(Object dest, Object source) {
    try {
      BeanUtils.copyProperties(dest, source);
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.warning(e.getMessage());
    }
  }

}
