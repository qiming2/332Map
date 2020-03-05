package seamcarving;

import astar.AStarSolver;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.List;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public int[] findHorizontalSeam() {
        PixelGraphH p = new PixelGraphH(this);
        AStarSolver<PointS> solver = new AStarSolver<>(p, PixelGraphH.start, PixelGraphH.end, 100);
        List<PointS> list = solver.solution();
        int[] ret = new int[width()];
        for (int i = 0; i < width(); i++) {
            ret[i] = list.get(i + 1).y;
        }
        return ret;
    }

    public int[] findVerticalSeam() {
        PixelGraph p = new PixelGraph(this);
        AStarSolver<PointS> solver = new AStarSolver<>(p, PixelGraph.start, PixelGraph.end, 100);
        List<PointS> list = solver.solution();
        int[] ret = new int[height()];
        for (int i = 0; i < height(); i++) {
            ret[i] = list.get(i + 1).x;
        }
        return ret;
    }
}
