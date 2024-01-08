package com.solvd.universityapp.dao.impl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.dao.ConnectionPool;
import com.solvd.universityapp.dao.CourseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CourseRepositoryImpl implements CourseRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(CourseRepositoryImpl.class);
    private static final String SELECT_STATEMENT = "Select\n" +
            "            c.id as course_id,\n" +
            "            cd.id as course_detail_id, cd.course_name as course_name, cd.credits as course_credits\n" +
            "        from courses c\n" +
            "        left join course_details cd on cd.id=c.course_detail_id\n";

    @Override
    public void create(Course course, Long courseDetailId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("Insert into courses(course_detail_id) values (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, courseDetailId);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            course.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Course> findById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps =
                    conn.prepareStatement(String.format("%s where c.id=?", SELECT_STATEMENT));
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return Optional.ofNullable(createCourseFromResultSet(rs));
            }
        } catch (SQLException e){
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
        return Optional.empty();
    }

    @Override
    public Set<Course> findAll() {
        Set<Course> courses = new HashSet<>();
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement(SELECT_STATEMENT);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                courses.add(createCourseFromResultSet(rs));
            }
        } catch (SQLException e){
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
        return courses;
    }

    @Override
    public void updateById(Course course, Long courseDetailId, Long courseId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("update courses set course_detail_id=? where id=?");
            ps.setLong(1, courseDetailId);
            ps.setLong(2, courseId);
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
            PreparedStatement ps = conn.prepareStatement("delete from courses where id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    private Course createCourseFromResultSet(ResultSet rs) throws SQLException{
        Course course = new Course();
        course.setId(rs.getLong("course_id"));

        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setId(rs.getLong("course_detail_id"));
        courseDetail.setCourseName(rs.getString("course_name"));
        courseDetail.setNumberOfCredits(rs.getByte("course_credits"));
        course.setCourseDetail(courseDetail);

        return course;
    }
}
