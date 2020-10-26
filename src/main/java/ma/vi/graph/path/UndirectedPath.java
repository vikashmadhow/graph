package ma.vi.graph.path;

import ma.vi.graph.Edge;
import ma.vi.graph.UndirectedGraph;

import java.util.*;

import static java.util.Collections.emptySet;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class UndirectedPath<V, W> extends UndirectedGraph<V, W> implements Path<V, W> {
  /**
   * Creates an empty path
   */
  public UndirectedPath() {
    super();
    this.firstVertex = this.lastVertex = null;
    this.firstEdge = this.lastEdge = null;
    this.cost = 0L;
  }

  /**
   * Creates a single-vertex path.
   */
  public UndirectedPath(Long cost, V vertex) {
    super(vertex);
    this.firstVertex = this.lastVertex = vertex;
    this.firstEdge = this.lastEdge = null;
    this.cost = cost;
  }

  /**
   * Creates a path from an ordered set of edges.
   */
  public UndirectedPath(Long cost, LinkedHashSet<Edge<V, W>> edges) {
    super(edges);
    List<Edge<V, W>> e = new ArrayList<>(edges);
    this.firstVertex = e.isEmpty() ? null : e.get(0).endPoint1;
    this.lastVertex = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2;
    this.firstEdge = e.isEmpty() ? null : e.get(0);
    this.lastEdge = e.isEmpty() ? null : e.get(e.size() - 1);
    this.cost = cost;
  }

  @Override
  public Optional<V> firstVertex() {
    return Optional.ofNullable(firstVertex);
  }

  @Override
  public Optional<V> lastVertex() {
    return Optional.ofNullable(lastVertex);
  }

  @Override
  public Long cost() {
    return cost;
  }

  @Override
  public Path<V, W> extend(V vertex, W weight, Long newPathCost) {
    LinkedHashSet<Edge<V, W>> es = new LinkedHashSet<>(edges());
    es.add(Edge.of(lastVertex, weight, vertex));
    return new UndirectedPath<>(newPathCost, es);
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
