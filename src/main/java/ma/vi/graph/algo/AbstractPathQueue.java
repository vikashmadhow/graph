package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.path.Path;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public abstract class AbstractPathQueue<V, W, E extends Edge<V, W>> implements PathQueue<V, W, E>  {
  @Override
  public boolean hasPathEndingAt(V vertex) {
    return false;
  }

  @Override
  public Path<V, W, E> pathEndingAt(V vertex) {
    return null;
  }

  @Override
  public boolean removePathEnd(V vertex) {
    return false;
  }

  @Override
  public int size() {
    return 0;
  }
}
