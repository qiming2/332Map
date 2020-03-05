package seamcarving;

import astar.AStarGraph;
import astar.WeightedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixelGraph  implements AStarGraph<PointS> {
    Map<PointS, List<WeightedEdge<PointS>>> relation;
    public static PointS start = new PointS(-1, -1);
    public static PointS end = new PointS(-2, -2);

    PixelGraph(SeamCarver s) {
        relation = new HashMap<>();
        int height = s.height();
        int width = s.width();
        List<WeightedEdge<PointS>> list;
        WeightedEdge<PointS> edge;
        list = new ArrayList<WeightedEdge<PointS>>();
        for (int i = 0; i < width; i++) {
            edge = new WeightedEdge<PointS>(start, new PointS(i, 0), s.energy(i, 0));
            list.add(edge);
        }
        relation.put(start, list);
        PointS curRow;
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                list = new ArrayList<>();
                curRow = new PointS(j, i);
                if (j == 0 && j == width - 1) {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j, i + 1), s.energy(j, i + 1)));
                } else if (j == 0) {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j, i + 1), s.energy(j, i + 1)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j + 1, i + 1), s.energy(j + 1, i + 1)));
                } else if (j == width - 1) {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j - 1, i + 1), s.energy(j - 1, i + 1)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j, i + 1), s.energy(j, i + 1)));
                } else {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j - 1, i + 1), s.energy(j - 1, i + 1)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j, i + 1), s.energy(j, i + 1)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(j + 1, i + 1), s.energy(j + 1, i + 1)));
                }
                relation.put(curRow, list);
            }
        }
        PointS cur;
        for (int i = 0; i < width; i++) {
            list = new ArrayList<WeightedEdge<PointS>>();
            cur = new PointS(i, height - 1);
            edge = new WeightedEdge<PointS>(cur, end, 0);
            list.add(edge);
            relation.put(cur, list);
        }
    }

    @Override
    public List<WeightedEdge<PointS>> neighbors(PointS v) {
        return relation.get(v);
    }

    @Override
    public double estimatedDistanceToGoal(PointS s, PointS goal) {
        return 0;
    }
}
