package de.digitalcollections.iiif.hymir;

import com.jayway.jsonpath.DocumentContext;
import com.revinate.assertj.json.JsonPathAssert;
import org.assertj.core.api.Assertions;

public class HymirAssertions extends Assertions {
  public static JsonPathAssert assertThat(DocumentContext ctx) {
    return new JsonPathAssert(ctx);
  }
}
