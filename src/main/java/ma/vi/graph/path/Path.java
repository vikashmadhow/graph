package ma.vi.graph.path;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;

import java.util.Optional;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface Path<V, W, E extends Edge<V, W>> extends Graph<V, W, E>, Comparable<Path<V, W, E>> {
  Optional<V> start();

  Optional<V> end();

  Optional<E> next(V from);

  Optional<E> previous(V from);

  Integer cost();

  Path<V, W, E> extend(V vertex, W weight, Integer newPathCost);

  @Override
  default int compareTo(Path<V, W, E> path) {
    return (this.cost() == null ? 0 : this.cost())
         - (path.cost() == null ? 0 : path.cost());
  }
}
