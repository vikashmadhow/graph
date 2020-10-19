package ma.vi.graph.algo.tree;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.ExploreOp;
import ma.vi.graph.path.Path;

import java.util.Optional;
import java.util.Set;

/**
 * An implementation of {@link ExploreOp} for growing trees from graphs
 * which just records the last edge of each explored path; these are then
 * used to create a tree.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class TreeExploreOp<V, W, E extends Edge<V, W>> implements ExploreOp<V, W, E, Path<V, W, E>> {
  public TreeExploreOp(Set<E> edges) {
    this.edges = edges;
  }

  @Override
  public Optional<Path<V, W, E>> op(Graph<V, W, E> graph,
                                    Path<V, W, E> path) {
    path.lastEdge().ifPresent(edges::add);
    return Optional.empty();
  }

  private final Set<E> edges;
}
