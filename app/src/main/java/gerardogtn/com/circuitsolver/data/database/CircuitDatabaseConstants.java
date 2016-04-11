package gerardogtn.com.circuitsolver.data.database;

/**
 * Created by gerardogtn on 4/10/16.
 */
public class CircuitDatabaseConstants {

    public static class CircuitSchema {
        public static final String TABLE_NAME = "circuit";

        public static final String ID = "id";
        public static final String START_WIRE = "start_wire";
        public static final String END_WIRE = "end_wire";

        public static final String COLUMNS[] = new String[]{
                START_WIRE, END_WIRE
        };

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        START_WIRE + " VARCHAR(4) NULL, " +
                        END_WIRE  + " VARCHAR(4) NULL" +
                        " ); ";
    }
    
    
    public static class CircuitConnectionSchema {

        public static final String TABLE_NAME = "circuit_connections";

        public static final String ID = "id";
        public static final String ENTRY_NODE_ID = "id_entry_node";
        public static final String EXIT_NODE_ID = "id_exit_node";

        public static final String COLUMNS[] = new String[]{
                ENTRY_NODE_ID, EXIT_NODE_ID
        };

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ENTRY_NODE_ID + " VARCHAR(4) NOT NULL, " +
                        EXIT_NODE_ID  + " VARCHAR(4) NOT NULL" +
                        " ); ";

    }

    public static class WireSchema {
        public static final String TABLE_NAME = "wires";

        public static final String LABEL = "label";
        public static final String X_POSITION = "x_position";
        public static final String Y_POSITION = "y_position";

        public static final String COLUMNS[] = new String[] {
               LABEL, X_POSITION, Y_POSITION
        };

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        LABEL      + " VARCHAR(4) PRIMARY KEY, " +
                        X_POSITION + " REAL NOT NULL, " +
                        Y_POSITION + " REAL NOT NULL" +
                        " ); ";

    }

    public static class GateSchema {
        public static final String TABLE_NAME = "gates";

        public static final String LABEL = "label";
        public static final String X_POSITION = "x_position";
        public static final String Y_POSITION = "y_position";

        public static final String COLUMNS[] = new String[] {
                LABEL, X_POSITION, Y_POSITION
        };

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        LABEL      + " VARCHAR(4) PRIMARY KEY, " +
                        X_POSITION + " REAL NOT NULL, " +
                        Y_POSITION + " REAL NOT NULL" +
                        " ); ";
    }
}
