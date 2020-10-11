package ma.vi.graph.explore;

import ma.vi.graph.Path;

import java.util.Collection;
import java.util.Iterator;

/**
 * A path queue holds paths and is used by graph exploration algorithms. Depending on
 * how the path-queue returns paths on retrieval, the search will work as breadth-first,
 * depth-first, etc. The use of different queue types for different search behaviours
 * is described in the book "Artificial intelligence: a modern approach" by Stuart Russell
 * & Peter Norvig.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface PathQueue<V> extends Iterator<Path<V>> {
  void add(Path<V> path);

  default void addAll(Collection<Path<V>> paths) {
    for (Path<V> path: paths) {
      add(path);
    }
  }

  int size();
}
