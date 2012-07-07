package by.inhw.flur.util;

public class Timing
{
    public static final int FRAME_TIME_MILLIS = 10;
    public static final double FRAME_TIME_SEC = (double) FRAME_TIME_MILLIS / 1000.0;

    long frameNumber = 0;

    double lastFrameTimestamp = System.currentTimeMillis();
    double lastFrameDuration = 0;

    float fps = 0;
    double averageFrameDuration = 0;

    static boolean isPaused = false;

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
