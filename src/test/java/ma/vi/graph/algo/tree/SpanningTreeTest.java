package ma.vi.graph.algo.tree;

import ma.vi.graph.Graph;
import ma.vi.graph.UndirectedGraph;
import ma.vi.graph.VertexMap;
import ma.vi.graph.algo.TestGraphs;
import org.junit.jupiter.api.Assertions;
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
    Graph<String, Integer> tree =
        TestGraphs.basicGraph2.apply(new PrimMst<>());
    System.out.println(tree.toGraphViz());
    Assertions.assertEquals(
        new UndirectedGraph<>(
            new VertexMap<String, Integer>()
                .add("v0", 4,   "v1")
                .add("v0", 8,   "v8")
                .add("v3", 7,   "v4")
                .add("v2", 2,   "v3")
                .add("v3", 4,   "v6")
                .add("v4", 9,   "v5")
                .add("v6", 2,   "v7")
                .add("v7", 1,   "v8")
                .build()
        ),
        tree);
  }

  @Test
  void kruskalMst() {
    Graph<String, Integer> tree =
        TestGraphs.basicGraph2.apply(new KruskalMst<>());
    System.out.println(tree.toGraphViz());
    Assertions.assertEquals(
        new UndirectedGraph<>(
            new VertexMap<String, Integer>()
                .add("v0", 4,   "v1")
                .add("v0", 8,   "v8")
                .add("v3", 7,   "v4")
                .add("v2", 2,   "v3")
                .add("v3", 4,   "v6")
                .add("v4", 9,   "v5")
                .add("v6", 2,   "v7")
                .add("v7", 1,   "v8")
                .build()
        ),
        tree);
  }
}