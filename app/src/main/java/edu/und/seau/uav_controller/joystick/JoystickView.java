package edu.und.seau.uav_controller.joystick;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import edu.und.seau.uav_controller.R;

/**
 * Created with help from Daniel at https://www.instructables.com/id/A-Simple-Android-UI-Joystick/
 */
public class JoystickView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private int mDirection;
    private JoystickListener joystickListener;  //
    private final int ratio = 10;                // The smaller, the more shading will occur

    // Setups the dimensions of the joystick (hat and base and position) on create
    private void setupDimensions() {
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        baseRadius = 7*Math.min(getWidth(), getHeight())/16;
        hatRadius = Math.min(getWidth(), getHeight())/6;
    }

    // Good practice to expose the direction property according to Android Developer page
    public int isDirection() {
        return mDirection;
    }

    // Allows the setting of direction property, this is not necessarily needed and may not even be used
    public void setDirection(int direction) {
        // Avoiding invalid direction values, defaulting to values
        if (direction >= 1) {
            direction = 1;
        } else {
            direction = 0;
        }
        mDirection = direction;
        invalidate();
        requestLayout();
    }

    public JoystickView(Context context) {
        super(context);
        getHolder().addCallback(this);                      // Setup the SurfaceHolder callback so that we know when it's ready
        setOnTouchListener(this);                           // Allow this class to handle touch input
    }

    public JoystickView(Context context, AttributeSet attributes, int style) {
        super(context, attributes, style);
        getHolder().addCallback(this);
        setOnTouchListener(this);                           // Allow this class to handle touch input
        // Gathering custom attributes for Joystick
        TypedArray a = context.getTheme().obtainStyledAttributes(attributes, R.styleable.JoystickView, 0, 0);

        try {
            mDirection = a.getInteger(R.styleable.JoystickView_direction, 0);
        } finally {
            a.recycle();
        }
    }

    public JoystickView(Context context, AttributeSet attributes) {
        super (context, attributes);
        getHolder().addCallback(this);
        setOnTouchListener(this);                           // Allow this class to handle touch input
        // Gathering custom attributes for Joystick
        TypedArray a = context.getTheme().obtainStyledAttributes(attributes, R.styleable.JoystickView, 0, 0);

        try {
            mDirection = a.getInteger(R.styleable.JoystickView_direction, 0);
        } finally {
            a.recycle();
        }
    }

    public void setJoystickListener(JoystickListener joystickListener) {
        this.joystickListener = joystickListener;
    }

    private void drawJoystick(float newX, float newY) {
        // if prevents drawing method from executing if SurfaceView
        // has not been created on screen, prevents exceptions
        if(getHolder().getSurface().isValid()) {
            Canvas myCanvas = this.getHolder().lockCanvas();                // Stuff to draw
            Paint colors = new Paint();
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);   // Clear the BG

            // First determine sin and cos of angle that touched point is at relative to center of joystick
            float hypotenuse = (float) Math.sqrt(Math.pow(newX - centerX, 2) + Math.pow(newY - centerY, 2));
            float sin = (newY - centerY) / hypotenuse;
            float cos = (newX - centerX) / hypotenuse;

            // Draw base before shading
            colors.setARGB(255, 100, 100, 115);                    // Color of joystick base
            if (mDirection == 0) { // Drawing circular base for omni-directional joystick
                myCanvas.drawCircle(centerX, centerY, baseRadius, colors);          // Draw the joystick base
            } else { // Else drawing a rectangular base for bi-directional joystick
                myCanvas.drawRoundRect(new RectF(centerX - hatRadius, centerY - baseRadius, centerX + hatRadius, centerY + baseRadius), 20.0f, 20.0f, colors);
            }

            // Drawing the joystick hat
            for (int i = 0; i <= (int)(hatRadius/ratio); i++) {
                colors.setARGB(255, (int)(i*(150*ratio/hatRadius)+100), (int)(i*(150*ratio/hatRadius)+100), (int)(i*(150*ratio/hatRadius)+100));   // Color of joystick itself
                myCanvas.drawCircle(newX, newY, hatRadius - (float) i*(ratio)/2 , colors);                                                     // Draw the joystick hat
            }
            getHolder().unlockCanvasAndPost(myCanvas);                      // Write the new drawing to the SurfaceView
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setupDimensions();
        drawJoystick(centerX, centerY);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouch(View v, MotionEvent e) {
        if (v.equals(this)) {
            if (e.getAction() != e.ACTION_UP) {
                float displacement = (float) Math.sqrt(Math.pow(e.getX() - centerX, 2) + Math.pow(e.getY() - centerY, 2));
                if (mDirection == 0) { // If omni-direction, joystick moves in all directions
                    if (displacement < baseRadius) {
                        drawJoystick(e.getX(), e.getY());
                        joystickListener.onJoystickMoved((e.getX() - centerX) / baseRadius, (e.getY() - centerY) / baseRadius, getId());
                    } else {
                        float ratio = baseRadius / displacement;
                        float constrainedX = centerX + (e.getX() - centerX) * ratio;
                        float constrainedY = centerY + (e.getY() - centerY) * ratio;
                        drawJoystick(constrainedX, constrainedY);
                        joystickListener.onJoystickMoved((constrainedX - centerX) / baseRadius, (constrainedY - centerY) / baseRadius, getId());
                    }
                    // Else the joystick is bi-directional (only moves up and down)
                } else {
                    if (displacement < baseRadius) {
                        drawJoystick(getWidth()/2, e.getY());
                        joystickListener.onJoystickMoved(0, (e.getY() - centerY) / baseRadius, getId());
                    } else {
                        float ratio = baseRadius / displacement;
                        float constrainedY = centerY + (e.getY() - centerY) * ratio;
                        drawJoystick(getWidth()/2, constrainedY);
                        joystickListener.onJoystickMoved(0, (constrainedY - centerY) / baseRadius, getId());
                    }
                }
            }
            else {
                drawJoystick(centerX, centerY);
                joystickListener.onJoystickMoved(0, 0, getId());
            }
        }
        return true;
    }
}
