package xyz.fullonlabs.flon4j.ese;

/**
 * DataType
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
public enum DataType {

	name("name"), asset("asset"), string("string"), key("key"), unit16("unit16"), unit32("unit32"), varint32(
			"varint32"),unit64("unit64"),symbol("symbol");

	private DataType(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
