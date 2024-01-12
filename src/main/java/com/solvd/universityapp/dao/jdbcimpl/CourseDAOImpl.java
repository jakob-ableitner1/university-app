package com.solvd.universityapp.dao.jdbcimpl;

import com.solvd.universityapp.bin.Course;
import com.solvd.universityapp.bin.CourseDetail;
import com.solvd.universityapp.util.ConnectionPool;
import com.solvd.universityapp.dao.CourseDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CourseDAOImpl implements CourseDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(CourseDAOImpl.class);
    private static final String SELECT_STATEMENT = "Select\n" +
            "            c.id as course_id,\n" +
            "            cd.id as course_detail_id, cd.course_name as course_name, cd.credits as course_credits\n" +
            "        from courses c\n" +
            "        left join course_details cd on cd.id=c.course_detail_id\n";

    @Override
    public void create(Course course, Long courseDetailId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("Insert into courses(course_detail_id) values (?)", Statement.RETURN_GENERATED_KEYS)){
            ps.setLong(1, courseDetailId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Course> findById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();

        Optional<Course> course = null;
        ResultSet rs = null;

        try (PreparedStatement ps =
                     conn.prepareStatement(String.format("%s where c.id=?", SELECT_STATEMENT))){
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                course = Optional.ofNullable(createCourseFromResultSet(rs));
            }
        } catch (SQLException e){
            LOGGER.error(e);
        } finally {
            try {
                if (rs.next()){
                    rs.close();
                }
            } catch (SQLException e) {
                rs = null;
            }
            CONNECTION_POOL.releaseConnection(conn);
        }
        return course;
    }

    @Override
    public Set<Course> findAll() {

        Connection conn = CONNECTION_POOL.getConnection();

        Set<Course> courses = new HashSet<>();
        ResultSet rs = null;

        try (PreparedStatement ps = conn.prepareStatement(SELECT_STATEMENT)){
            rs = ps.executeQuery();
            while(rs.next()){
                courses.add(createCourseFromResultSet(rs));
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
        return courses;
    }

    @Override
    public void updateById(Course course, Long courseDetailId, Long courseId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("update courses set course_detail_id=? where id=?")){
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

        try (PreparedStatement ps = conn.prepareStatement("delete from courses where id=?")){
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
