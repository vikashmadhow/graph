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
public class TreeExploreOp<V, W> implements ExploreOp<V, W, Path<V, W>> {
  public TreeExploreOp(Set<Edge<V, W>> edges) {
    this.edges = edges;
  }

  @Override
  public Optional<Path<V, W>> op(Graph<V, W> graph,
                                    Path<V, W> path) {
    path.lastEdge().ifPresent(edges::add);
    return Optional.empty();
  }

  private final Set<Edge<V, W>> edges;
}
