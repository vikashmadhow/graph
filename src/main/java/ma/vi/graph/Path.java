package ma.vi.graph;

import java.util.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class Path<V, W>
     extends DirectedGraph<V, W>
  implements Comparable<Path<V, W>>, Iterable<Edge<V, W>> {
  /**
   * Creates an empty path
   */
  public Path() {
    this.firstVertex = this.lastVertex = null;
    this.firstEdge = this.lastEdge = null;
    this.cost = 0L;
  }

  /**
   * Creates a single-vertex path.
   */
  public Path(Long cost, V vertex) {
    super(vertex);
    this.firstVertex = this.lastVertex = vertex;
    this.firstEdge = this.lastEdge = null;
    this.cost = cost;
  }

  /**
   * Creates a path from an ordered set of edges.
   */
  public Path(Long cost, LinkedHashSet<Edge<V, W>> edges) {
    super(edges);
    List<Edge<V, W>> e = new ArrayList<>(edges);
    this.firstVertex = e.isEmpty() ? null : e.get(0).endPoint1;
    this.lastVertex = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2;
    this.firstEdge = e.isEmpty() ? null : e.get(0);
    this.lastEdge = e.isEmpty() ? null : e.get(e.size() - 1);
    this.cost = cost;
  }

  public static <V,W> Path<V, W> of(Long cost, LinkedHashSet<Edge<V, W>> edges) {
    return new Path<>(cost, edges);
  }

  public static <V,W> Path<V, W> of(Long cost, Edge<V, W>... edges) {
    return new Path<>(cost, new LinkedHashSet<>(Arrays.asList(edges)));
  }

  public static <V, W> Path<V, W> of(Long cost, Optional<Edge<V, W>>... edges) {
    LinkedHashSet<Edge<V, W>> edgesSet = new LinkedHashSet<>();
    for (Optional<Edge<V, W>> e: edges) {
      e.ifPresent(edgesSet::add);
    }
    return new Path<>(cost, edgesSet);
  }

  public Optional<V> firstVertex() {
    return Optional.ofNullable(firstVertex);
  }

  public Optional<V> lastVertex() {
    return Optional.ofNullable(lastVertex);
  }

  public Optional<Edge<V, W>> firstEdge() {
    return Optional.ofNullable(firstEdge);
  }

  public Optional<Edge<V, W>> lastEdge() {
    return Optional.ofNullable(lastEdge);
  }

  public Optional<Edge<V, W>> nextEdge(V from) {
    Set<Edge<V, W>> outgoing = outgoing(from);
    return outgoing == null || outgoing.isEmpty()
             ? Optional.empty()
             : Optional.of(outgoing.iterator().next());
  }

  public Optional<Edge<V, W>> previousEdge(V from) {
    Set<Edge<V, W>> incoming = incoming(from);
    return incoming == null || incoming.isEmpty()
             ? Optional.empty()
             : Optional.of(incoming.iterator().next());
  }

  public Long cost() {
    return cost;
  }

  public int length() {
    return edges().size();
  }

  /**
   * Returns the total weight of the path by summing up the weight
   * of the each edge in the path, if the edge weights are numbers.
   */
  public long weight() {
    long weight = 0;
    for (Edge<V, W> edge: edges()) {
      W w = edge.weight;
      if (w instanceof Number) {
        weight += ((Number)w).longValue();
      }
    }
    return weight;
  }

  public Path<V, W> extend(V vertex, W weight, Long newPathCost) {
    LinkedHashSet<Edge<V, W>> es = new LinkedHashSet<>(edges());
    es.add(Edge.of(lastVertex, weight, vertex));
    return new Path<>(newPathCost, es);
  }

  @Override
  public Iterator<Edge<V, W>> iterator() {
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

  @Override
  public int compareTo(Path<V, W> path) {
    return (int)((this.cost() == null ? 0 : this.cost())
               - (path.cost() == null ? 0 : path.cost()));
  }

  @Override
  public String toString() {
    return super.toString() + ", Cost=" + cost;
  }

  protected final V firstVertex;
  protected final V lastVertex;
  protected final Edge<V, W> firstEdge;
  protected final Edge<V, W> lastEdge;
  protected final Long cost;
}
