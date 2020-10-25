package ma.vi.graph.algo.explore;

import ma.vi.graph.algo.TestGraphs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Vikash Madhow (vikash.madhow@gmail.com)
 */
class ExploreTest {
  @BeforeAll
  static void print() {
    System.out.println(TestGraphs.basicGraph1.toGraphViz());
  }

  @Test
  void breadthFirst() {
    List<Integer> acc = new ArrayList<>();
    TestGraphs.basicGraph1.apply(
        new BreadthFirstExplore<Integer, Integer, Void>(1)
          .exploreOp(
              (g, p) -> {
                System.out.println(p);
                acc.add(p.lastVertex().orElse(0));
                return Optional.empty();
              }
          )
    );
    System.out.println(acc);
    System.out.println();
    List<Integer> exp = asList(1, 4, 2, 5, 3, 6, 7, 8, 9, 10);
    assertEquals(new HashSet<>(acc.subList(0, 1)),
                 new HashSet<>(exp.subList(0, 1)));
    assertEquals(new HashSet<>(acc.subList(1, 5)),
                 new HashSet<>(exp.subList(1, 5)));
    assertEquals(new HashSet<>(acc.subList(5, 7)),
                 new HashSet<>(exp.subList(5, 7)));
    assertEquals(new HashSet<>(acc.subList(7, 9)),
                 new HashSet<>(exp.subList(7, 9)));
    assertEquals(new HashSet<>(acc.subList(9, 10)),
                 new HashSet<>(exp.subList(9, 10)));
  }

  @Test
  void depthFirst() {
    List<Integer> acc = new ArrayList<>();
    TestGraphs.basicGraph1.apply(
        new DepthFirstExplore<Integer, Integer, Void>(1)
            .exploreOp(
                (g, p) -> {
                  System.out.println(p);
                  Integer i = p.lastVertex().orElse(0);
                  System.out.println(i);
                  acc.add(i);
                  return Optional.empty();
                }
            )
    );
    System.out.println(acc);
    System.out.println();

    assertEquals(10, acc.size());
    assertEquals(IntStream.range(1, 11).boxed().collect(Collectors.toSet()), new HashSet<>(acc));
  }

  @Test
  void minCostFirst() {
    List<Integer> acc = new ArrayList<>();
    TestGraphs.basicGraph1.apply(
        new MinCostExplore<Integer, Integer, Void>(1)
            .exploreOp(
                (g, p) -> {
                  System.out.println(p);
                  acc.add(p.lastVertex().orElse(0));
                  return Optional.empty();
                }
            )
    );

    System.out.println(acc);
    System.out.println();
    List<Integer> exp = asList(1, 4, 2, 5, 3, 6, 7, 8, 9, 10);
    assertEquals(new HashSet<>(acc.subList(0, 1)),
                 new HashSet<>(exp.subList(0, 1)));
    assertEquals(new HashSet<>(acc.subList(1, 5)),
                 new HashSet<>(exp.subList(1, 5)));
    assertEquals(new HashSet<>(acc.subList(5, 7)),
                 new HashSet<>(exp.subList(5, 7)));
    assertEquals(new HashSet<>(acc.subList(7, 8)),
                 new HashSet<>(exp.subList(7, 8)));
    assertEquals(new HashSet<>(acc.subList(8, 9)),
                 new HashSet<>(exp.subList(8, 9)));
    assertEquals(new HashSet<>(acc.subList(9, 10)),
                 new HashSet<>(exp.subList(9, 10)));
  }
}
