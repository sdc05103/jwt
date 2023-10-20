package hello.hellospring.repository;

import hello.hellospring.domain.*;
import hello.hellospring.dto.AllClassDTO;
import hello.hellospring.dto.CompleteDTO;
import hello.hellospring.dto.GuideDTO;
import hello.hellospring.dto.SubjectDataDTO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.resource.jdbc.internal.ResourceRegistryStandardImpl.close;
import static org.springframework.jdbc.datasource.DataSourceUtils.getConnection;

@Repository
public class GuideRepository {

    private final DataSource dataSource;

    public GuideRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public List<String> getMajor(String id) {
        String sql = "select m.학과 from recommended_major r, major m where (r.mid1=m.pid or r.mid2=m.pid or r.mid3=m.pid) and r.sid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            List<String> major_list = new ArrayList<>();
            while (rs.next()) {
                major_list.add(rs.getString("학과"));
            }
            return major_list;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }


    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    public List<AllClassDTO> getAllClass(String major) {
        String sql = "select c.name as class_name, c.credit as credit, s.name as subject_name from class c, subject s where c.sid = s.sid";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<AllClassDTO> class_list = new ArrayList<>();
            while (rs.next()) {
                AllClassDTO class_element = new AllClassDTO();
                class_element.setClass_name(rs.getString("class_name"));
                class_element.setCredit(rs.getInt("credit"));
                class_element.setSubject_name(rs.getString("subject_name"));
                class_list.add(class_element);
            }
            return class_list;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }


    public GuideDTO getAllTotalguide(String id, String major) {
        String sql = "select * from total_guide where sid = ? and major = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, major);
            rs = pstmt.executeQuery();

            List<SubjectDataDTO> subjectDataDTOList = new ArrayList<>();

            while (rs.next()) {

                // SubjectDataDTO를 생성하고 설정
               SubjectDataDTO subjectData = new SubjectDataDTO(
                        rs.getString("category"),
                        rs.getString("subject"),
                        rs.getString("class"),
                        rs.getInt("credit"),
                        rs.getString("course"),
                        rs.getInt("complete"),
                        rs.getBoolean("recommend"),
                        rs.getBoolean("chosen")
                );
               subjectDataDTOList.add(subjectData);
            }
            GuideDTO guide_list = new GuideDTO(major, subjectDataDTOList);
            return guide_list;

        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt,rs);
        }
    }

    public String getSID(String id){
        String sql = "select t.sid from total_guide t, member m where t.pid = m.id = ? ";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                id = rs.getString("sid");
            }

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace(); // 예외 정보를 출력하거나 다른 처리를 수행할 수 있습니다.
        } finally {
            close(conn, pstmt,rs);
        }
        return id;
    }


    public List<CompleteDTO> getCompleteClass(String id) {
        String sql = "select c.name as 'class_name', cl.credit as 'credit', s.name as 'subject' from class_list cl, class c, subject s where cl.member_id = ? and cl.class_id = c.cid and c.sid = s.sid;";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            List<CompleteDTO> class_list = new ArrayList<>();
            while (rs.next()) {
                CompleteDTO class_element = new CompleteDTO();
                class_element.setClass_name(rs.getString("class_name"));
                class_element.setCredit(rs.getString("credit"));
                class_element.setSubject_name(rs.getString("subject"));
                class_list.add(class_element);
            }
            return class_list;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }
}
