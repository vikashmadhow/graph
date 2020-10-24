package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.path.Path;

import java.util.Collection;
import java.util.Iterator;

/**
 * A path queue holds paths and is used by graph exploration algorithms. Depending on
 * how the path-queue returns paths on retrieval, the exploration will proceed as
 * breadth-first, depth-first, etc. The use of different queue types for different
 * search behaviours is described in the book "Artificial intelligence: a modern
 * approach" by Stuart Russell &amp; Peter Norvig.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface PathQueue<V, W, E extends Edge<V, W>> extends Iterator<Path<V, W, E>> {
  void add(Path<V, W, E> path);

  default void addAll(Collection<Path<V, W, E>> paths) {
    for (Path<V, W, E> path: paths) {
      add(path);
    }
  }

  boolean remove(Path<V, W, E> path);

  boolean hasPathEndingAt(V vertex);

  Path<V, W, E> pathEndingAt(V vertex);

  int size();
}
