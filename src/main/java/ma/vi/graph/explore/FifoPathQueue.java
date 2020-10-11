package ma.vi.graph.explore;

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
public class FifoPathQueue<N> implements PathQueue<N> {
  @Override
  public void add(Path<N> path) {
    paths.addFirst(path);
  }

  @Override
  public boolean hasNext() {
    return !paths.isEmpty();
  }

  @Override
  public Path<N> next() {
    return paths.removeLast();
  }

  @Override
  public int size() {
    return paths.size();
  }

  private final Deque<Path<N>> paths = new ArrayDeque<>();
}
