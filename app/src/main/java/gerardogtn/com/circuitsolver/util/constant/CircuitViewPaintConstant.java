package gerardogtn.com.circuitsolver.util.constant;

import android.graphics.Paint;

/**
 * Created by gerardogtn on 2/16/16.
 */
public class CircuitViewPaintConstant {

    public static final Paint BACKGROUND_PAINT = getPaintWithColor(CircuitViewColorConstant.BACKGROUND_COLOR);

    public static final Paint GATE_PAINT = getPaintWithColor(CircuitViewColorConstant.GATE_COLOR);
    public static final Paint GATE_TEXT_PAINT = getTextPaintWithColor(CircuitViewColorConstant.GATE_TEXT_COLOR);

    public static final Paint WIRE_PAINT = getPaintWithColor(CircuitViewColorConstant.WIRE_COLOR);
    public static final Paint WIRE_TEXT_PAINT = getTextPaintWithColor(CircuitViewColorConstant.WIRE_TEXT_COLOR);

    public static final Paint CONNECTION_PAINT = getPaintWihColorAndStroke(CircuitViewColorConstant.CONNECTION_COLOR);

    private static Paint getPaintWithColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        return paint;
    }

    private static Paint getPaintWihColorAndStroke(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(CircuitViewSizeConstant.sStrokeWidth);
        paint.setAntiAlias(true);
        return paint;
    }

    private static Paint getTextPaintWithColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(CircuitViewSizeConstant.sTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        return paint;
    }
}
