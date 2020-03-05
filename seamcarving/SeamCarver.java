package seamcarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public interface SeamCarver {

    /** Returns the current image. (This updates whenever a seam is removed.) */
    Picture picture();

    /** Sets the current image. */
    void setPicture(Picture picture);

    /** Returns the width of the current image, in pixels. */
    int width();

    /** Returns the height of the current image, in pixels. */
    int height();

    /** Returns the color of pixel (x, y) in the current image. */
    Color get(int x, int y);

    /** Returns the energy of pixel (x, y) in the current image. */
    default double energy(int x, int y) {
        if (!inBounds(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        int xPre;
        int xNext;
        int yPre;
        int yNext;
        if (x == 0 && x == width() - 1) {
            xNext = x;
            xPre = x;
        } else if (x == 0) {
            xNext = x + 1;
            xPre = width() - 1;
        } else if (x == width() - 1) {
            xNext = 0;
            xPre = x - 1;
        } else {
            xNext = x + 1;
            xPre = x - 1;
        }

        if (y == 0 && y == height() - 1) {
            yNext = y;
            yPre = y;
        } else if (y == 0) {
            yNext = y + 1;
            yPre = height() - 1;
        } else if (y == height() - 1) {
            yNext = 0;
            yPre = y - 1;
        } else {
            yNext = y + 1;
            yPre = y - 1;
        }

        return helperEnergy(get(xPre, y), get(xNext, y), get(x, yPre), get(x, yNext));
    }

    private double helperEnergy(Color cL, Color cR, Color cT, Color cB) {
        return Math.sqrt(gradiantFunc(cL, cR) + gradiantFunc(cT, cB));
    }

    private double gradiantFunc(Color c1, Color c2) {
        return Math.pow(c1.getRed() - c2.getRed(), 2) +
                Math.pow(c1.getBlue() - c2.getBlue(), 2) +
                Math.pow(c1.getGreen() - c2.getGreen(), 2);
    }

    /** Returns true iff pixel (x, y) is in the current image. */
    default boolean inBounds(int x, int y) {
        return (x >= 0) && (x < width()) && (y >= 0) && (y < height());
    }

    /**
     * Calculates and returns a minimum-energy horizontal seam in the current image.
     * The returned array will have the same length as the width of the image.
     * A value of v at index i of the output indicates that pixel (i, v) is in the seam.
     */
    int[] findHorizontalSeam();

    /**
     * Calculates and returns a minimum-energy vertical seam in the current image.
     * The returned array will have the same length as the height of the image.
     * A value of v at index i of the output indicates that pixel (v, i) is in the seam.
     */
    int[] findVerticalSeam();

    /** Calculates and removes a minimum-energy horizontal seam from the current image. */
    default void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException("Input seam array cannot be null.");
        } else if (width() == 1) {
            throw new IllegalArgumentException("Image width is 1.");
        } else if (seam.length != width()) {
            throw new IllegalArgumentException("Seam length does not match image width.");
        }

        for (int i = 0; i < seam.length - 2; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException(
                        "Invalid seam, consecutive vertical indices are greater than one apart.");
            }
        }

        Picture carvedPicture = new Picture(width(), height() - 1);
        /* Copy over the all indices besides the index specified by the seam */
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < seam[i]; j++) {
                carvedPicture.set(i, j, get(i, j));
            }

            for (int j = seam[i] + 1; j < height(); j++) {
                carvedPicture.set(i, j - 1, get(i, j));
            }
        }

        setPicture(carvedPicture);
    }

    /** Calculates and removes a minimum-energy vertical seam from the current image. */
    default void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new NullPointerException("Input seam array cannot be null.");
        } else if (height() == 1) {
            throw new IllegalArgumentException("Image height is 1.");
        } else if (seam.length != height()) {
            throw new IllegalArgumentException("Seam length does not match image height.");
        }

        for (int i = 0; i < seam.length - 2; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException(
                        "Invalid seam, consecutive horizontal indices are greater than one apart.");
            }
        }

        Picture carvedPicture = new Picture(width() - 1, height());
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < seam[i]; j++) {
                carvedPicture.set(j, i, get(j, i));
            }

            for (int j = seam[i] + 1; j < width(); j++) {
                carvedPicture.set(j - 1, i, get(j, i));
            }
        }

        setPicture(carvedPicture);
    }
}
