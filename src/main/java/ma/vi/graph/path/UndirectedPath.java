package ma.vi.graph.path;

import ma.vi.graph.UndirectedEdge;
import ma.vi.graph.UndirectedGraph;
import ma.vi.graph.VertexMap;

import java.util.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class UndirectedPath<V, W> extends UndirectedGraph<V, W> implements Path<V, W, UndirectedEdge<V, W>> {
  public UndirectedPath(V vertex) {
    this(0, vertex);
  }

  public UndirectedPath(Integer cost, V vertex) {
    super(Collections.emptySet());
    this.start = this.end = vertex;
    this.cost = cost;
  }

  public UndirectedPath(Integer cost, LinkedHashSet<UndirectedEdge<V, W>> undirectedEdges) {
    super(undirectedEdges);
    List<UndirectedEdge<V, W>> e = new ArrayList<>(undirectedEdges);
    this.start = e.isEmpty() ? null : e.get(0).endPoint1();
    this.end = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2();
    this.cost = cost;
  }

  public UndirectedPath(Integer cost, VertexMap<V, W> vertexMap) {
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
  public Integer cost() {
    return cost;
  }

  @Override
  public Path<V, W, UndirectedEdge<V, W>> extend(V vertex, W weight, Integer newPathCost) {
    LinkedHashSet<UndirectedEdge<V, W>> es = new LinkedHashSet<>(edges());
    es.add(newEdge(end, weight, vertex));
    return new UndirectedPath<>(newPathCost, es);
  }

  @Override
  public String toString() {
    return super.toString() + ", Cost=" + cost;
  }

  protected final V start;
  protected final V end;
  protected final Integer cost;
}
