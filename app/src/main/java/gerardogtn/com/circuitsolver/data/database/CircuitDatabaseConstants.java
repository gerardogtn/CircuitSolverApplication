package gerardogtn.com.circuitsolver.data.database;

/**
 * Created by gerardogtn on 4/10/16.
 */
public class CircuitDatabaseConstants {


    public static class CircuitConnectionSchema {

        public static final String TABLE_NAME = "circuit_connections";

        private static final String ID = "id";
        private static final String ENTRY_NODE_ID = "id_entry_node";
        private static final String EXIT_NODE_ID = "id_exit_node";

        private static final String COLUMNS[] = new String[]{
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

        private static final String LABEL = "label";
        private static final String X_POSITION = "x_position";
        private static final String Y_POSITION = "y_position";

        private static final String COLUMNS[] = new String[] {
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

        private static final String LABEL = "label";
        private static final String X_POSITION = "x_position";
        private static final String Y_POSITION = "y_position";

        private static final String COLUMNS[] = new String[] {
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
