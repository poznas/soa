package com.agh.soa.lab8.mime;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Produces("text/uri-list")
public class UriListMessageBodyWriter implements MessageBodyWriter<List<String>> {

  @Override
  public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations,
                             MediaType mediaType) {
    return true;
  }

  @Override
  public void writeTo(List<String> strings, Class<?> aClass, Type type, Annotation[] annotations,
                      MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap,
                      OutputStream outputStream) {
    var writer = new PrintWriter(outputStream);
    strings.forEach(writer::println);
    writer.flush();
  }
}
