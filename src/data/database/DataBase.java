package data.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private Connection connection;
    private Statement statement;

    public void connect(){
        try {
            connection = DriverManager.getConnection("jdbc:h2:./lib/Conway-Game-Of-Live", "sa", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void simpleSQL(String sqlStatement){
        try {
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet resultSetSQL(String sqlStatement){
        try {
            return statement.executeQuery(sqlStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void createTables(){
        connect();

        simpleSQL("create table gameInfo(id int not null auto_increment, name varchar, width int, height int, generation int)");
        simpleSQL("create table gameData(id int, x int, y int)");

        disconnect();
    }

    public void deleteTables(){
        connect();

        simpleSQL("drop table gameInfo");
        simpleSQL("drop table gameData");

        disconnect();
    }

    public List<String> getAllGrids(){

        List<String> allGrids = new ArrayList<>();

        connect();

        ResultSet resultSet = resultSetSQL("select name from gameInfo");

        while (true){
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                allGrids.add(resultSet.getString(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        disconnect();

        return allGrids;

    }

    public void saveGrid(boolean[][] grid, String gridName, int generation){

        connect();

        simpleSQL("insert into GAMEINFO(name, width, height, GENERATION) values ('"+gridName+"', "+grid.length+", "+grid[0].length+", "+generation+")");
        ResultSet resultSet = resultSetSQL("select * from gameInfo order by id desc limit 1");


        try {
            resultSet.next();
            int id = resultSet.getInt("id");

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    if (grid[x][y]){
                        simpleSQL("insert into gameData (id, x, y) values ("+id+", "+x+", "+y+")");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

    }

    public boolean[][] getGrid(String gridName){

        connect();



        ResultSet resultInfo = resultSetSQL("select * from gameInfo where name = '"+gridName+"'");

        try {
            resultInfo.next();
            int id = resultInfo.getInt("id");
            int width = resultInfo.getInt("width");
            int height = resultInfo.getInt("height");

            resultInfo.close();

            boolean[][] cells = new boolean[width][height];


            ResultSet resultData = resultSetSQL("select * from gameData where id ="+id);



            while (resultData.next()){
                cells[resultData.getInt("x")][resultData.getInt("y")] = true;
            }

            return cells;

        } catch (SQLException e) {
            e.printStackTrace();
        }


        disconnect();

        return null;
    }

    public int getGen(String gridName){
        connect();
        ResultSet resultSet = resultSetSQL("select generation from gameInfo where name = '"+gridName+"'");
        try {
            resultSet.next();
            int gen = resultSet.getInt("generation");
            disconnect();
            return gen;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();
        return 0;

    }

}
