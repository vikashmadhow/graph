package ma.vi.graph.algo.search;

import ma.vi.graph.Graph;
import ma.vi.graph.algo.ExploreOp;
import ma.vi.graph.algo.GoalOp;
import ma.vi.graph.Path;

import java.util.Optional;

/**
 * An implementation of {@link ExploreOp} for searching.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class SearchExploreOp<V, W> implements ExploreOp<V, W, Path<V, W>> {
  public SearchExploreOp(GoalOp<V, W> goalOp) {
    this.goalOp = goalOp;
  }

  @Override
  public Optional<Path<V, W>> op(Graph<V, W> graph, Path<V, W> path) {
    return goalOp.op(graph, path) ? Optional.of(path) : Optional.empty();
  }

  private final GoalOp<V, W> goalOp;
}
