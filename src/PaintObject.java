import java.awt.*;

public class PaintObject {
    public int X;
    public int Y;

    public Color color;
    public boolean isCentroid;

    public PaintObject(int x, int y, Color color, boolean isCentroid) {
        X = x;
        Y = y;
        this.color = color;
        this.isCentroid = isCentroid;
    }
}
