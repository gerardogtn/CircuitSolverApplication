package gerardogtn.com.circuitsolver.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import gerardogtn.com.circuitsolver.data.model.Circuit;
import gerardogtn.com.circuitsolver.data.model.CircuitComponent;
import gerardogtn.com.circuitsolver.data.model.CircuitConnection;
import gerardogtn.com.circuitsolver.data.model.CircuitTranslator;
import gerardogtn.com.circuitsolver.data.model.Gate;
import gerardogtn.com.circuitsolver.data.model.Wire;
import gerardogtn.com.circuitsolver.data.model.bean.CircuitBean;

import static gerardogtn.com.circuitsolver.data.database.CircuitDatabaseConstants.CircuitConnectionSchema;
import static gerardogtn.com.circuitsolver.data.database.CircuitDatabaseConstants.CircuitSchema;
import static gerardogtn.com.circuitsolver.data.database.CircuitDatabaseConstants.GateSchema;
import static gerardogtn.com.circuitsolver.data.database.CircuitDatabaseConstants.WireSchema;

/**
 * Created by gerardogtn on 4/10/16.
 * <p>
 * A simple repository to store a Circuit. It must be initialized with initialize() before
 * being able to be used. CircuitSqlRepository is designed to store a single Circuit in
 * persistent storage.
 * </p>
 */
public class CircuitSqlRepository implements CircuitDatabaseRepository {


    private static final String TAG = "CircuitSqlRepository";
    private static CircuitSqlRepository sInstance;
    private static SQLiteOpenHelper sOpenHelper;
    private static SQLiteDatabase sDatabase;
    private static Context sContext;

    private static CircuitBean sCircuitBean;

    public static void initialize(Context context) {
        sOpenHelper = new CircuitDbOpenHelper(context);
        sDatabase = sOpenHelper.getWritableDatabase();
        sContext = context;
    }

    public static void reset() {
        if (sDatabase != null) {
            sDatabase.delete(CircuitSchema.TABLE_NAME, null, null);
            sDatabase.delete(CircuitConnectionSchema.TABLE_NAME, null, null);
            sDatabase.delete(WireSchema.TABLE_NAME, null, null);
            sDatabase.delete(GateSchema.TABLE_NAME, null, null);
        }
    }

    private CircuitSqlRepository() {

    }

    public static CircuitSqlRepository getInstance() {
        if (sOpenHelper == null) {
            throw new IllegalStateException("CircuitSqlRepository must be initialized");
        } else if (sInstance == null) {
            sInstance = new CircuitSqlRepository();
        }
        return sInstance;
    }

    @Override
    public void add(Circuit item) {
        reset();
        sCircuitBean = CircuitTranslator.getCircuitBean(item);

        addCircuitData();
        addCircuitComponents();
        addCircuitConnections();
    }

    private void addCircuitData() {
        ContentValues contentValues = new ContentValues();
        boolean insert = false;

        if (sCircuitBean.getStartWire() != null) {
            insert = true;
            contentValues.put(CircuitSchema.START_WIRE, sCircuitBean.getStartWire().getLabel());
        }
        if (sCircuitBean.getEndWire() != null) {
            insert = true;
            contentValues.put(CircuitSchema.END_WIRE, sCircuitBean.getEndWire().getLabel());
        }

        if (insert) {
            sDatabase.insert(CircuitSchema.TABLE_NAME, null, contentValues);
        }
    }

    private void addCircuitComponents() {
        for (CircuitComponent component : sCircuitBean.getComponents()) {
            if (component instanceof Gate) {
                addGate((Gate) component);
            } else if (component instanceof Wire) {
                addWire((Wire) component);
            } else {
                throw new IllegalStateException("An unexpected circuit component type was found");
            }

        }
    }

    private void addGate(Gate gate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(GateSchema.LABEL, gate.getLabel());
        contentValues.put(GateSchema.X_POSITION, gate.getX());
        contentValues.put(GateSchema.Y_POSITION, gate.getY());
        sDatabase.insert(GateSchema.TABLE_NAME, null, contentValues);
    }

    private void addWire(Wire wire) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WireSchema.LABEL, wire.getLabel());
        contentValues.put(WireSchema.X_POSITION, wire.getX());
        contentValues.put(WireSchema.Y_POSITION, wire.getY());
        sDatabase.insert(WireSchema.TABLE_NAME, null, contentValues);
    }

    private void addCircuitConnections() {
        for (CircuitConnection connection : sCircuitBean.getConnections()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(CircuitConnectionSchema.ENTRY_NODE_ID, connection.getEntryComponent().getLabel());
            contentValues.put(CircuitConnectionSchema.EXIT_NODE_ID, connection.getExitComponent().getLabel());
            sDatabase.insert(CircuitConnectionSchema.TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void remove(Circuit item) {
        sCircuitBean = null;
        reset();
    }

    @Override
    public CircuitBean get() {
        sCircuitBean = new CircuitBean();
        getCircuitComponents();
        getCircuitData();
        getCircuitConnections();
        return sCircuitBean;
    }

    private void getCircuitComponents() {
        getWires();
        getGates();
    }

    private void getWires() {
        Cursor cursor = sDatabase.query(WireSchema.TABLE_NAME, WireSchema.COLUMNS,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String label = cursor.getString(0);
                float x = cursor.getFloat(1);
                float y = cursor.getFloat(2);
                Wire wire = new Wire(label);
                wire.setX(x);
                wire.setY(y);

                sCircuitBean.addWires(wire);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private void getGates() {
        Cursor cursor = sDatabase.query(GateSchema.TABLE_NAME, GateSchema.COLUMNS,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String label = cursor.getString(0);
                float x = cursor.getFloat(1);
                float y = cursor.getFloat(2);
                Gate gate = new Gate(label);
                gate.setX(x);
                gate.setY(y);

                sCircuitBean.addGates(gate);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    private void getCircuitData() {
        Cursor cursor = sDatabase.query(CircuitSchema.TABLE_NAME, CircuitSchema.COLUMNS,
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                if (!cursor.isNull(1)) {
                    sCircuitBean.setStartWire(sCircuitBean.getWire(cursor.getString(1)));
                }
                if (!cursor.isNull(2)) {
                    sCircuitBean.setEndWire(sCircuitBean.getWire(cursor.getString(2)));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
    }


    private void getCircuitConnections() {
        Cursor cursor = sDatabase.query(CircuitConnectionSchema.TABLE_NAME, CircuitConnectionSchema.COLUMNS,
                null, null, null, null, null);

        ArrayList<CircuitConnection> connectionList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String entryLabel = cursor.getString(1);
                String exitLabel = cursor.getString(2);
                connectionList.add(new CircuitConnection(sCircuitBean.getComponent(entryLabel),
                        sCircuitBean.getComponent(exitLabel)));
            } while (cursor.moveToNext());
        }

        sCircuitBean.setConnections(connectionList);
        cursor.close();
    }

    /**
     * Replaces the data on the Circuit singleton with the CircuitBean stored in persistent storage.
     */
    public void restoreCircuitFromDatabase() {
        CircuitTranslator.updateCircuitSingleton(get());
    }

    /**
     * Resets the database and saves Circuit singleton to database.
     */
    public void updateDatabaseFromCircuit() {
        if (!Circuit.getInstance().isEmpty()) {
            add(Circuit.getInstance());
        }
    }

    private static class CircuitDbOpenHelper extends SQLiteOpenHelper {

        public static final int VERSION = 1;
        public static final String DATABASE_NAME = "circuit.db";

        public CircuitDbOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CircuitSchema.CREATE_TABLE);
            db.execSQL(CircuitConnectionSchema.CREATE_TABLE);
            db.execSQL(WireSchema.CREATE_TABLE);
            db.execSQL(GateSchema.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
