import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class FollowTable extends AbstractTable{
    private static final String TABLE_NAME = "follow_table";
    private static final String COLUMN_ID = "users";
    private static final String COLUMN_FOLLOWER = "follower";//the username of follower
    private static final String COLUMN_FOLLOWING = "following";//...
    @Override
    public synchronized void createTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_FOLLOWER + " string," +
                COLUMN_FOLLOWING + " string," +
                ")");
    }
    public synchronized HashSet<String> getFollowers(String username) throws SQLException {
        String query = "SELECT " + COLUMN_FOLLOWER + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_FOLLOWING + " = '"+username+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        HashSet<String> userNames = new HashSet<>();
        while (set.next()){
            userNames.add(set.getString(COLUMN_FOLLOWER));
        }
        set.close();
        statement.close();
        return userNames;
    }
    public synchronized HashSet<String> getFollowings(String username) throws SQLException {
        String query = "SELECT " + COLUMN_FOLLOWING + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_FOLLOWER + " = '"+username+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        HashSet<String> userNames = new HashSet<>();
        while (set.next()){
            userNames.add(set.getString(COLUMN_FOLLOWING));
        }
        set.close();
        statement.close();
        return userNames;
    }
    public synchronized void firstUnfollowsSecend(String follower, String following) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME +  " WHERE " + COLUMN_FOLLOWER + " = '"+follower+"'" + " AND "+ COLUMN_FOLLOWING + " = '"+following+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
        statement.close();
    }
    public synchronized void firstFollowsSecend(String follower, String following) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME +  " VALUES " + " ( '"+follower+"'" + " , '"+following+"')" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
        statement.close();
    }
}