package ma.vi.graph.algo.tree;

import ma.vi.graph.Graph;
import ma.vi.graph.UndirectedEdge;
import ma.vi.graph.algo.TestGraphs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class SpanningTreeTest {
  @BeforeAll
  static void print() {
    System.out.println(TestGraphs.basicGraph2.toGraphViz());
  }

  @Test
  void primMst() {
    Graph<String, Integer, UndirectedEdge<String, Integer>> tree =
        TestGraphs.basicGraph2.apply(new PrimMst<>());
    System.out.println(tree.toGraphViz());
  }
}