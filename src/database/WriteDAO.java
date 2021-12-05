package database;

import org.h2.tools.DeleteDbFiles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WriteDAO extends AbstractDAO{

    public void createTables(){
        connect();
        simpleStatement("create table IF NOT EXISTS gameInfo(id int not null auto_increment, name varchar, width int, height int, generation int, cellcount int)");
        simpleStatement("create table IF NOT EXISTS gameData(id int, x int, y int)");
        disconnect();
    }

    public void deleteTable(String table){
        connect();
        simpleStatement("drop table "+table);
        disconnect();
    }

    public void deleteDatabase(String dbDir, String dbName, boolean quiet){
        DeleteDbFiles.execute(dbDir, dbName, quiet);
    }

    public void saveGrid(boolean[][] grid, String gridName, int generation, int cellCount){

        connect();

        simpleStatement("insert into GAMEINFO(NAME, WIDTH, HEIGHT, GENERATION, CELLCOUNT) values ('"+gridName+"', "+grid.length+", "+grid[0].length+", "+generation+","+cellCount+")");
        ResultSet resultSet = resultSetStatement("select * from gameInfo order by id desc limit 1");


        try {
            resultSet.next();
            int id = resultSet.getInt("id");

            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid[0].length; y++) {
                    if (grid[x][y]){
                        simpleStatement("insert into gameData (id, x, y) values ("+id+", "+x+", "+y+")");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();

    }
}
