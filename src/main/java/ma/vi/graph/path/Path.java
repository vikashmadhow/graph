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
  Optional<V> start();

  Optional<V> end();

  default Optional<E> next(V from) {
    Set<E> outgoing = outgoing(from);
    return outgoing == null || outgoing.isEmpty()
              ? Optional.empty()
              : Optional.of(outgoing.iterator().next());
  }

  default Optional<E> previous(V from) {
    Set<E> incoming = incoming(from);
    return incoming == null || incoming.isEmpty()
              ? Optional.empty()
              : Optional.of(incoming.iterator().next());
  }

  Integer cost();

  Path<V, W, E> extend(V vertex, W weight, Integer newPathCost);

  @Override
  default int compareTo(Path<V, W, E> path) {
    return (this.cost() == null ? 0 : this.cost())
         - (path.cost() == null ? 0 : path.cost());
  }

  @Override
  default Iterator<E> iterator() {
    return new Iterator<>() {
      @Override
      public boolean hasNext() {
        return last.isPresent() && !outgoing(last.get()).isEmpty();
      }

      @Override
      public E next() {
        if (last.isPresent()) {
          E next = outgoing(last.get()).iterator().next();
          last = Optional.of(next.endPoint2());
          return next;
        }
        throw new NoSuchElementException("No more edges");
      }

      private transient Optional<V> last = start();
    };
  }
}
