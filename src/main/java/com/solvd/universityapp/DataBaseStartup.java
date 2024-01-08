package com.solvd.universityapp;

import com.solvd.universityapp.dao.ConnectionPool;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class DataBaseStartup {
    public static void main(String[] args){

        try(Reader reader = new FileReader("src/main/resources/MySQLCreation.sql")) {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            Connection conn = connectionPool.getConnection();
            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
