package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.*;

/**
 * A path is a list of vertices together with an optional cost.
 *
 * @param <V> The vertex type.
 * @author vikash.madhow@gmail.com
 */
public class Path<V, W, E extends Edge<V, W>> extends T2<List<V>, Integer> implements Comparable<Path<V, W, E>> {
  public Path(Graph<V, W, E> graph, V vertex) {
    this(graph, Collections.singletonList(vertex));
  }

  public Path(Graph<V, W, E> graph, V vertex, Integer cost) {
    this(graph, Collections.singletonList(vertex), cost);
  }

  public Path(Graph<V, W, E> graph, List<V> vertices) {
    this(graph, vertices, null);
  }

  public Path(Graph<V, W, E> graph, List<V> vertices, Integer cost) {
    super(vertices, cost);
    this.graph = graph;
  }

  public Path(Graph<V, W, E> graph, Integer cost, V... vertices) {
    super(Arrays.asList(vertices), cost);
    this.graph = graph;
  }

  public Graph<V, W, E> graph() {
    return this.graph;
  }

  public List<V> vertices() {
    return a;
  }

  public Integer cost() {
    return b;
  }

  public int length() {
    return a.size();
  }

  public Optional<V> start() {
    return vertices().isEmpty() ? Optional.empty() : Optional.of(vertices().get(0));
  }

  public Optional<V> end() {
    return vertices().isEmpty() ? Optional.empty() : Optional.of(vertices().get(vertices().size() - 1));
  }

  public Path<V, W, E> extend(V vertex, Integer newPathCost) {
    List<V> vertices = new ArrayList<>(this.vertices());
    vertices.add(vertex);
    return new Path<>(graph, vertices, newPathCost);
  }

  @Override
  public int compareTo(Path<V, W, E> path) {
    return (this.cost() == null ? 0 : this.cost())
         - (path.cost() == null ? 0 : path.cost());
  }

  public final Graph<V, W, E> graph;
}
