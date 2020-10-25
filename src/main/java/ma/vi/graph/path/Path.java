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
public interface Path<V, W> extends Graph<V, W>,
                                    Comparable<Path<V, W>>,
                                    Iterable<Edge<V, W>> {
  Optional<V> firstVertex();

  Optional<V> lastVertex();

  default Optional<Edge<V, W>> firstEdge() {
    return firstVertex().map(v -> {
      Set<Edge<V, W>> outgoing = outgoing(v);
      return outgoing == null || outgoing.isEmpty()
             ? null
             : outgoing.iterator().next();
    });
  }

  default Optional<Edge<V, W>> lastEdge() {
    return lastVertex().map(v -> {
      Set<Edge<V, W>> incoming = incoming(v);
      return incoming == null || incoming.isEmpty()
             ? null
             : incoming.iterator().next();
    });
  }

  default Optional<Edge<V, W>> nextEdge(V from) {
    Set<Edge<V, W>> outgoing = outgoing(from);
    return outgoing == null || outgoing.isEmpty()
              ? Optional.empty()
              : Optional.of(outgoing.iterator().next());
  }

  default Optional<Edge<V, W>> previousEdge(V from) {
    Set<Edge<V, W>> incoming = incoming(from);
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
    for (Edge<V, W> edge: edges()) {
      W w = edge.weight;
      if (w instanceof Number) {
        weight += ((Number)w).longValue();
      }
    }
    return weight;
  }

  Path<V, W> extend(V vertex, W weight, Long newPathCost);

  @Override
  default int compareTo(Path<V, W> path) {
    return (int)((this.cost() == null ? 0 : this.cost())
               - (path.cost() == null ? 0 : path.cost()));
  }

  @Override
  default Iterator<Edge<V, W>> iterator() {
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return last != null && !outgoing(last).isEmpty();
      }

      @Override
      public Edge<V, W> next() {
        if (last != null) {
          Edge<V, W> next = outgoing(last).iterator().next();
          last = next.endPoint2;
          return next;
        }
        throw new NoSuchElementException("No more edges");
      }

      private transient V last = firstVertex().orElse(null);
    };
  }
}
