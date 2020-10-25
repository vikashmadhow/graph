package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.path.Path;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A path-queue returning paths in last-in-first-out order. Using this
 * path-queue in the search and explore functions produces depth-first
 * exploration behaviour.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class LifoPathQueue<V, W> extends AbstractPathQueue<V, W> {
  @Override
  protected void addToQueue(Path<V, W> path) {
    paths.push(path);
  }

  @Override
  public boolean removeFromQueue(Path<V, W> path) {
    return paths.remove(path);
  }

  @Override
  public boolean hasNext() {
    return !paths.isEmpty();
  }

  @Override
  public Path<V, W> next() {
    return paths.pop();
  }

  @Override
  public int size() {
    return paths.size();
  }

  private final Deque<Path<V, W>> paths = new ArrayDeque<>();
}
