package me.pyrey.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLManager {

    private final ConnectionPoolManager pool;
    private final String playerDataTable;

    public SQLManager(ConnectionPoolManager pool, String infoTable) {
        this.pool = pool;
        this.playerDataTable = infoTable;
        makeTable();
    }

    private void makeTable() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            /*
             * CREATING WHOLE TABLE
             */
            String statement = "CREATE TABLE IF NOT EXISTS `" + playerDataTable + "` " +
                    "(" +
                    "uuid TEXT, alerts_enabled INT, chatalerts_enabled INT, chat_enabled INT, joins_enabled INT" +
                    ")";

            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public void createPlayer(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null, ps2 = null;
        ResultSet rs = null;

        try {
            /*
             * CREATING WHOLE TABLE
             */
            String statement = "SELECT * FROM `" + playerDataTable + "` WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();

            if (rs.next()) return;

            String stmt = "INSERT INTO " + playerDataTable + "(uuid,alerts_enabled,chatalerts_enabled,chat_enabled,joins_enabled) VALUE(?,?,?,?,?)";
            ps2 = conn.prepareStatement(stmt);
            ps2.setString(1, uuid.toString());
            ps2.setInt(2, 1);
            ps2.setInt(3, 1);
            ps2.setInt(4, 0);
            ps2.setInt(5, 1);
            ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
            pool.close(null, ps2, null);
        }
    }

    public boolean hasAlertsEnabled(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            /*
             * CREATING WHOLE TABLE
             */
            String statement = "SELECT * FROM `" + playerDataTable + "` WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();

            while (rs.next()) return rs.getInt("alerts_enabled") == 1;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return true;
    }

    public void setAlertsEnabled(UUID uuid, boolean enabled) {
        createPlayer(uuid);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String stmt = "UPDATE " + playerDataTable + " SET alerts_enabled=?  WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(stmt);

            ps.setInt(1, enabled ? 1 : 0);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public boolean hasChatEnabled(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            /*
             * CREATING WHOLE TABLE
             */
            String statement = "SELECT * FROM `" + playerDataTable + "` WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();

            while (rs.next()) return rs.getInt("chat_enabled") == 1;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return false;
    }

    public void setChatEnabled(UUID uuid, boolean enabled) {
        createPlayer(uuid);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String stmt = "UPDATE " + playerDataTable + " SET chat_enabled=?  WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(stmt);

            ps.setInt(1, enabled ? 1 : 0);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public boolean hasJoinsEnabled(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            /*
             * CREATING WHOLE TABLE
             */
            String statement = "SELECT * FROM `" + playerDataTable + "` WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();

            while (rs.next()) return rs.getInt("joins_enabled") == 1;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return true;
    }

    public void setJoinsEnabled(UUID uuid, boolean enabled) {
        createPlayer(uuid);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String stmt = "UPDATE " + playerDataTable + " SET joins_enabled=?  WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(stmt);

            ps.setInt(1, enabled ? 1 : 0);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public boolean hasChatAlertsEnabled(UUID uuid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            /*
             * CREATING WHOLE TABLE
             */
            String statement = "SELECT * FROM `" + playerDataTable + "` WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(statement);
            ps.setString(1, uuid.toString());
            rs = ps.executeQuery();

            while (rs.next()) return rs.getInt("chatalerts_enabled") == 1;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
        return true;
    }

    public void setChatAlertsEnabled(UUID uuid, boolean enabled) {
        createPlayer(uuid);

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String stmt = "UPDATE " + playerDataTable + " SET chatalerts_enabled=?  WHERE uuid=?";

            conn = pool.getConnection();
            ps = conn.prepareStatement(stmt);

            ps.setInt(1, enabled ? 1 : 0);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(conn, ps, rs);
        }
    }

    public void onDisable() {
        pool.closePool();
    }

}
