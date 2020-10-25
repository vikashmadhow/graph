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
public class PriorityPathQueue<V, W> extends AbstractPathQueue<V, W> {
  @Override
  protected void addToQueue(Path<V, W> path) {
    paths.add(path);
  }

  @Override
  public boolean removeFromQueue(Path<V, W> path) {
    return paths.remove(path);
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
  public Path<V, W> next() {
    return paths.remove();
  }

  private final PriorityQueue<Path<V, W>> paths = new PriorityQueue<>();
}
