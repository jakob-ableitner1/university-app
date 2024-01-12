package com.solvd.universityapp.dao.jdbcimpl;

import com.solvd.universityapp.bin.*;
import com.solvd.universityapp.util.ConnectionPool;
import com.solvd.universityapp.dao.StudentDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StudentDAOImpl implements StudentDAO {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(StudentDAOImpl.class);
    private static final String SELECT_STATEMENT = "Select\n" +
            "            s.id as student_id, s.email as student_email, s.first_name as student_first_name, s.last_name as student_last_name, s.address_id as student_address_id,\n" +
            "            dp.id as degree_program_id, dp.degree_program_name as degree_program_name, dp.total_credits as degree_program_total_credits,\n" +
            "            tr.id as test_result_id, tr.score as test_result_score, tr.subject as test_result_subject,\n" +
            "            c.id as course_id, c.term_id as course_term_id, c.course_detail_id as course_detail_id\n" +
            "        from students s\n" +
            "        left join test_results tr on s.id=tr.student_id\n" +
            "        left join degree_programs dp on s.degree_program_id=dp.id\n" +
            "        left join student_courses sc on s.id=sc.student_id\n" +
            "        left join courses c on sc.course_id=c.id\n";

    @Override
    public void create(Student student, Long degreeProgramId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try(PreparedStatement ps =
                    conn.prepareStatement("Insert into students(email, first_name, last_name, degree_program_id) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);){
            ps.setString(1, student.getEmail());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setLong(4, degreeProgramId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public Optional<Student> findById(Long id) {
        Connection conn = CONNECTION_POOL.getConnection();
        Optional<Student> student= null;
        ResultSet rs = null;
        try (PreparedStatement ps =
                     conn.prepareStatement(String.format("%s where s.id=?", SELECT_STATEMENT))){
            ps.setLong(1, id);
            rs = ps.executeQuery();
            student = createStudentFromResultSet(rs);
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
        return student;
    }

    @Override
    public Optional<Student> findByEmail(String email) {

        Connection conn = CONNECTION_POOL.getConnection();
        Optional<Student> student= null;
        ResultSet rs = null;

        try (PreparedStatement ps =
                     conn.prepareStatement(String.format("%s where s.email=?", SELECT_STATEMENT))){
            ps.setString(1, email);
            rs = ps.executeQuery();
            student = createStudentFromResultSet(rs);
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
        return student;
    }

    @Override
    public void updateById(Student student, Long degreeProgramId, Long studentId) {

        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn.prepareStatement
                ("update students set email=?, first_name=?, last_name=?, degree_program_id=? where id=?")){
            ps.setString(1, student.getEmail());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setLong(4, degreeProgramId);
            ps.setLong(5, studentId);
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

        try (PreparedStatement ps = conn.prepareStatement("delete from students where id=?")){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public void addCourse(Long studentId, Long courseId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try(PreparedStatement ps = conn.prepareStatement("insert into student_courses(student_id, course_id) values (?, ?)")){
            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    @Override
    public void removeCourse(Long studentId, Long courseId) {
        Connection conn = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("delete from student_courses where student_id=? and course_id=?")){

            ps.setLong(1, studentId);
            ps.setLong(2, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(conn);
        }
    }

    private Optional<Student> createStudentFromResultSet(ResultSet rs) throws SQLException{
        if (rs.next()){
            Student student = new Student();
            student.setId(rs.getLong("student_id"));
            student.setEmail(rs.getString("student_email"));
            student.setFirstName(rs.getString("student_first_name"));
            student.setLastName(rs.getString("student_last_name"));
            student.setDegreeProgram(new DegreeProgram(rs.getLong("degree_program_id"), rs.getString("degree_program_name"), rs.getShort("degree_program_total_credits")));
            student.setAddress(new Address(rs.getLong("student_address_id")));

            Set<TestResult> testResults = new HashSet<>();
            Set<Course> courses = new HashSet<>();

            mapCourseAndTestResultsToSet(rs, testResults, courses);

            while (rs.next()){
                mapCourseAndTestResultsToSet(rs, testResults, courses);
            }

            student.setTestResults(testResults);
            student.setCourses(courses);
            return Optional.ofNullable(student);
        }
        return Optional.empty();
    }

    private void mapCourseAndTestResultsToSet(ResultSet rs, Set<TestResult> testResults, Set<Course> courses) throws SQLException{
        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setId(rs.getLong("course_detail_id"));

        Term term = new Term();
        term.setId(rs.getLong("course_term_id"));

        Course course = new Course();
        course.setId(rs.getLong("course_id"));
        course.setCourseDetail(courseDetail);
        course.setTerm(term);
        courses.add(course);

        TestResult testResult = new TestResult();
        testResult.setId(rs.getLong("test_result_id"));
        testResult.setSubject(rs.getString("test_result_subject"));
        testResult.setScore(rs.getByte("test_result_score"));
        testResults.add(testResult);
    }
}
