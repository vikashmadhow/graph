package ma.vi.graph.path;

import ma.vi.graph.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class UndirectedPath<V, W> extends UndirectedGraph<V, W> implements Path<V, W, UndirectedEdge<V, W>> {
  public UndirectedPath(List<UndirectedEdge<V, W>> undirectedEdges) {
    super(new HashSet<>(undirectedEdges));
    this.start = undirectedEdges.isEmpty() ? null : undirectedEdges.get(0).endPoint1();
    this.end = undirectedEdges.isEmpty() ? null : undirectedEdges.get(undirectedEdges.size() - 1).endPoint2();
  }

  public UndirectedPath(VertexMap<V, W> vertexMap) {
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
  public Optional<UndirectedEdge<V, W>> next(V from) {
    Set<UndirectedEdge<V, W>> outgoing = outgoing(from);
    return outgoing == null || outgoing.isEmpty()
              ? Optional.empty()
              : Optional.of(outgoing.iterator().next());
  }

  @Override
  public Optional<UndirectedEdge<V, W>> previous(V from) {
    Set<UndirectedEdge<V, W>> incoming = incoming(from);
    return incoming == null || incoming.isEmpty()
              ? Optional.empty()
              : Optional.of(incoming.iterator().next());
  }

  protected final V start;
  protected final V end;
}
