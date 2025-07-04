package xyz.fullonlabs.flon4j.api.vo.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;

/**
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction extends Base {

	@JsonProperty("transaction_id")
	private String transactionId;

	@JsonProperty("processed")
	private Processed processed;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Processed getProcessed() {
		return processed;
	}

	public void setProcessed(Processed processed) {
		this.processed = processed;
	}

}
