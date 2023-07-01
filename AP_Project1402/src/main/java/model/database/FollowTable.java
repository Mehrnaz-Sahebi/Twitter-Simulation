package model.database;

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
        executeUpdate(" CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                COLUMN_FOLLOWER + " VARCHAR(20), " +
                COLUMN_FOLLOWING + " VARCHAR(20)" +
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
    public synchronized void firstUnfollowsSecond(String follower, String following) throws SQLException {
        String query = " delete  from follow_table where follower = \"" + follower +"\" AND following = \"" + following + "\"" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        int set = statement.executeUpdate();
        statement.close();
    }
    public synchronized void firstFollowsSecond(String follower, String following) throws SQLException {
        String query = " insert into follow_table (follower , following)"
                + " values (?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = getConnection().prepareStatement(query);
        preparedStmt.setString (1, follower);
        preparedStmt.setString (2, following);

        // execute the preparedstatement
        preparedStmt.execute();

    }
    // TODO duobtful about username , it shouldn't be int , i think
    public synchronized void updateUsername(int username, String newUsername) throws SQLException {
        String query1 = "UPDATE " + TABLE_NAME + " SET " + COLUMN_FOLLOWER + " = '"+newUsername+"'" + " WHERE "+ COLUMN_FOLLOWER + " = '"+username+"'";
        PreparedStatement statement1 = getConnection().prepareStatement(query1);
        int set1 = statement1.executeUpdate();
//        set1.close();
        String query2 = "UPDATE " + TABLE_NAME + " SET " + COLUMN_FOLLOWING + " = '"+newUsername+"'" + " WHERE "+ COLUMN_FOLLOWING + " = '"+username+"'";
        PreparedStatement statement2 = getConnection().prepareStatement(query2);
        int set2 = statement2.executeUpdate();
//        set2.close();
    }
}
