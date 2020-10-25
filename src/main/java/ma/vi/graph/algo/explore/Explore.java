package ma.vi.graph.algo.explore;

import ma.vi.graph.Edge;
import ma.vi.graph.Graph;
import ma.vi.graph.algo.*;
import ma.vi.graph.path.Path;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * A generic algorithm to explore the vertices in the graph starting from a start vertex.
 * For every path from the start vertex to an arbitrary vertex in the graph, an explore
 * function (of type {@link ExploreOp} is called with the graph and the path (from the start
 * vertex to that vertex). The exploreOp returns null to signify that the path should be
 * ignored (and exploration should continue from the next); an empty {@link Optional} means
 * that the exploration should expand this path; finally a value in the Optional means
 * that exploration is completed and the value should be returned as the result of the
 * exploration.
 * </p>
 *
 * <p>
 * The path cost op function (of type {@link PathCostOp}), if provided, is used to compute
 * the cost of every path produced and is useful when searching the graph using more complicated
 * cost functions (such as the heuristics used in A* search). If not provided, the path cost
 * is taken to be the sum of the weight of the edges (if the weight are numeric) or simply
 * the length of the path, otherwise.
 * </p>
 *
 * <p>
 * This exploration algorithm is used by other algorithms such as search and tree-growing algos.
 * </p>

 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 *   graph.apply(
 *     new Explore(startVertex).pathQueue(new LifoPathQueue())
 *                             .exploreOp((graph, path, acc) -&gt; log(path)))
 * </pre>
 *
 * @param <V> The vertex type of the graph to explore.
 * @param <W> The weight type on the edges of the graph to explore.
 * @param <E> The edge type of the graph to explore.
 * @param <R> The result of the exploration which is the result returned by the exploreOp function.
 *
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
public class Explore<V, W, R> implements Algorithm<V, W, R> {

  /**
   * Create a new instance of the algorithm.
   * @param startVertex The vertex to start the exploration at.
   */
  public Explore(V startVertex) {
    this.startVertex = startVertex;
  }

  /**
   * Sets the path queue to use which controls the exploration behaviour.
   * For instance, {@link FifoPathQueue} will explore breadth-first,
   * {@link LifoPathQueue} will result in depth-first while {@link PriorityPathQueue}
   * will explore path in order of costs.
   */
  public Explore<V, W, R> pathQueue(PathQueue<V, W> pathQueue) {
    this.pathQueue = pathQueue;
    return this;
  }

  /**
   * The function taking a graph and a path invoked for every path explored for the
   * graph. The accumulator parameter is passed as the last parameter to this function
   * and can be used to accumulate certain information during the exploration. It could
   * be used, for instance, to construct a spanning subgraph of this graph.
   */
  public Explore<V, W, R> exploreOp(ExploreOp<V, W, R> exploreOp) {
    this.exploreOp = exploreOp;
    return this;
  }

  /**
   * A function taking as parameters the graph and current exploring path and
   * returning the successor edges to explore. The default function returns
   * all outgoing edges of the current path.
   */
  public Explore<V, W, R> expandOp(ExpandOp<V, W> expandOp) {
    this.expandOp = expandOp;
    return this;
  }

  /**
   * A function to compute the cost of a new path, invoked with the graph, path
   * to be extended, the edge with which the path is being extended and, optionally,
   * a goal vertex.
   */
  public Explore<V, W, R> pathCostOp(PathCostOp<V, W> pathCostOp) {
    this.pathCostOp = pathCostOp;
    return this;
  }

  @Override
  public R execute(Graph<V, W> graph) {
     if (pathQueue == null) {
      pathQueue = new FifoPathQueue<>();
    }
    if (startVertex != null) {
      pathQueue.add(graph.path(startVertex));
    } else {
      throw new IllegalArgumentException("Start vertex to explore from not provided");
    }
    Set<V> explored = new HashSet<>();
    while (pathQueue.hasNext()) {
      Path<V, W> path = pathQueue.next();
      System.out.println("# Exploring " + path + "; length: " + path.length());
      Optional<V> f = path.lastVertex();
      if (f.isPresent()) {
        V frontier = f.get();
        if (!explored.contains(frontier)) {
          explored.add(frontier);
          Optional<R> result = exploreOp.op(graph, path);
          if (result != null) {
            /*
             * null returned from the exploreOp signifies that this path should be ignored
             * (and exploration should continue from the next); an empty Optional means that
             * the exploration should expand this path; finally a value in the Optional means
             * that exploration is completed and the value should be returned as its result.
             */
            if (result.isEmpty()) {
              Set<Edge<V, W>> successors = expandOp.op(graph, path);
              if (!successors.isEmpty()) {
                for (Edge<V, W> edge: successors) {
                  V newVertex = edge.endPoint2;
                  if (!explored.contains(newVertex)) {
                    if (pathCostOp == null) {
                      pathQueue.add(path.extend(newVertex, edge.weight, path.length() + 1L));
                    } else {
                      long cost = pathCostOp.op(graph, path, edge);
                      if (pathQueue.hasPathEndingAt(newVertex)) {
                        Path<V, W> existing = pathQueue.pathEndingAt(newVertex);
                        if (existing.cost() > cost) {
                          pathQueue.remove(existing);
                          Path<V, W> newPath = path.extend(newVertex, edge.weight, cost);
                          pathQueue.add(newPath);
                        }
                      } else {
                        Path<V, W> newPath = path.extend(newVertex, edge.weight, cost);
                        pathQueue.add(newPath);
                      }
                    }
                  }
                }
              }
            } else {
              return result.get();
            }
          }
        }
      }
    }
    return null;
  }

  protected final V startVertex;
  protected PathQueue<V, W> pathQueue;
  protected ExpandOp<V, W> expandOp = ExpandOp::outgoingEdges;
  protected ExploreOp<V, W, R> exploreOp;
  protected PathCostOp<V, W> pathCostOp;
}
