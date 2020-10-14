package ma.vi.graph.path;

import ma.vi.graph.DirectedEdge;
import ma.vi.graph.DirectedGraph;
import ma.vi.graph.VertexMap;

import java.util.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class DirectedPath<V, W> extends DirectedGraph<V, W> implements Path<V, W, DirectedEdge<V, W>> {
  public DirectedPath(LinkedHashSet<DirectedEdge<V, W>> directedEdges) {
    super(directedEdges);
    List<DirectedEdge<V, W>> e = new ArrayList<>(directedEdges);
    this.start = e.isEmpty() ? null : e.get(0).endPoint1();
    this.end = e.isEmpty() ? null : e.get(e.size() - 1).endPoint2();
  }

  public DirectedPath(VertexMap<V, W> vertexMap) {
    super(vertexMap);
    this.start = vertexMap.start();
    this.end = vertexMap.end();
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
  public Optional<DirectedEdge<V, W>> next(V from) {
    Set<DirectedEdge<V, W>> outgoing = outgoing(from);
    return outgoing == null || outgoing.isEmpty()
              ? Optional.empty()
              : Optional.of(outgoing.iterator().next());
  }

  @Override
  public Optional<DirectedEdge<V, W>> previous(V from) {
    Set<DirectedEdge<V, W>> incoming = incoming(from);
    return incoming == null || incoming.isEmpty()
              ? Optional.empty()
              : Optional.of(incoming.iterator().next());
  }

  protected final V start;
  protected final V end;
}
