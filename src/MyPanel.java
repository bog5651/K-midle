import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    private Calcker calcker;

    public MyPanel(Calcker calcker) {
        this.calcker = calcker;
    }

    public void setCalcker(Calcker calcker) {
        this.calcker = calcker;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (calcker != null)
            for (PaintObject object : calcker.getAllPoint()) {
                g2.setColor(object.color);
                g2.setStroke(new BasicStroke(1));
                g2.fillOval(object.X, object.Y, 20, 20);
                if(object.isCentroid){
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(3));
                    g2.drawOval(object.X, object.Y, 20, 20);
                }
            }
    }
}
