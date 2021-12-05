package database;

import org.h2.tools.DeleteDbFiles;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReadDAO extends AbstractDAO {

    public List<String> getAllGridNames(){

        connect();

        List<String> result = new ArrayList<>();

        ResultSet resultSet = resultSetStatement("select name from gameInfo");

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

        ResultSet resultInfo = resultSetStatement("select * from gameInfo where name = '"+gridName+"'");

        try {
            resultInfo.next();
            int id = resultInfo.getInt("id");
            int width = resultInfo.getInt("width");
            int height = resultInfo.getInt("height");

            resultInfo.close();

            boolean[][] cells = new boolean[width][height];

            ResultSet resultData = resultSetStatement("select * from gameData where id ="+id);

            while (resultData.next()){
                cells[resultData.getInt("x")][resultData.getInt("y")] = true;
            }

            disconnect();

            return cells;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

        return null;
    }

    public int getGen(String gridName){

        connect();

        ResultSet resultSet = resultSetStatement("select generation from gameInfo where name = '"+gridName+"'");
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

        ResultSet resultSet = resultSetStatement("select CELLCOUNT from gameInfo where name = '"+gridName+"'");
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
