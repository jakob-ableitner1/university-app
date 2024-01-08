package com.solvd.universityapp.dao.impl;

import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.dao.ConnectionPool;
import com.solvd.universityapp.dao.CourseDetailRepository;
import com.solvd.universityapp.service.CourseDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class CourseDetailRepositoryImpl implements CourseDetailRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(CourseDetailRepositoryImpl.class);

    @Override
    public void create(CourseDetail courseDetail) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("Insert into course_details(course_name, credits) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, courseDetail.getCourseName());
            ps.setLong(2, courseDetail.getNumberOfCredits());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            courseDetail.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<CourseDetail> findById(Long id) {

        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("select id, course_name, credits from course_details where id=?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                CourseDetail courseDetail = new CourseDetail();
                courseDetail.setId(rs.getLong("id"));
                courseDetail.setCourseName(rs.getString("course_name"));
                courseDetail.setNumberOfCredits(rs.getByte("credits"));
                return Optional.ofNullable(courseDetail);
            }
        } catch(SQLException e){
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public void updateById(CourseDetail courseDetail, Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("update course_details set course_name=?, credits=? where id=?");
            ps.setString(1, courseDetail.getCourseName());
            ps.setLong(2, courseDetail.getNumberOfCredits());
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
            PreparedStatement ps = conn.prepareStatement("delete from course_details where id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }
}
