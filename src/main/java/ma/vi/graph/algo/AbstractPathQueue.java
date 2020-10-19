package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.path.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public abstract class AbstractPathQueue<V, W, E extends Edge<V, W>> implements PathQueue<V, W, E>  {
  @Override
  public final void add(Path<V, W, E> path) {
    endToPathMap.put(path.lastVertex().orElse(null), path);
    addToQueue(path);
  }

  protected abstract void addToQueue(Path<V, W, E> path);

  @Override
  public boolean hasPathEndingAt(V vertex) {
    return endToPathMap.containsKey(vertex);
  }

  @Override
  public Path<V, W, E> pathEndingAt(V vertex) {
    return endToPathMap.get(vertex);
  }

  @Override
  public final boolean remove(Path<V, W, E> path) {
    V vertex = path.lastVertex().orElse(null);
    if (endToPathMap.containsKey(vertex)) {
      removeFromQueue(path);
      endToPathMap.remove(vertex);
      return true;
    }
    return false;
  }

  protected abstract boolean removeFromQueue(Path<V, W, E> path);

  @Override
  public int size() {
    return endToPathMap.size();
  }

  private final Map<V, Path<V, W, E>> endToPathMap = new HashMap<>();
}
