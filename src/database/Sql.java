package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import codes.Commons;
import javafx.util.Duration;

public class Sql {

	public static String getCovered(String str) {
		return "\"" + str + "\"";
	}

	public static String getCoveredt(String str) {
		return "`" + str + "`";
	}

	public static String getCoveredLike(String str) {
		return "\"%" + str + "%\"";
	}

	public static void Execute(String sql, Connection conn) throws SQLException {
		PreparedStatement pst1 = conn.prepareStatement(sql);
		pst1.execute();
	}

	public static void insertFile(PreparedStatement pst, File f)
			throws FileNotFoundException, IOException, SQLException {
		if (f.exists()) {
			pst.setBytes(1, Commons.getBytes(f).toByteArray());
			pst.execute();
		}
	}

	public static byte[] getFile(String sql, Connection conn) {
		try {
			ResultSet r = ExecuteSQL(sql, conn);
			if (r.next()) {
				return r.getBytes(1);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static List<Object> getRow(String sql, Connection conn) {
		try {
			List<List<Object>> list = getNestedList(ExecuteSQL(sql, conn));
			return list.size() == 0 ? null : list.get(0);
		} catch (SQLException ex) {
			Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static Object getValue(String sql, Connection conn) {
		List<List<Object>> list;
		try {
			list = getNestedList(ExecuteSQL(sql, conn));
			return list.size() == 0 ? null : list.get(0).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getValueS(String sql, Connection conn) {
		Object val = getValue(sql, conn);
		return val == null ? null : val.toString();
	}

	public static List<Object> getColumn(String sql, int columnIdx, Connection conn) {
		try {
			ResultSet rst = ExecuteSQL(sql, conn);
			List<Object> col = new ArrayList<Object>();
			getNestedList(rst).forEach((ele) -> {
				col.add(ele.get(columnIdx));
			});
			return col;
		} catch (SQLException ex) {
			Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static ResultSet ExecuteSQL(String sql, Connection conn) throws SQLException {
		PreparedStatement pst = conn.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		return rs;
	}

	public static void main(String[] args) throws SQLException, IOException {
		
	}

	public static List<List<Object>> getNestedList(ResultSet rs) {
		try {
			// To contain all rows.
			List<List<Object>> rows = new ArrayList<List<Object>>();
			ResultSetMetaData metaData = rs.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			// Get the data
			while (rs.next()) {
				List<Object> newRow = new ArrayList<Object>();

				for (int i = 1; i <= numberOfColumns; i++) {
					newRow.add(rs.getObject(i));
				}

				rows.add(newRow);
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}
