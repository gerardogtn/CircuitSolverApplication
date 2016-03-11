package gerardogtn.com.circuitsolver.ui.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.CircuitComponent;
import gerardogtn.com.circuitsolver.data.model.CircuitConnection;
import gerardogtn.com.circuitsolver.data.model.Gate;
import gerardogtn.com.circuitsolver.data.model.Wire;
import gerardogtn.com.circuitsolver.util.constant.CircuitViewPaintConstant;
import gerardogtn.com.circuitsolver.util.constant.CircuitViewSizeConstant;
import gerardogtn.com.circuitsolver.util.exception.NoPaintForComponentException;
import gerardogtn.com.circuitsolver.util.exception.NoTextPaintForComponentException;

/**
 * Created by gerardogtn on 2/17/16.
 */
public class CircuitViewDrawer {

    private Circuit mCircuit;
    private Canvas mDrawCanvas;
    private CircuitComponent mCurrentDrawingComponent;

    public CircuitViewDrawer() {
        mCircuit = Circuit.getInstance();
    }

    public void draw(Canvas canvas) {
        mDrawCanvas = canvas;
        mDrawCanvas.drawPaint(CircuitViewPaintConstant.BACKGROUND_PAINT);
        drawConnections();
        drawComponents();
    }

    public void drawConnections() {
        for (CircuitConnection circuitConnection : mCircuit.getConnections()) {
            CircuitComponent origin = circuitConnection.getEntryComponent();
            CircuitComponent destination = circuitConnection.getExitComponent();
            Paint connectionPaint = CircuitViewPaintConstant.CONNECTION_PAINT;
            drawDirectedLine(origin, destination, connectionPaint);
        }
    }

    private void drawDirectedLine(CircuitComponent origin, CircuitComponent destination, Paint connectionPaint) {
        mDrawCanvas.drawLine(origin.getX(), origin.getY(), destination.getX(), destination.getY(), connectionPaint);
        Point circle = getPosPoint(origin.getX(), origin.getY(), destination.getX(), destination.getY());
        connectionPaint.setStyle(Paint.Style.FILL);
        mDrawCanvas.drawCircle(circle.x, circle.y, CircuitViewSizeConstant.sDirectionIndicatorRadius, connectionPaint);
    }

    private Point getPosPoint(float x0, float y0, float x1, float y1) {

        Point point = new Point();

        if (Math.abs(x1 - x0) < 40) {
            point.x = (int) x1;
            if (y0 < y1) {
                point.y = (int) y1 - 50;
            } else {
                point.y = (int) y1 + 50;
            }
        } else {
            float m = (y1 - y0) / (x1 - x0);
            float b = y0 - m * x0;
            float totalLength = (float) Math.sqrt(Math.pow((y1 - y0), 2) + Math.pow((x1 - x0), 2));
            float xEval = (totalLength - 50) * (float) Math.cos(Math.atan((y1 - y0) / (x1 - x0)));
            point.x = (int) (x0 - xEval);
            if (x0 < x1) {
                if (!(x0 < point.x && point.x < x1)) point.x = (int) (x0 + xEval);
            } else {
                if (!(x1 < point.x && point.x < x0)) point.x = (int) (x0 + xEval);
            }
            point.y = (int) (m * point.x + b);
        }
        return point;
    }

    public void drawComponents() {
        for (CircuitComponent component : mCircuit.getComponents()) {
            mCurrentDrawingComponent = component;
            drawComponent();
        }
    }

    private void drawComponent() {
        drawComponentCircle();
        drawComponentLabel();
    }

    private void drawComponentCircle() {
        Paint paint = getPaintForComponent();
        float y = getCenteredYCoordinate(paint);
        mDrawCanvas.drawCircle(mCurrentDrawingComponent.getX(), y,
                CircuitViewSizeConstant.sCircuitComponentRadius, paint);
    }

    private Paint getPaintForComponent() {
        Paint paint;
        if (mCurrentDrawingComponent instanceof Gate) {
            paint = CircuitViewPaintConstant.GATE_PAINT;
        } else if (mCurrentDrawingComponent instanceof Wire) {
            paint = CircuitViewPaintConstant.WIRE_PAINT;
        } else {
            throw new NoPaintForComponentException();
        }
        return paint;
    }

    private float getCenteredYCoordinate(Paint paint) {
        Rect rect = new Rect();
        String label = mCurrentDrawingComponent.getLabel();
        paint.getTextBounds(label, 0, label.length(), rect);
        return mCurrentDrawingComponent.getY() - Math.abs(rect.height()) / 2;
    }

    private void drawComponentLabel() {
        Paint paint = getPaintForComponentLabel();
        mDrawCanvas.drawText(mCurrentDrawingComponent.getLabel(), mCurrentDrawingComponent.getX(),
                mCurrentDrawingComponent.getY(), paint);
    }

    private Paint getPaintForComponentLabel( ) {
        Paint paint;
        if (mCurrentDrawingComponent instanceof Gate) {
            paint = CircuitViewPaintConstant.GATE_TEXT_PAINT;
        } else if (mCurrentDrawingComponent instanceof Wire) {
            paint = CircuitViewPaintConstant.WIRE_TEXT_PAINT;
        } else {
            throw new NoTextPaintForComponentException();
        }
        return paint;
    }
}