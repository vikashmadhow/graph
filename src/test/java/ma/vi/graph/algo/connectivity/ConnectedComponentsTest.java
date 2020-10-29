package ma.vi.graph.algo.connectivity;

import ma.vi.graph.Graph;
import ma.vi.graph.algo.TestGraphs;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ConnectedComponentsTest {
  @Test
  void singleComponent() {
    List<Set<String>> components = TestGraphs.basicGraph2.apply(new ConnectedComponents<>());
    assertEquals(1, components.size());
    assertEquals(components.get(0), TestGraphs.basicGraph2.vertices());
  }

  @Test
  void twoComponents() {
    List<Set<String>> components = TestGraphs.basicGraph3Disjoint.apply(new ConnectedComponents<>());
    assertEquals(2, components.size());
    assertEquals(
        Set.of(components.get(0), components.get(1)),
        Set.of(Set.of("v0", "v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8"),
               Set.of("v9", "v10", "v11", "v12", "v13")));
  }
}