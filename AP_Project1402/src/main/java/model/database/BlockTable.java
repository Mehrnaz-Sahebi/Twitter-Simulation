package model.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class BlockTable extends AbstractTable {
    private static final String TABLE_NAME = "block_table";
    private static final String COLUMN_ID = "users";
    private static final String COLUMN_BLOCKER = "blocker";//the username of follower
    private static final String COLUMN_BLOCKING = "blocking";//...
    @Override
    public synchronized void createTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT," +
                COLUMN_BLOCKER + " VARCHAR(20)," +
                COLUMN_BLOCKING + " VARCHAR(20)" +
                ")");
    }
    public synchronized HashSet<String> getBlockers(String username) throws SQLException {
        String query = "SELECT " + COLUMN_BLOCKER + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_BLOCKING + " = '"+username+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        HashSet<String> userNames = new HashSet<>();
        while (set.next()){
            userNames.add(set.getString(COLUMN_BLOCKER));
        }
        set.close();
        statement.close();
        return userNames;
    }
    public synchronized HashSet<String> getBlockings(String username) throws SQLException {
        String query = "SELECT " + COLUMN_BLOCKING + " FROM " + TABLE_NAME +  " WHERE " + COLUMN_BLOCKER + " = '"+username+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        HashSet<String> userNames = new HashSet<>();
        while (set.next()){
            userNames.add(set.getString(COLUMN_BLOCKING));
        }
        set.close();
        statement.close();
        return userNames;
    }
    public synchronized void firstUnblockSecend(String blocker, String blocking) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME +  " WHERE " + COLUMN_BLOCKER + " = '"+blocker+"'" + " AND "+ COLUMN_BLOCKING + " = '"+blocking+"'" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
        statement.close();
    }
    public synchronized void firstBlocksSecend(String blocker, String blocking) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME +  " VALUES " + " ( '"+blocker+"'" + " , '"+blocking+"')" ;
        PreparedStatement statement = getConnection().prepareStatement(query);
        ResultSet set = statement.executeQuery();
        set.close();
        statement.close();
        FollowTable followTable = new FollowTable();
        followTable.firstUnfollowsSecond(blocking,blocker);
        followTable.firstUnfollowsSecond(blocker,blocking);
    }
    public synchronized void updateUsername(int username, String newUsername) throws SQLException {
        String query1 = "UPDATE " + TABLE_NAME + " SET " + COLUMN_BLOCKER + " = '"+newUsername+"'" + " WHERE "+ COLUMN_BLOCKER + " = '"+username+"'";
        PreparedStatement statement1 = getConnection().prepareStatement(query1);
        ResultSet set1 = statement1.executeQuery();
        set1.close();
        String query2 = "UPDATE " + TABLE_NAME + " SET " + COLUMN_BLOCKING + " = '"+newUsername+"'" + " WHERE "+ COLUMN_BLOCKING + " = '"+username+"'";
        PreparedStatement statement2 = getConnection().prepareStatement(query2);
        ResultSet set2 = statement2.executeQuery();
        set2.close();
    }
}
