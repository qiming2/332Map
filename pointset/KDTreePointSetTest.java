package pointset;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreePointSetTest {

    @Test
    public void testNaive() {
        List<Point> test = List.of(new Point(1.0, 1.0), new Point(2.0, 2.0),
        new Point(3.0, 3.0),
        new Point(2.5, 2.5));
        NaivePointSet set = new NaivePointSet(test);
        assertEquals(set.nearest(2.6, 2.6), new Point(2.5, 2.5));
    }

    @Test
    public void testKd() {
        List<Point> test = List.of(new Point(1.0, 1.0), new Point(2.0, 2.0),
                new Point(3.0, 3.0),
                new Point(2.6, 2.6),
                new Point(2.5, 2.5));
        KDTreePointSet set = new KDTreePointSet(test);
        assertEquals(set.nearest(1.7, 2.7), new Point(2.0, 2.0));
    }

    @Test
    public void testKdBadSide() {
        List<Point> test = List.of(new Point(2.0, 2.0),
                new Point(1.0, 1.0),
                new Point(4.0, 8.0));
        KDTreePointSet set = new KDTreePointSet(test);
        assertEquals(set.nearest(2.0, 8.0), new Point(4.0, 8.0));
    }

    @Test
    public void randomTest() {
        Random rand = new Random();
        int maxNum = 1000;
        double max = 1000.0;
        List<Point> list = new ArrayList<>();
        int numPoints = rand.nextInt(maxNum);
        // Insert numPoints
        for (int i = 0; i < numPoints; i++) {
            double xR = rand.nextDouble() * max;
            double yR = rand.nextDouble() * max;
            Point p = new Point(xR, yR);
            list.add(p);
        }
        NaivePointSet nai = new NaivePointSet(list);
        KDTreePointSet treeSet = new KDTreePointSet(list);
        int testNum = rand.nextInt(maxNum);
        int listSize = list.size();
        for (int i = 0; i < testNum; i++) {
            Point test = list.get(rand.nextInt(listSize));
            double x = test.x();
            double y = test.y();
            assertEquals(nai.nearest(x, y), treeSet.nearest(x, y));
        }
    }
}
