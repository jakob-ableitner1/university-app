package com.solvd.universityapp;

import com.solvd.universityapp.util.ConnectionPool;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

public class ServiceTest {

    @BeforeSuite
    public void suiteSetUp(){
        try(Reader reader = new FileReader("src/test/resources/SQLScripts/MySQLCreation.sql")) {
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

    @AfterSuite
    public void suiteClose(){
        // delete database
    }

    @BeforeTest
    public void testSetUp(){
        // add data to test?
    }

    @AfterTest
    public void testClose(){
        // truncate tables
    }

    @Test(description = "Verify create method in student service")
    public void verifyStudentCreate(){

    }
}
