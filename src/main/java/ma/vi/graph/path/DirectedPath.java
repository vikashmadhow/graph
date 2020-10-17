package ma.vi.graph.path;

import ma.vi.graph.DirectedEdge;
import ma.vi.graph.DirectedGraph;
import ma.vi.graph.VertexMap;

import java.util.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class DirectedPath<V, W> extends DirectedGraph<V, W> implements Path<V, W, DirectedEdge<V, W>> {
  public DirectedPath(V vertex) {
    this(0L, vertex);
  }

  public DirectedPath(Long cost, V vertex) {
    super(Collections.emptySet());
    this.start = this.end = vertex;
    this.cost = cost;
  }

  public DirectedPath(Long cost, LinkedHashSet<DirectedEdge<V, W>> directedEdges) {
    super(directedEdges);
    List<DirectedEdge<V, W>> e = new ArrayList<>(directedEdges);
    this.start = e.isEmpty() ? null : e.get(0).endPoint1();
    this.end = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2();
    this.cost = cost;
  }

  public DirectedPath(VertexMap<V, W> vertexMap, Long cost) {
    super(vertexMap);
    this.start = vertexMap.start();
    this.end = vertexMap.end();
    this.cost = cost;
  }

  @Override
  public Optional<V> start() {
    return Optional.ofNullable(start);
  }

  @Override
  public Optional<V> end() {
    return Optional.ofNullable(end);
  }

  @Override
  public Long cost() {
    return cost;
  }

  @Override
  public Path<V, W, DirectedEdge<V, W>> extend(V vertex, W weight, Long newPathCost) {
    LinkedHashSet<DirectedEdge<V, W>> es = new LinkedHashSet<>(edges());
    es.add(newEdge(end, weight, vertex));
    return new DirectedPath<>(newPathCost, es);
  }

  @Override
  public String toString() {
    return super.toString() + ", Cost=" + cost;
  }
  protected final V start;
  protected final V end;
  protected final Long cost;
}
