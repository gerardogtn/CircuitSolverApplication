package gerardogtn.com.circuitsolver.ui.view;

import android.graphics.Canvas;
import android.graphics.Paint;
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

    private Canvas mDrawCanvas;
    private CircuitComponent mCurrentDrawingComponent;

    public CircuitViewDrawer() {

    }

    public void draw(Canvas canvas) {
        mDrawCanvas = canvas;
        mDrawCanvas.drawPaint(CircuitViewPaintConstant.BACKGROUND_PAINT);
        drawConnections();
        drawComponents();
    }

    public void drawConnections() {
        for (CircuitConnection circuitConnection : Circuit.getInstance().getConnections()) {
            CircuitComponent origin = circuitConnection.getEntryComponent();
            CircuitComponent destination = circuitConnection.getExitComponent();
            Paint connectionPaint = CircuitViewPaintConstant.CONNECTION_PAINT;
            drawDirectedLine(origin, destination, connectionPaint);
        }
    }

    private void drawDirectedLine(CircuitComponent origin, CircuitComponent destination, Paint connectionPaint) {
        mDrawCanvas.drawLine(origin.getX(), origin.getY(), destination.getX(), destination.getY(), connectionPaint);
        connectionPaint.setStyle(Paint.Style.FILL);
    }

    public void drawComponents() {
        for (CircuitComponent component : Circuit.getInstance().getComponents()) {
            mCurrentDrawingComponent = component;
            if (component instanceof Gate) {
                drawGate();
            } else if (component instanceof Wire) {
                drawWire();
            }
        }
    }

    private void drawGate() {
        drawComponentSquare();
        drawComponentLabel();
    }

    private void drawWire() {
        drawComponentCircle();
        drawComponentLabel();
    }

    private void drawComponentSquare() {
        Paint paint = getPaintForComponent();
        float y = getCenteredYCoordinate(paint);
        mDrawCanvas.drawRect(mCurrentDrawingComponent.getX() - CircuitViewSizeConstant.sSquareHalfSide,
                y - CircuitViewSizeConstant.sSquareHalfSide,
                mCurrentDrawingComponent.getX() + CircuitViewSizeConstant.sSquareHalfSide,
                y + CircuitViewSizeConstant.sSquareHalfSide,
                paint);

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

    private Paint getPaintForComponentLabel() {
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
