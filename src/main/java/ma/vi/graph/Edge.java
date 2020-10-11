package ma.vi.graph;

/**
 * An edge links two nodes, known as the source and target. Edges comes in two varieties:
 * Directed and Undirected. Directed edges discriminate between the source and target
 * nodes with the edge akin to a one-way road where traffic can only move from the source to
 * the target; undirected edges have no such restrictions and movement can be construed as
 * allowed in both directions. Another major difference between directed and undirected edges
 * is that an undirected edge with source A and target B is equal to an undirected edge with
 * source B and target A, i.e., an undirected edge is an unordered pair; a directed edge,
 * meanwhile, behaves as an ordered pair.
 *
 * @param <N> The type of nodes that this edge connects.
 * @param <W> The weight type of the edge.
 * @author vikash.madhow@gmail.com
 */
public interface Edge<N, W> {
  /**
   * The first endpoint (node) of the edge.
   */
  N endPoint1();

  /**
   * The second endpoint (node) of the edge.
   */
  N endPoint2();

  /**
   * The weight of the edge.
   */
  W weight();
}
