import java.sql.Connection;
import java.sql.Statement;



public abstract class AbstractTable {

    public void createTable(){};
    public void createTable(String username){};


    protected Connection getConnection() {
        return SQLConnection.getInstance().getConnection();
    }

    protected boolean executeUpdate(String query) {
        return SafeRunning.safe(() -> {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        });
    }
}
