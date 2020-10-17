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
public class LifoPathQueue<V, W, E extends Edge<V, W>> extends AbstractPathQueue<V, W, E> {
  @Override
  protected void addToQueue(Path<V, W, E> path) {
    paths.push(path);
  }

  @Override
  public boolean removeFromQueue(Path<V, W, E> path) {
    return paths.remove(path);
  }

  @Override
  public boolean hasNext() {
    return !paths.isEmpty();
  }

  @Override
  public Path<V, W, E> next() {
    return paths.pop();
  }

  @Override
  public int size() {
    return paths.size();
  }

  private final Deque<Path<V, W, E>> paths = new ArrayDeque<>();
}
