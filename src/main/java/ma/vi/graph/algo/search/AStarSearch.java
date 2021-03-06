package ma.vi.graph.algo.search;

import ma.vi.graph.Graph;
import ma.vi.graph.algo.Algorithm;
import ma.vi.graph.algo.ExpandOp;
import ma.vi.graph.algo.GoalOp;
import ma.vi.graph.algo.PriorityPathQueue;
import ma.vi.graph.algo.explore.Explore;
import ma.vi.graph.Path;

/**
 * <p>
 * Searches a graph using the A* search algorithm.
 * A* combines path costs with a heuristics to estimate
 * the cost towards a goal through a particular vertex.
 * </p>
 *
 * @param <V> The vertex type of the graph to search.
 * @param <W> The weight type on the edges of the graph to search.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class AStarSearch<V, W> implements Algorithm<V, W, Path<V, W>> {
  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the search at.
   * @param goalVertex Goal vertex.
   * @param goalEstimate An heuristic to estimate the cost to
   *                     get to the goal vertex from a particular
   *                     vertex.
   */
  public AStarSearch(V startVertex, V goalVertex, GoalEstimate<V> goalEstimate) {
    this.startVertex = startVertex;
    this.goalVertex = goalVertex;
    this.goalEstimate = goalEstimate;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public AStarSearch<V, W> expandOp(ExpandOp<V, W> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  @Override
  public Path<V, W> execute(Graph<V, W> graph) {
    return new Explore<V, W, Path<V, W>>(startVertex)
                .pathQueue(new PriorityPathQueue<>())
                .exploreOp(new SearchExploreOp<>(new GoalOp.MatchVertex<>(goalVertex)))
                .pathCostOp((g, path, edge) ->
                                path.weight()
                                    + (edge.weight instanceof Number ? ((Number)edge.weight).longValue() : 0L)
                                    + goalEstimate.op(edge.endPoint2, goalVertex))
                .expandOp(expandOp)
                .execute(graph);
  }

  protected final V startVertex;
  protected final V goalVertex;
  protected final GoalEstimate<V> goalEstimate;
  protected ExpandOp<V, W> expandOp = ExpandOp::outgoingEdges;
}