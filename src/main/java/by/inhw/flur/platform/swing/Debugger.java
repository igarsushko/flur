package by.inhw.flur.platform.swing;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Debugger
{
    static JFrame frame;
    static JPanel panel;
    static boolean on = true;

    static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    static Map<String, JLabel> labels = new HashMap<String, JLabel>();

    public static void on()
    {
        on = true;
    }

    public static void off()
    {
        on = false;
    }

    public static void setFrame(JFrame jframe)
    {
        frame = jframe;
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBounds(5, 5, 200, 20);
        panel.setOpaque(false);
        // panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        frame.add(panel);

        // logSome();
    }

    static void addLabelToFrame(JLabel label)
    {
        int count = labels.size();
        panel.setBounds(5, 5, 200, 20 * count);
        panel.add(label);
        panel.repaint();
    }

    public static void log(String name, Object value)
    {
        if (on)
        {
            JLabel label = labels.get(name);
            if (label == null)
            {
                label = new JLabel();
                labels.put(name, label);
                addLabelToFrame(label);
            }

            String formattedValue = format(value);
            label.setText(name + formattedValue);
        }
    }

    static String format(Object value)
    {
        String result = null;

        if (value instanceof Double)
        {
            Double d = (Double) value;
            DecimalFormat df = new DecimalFormat("#.############");
            result = df.format(d.doubleValue());
        }
        else
        {
            result = value.toString();
        }

        return result;
    }

    static void logSome()
    {
        new Thread()
        {
            public void run()
            {
                while (true)
                {
                    long[] ids = threadMXBean.getAllThreadIds();
                    for (long id : ids)
                    {
                        ThreadInfo info = threadMXBean.getThreadInfo(id, Integer.MAX_VALUE);
                        log(info.getThreadName(), info.getThreadId());
                    }

                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }.start();
    }
}
