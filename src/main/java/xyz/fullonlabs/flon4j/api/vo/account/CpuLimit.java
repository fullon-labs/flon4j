package xyz.fullonlabs.flon4j.api.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CpuLimit {

	private Long used;

	private Long available;

	private Long max;

	public CpuLimit() {

	}

	public Long getUsed() {
		return used;
	}

	public void setUsed(Long used) {
		this.used = used;
	}

	public Long getAvailable() {
		return available;
	}

	public void setAvailable(Long available) {
		this.available = available;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

}
