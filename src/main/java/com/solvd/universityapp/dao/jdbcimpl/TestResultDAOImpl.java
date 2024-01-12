package com.solvd.universityapp.dao.jdbcimpl;

import com.solvd.universityapp.bin.TestResult;
import com.solvd.universityapp.util.ConnectionPool;
import com.solvd.universityapp.dao.TestResultDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class TestResultDAOImpl implements TestResultDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(TestResultDAOImpl.class);

    @Override
    public void create(TestResult testResult, Long studentId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn.prepareStatement
                ("Insert into test_results(score, subject, student_id) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            ps.setShort(1, testResult.getScore());
            ps.setString(2, testResult.getSubject());
            ps.setLong(3, studentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<TestResult> findTestResultById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();
        ResultSet rs = null;
        TestResult testResult = null;

        try (PreparedStatement ps =
                     conn.prepareStatement("select id, score, subject from test_results where id=?")){
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                testResult = new TestResult();
                testResult.setId(rs.getLong("tr.id"));
                testResult.setScore(rs.getByte("tr.score"));
                testResult.setSubject(rs.getString("tr.subject"));
            }
        } catch (SQLException e){
            LOGGER.error(e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    rs = null;
                }
            }
            CONNECTION_POOL.releaseConnection(conn);
        }
        return Optional.ofNullable(testResult);
    }

    @Override
    public void updateById(TestResult testResult, Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("update test_results set score=?, subject=? where id=?")){
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

        try (PreparedStatement ps = conn.prepareStatement("delete from test_results where id=?")){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }
}
