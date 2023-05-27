package DataBase;

import java.sql.Connection;
import java.sql.Statement;

import static Common.Running.SafeRunning.safe;


public abstract class AbstractTable {

    public abstract void createTable();

    protected Connection getConnection() {
        return SQLConnection.getInstance().getConnection();
    }

    protected boolean executeUpdate(String query) {
        return safe(() -> {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        });
    }
}
