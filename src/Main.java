import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private static Calcker calcker;

    public static void main(String[] args) {
        JFrame frame = new JFrame("моя прекрасная лаба");
        MyPanel panel = new MyPanel(calcker);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Операции");
        JMenuItem itme = new JMenuItem("Просто начать");
        itme.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(() -> {
                    double lastMove = 0;
                    for (int i = 0; i < 30; i++) {
                        double moved = calcker.calcDist();
                        calcker.relocateCentroids();
                        panel.repaint();
                        if (Math.abs(lastMove - moved) < 1) {
                            break;
                        }
                        //System.out.println("step " + i + " moved " + Math.abs(lastMove - moved));
                        lastMove = moved;
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                thread.start();

            }
        });
        menu.add(itme);
        menu.addSeparator();
        itme = new JMenuItem("Сгруппировать по кластерам");
        itme.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcker.calcDist();
                panel.repaint();
            }
        });
        menu.add(itme);
        menu.addSeparator();
        itme = new JMenuItem("Пересчитать центр масс");
        itme.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcker.relocateCentroids();
                panel.repaint();
            }
        });
        menu.add(itme);
        menu.addSeparator();
        itme = new JMenuItem("Добавить точек");
        itme.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcker.generateNewPoints(10000);
                panel.repaint();
            }
        });
        menu.add(itme);
        menu.addSeparator();
        itme = new JMenuItem("Очистить");
        itme.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcker.CleanAll();
                panel.repaint();
            }
        });
        menu.add(itme);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
        frame.add(panel);
        frame.setSize(new Dimension(1000, 500));

        calcker = new Calcker(frame.getWidth(), frame.getHeight(), 100, 7);
        panel.setCalcker(calcker);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.repaint();
                calcker.setHeight(frame.getHeight());
                calcker.setWidth(frame.getWidth());
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                panel.repaint();
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    calcker.addCentroid(e);
                } else {
                    calcker.addPoint(e);
                }
                panel.repaint();
            }
        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    calcker.addCentroid(e);
                } else {
                    calcker.addPoint(e);
                }
                panel.repaint();
            }
        });
        frame.setVisible(true);
    }
}
