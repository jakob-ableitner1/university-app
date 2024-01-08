package com.solvd.universityapp.dao.impl;

import com.solvd.universityapp.bin.DegreeProgram;
import com.solvd.universityapp.dao.ConnectionPool;
import com.solvd.universityapp.dao.DegreeProgramRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DegreeProgramRepositoryImpl implements DegreeProgramRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DegreeProgramRepositoryImpl.class);

    @Override
    public void create(DegreeProgram degreeProgram) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("Insert into degree_programs(degree_program_name, total_credits) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, degreeProgram.getDegreeProgramName());
            ps.setShort(2, degreeProgram.getTotalCredits());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            degreeProgram.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<DegreeProgram> findById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps =
                    conn.prepareStatement("select id, degree_program_name, total_credits from degree_programs where id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                DegreeProgram degreeProgram = new DegreeProgram();
                degreeProgram.setId(rs.getLong("id"));
                degreeProgram.setDegreeProgramName(rs.getString("degree_program_name"));
                degreeProgram.setTotalCredits(rs.getShort("total_credits"));
                return Optional.ofNullable(degreeProgram);
            }
        } catch (SQLException e){
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public Set<DegreeProgram> findAll() {
        Set<DegreeProgram> degreePrograms = new HashSet<>();
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps =
                    conn.prepareStatement("select id, degree_program_name, total_credits from degree_programs");
            ResultSet rs = ps.executeQuery();

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
            CONNECTION_POOL.releaseConnection(conn);
        }
        return degreePrograms;
    }

    @Override
    public void updateById(DegreeProgram degreeProgram, Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("update degree_programs set degree_program_name=?, total_credits=? where id=?");
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

        try{
            PreparedStatement ps = conn.prepareStatement("delete from degree_programs where id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }
}
