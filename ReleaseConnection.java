package website;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReleaseConnection {
	public static void ReleaseConnection(Connection con, Statement st, PreparedStatement ps, ResultSet rs) {
	try {	
		if(rs != null)
			rs.close();
	} catch(SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		try {	
			if(st != null)
			st.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(ps != null)
				try {
				ps.close();
				} catch(SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(con != null)
						try {
							con.close();
						} catch(SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
	}
}
