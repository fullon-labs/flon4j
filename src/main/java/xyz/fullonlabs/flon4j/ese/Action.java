package xyz.fullonlabs.flon4j.ese;

/**
 * Action
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
public enum Action {

	transfer("${precision},${quantity}@flon.token"), account("account"), ram("ram"), delegate("${precision},${quantity}@flon.token"), voteproducer("voteproducer"),
	close("${precision},${quantity}@flon.token");

	private String code;

	private Action(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}