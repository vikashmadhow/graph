package ma.vi.graph.path;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface Path<V, W, E extends Edge<V, W>> extends Graph<V, W, E>, Comparable<Path<V, W, E>> {
  @Override LinkedHashSet<V> vertices();

  @Override LinkedHashSet<E> edges();

  @Override LinkedHashSet<E> incoming(V vertex);

  @Override LinkedHashSet<E> outgoing(V vertex);

  @Override LinkedHashSet<E> edges(V vertex);

  Optional<V> start();

  Optional<V> end();

  Optional<E> next(V from);

  Optional<E> previous(V from);

  Integer cost();

  int length();

  Path<V, W, E> extend(V vertex, Integer newPathCost);

  @Override
  default int compareTo(Path<V, W, E> path) {
    return (this.cost() == null ? 0 : this.cost())
         - (path.cost() == null ? 0 : path.cost());
  }
}
