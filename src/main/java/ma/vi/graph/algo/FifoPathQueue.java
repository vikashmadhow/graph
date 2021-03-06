package ma.vi.graph.algo;

import ma.vi.graph.Path;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A path-queue returning paths in first-in-first-out order. Using this
 * path-queue in the search and explore functions produces breadth-first
 * exploration behaviour.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class FifoPathQueue<V, W> extends AbstractPathQueue<V, W> {
  @Override
  protected void addToQueue(Path<V, W> path) {
    paths.addFirst(path);
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
    return paths.removeLast();
  }

  @Override
  public int size() {
    return paths.size();
  }

  private final Deque<Path<V, W>> paths = new ArrayDeque<>();
}
