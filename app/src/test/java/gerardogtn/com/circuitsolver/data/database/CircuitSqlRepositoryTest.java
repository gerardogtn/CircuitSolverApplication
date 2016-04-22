package gerardogtn.com.circuitsolver.data.database;

import android.content.Context;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import gerardogtn.com.circuitsolver.BuildConfig;
import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.CircuitConnection;
import gerardogtn.com.circuitsolver.data.model.Gate;
import gerardogtn.com.circuitsolver.data.model.Wire;
import gerardogtn.com.circuitsolver.data.model.bean.CircuitBean;

import static org.junit.Assert.*;

/**
 * Created by gerardogtn on 4/10/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class CircuitSqlRepositoryTest {

    private Circuit circuit;

    @Before
    public void setUp() throws Exception {
        Context context = RuntimeEnvironment.application;
        CircuitSqlRepository.reset();
        CircuitSqlRepository.initialize(context);

        circuit = Circuit.getNewInstance();
    }

    @Test
    public void testAdd() throws Exception {
        Wire a = new Wire("A");
        Wire b = new Wire("B");
        Gate c = new Gate("C");

        CircuitConnection aBconnection = new CircuitConnection(a, b);
        circuit.addComponents(a, b, c);
        circuit.addConnection(aBconnection);

        CircuitSqlRepository.getInstance().add(circuit);

        CircuitBean circuitBean = new CircuitBean();
        circuitBean.setComponents(a, b, c);
        circuitBean.setConnections(aBconnection);
        assertEquals(circuitBean, CircuitSqlRepository.getInstance().get());
    }

    @Test
    public void testRemove() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        Wire a = new Wire("A");
        Wire b = new Wire("B");
        Gate c = new Gate("C");

        CircuitConnection aBconnection = new CircuitConnection(a, b);
        circuit.addComponents(a, b, c);
        circuit.addConnection(aBconnection);
        circuit.setStartWire(a);
        circuit.setEndWire(b);

        CircuitSqlRepository.getInstance().add(circuit);

        CircuitBean circuitBean = new CircuitBean();
        circuitBean.setComponents(a, b, c);
        circuitBean.setConnections(aBconnection);
        circuitBean.setStartWire(a);
        circuitBean.setEndWire(b);
        assertEquals(circuitBean, CircuitSqlRepository.getInstance().get());
    }
}