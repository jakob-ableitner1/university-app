package com.solvd.universityapp.dao.impl;

import com.solvd.universityapp.bin.TestResult;
import com.solvd.universityapp.dao.ConnectionPool;
import com.solvd.universityapp.dao.TestResultRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class TestResultRepositoryImpl implements TestResultRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(TestResultRepositoryImpl.class);

    @Override
    public void create(TestResult testResult, Long studentId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("Insert into test_results(score, subject, student_id) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setShort(1, testResult.getScore());
            ps.setString(2, testResult.getSubject());
            ps.setLong(3, studentId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            testResult.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<TestResult> findTestResultById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps =
                    conn.prepareStatement("select id, score, subject from test_results where id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                TestResult testResult = new TestResult();
                testResult.setId(rs.getLong("tr.id"));
                testResult.setScore(rs.getByte("tr.score"));
                testResult.setSubject(rs.getString("tr.subject"));
                return Optional.ofNullable(testResult);
            }
        } catch (SQLException e){
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public void updateById(TestResult testResult, Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("update test_results set score=?, subject=? where id=?");
            ps.setShort(1, testResult.getScore());
            ps.setString(2, testResult.getSubject());
            ps.setLong(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public void deleteById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("delete from test_results where id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }
}
