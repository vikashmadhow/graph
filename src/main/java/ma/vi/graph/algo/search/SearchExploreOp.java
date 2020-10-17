package ma.vi.graph.algo.search;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.ExploreOp;
import ma.vi.graph.algo.GoalOp;
import ma.vi.graph.path.Path;

import java.util.Optional;

/**
 * An implementation of {@link ExploreOp} for searching.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class SearchExploreOp<V, W, E extends Edge<V, W>> implements ExploreOp<V, W, E, Void, Path<V, W, E>> {
  public SearchExploreOp(GoalOp<V, W, E> goalOp) {
    this.goalOp = goalOp;
  }

  @Override
  public Optional<Path<V, W, E>> op(Graph<V, W, E> graph,
                                    Path<V, W, E> path,
                                    Void accumulator) {
    return goalOp.op(graph, path) ? Optional.of(path) : Optional.empty();
  }

  private final GoalOp<V, W, E> goalOp;
}
