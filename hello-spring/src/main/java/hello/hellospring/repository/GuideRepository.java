package hello.hellospring.repository;

import hello.hellospring.domain.*;
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
}
