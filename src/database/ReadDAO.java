package database;

import org.h2.tools.DeleteDbFiles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadDAO extends AbstractDAO {

    public List<String> getAllGridNames(){

        connect();

        List<String> result = new ArrayList<>();

        ResultSet resultSet = resultSetStatement("select name from GAME");

        while (true){
            try {
                if (!resultSet.next()){
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

    public boolean[][] getGrid(String gridName){

        connect();

        ResultSet res = resultSetStatement("select * from GAME where name = '"+gridName+"'");

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

    public int getGen(String gridName){

        connect();

        ResultSet resultSet = resultSetStatement("select generation from GAME where name = '"+gridName+"'");
        try {

            resultSet.next();
            int gen = resultSet.getInt("generation");

            return gen;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

        return 0;

    }

    public int getCellCount(String gridName){

        connect();

        ResultSet resultSet = resultSetStatement("select CELLCOUNT from GAME where name = '"+gridName+"'");
        try {
            resultSet.next();
            int cellCount = resultSet.getInt("CELLCOUNT");
            disconnect();
            return cellCount;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

        return 0;

    }

    public boolean checkName(String name){

        connect();

        List<String> allNames = getAllGridNames();

        if (allNames.contains(name)){
            return false;
        } else {
            return true;
        }

    }


}
