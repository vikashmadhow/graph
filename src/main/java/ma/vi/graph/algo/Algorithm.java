package ma.vi.graph.algo;

import ma.vi.base.lang.Builder;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface Algorithm<R> extends Builder<R> {
  default R execute() {
    return build();
  }
}
