package ma.vi.graph.explore;

import org.junit.jupiter.api.Test;
import ma.vi.graph.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ExploreTest {
  @Test
  void breadthFirst() {
    DirectedGraph<Integer, Integer> graph = new DirectedGraph<>(
        new VertexMap<Integer, Integer>()
            .add(1, 1, 2, 3, 4, 5)
            .add(5, 2, 6, 7)
            .add(6, 8, 1)
            .add(6, 9, 4)
            .add(8, 9, 1)
            .add(8, 10, 20)
            .add(9, 10, 2)
    );
    System.out.println(graph.toGraphViz());

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

    acc.clear();
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

    acc.clear();
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
  }
}