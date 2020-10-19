package ma.vi.graph.path;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public interface Path<V, W, E extends Edge<V, W>> extends Graph<V, W, E>,
                                                          Comparable<Path<V, W, E>>,
                                                          Iterable<E> {
  Optional<V> firstVertex();

  Optional<V> lastVertex();

  default Optional<E> firstEdge() {
    return firstVertex().map(v -> {
      Set<E> outgoing = outgoing(v);
      return outgoing == null || outgoing.isEmpty()
             ? null
             : outgoing.iterator().next();
    });
  }

  default Optional<E> lastEdge() {
    return lastVertex().map(v -> {
      Set<E> incoming = incoming(v);
      return incoming == null || incoming.isEmpty()
             ? null
             : incoming.iterator().next();
    });
  }

  default Optional<E> nextEdge(V from) {
    Set<E> outgoing = outgoing(from);
    return outgoing == null || outgoing.isEmpty()
              ? Optional.empty()
              : Optional.of(outgoing.iterator().next());
  }

  default Optional<E> previousEdge(V from) {
    Set<E> incoming = incoming(from);
    return incoming == null || incoming.isEmpty()
              ? Optional.empty()
              : Optional.of(incoming.iterator().next());
  }

  Long cost();

  default int length() {
    return edges().size();
  }

  /**
   * Returns the total weight of the path by summing up the weight
   * of the each edge in the path, if the edge weights are numbers.
   */
  default long weight() {
    long weight = 0;
    for (E edge: edges()) {
      W w = edge.weight();
      if (w instanceof Number) {
        weight += ((Number)w).longValue();
      }
    }
    return weight;
  }

  Path<V, W, E> extend(V vertex, W weight, Long newPathCost);

  @Override
  default int compareTo(Path<V, W, E> path) {
    return (int)((this.cost() == null ? 0 : this.cost())
               - (path.cost() == null ? 0 : path.cost()));
  }

  @Override
  default Iterator<E> iterator() {
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return last != null && !outgoing(last).isEmpty();
      }

      @Override
      public E next() {
        if (last != null) {
          E next = outgoing(last).iterator().next();
          last = next.endPoint2();
          return next;
        }
        throw new NoSuchElementException("No more edges");
      }

      private transient V last = firstVertex().orElse(null);
    };
  }
}
