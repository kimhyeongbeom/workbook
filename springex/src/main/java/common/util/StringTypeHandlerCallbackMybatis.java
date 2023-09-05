/*
 * Created on 2004. 12. 3.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package common.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author joomanba
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */

public class StringTypeHandlerCallbackMybatis implements TypeHandler {
	private String fromDbEnc;
	private String toDbEnc;
	private boolean doConversion;

	public StringTypeHandlerCallbackMybatis() {
		fromDbEnc = "KSC5601";
		toDbEnc = "8859_1";
		doConversion = true;
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
		if (isDoConversion()) {
			ps.setString(i, CharsetConversionHandler.toDB((String) parameter, fromDbEnc, toDbEnc));
		} else {
			ps.setString(i, (String) parameter);
		}
	}

	@Override
	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		String str = rs.getString(columnName);
		if (str == null || str == "null") {
			str = "";
		}

		if (isDoConversion()) {
			String result = "";
			result = CharsetConversionHandler.fromDB(str, fromDbEnc, toDbEnc);
			return result;
		}

		return str;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ibatis.sqlmap.client.extensions.TypeHandlerCallback#valueOf(java.lang.
	 * String)
	 */
	public Object valueOf(String s) {
		return s;
	}

	/**
	 * @return Returns the doConversion.
	 */
	public boolean isDoConversion() {
		return doConversion;
	}

	/**
	 * @param doConversion The doConversion to set.
	 */
	public void setDoConversion(boolean doConversion) {
		this.doConversion = doConversion;
	}

	/**
	 * @return Returns the fromDbEnc.
	 */
	public String getFromDbEnc() {
		return fromDbEnc;
	}

	/**
	 * @param fromDbEnc The fromDbEnc to set.
	 */
	public void setFromDbEnc(String fromDbEnc) {
		this.fromDbEnc = fromDbEnc;
	}

	/**
	 * @return Returns the toDbEnc.
	 */
	public String getToDbEnc() {
		return toDbEnc;
	}

	/**
	 * @param toDbEnc The toDbEnc to set.
	 */
	public void setToDbEnc(String toDbEnc) {
		this.toDbEnc = toDbEnc;
	}

	/* PROCEDURE 호출시 변환이 되도록 수정함 2023-08-02 김형범 */
	@Override
	public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
		String str = rs.getString(columnIndex);
		if (isDoConversion()) {
			return CharsetConversionHandler.fromDB(str, fromDbEnc, toDbEnc);
		}
		return str;
	}

	/* PROCEDURE 호출시 변환이 되도록 수정함 2023-08-02 김형범 */
	@Override
	public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String str = cs.getString(columnIndex);
		if (isDoConversion()) {
			return CharsetConversionHandler.fromDB(str, fromDbEnc, toDbEnc);
		}
		return str;
	}
	
}