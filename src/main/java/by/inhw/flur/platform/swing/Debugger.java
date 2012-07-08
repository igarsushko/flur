package by.inhw.flur.platform.swing;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import by.inhw.flur.util.CpuProfiler;

public class Debugger
{
    private static JFrame frame;
    private static JPanel panel;
    private static boolean on = true;

    // in milliseconds
    private static int statInterval = 1000;

    private static DecimalFormat cpuLoadFormat = new DecimalFormat("#.##");
    private static DecimalFormat memUsedFormat = new DecimalFormat("#.##");
    private static DecimalFormat decimalFormat = new DecimalFormat("#.############");

    private static Map<String, JLabel> labels = Collections.synchronizedMap(new HashMap<String, JLabel>());

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
        frame.add(panel);

        logCpuUsage();
        logMemoryUsage();
    }

    static synchronized JLabel addLabelToFrame(String name, JLabel label)
    {
        label = new JLabel();
        labels.put(name, label);

        int count = labels.size();
        panel.setBounds(5, 5, 200, 20 * count);
        panel.add(label);
        panel.repaint();
        return label;
    }

    static void D(Object o)
    {
        System.out.println(Thread.currentThread().getName() + ": " + o);
    }

    public static void log(String name, Object value)
    {
        if (on)
        {
            JLabel label = labels.get(name);
            if (label == null)
            {
                label = addLabelToFrame(name, label);
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
            result = decimalFormat.format(d);
        }
        else
        {
            result = value.toString();
        }

        return result;
    }

    static void logCpuUsage()
    {
        CpuProfiler profiler = new CpuProfiler(statInterval);
        profiler.setUpdateHandler(new CpuProfiler.UpdateHandler()
        {
            public void onUpdate(double cpuLoad)
            {
                String cpuLoadFormatted = cpuLoadFormat.format(cpuLoad);
                Debugger.log("CPU ", cpuLoadFormatted + "%");
            }
        });
        profiler.start();
    }

    static void logMemoryUsage()
    {
        new Thread()
        {
            public void run()
            {
                this.setName("Memory monitor");

                while (true)
                {
                    long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    double usedKb = (double) used / 1024;
                    String useKbFormatted = memUsedFormat.format(usedKb);

                    Debugger.log("Memory ", useKbFormatted + "Kb");

                    try
                    {
                        Thread.sleep(statInterval);
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