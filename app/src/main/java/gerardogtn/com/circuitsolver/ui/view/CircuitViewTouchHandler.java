package gerardogtn.com.circuitsolver.ui.view;

import android.graphics.PointF;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;

import gerardogtn.com.circuitsolver.R;
import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.CircuitComponent;
import gerardogtn.com.circuitsolver.util.exception.CannotConnectTwoGatesException;
import gerardogtn.com.circuitsolver.util.exception.CircuitComponentNotFoundException;

/**
 * Created by gerardogtn on 2/17/16.
 */
public class CircuitViewTouchHandler {

    private static final String TAG = "CircuitViewTouchHandler";

    private Circuit mCircuit;
    private CircuitComponent mCurrentSelectedComponent;
    private CircuitComponent mPreviouslySelectedComponent;
    private CircuitView mCircuitView;

    private PointF mCurrentTouchPosition;

    private boolean mWasMoved;

    public CircuitViewTouchHandler(CircuitView circuitView) {
        mCircuit = Circuit.getInstance();
        mCircuitView = circuitView;
        mCurrentTouchPosition = new PointF();
    }

    public boolean handleTouchEvent(MotionEvent event) {
        updateCurrentTouchPosition(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mWasMoved = false;
                initializeCircuitComponents();
                createCircuitConnection();
                break;
            case MotionEvent.ACTION_UP:
                if (mWasMoved) {
                    mCurrentSelectedComponent = null;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                moveCurrentComponent();
                mWasMoved = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCurrentSelectedComponent = null;
                break;
        }

        return true;
    }

    private void updateCurrentTouchPosition(MotionEvent event) {
        mCurrentTouchPosition.x = event.getX();
        mCurrentTouchPosition.y = event.getY();
    }

    private void initializeCircuitComponents() {
        mPreviouslySelectedComponent = mCurrentSelectedComponent;
        initializeCurrentCircuitComponent();
    }

    private void initializeCurrentCircuitComponent() {
        try {
            mCurrentSelectedComponent = mCircuit.getComponentAtPosition(mCurrentTouchPosition);
        } catch (CircuitComponentNotFoundException e) {
            mCurrentSelectedComponent = null;
        }
    }

    private void createCircuitConnection() {
        try {
            if (mCurrentSelectedComponent != null &&
                    mPreviouslySelectedComponent != null &&
                    !mCurrentSelectedComponent.equals(mPreviouslySelectedComponent)) {
                mCircuit.addConnection(mPreviouslySelectedComponent, mCurrentSelectedComponent);
                mCircuitView.invalidate();
            }
        } catch (CannotConnectTwoGatesException e) {
            Snackbar.make(mCircuitView, R.string.cannot_connect_two_gates, Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void moveCurrentComponent() {
        if (mCurrentSelectedComponent != null) {
            mCurrentSelectedComponent.setX(mCurrentTouchPosition.x);
            mCurrentSelectedComponent.setY(mCurrentTouchPosition.y);
            mCircuitView.invalidate();
        }
    }

    public void reset() {
        mCurrentSelectedComponent = null;
        mPreviouslySelectedComponent = null;
    }
}
