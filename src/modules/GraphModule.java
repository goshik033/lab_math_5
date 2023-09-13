package modules;

import interfaces.IFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphModule extends JPanel {
    double x1 = -5, x2 = 5, y1 = -5, y2 = 5;
    double step_x = 1;
    double step_y = 1;
    int WIDTH = 640;
    int HEIGHT = 640;
    int lastX = 0;
    int lastY = 0;
    double a;
    double b;
    double y0;
    Map<JCheckBox, ArrayList<IFunction>> f = new HashMap<>();
    Map<JCheckBox, ArrayList<Point<Double>>> points = new HashMap<>();

    public GraphModule(Map<String, ArrayList<IFunction>> map_func, Map<String, ArrayList<Point<Double>>> map_points,
                       double a,double b,double y0) {
        this.a=a;
        this.b=b;
        this.y0=y0;
        for (Map.Entry<String, ArrayList<IFunction>> entry : map_func.entrySet()) {
            JCheckBox j = new JCheckBox(entry.getKey());
            j.setSelected(true);
            j.addItemListener(e -> updateUI());
            f.put(j, entry.getValue());
        }
        for (Map.Entry<String, ArrayList<Point<Double>>> entry : map_points.entrySet()) {
            JCheckBox j = new JCheckBox(entry.getKey());
            j.setSelected(true);
            j.addItemListener(e -> updateUI());
            points.put(j, entry.getValue());
        }
        frameOp();
    }

    public void frameOp() {
        JFrame JF = new JFrame("Красиво изогнутые линии");
        JF.setBounds(100, 100, WIDTH + 16, HEIGHT + 28 + 38 + 10 + 10 * (f.size() + points.size()));
        JF.setVisible(true);
        JF.setResizable(true);

        Box box = Box.createVerticalBox();
        box.add(this);
        for (Map.Entry<JCheckBox, ArrayList<IFunction>> entry : f.entrySet()) {
            box.add(entry.getKey());
        }
        for (Map.Entry<JCheckBox, ArrayList<Point<Double>>> entry : points.entrySet()) {
            box.add(entry.getKey());
        }
        JButton b_selall = new JButton("Поставить все галочки");
        b_selall.addActionListener(e -> {
            for (Map.Entry<JCheckBox, ArrayList<IFunction>> entry : f.entrySet()) {
                entry.getKey().setSelected(true);
            }
            for (Map.Entry<JCheckBox, ArrayList<Point<Double>>> entry : points.entrySet()) {
                entry.getKey().setSelected(true);
            }
            updateUI();
        });
        JButton b_clean = new JButton("Убрать все галочки");
        b_clean.addActionListener(e -> {
            for (Map.Entry<JCheckBox, ArrayList<IFunction>> entry : f.entrySet()) {
                entry.getKey().setSelected(false);
            }
            for (Map.Entry<JCheckBox, ArrayList<Point<Double>>> entry : points.entrySet()) {
                entry.getKey().setSelected(false);
            }
            updateUI();
        });
        box.add(b_selall);
        box.add(b_clean);
        JF.add(box);
        this.setBackground(Color.WHITE);
        updateUI();
        MouseAdapter MA = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent evt) {
                lastX = evt.getX();
                lastY = evt.getY();
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                lastX = evt.getX();
                lastY = evt.getY();
            }

            @Override
            public void mouseDragged(MouseEvent evt) {
                if (evt.getModifiers() == InputEvent.BUTTON1_MASK) {
                    int newX = evt.getX();
                    int newY = evt.getY();
                    double dx = (x2 - x1) / WIDTH * (lastX - newX);
                    double dy = -(y2 - y1) / HEIGHT * (lastY - newY);
                    x1 += dx;
                    x2 += dx;
                    y1 += dy;
                    y2 += dy;
                    lastX = newX;
                    lastY = newY;
                    repaint();
                } else if (evt.getModifiers() == InputEvent.BUTTON3_MASK) {
                    int newX = evt.getX();
                    int newY = evt.getY();
                    double dx = (x2 - x1) / WIDTH * (lastX - newX);
                    double dy = -(y2 - y1) / HEIGHT * (lastY - newY);
                    x2 += dx;
                    y1 += dy;
                    lastX = newX;
                    lastY = newY;
                    repaint();
                }
            }
        };

        addMouseListener(MA);
        addMouseMotionListener(MA);

        this.addMouseWheelListener(e -> {
            int r = e.getWheelRotation();
            double dx = (x2 - x1) / (10 + Math.abs(r + 1) / 2);
            double dy = (y2 - y1) / (10 + Math.abs(r + 1) / 2);
            x1 -= r * dx * lastX / WIDTH;
            x2 += r * dx * (WIDTH - lastX) / WIDTH;
            y1 -= r * dy * (HEIGHT - lastY) / HEIGHT;
            y2 += r * dy * lastY / HEIGHT;
            repaint();
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintD(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g.setColor(Color.RED);
        paintF(g);
    }

    public void paintD(Graphics g) {
        g.setColor(Color.GRAY);

        int OX = (int) (-x1 / (x2 - x1) * WIDTH);
        int OY = (int) (HEIGHT + y1 / (y2 - y1) * HEIGHT);

        for (int i = (int) Math.floor(x1 / step_x); i <= Math.floor(x2 / step_x); i++) {
            int positionX = (int) (-(x1 - step_x * i) / (x2 - x1) * WIDTH);

            g.drawLine(positionX, 0, positionX, HEIGHT);

            int positionY;

            if (OY < 12) {
                positionY = 10;
            } else if (OY > HEIGHT - 2) {
                positionY = HEIGHT - 2;
            } else {
                positionY = OY - 2;
            }

            int format = (int) (HEIGHT / 5 / (x2 - x1) / step_x);

            if (step_x * i == (int) (step_x * i)) {
                format = 0;
            } else if (format > 10) {
                format = 10;
            }

            g.drawString(String.format("%." + String.valueOf(format) + "f", i * step_x), positionX + 2, positionY);
        }
        for (int i = (int) Math.floor(y1 / step_y); i <= Math.floor(y2 / step_y); i++) {
            int positionY = (int) (HEIGHT + (y1 - step_y * i) / (y2 - y1) * HEIGHT);

            g.drawLine(0, positionY, WIDTH, positionY);

            int positionX;

            int format = (int) (HEIGHT / 5 / (x2 - x1) / step_y);

            if (step_y * i == (int) (step_y * i)) {
                format = 0;
            } else if (format > 10) {
                format = 10;
            }

            String formated_value = String.format("%." + String.valueOf(format) + "f", i * step_y);

            int len = (int) new TextLayout(formated_value, g.getFont(), new FontRenderContext(null, true, true)).getBounds().getWidth();

            if (OX < 1) {
                positionX = 2;
            } else if (OX > WIDTH - 7 - len) {
                positionX = WIDTH - 5 - len;
            } else {
                positionX = OX + 2;
            }

            g.drawString(formated_value, positionX, positionY - 1);
        }
        g.setColor(Color.BLACK);
        g.fillRect(OX - 1, 0, 3, HEIGHT);
        g.fillRect(0, OY - 1, WIDTH, 3);

    }

    public void paintF(Graphics g) {
        int t = 0;
        for (Map.Entry<JCheckBox, ArrayList<IFunction>> entry : f.entrySet()) {
            Color color = getColor(t);
            if (entry.getKey().isSelected()) {
                for (IFunction f1 : entry.getValue()) {
                    int q1;
                    q1 = HEIGHT - (int) Math.floor((HEIGHT / (Math.abs(y2 - y1))) * (f1.solve2(a, y0) - y1));
                    for (int i = (int) Math.floor(((a - x1) / (Math.abs(x2 - x1))) * WIDTH); i < Math.floor(((b - x1) / (Math.abs(x2 - x1))) * WIDTH); i++) {
                        double i2 = f1.solve2(x1 + ((Math.abs(x2 - x1)) / WIDTH) * i, y0);
                        int q2 = HEIGHT - (int) Math.floor((HEIGHT / (Math.abs(y2 - y1))) * (i2 - y1));
                        g.drawLine(i - 1, q1, i, q2);

                        q1 = q2;
                    }
                }
            }
            t++;
        }
        t = 0;
//        for (Map.Entry<JCheckBox, ArrayList<Point<Double>>> entry : points.entrySet()) {
//            Color color = getColor(t);
//            if (entry.getKey().isSelected()) {
//                for (Point<Double> p : entry.getValue()) {
//                    int positionX = (int) Math.floor((WIDTH / Math.abs(x1 - x2)) * (p.getX() - x1));
//                    int positionY = HEIGHT - (int) Math.floor((HEIGHT / (Math.abs(y2 - y1))) * (p.getY() - y1));
//                    g.setColor(color);
//                    g.fillOval(positionX - 3, positionY - 3, 6, 6);
//                }
//            }
//            t++;
//
//        }
        t = 0;
        for (Map.Entry<JCheckBox, ArrayList<Point<Double>>> entry : points.entrySet()) {
            Color color = getColor(t);
            if (entry.getKey().isSelected()) {
                int kekx = (int) Math.floor((WIDTH / Math.abs(x1 - x2)) * (entry.getValue().get(0).getX() - x1));
                int keky = HEIGHT - (int) Math.floor((WIDTH / Math.abs(x1 - x2)) * (entry.getValue().get(0).getY() - y1));

                for (Point<Double> p : entry.getValue()) {
                    int positionX = (int) Math.floor((WIDTH / Math.abs(x1 - x2)) * (p.getX() - x1));
                    int positionY = HEIGHT - (int) Math.floor((HEIGHT / (Math.abs(y2 - y1))) * (p.getY() - y1));
                    g.setColor(color);
                    g.drawLine(kekx, keky, positionX, positionY);
                    kekx = positionX;
                    keky = positionY;

                }
            }
            t++;
        }
    }

    private Color getColor(int i) {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.green);
        colors.add(Color.blue);
        colors.add(Color.black);
        colors.add(Color.magenta);
        colors.add(Color.YELLOW);
        if (i > 0) {
            while (i > colors.size()) {
                i -= colors.size();
            }
            return colors.get(i);
        } else {
            return Color.green;
        }
    }
}

