package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadDAO extends AbstractDAO {



    public List<String> getAllGridNames() {

        connect();

        List<String> result = new ArrayList<>();

        ResultSet resultSet = resultSetStatement("select name from GAME");

        while (true) {
            try {
                if (!resultSet.next()) {
                    break;
                } else {
                    result.add(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connect();
            }
        }

        disconnect();
        return result;
    }

    public boolean[][] getGrid(String gridName) {

        connect();

        ResultSet res = resultSetStatement("select * from GAME where name = '" + gridName + "'");

        try {
            res.next();
            int width = res.getInt("width");
            int height = res.getInt("height");
            String data = res.getString("data");
            res.close();

            ArrayParser arrayParser = new ArrayParser();

            boolean[][] grid = arrayParser.deserialize(width, height, data);
            disconnect();
            return grid;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        disconnect();

        return null;
    }

    public boolean checkName(String name) {
        connect();

        List<String> allNames = getAllGridNames();

        return !allNames.contains(name);
    }


    public String getGameInfo(String gridName, String columnName) {

        connect();

        ResultSet resultSet = resultSetStatement("select "+columnName+" from GAME where name = '" + gridName + "'");
        try {
            resultSet.next();
            String result = resultSet.getString(1);
            disconnect();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

        return null;

    }

    public String[] getGameInfo(String gridName, String[] columnNames) {

        List<String> result = new ArrayList<>();

        connect();

        ResultSet resultSet = resultSetStatement("select * from GAME where name = '" + gridName + "'");
        try {
            resultSet.next();
             for (String columnName: columnNames){
                    result.add(resultSet.getString(columnName));
             }
            disconnect();
            return result.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

        return null;

    }

}
