package ma.vi.graph;

import ma.vi.base.tuple.T2;

import java.util.List;

/**
 * A path is simply a list of nodes together with a cost value which
 * may be nominal only in certain applications (e.g. simple exploration)
 * while, in others, it can be of significant value (e.g. selecting
 * least cost paths).
 *
 * @param <N> The node type.
 * @param <C> A value for the cost of the path.
 * @author vikash.madhow@gmail.com
 */
public class Path<N, C> extends T2<List<N>, C> {
  public Path(List<N> nodes, C cost) {
    super(nodes, cost);
  }
}
