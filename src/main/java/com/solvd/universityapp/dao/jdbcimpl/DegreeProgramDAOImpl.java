package com.solvd.universityapp.dao.jdbcimpl;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.util.ConnectionPool;
import com.solvd.universityapp.dao.DegreeProgramDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DegreeProgramDAOImpl implements DegreeProgramDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DegreeProgramDAOImpl.class);

    @Override
    public void create(DegreeProgram degreeProgram) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn
                .prepareStatement("Insert into degree_programs(degree_program_name, total_credits) values (?, ?)", Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, degreeProgram.getDegreeProgramName());
            ps.setShort(2, degreeProgram.getTotalCredits());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<DegreeProgram> findById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        ResultSet rs = null;
        DegreeProgram degreeProgram = null;

        try (PreparedStatement ps =
                     conn.prepareStatement("select id, degree_program_name, total_credits from degree_programs where id=?")){
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                degreeProgram = new DegreeProgram();
                degreeProgram.setId(rs.getLong("id"));
                degreeProgram.setDegreeProgramName(rs.getString("degree_program_name"));
                degreeProgram.setTotalCredits(rs.getShort("total_credits"));
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
        return Optional.ofNullable(degreeProgram);
    }

    @Override
    public Set<DegreeProgram> findAll() {
        Connection conn = CONNECTION_POOL.getConnection();

        Set<DegreeProgram> degreePrograms = new HashSet<>();
        ResultSet rs = null;

        try (PreparedStatement ps = conn
                .prepareStatement("select id, degree_program_name, total_credits from degree_programs")){
            rs = ps.executeQuery();
            while(rs.next()){
                DegreeProgram degreeProgram = new DegreeProgram();
                degreeProgram.setId(rs.getLong("id"));
                degreeProgram.setDegreeProgramName(rs.getString("degree_program_name"));
                degreeProgram.setTotalCredits(rs.getShort("total_credits"));
                degreePrograms.add(degreeProgram);
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
        return degreePrograms;
    }

    @Override
    public void updateById(DegreeProgram degreeProgram, Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn
                .prepareStatement("update degree_programs set degree_program_name=?, total_credits=? where id=?")){
            ps.setString(1, degreeProgram.getDegreeProgramName());
            ps.setShort(2, degreeProgram.getTotalCredits());
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

        try (PreparedStatement ps = conn.prepareStatement("delete from degree_programs where id=?")){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }
}
