package neo.photogridart.bitmap;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.lang.reflect.Array;

public class ConvolutionMatrix {
    public static final int SIZE = 3;
    public double Factor = 1.0d;
    public double[][] Matrix;
    public double Offset = 1.0d;

    public ConvolutionMatrix(int size) {
        this.Matrix = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, size});
    }

    public void setAll(double value) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.Matrix[x][y] = value;
            }
        }
    }

    public void applyConfig(double[][] config) {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.Matrix[x][y] = config[x][y];
            }
        }
    }

    public static Bitmap computeConvolution3x3(Bitmap src, ConvolutionMatrix matrix) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
        int[][] pixels = (int[][]) Array.newInstance(Integer.TYPE, new int[]{3, 3});
        for (int y = 0; y < height - 2; y++) {
            for (int x = 0; x < width - 2; x++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        pixels[i][j] = src.getPixel(x + i, y + j);
                    }
                }
                int A = Color.alpha(pixels[1][1]);
                int sumB = 0;
                int sumG = 0;
                int sumR = 0;
                for (int i2 = 0; i2 < 3; i2++) {
                    for (int j2 = 0; j2 < 3; j2++) {
                        sumR = (int) (((double) sumR) + (((double) Color.red(pixels[i2][j2])) * matrix.Matrix[i2][j2]));
                        sumG = (int) (((double) sumG) + (((double) Color.green(pixels[i2][j2])) * matrix.Matrix[i2][j2]));
                        sumB = (int) (((double) sumB) + (((double) Color.blue(pixels[i2][j2])) * matrix.Matrix[i2][j2]));
                    }
                }
                int R = (int) ((((double) sumR) / matrix.Factor) + matrix.Offset);
                if (R < 0) {
                    R = 0;
                } else if (R > 255) {
                    R = 255;
                }
                int G = (int) ((((double) sumG) / matrix.Factor) + matrix.Offset);
                if (G < 0) {
                    G = 0;
                } else if (G > 255) {
                    G = 255;
                }
                int B = (int) ((((double) sumB) / matrix.Factor) + matrix.Offset);
                if (B < 0) {
                    B = 0;
                } else if (B > 255) {
                    B = 255;
                }
                result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
            }
        }
        src.recycle();
        return result;
    }
}
