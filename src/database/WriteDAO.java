package database;

import org.h2.tools.DeleteDbFiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class WriteDAO extends AbstractDAO{

    public void createTable(){
        connect();
        simpleStatement("create table if not exists GAME(id int auto_increment,name varchar not null,width int not null,height int not null,generation int not null,max_cellcount int not null,data clob);create unique index GAME_ID_UINDEX on GAME (id);create unique index GAME_NAME_UINDEX on GAME (name);");
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

    public void saveGrid(boolean[][] grid, String gridName, int generation, int maxCellCount){

        ArrayParser arrayParser = new  ArrayParser();
        String data = arrayParser.serialize(grid);

        connect();
        simpleStatement("insert into GAME (NAME, WIDTH, HEIGHT, GENERATION, MAX_CELLCOUNT, DATA) VALUES ( '"+gridName+"', "+grid[0].length+", "+grid.length+", "+generation+", "+maxCellCount+", '"+data+"' )");
        disconnect();

    }
}
