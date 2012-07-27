package by.inhw.flur.platform.opengl;

import java.awt.BorderLayout;

import javax.media.opengl.DebugGL3;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;

public class JoglCanvas extends GLCanvas implements GLEventListener
{
    /** The OpenGL animator. */
    private FPSAnimator animator;
    private GLU glu = new GLU();
    private int fps = 60;

    private static int width = 200;
    private static int height = 150;

    public JoglCanvas(int width, int height, GLCapabilities capabilities)
    {
        super(capabilities);
        setSize(width, height);
        addGLEventListener(this);
    }

    public void init(GLAutoDrawable drawable)
    {
        glu = new GLU();

        GL gl = drawable.getGL();

        GL debugGL = new DebugGL3((GL3) gl);
        drawable.setGL(debugGL);

        // Global settings.
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        // gl.glShadeModel(GL.GL_SMOOTH);
        // gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glClearColor(0f, 0f, 0f, 1f);

        // Start animator (which should be a field).
        animator = new FPSAnimator(this, fps);
        animator.start();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height)
    {
        GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged)
    {
        // when switching to another monitor, ditch it
        throw new UnsupportedOperationException(
                "Changing display is not supported.");
    }

    public void display(GLAutoDrawable drawable)
    {
        GL gl = drawable.getGL();

        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    public void dispose(GLAutoDrawable drawable)
    {
    }

    private static GLCapabilities createGLCapabilities()
    {
        GLCapabilities capabilities = new GLCapabilities(null);
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
        return capabilities;
    }

    public final static void main(String[] args)
    {
        JoglCanvas canvas = new JoglCanvas(width, height,
                createGLCapabilities());
        JFrame frame = new JFrame("JOGL");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        canvas.requestFocus();
    }
}