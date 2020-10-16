package ma.vi.graph.algo.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.path.Path;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A path-queue returning paths in first-in-first-out order. Using this
 * path-queue in the search and explore functions produces breadth-first
 * exploration behaviour.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class FifoPathQueue<V, W, E extends Edge<V, W>> implements PathQueue<V, W, E> {
  @Override
  public void add(Path<V, W, E> path) {
    paths.addFirst(path);
  }

  @Override
  public boolean hasNext() {
    return !paths.isEmpty();
  }

  @Override
  public Path<V, W, E> next() {
    return paths.removeLast();
  }

  @Override
  public int size() {
    return paths.size();
  }

  private final Deque<Path<V, W, E>> paths = new ArrayDeque<>();
}
