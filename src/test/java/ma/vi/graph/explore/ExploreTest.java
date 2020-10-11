package ma.vi.graph.explore;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ma.vi.graph.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ExploreTest {
  static DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(
      new VertexMap<Integer, Integer>()
          .add(1, 1, 2, 3, 4, 5)
          .add(5, 2, 6, 7)
          .add(6, 1, 8)
          .add(6, 4, 9)
          .add(8, 1, 9)
          .add(8, 20, 10)
          .add(9, 2, 10)
  );

  @BeforeAll
  static void print() {
    System.out.println(graph.toGraphViz());
  }

  @Test
  void breadthFirst() {
    List<Integer> acc = new ArrayList<>();
    Explore.breadthFirst(
      graph, 1, (g, p, a) -> {
        System.out.println(p);
        acc.add(p.end().orElse(0));
        return Collections.emptySet();
      },
      acc
    );
    System.out.println(acc);
    System.out.println();
    assertEquals(acc, Arrays.asList(1, 4, 2, 5, 3, 6, 7, 8, 9, 10));
  }

  @Test
  void depthFirst() {
    List<Integer> acc = new ArrayList<>();
    Explore.depthFirst(
      graph, 1, (g, p, a) -> {
        System.out.println(p);
        acc.add(p.end().orElse(0));
        return Collections.emptySet();
      },
      acc
    );
    System.out.println(acc);
    System.out.println();
    assertEquals(acc, Arrays.asList(1, 3, 5, 7, 6, 9, 10, 8, 2, 4));
  }

  @Test
  void minCostFirst() {
    List<Integer> acc = new ArrayList<>();
    Explore.minCostFirst(
      graph, 1, (g, p, a) -> {
        System.out.println(p);
        acc.add(p.end().orElse(0));
        return Collections.emptySet();
      },
      acc
    );
    System.out.println(acc);
    System.out.println();
    assertEquals(acc, Arrays.asList(1, 4, 3, 5, 2, 7, 6, 8, 9, 10));
  }
}