package xyz.fullonlabs.flon4j.api.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	@JsonProperty("account_name")
	private String accountName;

	@JsonProperty("privileged")
	private Boolean privileged;

	@JsonProperty("last_code_update")
	private Date lastCodeUpdate;

	@JsonProperty("created")
	private Date created;

	@JsonProperty("creator")
	private String creator;

	@JsonProperty("core_liquid_balance")
	private String coreLiquidBalance;

	@JsonProperty("is_res_unlimited")
	private Boolean isResUnlimited;

	@JsonProperty("gas_reserved")
	private Long gasReserved;

	@JsonProperty("gas_max")
	private String gasMax;

	@JsonProperty("net_res")
	private Res netRes;

	@JsonProperty("cpu_res")
	private Res cpuRes;

	@JsonProperty("ram_res")
	private Res ramRes;

	@JsonProperty("permissions")
	private List<Permission> permissions;

	@JsonProperty("refund_request")
	private Object refundRequest;

	@JsonProperty("voter_info")
	private Object voterInfo;

	@JsonProperty("subjective_cpu_bill")
	private Long subjectiveCpuBill;

	@JsonProperty("eosio_any_linked_actions")
	private List<Object> eosioAnyLinkedActions;

	// ========== 内部结构 ==========
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Res {
		@JsonProperty("used")
		private Long used;

		@JsonProperty("max")
		private String max;

		public Long getUsed() { return used; }
		public void setUsed(Long used) { this.used = used; }
		public String getMax() { return max; }
		public void setMax(String max) { this.max = max; }
	}

	// ========== getter/setter for Account ==========
	public String getAccountName() { return accountName; }
	public void setAccountName(String accountName) { this.accountName = accountName; }
	public Boolean getPrivileged() { return privileged; }
	public void setPrivileged(Boolean privileged) { this.privileged = privileged; }
	public Date getLastCodeUpdate() { return lastCodeUpdate; }
	public void setLastCodeUpdate(Date lastCodeUpdate) { this.lastCodeUpdate = lastCodeUpdate; }
	public Date getCreated() { return created; }
	public void setCreated(Date created) { this.created = created; }
	public String getCreator() { return creator; }
	public void setCreator(String creator) { this.creator = creator; }
	public String getCoreLiquidBalance() { return coreLiquidBalance; }
	public void setCoreLiquidBalance(String coreLiquidBalance) { this.coreLiquidBalance = coreLiquidBalance; }
	public Boolean getIsResUnlimited() { return isResUnlimited; }
	public void setIsResUnlimited(Boolean isResUnlimited) { this.isResUnlimited = isResUnlimited; }
	public Long getGasReserved() { return gasReserved; }
	public void setGasReserved(Long gasReserved) { this.gasReserved = gasReserved; }
	public String getGasMax() { return gasMax; }
	public void setGasMax(String gasMax) { this.gasMax = gasMax; }
	public Res getNetRes() { return netRes; }
	public void setNetRes(Res netRes) { this.netRes = netRes; }
	public Res getCpuRes() { return cpuRes; }
	public void setCpuRes(Res cpuRes) { this.cpuRes = cpuRes; }
	public Res getRamRes() { return ramRes; }
	public void setRamRes(Res ramRes) { this.ramRes = ramRes; }
	public List<Permission> getPermissions() { return permissions; }
	public void setPermissions(List<Permission> permissions) { this.permissions = permissions; }
	public Object getRefundRequest() { return refundRequest; }
	public void setRefundRequest(Object refundRequest) { this.refundRequest = refundRequest; }
	public Object getVoterInfo() { return voterInfo; }
	public void setVoterInfo(Object voterInfo) { this.voterInfo = voterInfo; }
	public Long getSubjectiveCpuBill() { return subjectiveCpuBill; }
	public void setSubjectiveCpuBill(Long subjectiveCpuBill) { this.subjectiveCpuBill = subjectiveCpuBill; }
	public List<Object> getEosioAnyLinkedActions() { return eosioAnyLinkedActions; }
	public void setEosioAnyLinkedActions(List<Object> eosioAnyLinkedActions) { this.eosioAnyLinkedActions = eosioAnyLinkedActions; }
}