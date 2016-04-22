package gerardogtn.com.circuitsolver;

import android.app.Application;

import gerardogtn.com.circuitsolver.data.database.CircuitSqlRepository;

/**
 * Created by gerardogtn on 4/21/16.
 */
public class CircuitSolverApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CircuitSqlRepository.initialize(getApplicationContext());
    }
}
