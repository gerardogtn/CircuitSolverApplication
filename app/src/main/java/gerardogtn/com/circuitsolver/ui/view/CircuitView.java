package gerardogtn.com.circuitsolver.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import gerardogtn.com.circuitsolver.util.constant.CircuitViewSizeConstant;

/**
 * Created by gerardogtn on 2/16/16.
 */
public class CircuitView extends View {

    private CircuitViewDrawer mCircuitViewDrawer;
    private CircuitViewTouchHandler mCircuitViewTouchHandler;

    public CircuitView(Context context) {
        this(context, null);
    }

    public CircuitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCircuitViewDrawer = new CircuitViewDrawer();
        mCircuitViewTouchHandler = new CircuitViewTouchHandler(this);
        CircuitViewSizeConstant.sScreenDensity = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCircuitViewDrawer.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mCircuitViewTouchHandler.handleTouchEvent(event);
    }

    public void onAddCircuitComponent() {
        mCircuitViewTouchHandler.reset();
        invalidate();
    }
}
