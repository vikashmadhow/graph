package ma.vi.graph.explore;

import ma.vi.graph.Path;

import java.util.PriorityQueue;

/**
 * A path-queue returning paths in minimum-cost order. Using this path-queue
 * in the search and explore functions produces minimum-cost first (greedy)
 * exploration behavior.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class PriorityPathQueue<N> implements PathQueue<N> {
  @Override
  public void add(Path<N> path) {
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
  public Path<N> next() {
    return paths.remove();
  }

  private final PriorityQueue<Path<N>> paths = new PriorityQueue<>();
}
