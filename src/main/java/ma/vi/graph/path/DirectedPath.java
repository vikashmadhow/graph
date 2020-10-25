package ma.vi.graph.path;

import ma.vi.graph.DirectedEdge;
import ma.vi.graph.DirectedGraph;
import ma.vi.graph.Edge;
import ma.vi.graph.VertexMap;

import java.util.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class DirectedPath<V, W> extends DirectedGraph<V, W> implements Path<V, W> {
  public DirectedPath(V vertex) {
    this(0L, vertex);
  }

  public DirectedPath(Long cost, V vertex) {
    super(Collections.emptySet());
    this.firstVertex = this.lastVertex = vertex;
    this.firstEdge = this.lastEdge = null;
    this.cost = cost;
  }

  public DirectedPath(Long cost, LinkedHashSet<Edge<V, W>> directedEdges) {
    super(directedEdges);
    List<Edge<V, W>> e = new ArrayList<>(directedEdges);
    this.firstVertex = e.isEmpty() ? null : e.get(0).endPoint1;
    this.lastVertex = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2;
    this.firstEdge = e.isEmpty() ? null : e.get(0);
    this.lastEdge = e.isEmpty() ? null : e.get(e.size() - 1);
    this.cost = cost;
  }

//  public DirectedPath(VertexMap<V, W> vertexMap, Long cost) {
//    super(vertexMap);
//    this.firstVertex = vertexMap.firstVertex();
//    this.lastVertex = vertexMap.lastVertex();
//    if (this.firstVertex != null) {
//        Set<Edge<V, W>> outgoing = outgoing(this.firstVertex);
//        this.firstEdge = outgoing == null || outgoing.isEmpty()
//                           ? null
//                           : outgoing.iterator().next();
//    } else {
//      this.firstEdge = null;
//    }
//    if (this.lastVertex != null) {
//        Set<Edge<V, W>> incoming = incoming(this.lastVertex);
//        this.lastEdge = incoming == null || incoming.isEmpty()
//                           ? null
//                           : incoming.iterator().next();
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
  public Optional<Edge<V, W>> firstEdge() {
    return Optional.ofNullable(firstEdge);
  }

  @Override
  public Optional<Edge<V, W>> lastEdge() {
    return Optional.ofNullable(lastEdge);
  }

  @Override
  public Long cost() {
    return cost;
  }

  @Override
  public Path<V, W> extend(V vertex, W weight, Long newPathCost) {
    LinkedHashSet<Edge<V, W>> es = new LinkedHashSet<>(edges());
    es.add(newEdge(lastVertex, weight, vertex));
    return new DirectedPath<>(newPathCost, es);
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
