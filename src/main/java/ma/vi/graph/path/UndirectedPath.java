package ma.vi.graph.path;

import ma.vi.graph.Edge;
import ma.vi.graph.UndirectedGraph;

import java.util.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class UndirectedPath<V, W> extends UndirectedGraph<V, W> implements Path<V, W> {
  public UndirectedPath(V vertex) {
    this(0L, vertex);
  }

  public UndirectedPath(Long cost, V vertex) {
    super(Collections.emptySet());
    this.firstVertex = this.lastVertex = vertex;
    this.firstEdge = this.lastEdge = null;
    this.cost = cost;
  }

  public UndirectedPath(Long cost, LinkedHashSet<Edge<V, W>> undirectedEdges) {
    super(undirectedEdges);
    List<Edge<V, W>> e = new ArrayList<>(undirectedEdges);
    this.firstVertex = e.isEmpty() ? null : e.get(0).endPoint1;
    this.lastVertex = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2;
    this.firstEdge = e.isEmpty() ? null : e.get(0);
    this.lastEdge = e.isEmpty() ? null : e.get(e.size() - 1);
    this.cost = cost;
  }

//  public UndirectedPath(Long cost, VertexMap<V, W> vertexMap) {
//    super(vertexMap);
//    this.firstVertex = vertexMap.firstVertex();
//    this.lastVertex = vertexMap.lastVertex();
//    if (this.firstVertex != null) {
//      Set<Edge<V, W>> outgoing = outgoing(this.firstVertex);
//      this.firstEdge = outgoing == null || outgoing.isEmpty()
//                       ? null
//                       : outgoing.iterator().next();
//    } else {
//      this.firstEdge = null;
//    }
//    if (this.lastVertex != null) {
//      Set<Edge<V, W>> incoming = incoming(this.lastVertex);
//      this.lastEdge = incoming == null || incoming.isEmpty()
//                      ? null
//                      : incoming.iterator().next();
//    } else {
//      this.lastEdge = null;
//    }
//    this.cost = cost;
//  }

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
    es.add(newEdge(lastVertex, weight, vertex));
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
