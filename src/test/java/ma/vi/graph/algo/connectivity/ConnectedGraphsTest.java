package ma.vi.graph.algo.connectivity;

import ma.vi.graph.Graph;
import ma.vi.graph.UndirectedGraph;
import ma.vi.graph.VertexMap;
import ma.vi.graph.algo.TestGraphs;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ConnectedGraphsTest {
  @Test
  void singleComponent() {
    List<Graph<String, Integer>> components = TestGraphs.basicGraph2.apply(new ConnectedGraphs<>());
    assertEquals(1, components.size());
    assertEquals(components.get(0), TestGraphs.basicGraph2);
  }

  @Test
  void twoComponents() {
    List<Graph<String, Integer>> components = TestGraphs.basicGraph3Disjoint.apply(new ConnectedGraphs<>());
    assertEquals(2, components.size());
    assertEquals(
        Set.of(components.get(0), components.get(1)),
        Set.of(
            new UndirectedGraph<>(
                new VertexMap<String, Integer>()
                    .add("v0",  100, "v0")
                    .add("v0",  4,   "v1")
                    .add("v0",  8,   "v8")
                    .add("v1",  8,   "v3")
                    .add("v1",  11,  "v8")
                    .add("v2",  2,   "v3")
                    .add("v2",  7,   "v8")
                    .add("v2",  6,   "v7")
                    .add("v3",  7,   "v4")
                    .add("v3",  4,   "v6")
                    .add("v4",  9,   "v5")
                    .add("v4",  14,  "v6")
                    .add("v6",  2,   "v7")
                    .add("v7",  1,   "v8")
                    .build()),
            new UndirectedGraph<>(
                new VertexMap<String, Integer>()
                    .add("v9",  1,   "v10")
                    .add("v9",  1,   "v11")
                    .add("v11", 1,   "v12")
                    .add("v12", 1,   "v13")
                    .build())));
  }
}