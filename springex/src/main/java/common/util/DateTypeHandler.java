package common.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class DateTypeHandler extends BaseTypeHandler<LocalDate> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setTimestamp(i, Timestamp.valueOf(parameter.atStartOfDay()));
	}

	@Override
	public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnName);
	    if (sqlTimestamp != null) {
	      return sqlTimestamp.toLocalDateTime().toLocalDate();
	    }
	    return null;
	}

	@Override
	public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
	    if (sqlTimestamp != null) {
	      return sqlTimestamp.toLocalDateTime().toLocalDate();
	    }
		return null;
	}

	@Override
	public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
	    if (sqlTimestamp != null) {
	      return sqlTimestamp.toLocalDateTime().toLocalDate();
	    }
		return null;
	}

	}