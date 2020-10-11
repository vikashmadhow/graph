package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * A path is a list of vertices together with an optional cost.
 *
 * @param <V> The vertex type.
 * @author vikash.madhow@gmail.com
 */
public class Path<V> extends T2<List<V>, Integer> implements Comparable<Path<V>> {
  public Path(V vertex) {
    this(Collections.singletonList(vertex));
  }

  public Path(List<V> vertices) {
    this(vertices, null);
  }

  public Path(List<V> vertices, Integer cost) {
    super(vertices, cost);
  }

  public List<V> vertices() {
    return a;
  }

  public Integer cost() {
    return b;
  }

  public Optional<V> start() {
    return vertices().isEmpty() ? Optional.empty() : Optional.of(vertices().get(0));
  }

  public Optional<V> end() {
    return vertices().isEmpty() ? Optional.empty() : Optional.of(vertices().get(vertices().size() - 1));
  }

  public Path<V> extend(V vertex, Integer newPathCost) {
    List<V> vertices = new ArrayList<>(this.vertices());
    vertices.add(vertex);
    return new Path<>(vertices, newPathCost);
  }

  @Override
  public int compareTo(Path<V> path) {
    return (this.cost() == null ? 0 : this.cost())
         - (path.cost() == null ? 0 : path.cost());
  }
}
