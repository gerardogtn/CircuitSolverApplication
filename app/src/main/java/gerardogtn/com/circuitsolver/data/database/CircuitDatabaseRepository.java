package gerardogtn.com.circuitsolver.data.database;

import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.bean.CircuitBean;

/**
 * Created by gerardogtn on 4/10/16.
 */
public interface CircuitDatabaseRepository {

    void add(Circuit item);

    void remove(Circuit item);

    CircuitBean get();

}
