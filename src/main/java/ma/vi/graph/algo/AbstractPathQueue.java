package ma.vi.graph.algo;

import ma.vi.graph.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public abstract class AbstractPathQueue<V, W> implements PathQueue<V, W>  {
  @Override
  public final void add(Path<V, W> path) {
    endToPathMap.put(path.lastVertex().orElse(null), path);
    addToQueue(path);
  }

  protected abstract void addToQueue(Path<V, W> path);

  @Override
  public boolean hasPathEndingAt(V vertex) {
    return endToPathMap.containsKey(vertex);
  }

  @Override
  public Path<V, W> pathEndingAt(V vertex) {
    return endToPathMap.get(vertex);
  }

  @Override
  public final boolean remove(Path<V, W> path) {
    V vertex = path.lastVertex().orElse(null);
    if (endToPathMap.containsKey(vertex)) {
      removeFromQueue(path);
      endToPathMap.remove(vertex);
      return true;
    }
    return false;
  }

  protected abstract boolean removeFromQueue(Path<V, W> path);

  @Override
  public int size() {
    return endToPathMap.size();
  }

  private final Map<V, Path<V, W>> endToPathMap = new HashMap<>();
}
