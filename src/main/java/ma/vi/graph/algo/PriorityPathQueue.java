package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.path.Path;

import java.util.PriorityQueue;

/**
 * A path-queue returning paths in minimum-cost order. Using this path-queue
 * in the search and explore functions produces minimum-cost first (greedy)
 * exploration behavior.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class PriorityPathQueue<V, W, E extends Edge<V, W>> implements PathQueue<V, W, E> {
  @Override
  public void add(Path<V, W, E> path) {
    paths.add(path);
  }

  @Override
  public int size() {
    return paths.size();
  }

  @Override
  public boolean hasNext() {
    return !paths.isEmpty();
  }

  @Override
  public Path<V, W, E> next() {
    return paths.remove();
  }

  private final PriorityQueue<Path<V, W, E>> paths = new PriorityQueue<>();
}
