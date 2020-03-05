package huskymaps.server.logic;

import huskymaps.params.RasterRequest;
import huskymaps.params.RasterResult;

import java.util.Objects;

import static huskymaps.Constants.*;

/** Application logic for the RasterAPIHandler. */
public class Rasterer {

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param request RasterRequest
     * @return RasterResult
     */
    public static RasterResult rasterizeMap(RasterRequest request) {
        // Box goes out of bound
        if (request.ullat < ROOT_LRLAT || request.ullon > ROOT_LRLON ||
            request.lrlat > ROOT_ULLAT || request.lrlon < ROOT_ULLON) {
            Tile[][] ret = {{new Tile(request.depth, 0, 0)}};
            return new RasterResult(ret);
        }
        // Math to calculate the tiles of the map
        double yQuery = request.ullat - request.lrlat;
        double xQuery = request.lrlon - request.ullon;
        double xOff = request.ullon - ROOT_ULLON;
        double yOff = ROOT_ULLAT - request.ullat;
        double xRemain = xOff % LON_PER_TILE[request.depth];
        double yRemain = yOff % LAT_PER_TILE[request.depth];
        int xStartT = (int) (xOff / LON_PER_TILE[request.depth]);
        int yStartT = (int) (yOff / LAT_PER_TILE[request.depth]);

        int xEndT = computeConsecutiveTiles(xRemain, xStartT, request, xQuery, true);
        int yEndT = computeConsecutiveTiles(yRemain, yStartT, request, yQuery, false);
        // Check whether the box goes out of the bound
        if (xEndT > max(request.depth, true)) {
            xEndT = max(request.depth, true);
        }
        if (yEndT > max(request.depth, false)) {
            yEndT = max(request.depth, false);
        }
        if (xStartT < 0) {
            xStartT = 0;
        }
        if (yStartT < 0) {
            yStartT = 0;
        }
        int numX = xEndT - xStartT + 1;
        int numY = yEndT - yStartT + 1;
        // Creating the tiles
        Tile[][] ret = new Tile[numY][numX];
        for (int i = 0; i < numY; i++) {
            for (int j = 0; j < numX; j++) {
                ret[i][j] = new Tile(request.depth, xStartT + j,
                                    yStartT + i);
            }
        }
        return new RasterResult(ret);
    }

    // Check whether xEnd and yEnd goes out of bound
    private static int max(int depth, boolean isX) {
        if (isX) {
            return (int) Math.pow(2, depth + 1) - 1;
        } else {
            return (int) Math.pow(2, depth) - 1;
        }
    }

    // Compute how many more tiles are needed after start index
    private static int computeConsecutiveTiles(double remainder, int start,
                                               RasterRequest r, double boxLen, boolean isX) {
        double distance;
        if (isX) {
            if (remainder > 0) {
                distance = r.lrlon - ROOT_ULLON - (start + 1) * LON_PER_TILE[r.depth];
            } else {
                distance = boxLen;
            }
            System.out.println("X Distance:" + distance);
            int numT = (int) (distance / LON_PER_TILE[r.depth]);
            if (distance % LON_PER_TILE[r.depth] > 0) {
                numT++;
            }
            return start + numT;
        } else {
            if (remainder > 0) {
                distance = ROOT_ULLAT - r.lrlat - (start + 1) * LAT_PER_TILE[r.depth];
            } else {
                distance = boxLen;
            }
            System.out.println("Y Distance:" + distance);
            int numT = (int) (distance / LAT_PER_TILE[r.depth]);
            if (distance % LAT_PER_TILE[r.depth] >  0) {
                numT++;
            }
            return start + numT;
        }

    }

    public static class Tile {
        public final int depth;
        public final int x;
        public final int y;

        public Tile(int depth, int x, int y) {
            this.depth = depth;
            this.x = x;
            this.y = y;
        }

        public Tile offset() {
            return new Tile(depth, x + 1, y + 1);
        }

        /**
         * Return the latitude of the upper-left corner of the given slippy map tile.
         * @return latitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lat() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyY = MIN_Y_TILE_AT_DEPTH[depth] + y;
            double latRad = Math.atan(Math.sinh(Math.PI * (1 - 2 * slippyY / n)));
            return Math.toDegrees(latRad);
        }

        /**
         * Return the longitude of the upper-left corner of the given slippy map tile.
         * @return longitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lon() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyX = MIN_X_TILE_AT_DEPTH[depth] + x;
            return slippyX / n * 360.0 - 180.0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tile tile = (Tile) o;
            return depth == tile.depth &&
                    x == tile.x &&
                    y == tile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, x, y);
        }

        @Override
        public String toString() {
            return "d" + depth + "_x" + x + "_y" + y + ".jpg";
        }
    }
}
