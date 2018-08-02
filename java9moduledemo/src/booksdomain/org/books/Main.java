package org.books;

import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
public class Main{
  public static void main(String ...args) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    List<Book> books = mapper.readValue(System.in, new TypeReference<List<Book>>() {});
    books.forEach( t -> System.out.format( "%n%s: %s%n", t.getDate(), t.getTitle()));
  }
}
