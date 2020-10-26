package ma.vi.graph.algo;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.Path;

import java.util.Optional;

/**
 * <p>
 * This function is called during search to expand a path. It returns the set of edges
 * by which the path should be expanded.
 * </p>
 *
 * <p>
 * This function called during a search with the graph and the current path
 * and, if it returns true, the search ends returning the current path. Use
 * {@link MatchVertex} to create a GoalOp which matches a specific vertex in
 * the graph.
 * </p>
 *
 * @param <V> The vertex type of the graph
 * @param <W> The weight type on the edges of the graph.
 *
 * @author vikash.madhow@gmail.com
 */
@FunctionalInterface
public interface GoalOp<V, W> {

  /**
   * Returns true if the current path of the graph has reached the goal.
   */
  boolean op(Graph<V, W> graph, Path<V, W> path);

  /**
   * A GoalOp function returning true if the last vertex of the current path is
   * the supplied goal vertex. This can be used when the goal vertex is a static
   * vertex in the graph (as opposed to looking for a vertex with certain properties).
   *
   * @param <V> The vertex type of the graph
   * @param <E> The edge type of the graph. This must be a subclass of
   *            {@link ma.vi.graph.Edge} specialised on current the node
   *            type of the graph.
   * @param <W> The weight type on the edges of the graph.
   */
  class MatchVertex<V, W, E extends Edge<V, W>> implements GoalOp<V, W> {
    public MatchVertex(V goal) {
      this.goal = goal;
    }

    @Override
    public boolean op(Graph<V, W> graph, Path<V, W> path) {
      Optional<V> end = path.lastVertex();
      return end.isPresent() && end.get().equals(goal);
    }

    private final V goal;
  }
}
