package common.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * @author joomanba
 * @version $Revision: 1.2 $ $Date: 2015/08/18 04:16:13 $
 */
@SuppressWarnings({"unchecked","unused"} )
public class CharsetConversionHandler {

	/**
	 * All logging goes through this logger
	 */
	private static Log log = LogFactory.getLog(CharsetConversionHandler.class);
	private String fromDbEnc;
	private String toDbEnc;
	private boolean doConversion;

	/**
	 * @param str
	 * @return
	 */
	public String toDB(String str) {
		String value = null;

		try {
			value = new String((str.getBytes(fromDbEnc)), toDbEnc);
		} catch (UnsupportedEncodingException e) {
			System.out.println("toDB ERROR : " + e.toString());
		}

		return value;
	}

	/**
	 * @param str
	 * @return
	 */
	public String fromDB(String str) {
		String value = null;

		try {
			value = new String((str.getBytes(toDbEnc)), fromDbEnc);
		} catch (UnsupportedEncodingException e) {
			System.out.println("fromDB ERROR : " + e.toString());
		}

		return value;
	}

	/**
	 *
	 * @param str
	 * @param fromDbEnc
	 * @param toDbEnc
	 * @return
	 */
	public static String toDB(String str, String fromDbEnc, String toDbEnc) {

		if (str == null)
		    return null;

		try {
			return new String((str.getBytes(fromDbEnc)), toDbEnc);
		} catch (UnsupportedEncodingException e) {
			System.out.println("toDB ERROR : " + e.toString());
			return null;
		}

	}

	/**
	 *
	 * @param str
	 * @param fromDbEnc
	 * @param toDbEnc
	 * @return
	 */
	public static String fromDB(String str, String fromDbEnc, String toDbEnc) {

		if (str == null)
		    return null;

		try {
			return new String((str.getBytes(toDbEnc)), fromDbEnc);
		} catch (UnsupportedEncodingException e) {
			System.out.println("fromDB ERROR : " + e.toString());
			return null;
		}

	}


	/**
	 * @param obj
	 * @return
	 */
	public Object toDB(Object obj) {
		try {
			Object object = encodeCopy(obj, fromDbEnc, toDbEnc, false);
			return object;
		} catch (Exception e) {
			System.out.println("toDB ERROR : " + e.toString());
			return null;
		}
	}

	/**
	 * @param obj
	 * @return
	 */
	public Object fromDB(Object obj) {
		try {
			return encode(obj, toDbEnc, fromDbEnc);
		} catch (Exception e) {
			System.out.println("fromDB ERROR : " + e.toString());
			return null;
		}
	}

	/**
	 * @param obj
	 * @param fromEnc
	 * @param toEnc
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Object encode(Object obj, String fromEnc, String toEnc)
			throws UnsupportedEncodingException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		// Validate existence of the specified beans
		if (obj == null) {
			//throw new IllegalArgumentException("No destination bean
			// specified");
			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("BeanUtils.encode(" + obj + ", " + fromEnc + ", " + toEnc
					+ ")");
		}

		if ((obj instanceof String)) {
			return new String(((String) obj).getBytes(fromEnc), toEnc);

		} else if ((obj instanceof Map)) {
			Iterator names = ((Map) obj).keySet().iterator();

			while (names.hasNext()) {
				String name = (String) names.next();
				Object value = ((Map) obj).get(name);

				if (value instanceof String) {
					((Map) obj).put(name, new String(((String) value)
							.getBytes(fromEnc), toEnc));
				} else if (!isAssigned(value)) {
					encode(value, fromEnc, toEnc);
				}
			}
		} else if (obj instanceof List) {
			for (int i = 0; i < ((List) obj).size(); i++) {
				Object value = ((List) obj).get(i);

				if (value instanceof String) {
					((List) obj).set(i, new String(((String) value)
							.getBytes(fromEnc), toEnc));
				} else if (!isAssigned(value)) {
					encode(value, fromEnc, toEnc);
				}
			}
		} else {
			PropertyDescriptor[] objDescriptors = PropertyUtils
					.getPropertyDescriptors(obj);

			for (int i = 0; i < objDescriptors.length; i++) {
				String name = objDescriptors[i].getName();

				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}

				if (PropertyUtils.isReadable(obj, name)
						&& PropertyUtils.isWriteable(obj, name)) {
					Object value = PropertyUtils.getSimpleProperty(obj, name);

					if (value != null) {
						PropertyDescriptor[] objDes = PropertyUtils
								.getPropertyDescriptors(value);

						if (value instanceof String) {
							PropertyUtils.setSimpleProperty(obj, name,
									new String(((String) value)
											.getBytes(fromEnc), toEnc));
						} else if (!isAssigned(value)) {
							encode(value, fromEnc, toEnc);
						}
					}
				}
			}
		}

		return obj;
	}

	/**
	 * @param obj
	 * @param fromEnc
	 * @param toEnc
	 * @param isSupportNestedProperty
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Object encodeCopy(Object obj, String fromEnc, String toEnc, boolean isSupportNestedProperty)
			throws UnsupportedEncodingException, InvocationTargetException,
			NoSuchMethodException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		// Validate existence of the specified beans
		if (obj == null) {
			//throw new IllegalArgumentException("No destination bean
			// specified");
			return null;
		}
		if ((obj instanceof String)) {
			return new String(((String) obj).getBytes(fromEnc), toEnc);

		} else if (obj instanceof Map) {
			Iterator names = ((Map) obj).keySet().iterator();
			HashMap newObj = new HashMap();
			while (names.hasNext()) {
				String name = (String) names.next();
				Object value = ((Map) obj).get(name);

				if (value instanceof String) {
					newObj.put(name, new String(((String) value)
							.getBytes(fromEnc), toEnc));
				} else if (!isAssigned(value) && isSupportNestedProperty) {
					newObj.put(name, encodeCopy(value, fromEnc, toEnc, isSupportNestedProperty));
				}
			}
			return newObj;
		} else if (obj instanceof List) {
			ArrayList newObj = new ArrayList();
			for (int i = 0; i < ((List) obj).size(); i++) {
				Object value = ((List) obj).get(i);

				if (value instanceof String) {

					newObj.add(new String(
							((String) value).getBytes(fromEnc), toEnc));
				} else if (!isAssigned(value) && isSupportNestedProperty ) {
					newObj.add(encodeCopy(value, fromEnc, toEnc, isSupportNestedProperty));
				}
			}
			return newObj;
		} else if (!isAssigned(obj)) {
			Object newObj = new Object();

			newObj = Class.forName(obj.getClass().getName()).newInstance();

			PropertyDescriptor[] objDescriptors = PropertyUtils
					.getPropertyDescriptors(obj);

			for (int i = 0; i < objDescriptors.length; i++) {
				String name = objDescriptors[i].getName();

				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}

				if (PropertyUtils.isReadable(obj, name)
						&& PropertyUtils.isWriteable(obj, name)) {
					Object value = PropertyUtils.getSimpleProperty(obj, name);

					if (value != null) {

						PropertyDescriptor[] objDes = PropertyUtils
								.getPropertyDescriptors(value);

						if (value instanceof String) {
							PropertyUtils.setSimpleProperty(newObj, name,
									new String(((String) value)
											.getBytes(fromEnc), toEnc));
						} else if (!isAssigned(value) && isSupportNestedProperty ) {
							PropertyUtils.setSimpleProperty(newObj, name,
									encodeCopy(value, fromEnc, toEnc, isSupportNestedProperty));
						} else {
							PropertyUtils
									.setSimpleProperty(newObj, name, value);
						}
					}
				}
			}
			return newObj;
		} else {
			return obj;
		}

	}

	/**
	 * @param value
	 * @return
	 */
	public boolean isAssigned(Object value) {
		return ((value == null) || (value instanceof Boolean)
				|| (value instanceof Byte) || (value instanceof Character)
				|| (value instanceof Short) || (value instanceof Integer)
				|| (value instanceof Long) || (value instanceof Float)
				|| (value instanceof BigDecimal)
				|| (value instanceof java.util.Date)
				|| (value instanceof java.sql.Date)
				|| (value instanceof java.sql.Timestamp) || (value instanceof String));
	}

	/**
	 * @return
	 */
	public String getFromDbEnc() {
		return fromDbEnc;
	}

	/**
	 * @return
	 */
	public String getToDbEnc() {
		return toDbEnc;
	}

	/**
	 * @param string
	 */
	public void setFromDbEnc(String string) {
		fromDbEnc = string;
	}

	/**
	 * @param string
	 */
	public void setToDbEnc(String string) {
		toDbEnc = string;
	}

	/**
	 * @return
	 */
	public boolean isDoConversion() {
		return doConversion;
	}

	/**
	 * @param b
	 */
	public void setDoConversion(boolean b) {
		doConversion = b;
	}
}