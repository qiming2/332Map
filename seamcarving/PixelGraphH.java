package seamcarving;

import astar.AStarGraph;
import astar.WeightedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PixelGraphH implements AStarGraph<PointS> {
    Map<PointS, List<WeightedEdge<PointS>>> relation;
    public static PointS start = new PointS(-1, -1);
    public static PointS end = new PointS(-2, -2);

    PixelGraphH(SeamCarver s) {
        relation = new HashMap<>();
        int height = s.height();
        int width = s.width();
        List<WeightedEdge<PointS>> list;
        WeightedEdge<PointS> edge;
        list = new ArrayList<WeightedEdge<PointS>>();
        for (int i = 0; i < height; i++) {
            edge = new WeightedEdge<PointS>(start, new PointS(0, i), s.energy(0, i));
            list.add(edge);
        }
        relation.put(start, list);
        PointS curRow;
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height; j++) {
                list = new ArrayList<>();
                curRow = new PointS(i, j);
                if (j == 0 && j == height - 1) {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j), s.energy(i + 1, j)));
                } else if (j == 0) {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j), s.energy(i + 1, j)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j + 1), s.energy(i + 1, j + 1)));
                } else if (j == height - 1) {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j - 1), s.energy(i + 1, j - 1)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j), s.energy(i + 1, j)));
                } else {
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j - 1), s.energy(i + 1, j - 1)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j), s.energy(i + 1, j)));
                    list.add(new WeightedEdge<PointS>(curRow, new PointS(i + 1, j + 1), s.energy(i + 1, j + 1)));
                }
                relation.put(curRow, list);
            }
        }
        PointS cur;
        for (int i = 0; i < height; i++) {
            list = new ArrayList<WeightedEdge<PointS>>();
            cur = new PointS(width - 1, i);
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
