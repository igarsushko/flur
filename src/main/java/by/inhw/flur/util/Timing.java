package by.inhw.flur.util;

public class Timing
{
    public static final int FRAME_TIME_MILLIS = 25;
    public static final double FRAME_TIME_SEC = (double) FRAME_TIME_MILLIS / 1000.0;

    private long frameNumber = 0;

    private double lastFrameTimestamp = System.currentTimeMillis();
    private double lastFrameDuration = 0;

    private float fps = 0;
    private double averageFrameDuration = 0;

    private static boolean isPaused = false;

    private static final double startTime = System.currentTimeMillis();

    // Updates the global frame information. Should be called once per frame.
    public void update()
    {
        // Advance the frame number.
        if (!isPaused)
        {
            frameNumber++;
        }

        // Update the timing information.
        double currentTime = systemTime();
        lastFrameDuration = currentTime - lastFrameTimestamp;
        lastFrameTimestamp = currentTime;

        if (frameNumber > 1)
        {
            if (averageFrameDuration == 0)
            {
                averageFrameDuration = lastFrameDuration;
            }
            else
            {
                averageFrameDuration *= 0.99;
                averageFrameDuration += 0.01 * lastFrameDuration;

                fps = (float) (1000 / averageFrameDuration);
            }
        }
    }

    public double systemTime()
    {
        return System.currentTimeMillis();
    }

    public static boolean isPaused()
    {
        return Timing.isPaused;
    }

    public static void setPaused(boolean isPaused)
    {
        Timing.isPaused = isPaused;
    }
}
