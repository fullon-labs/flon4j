package xyz.fullonlabs.flon4j.api.vo.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	// 通用主键
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

	// ============== 新资源结构兼容 ==============
	@JsonProperty("is_res_unlimited")
	private Boolean isResUnlimited;

	@JsonProperty("gas_reserved")
	private Long gasReserved;

	@JsonProperty("gas_max")
	private String gasMax;

	@JsonProperty("ram_res")
	private Res ramRes;

	@JsonProperty("cpu_res")
	private Res cpuRes;

	@JsonProperty("net_res")
	private Res netRes;

	@JsonProperty("subjective_cpu_bill")
	private Long subjectiveCpuBill;

	// ============== 其余字段 ==============
	@JsonProperty("permissions")
	private List<Permission> permissions;

	@JsonProperty("refund_request")
	private Object refundRequest;

	@JsonProperty("voter_info")
	private Object voterInfo;

	@JsonProperty("subjective_cpu_bill_limit")
	private Limit subjectiveCpuBillLimit; // 老结构

	@JsonProperty("eosio_any_linked_actions")
	private List<Object> eosioAnyLinkedActions;

	// ========== 内部类区分新老结构 ==========

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Limit {
		@JsonProperty("used")
		private Long used;
		@JsonProperty("available")
		private Long available;
		@JsonProperty("max")
		private Long max;

		// getter/setter
		public Long getUsed() { return used; }
		public void setUsed(Long used) { this.used = used; }
		public Long getAvailable() { return available; }
		public void setAvailable(Long available) { this.available = available; }
		public Long getMax() { return max; }
		public void setMax(Long max) { this.max = max; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Res {
		@JsonProperty("used")
		private Long used;
		@JsonProperty("max")
		private String max;
		// getter/setter
		public Long getUsed() { return used; }
		public void setUsed(Long used) { this.used = used; }
		public String getMax() { return max; }
		public void setMax(String max) { this.max = max; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Permission {
		@JsonProperty("perm_name")
		private String permName;
		@JsonProperty("parent")
		private String parent;
		@JsonProperty("required_auth")
		private RequiredAuth requiredAuth;
		@JsonProperty("linked_actions")
		private List<Object> linkedActions;

		// getter/setter
		public String getPermName() { return permName; }
		public void setPermName(String permName) { this.permName = permName; }
		public String getParent() { return parent; }
		public void setParent(String parent) { this.parent = parent; }
		public RequiredAuth getRequiredAuth() { return requiredAuth; }
		public void setRequiredAuth(RequiredAuth requiredAuth) { this.requiredAuth = requiredAuth; }
		public List<Object> getLinkedActions() { return linkedActions; }
		public void setLinkedActions(List<Object> linkedActions) { this.linkedActions = linkedActions; }

		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class RequiredAuth {
			@JsonProperty("threshold")
			private Integer threshold;
			@JsonProperty("keys")
			private List<Key> keys;
			@JsonProperty("accounts")
			private List<Object> accounts;
			@JsonProperty("waits")
			private List<Object> waits;

			// getter/setter
			public Integer getThreshold() { return threshold; }
			public void setThreshold(Integer threshold) { this.threshold = threshold; }
			public List<Key> getKeys() { return keys; }
			public void setKeys(List<Key> keys) { this.keys = keys; }
			public List<Object> getAccounts() { return accounts; }
			public void setAccounts(List<Object> accounts) { this.accounts = accounts; }
			public List<Object> getWaits() { return waits; }
			public void setWaits(List<Object> waits) { this.waits = waits; }

			@JsonIgnoreProperties(ignoreUnknown = true)
			public static class Key {
				@JsonProperty("key")
				private String key;
				@JsonProperty("weight")
				private Integer weight;

				// getter/setter
				public String getKey() { return key; }
				public void setKey(String key) { this.key = key; }
				public Integer getWeight() { return weight; }
				public void setWeight(Integer weight) { this.weight = weight; }
			}
		}
	}

	// =========== getter/setter ===========

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
	public Res getRamRes() { return ramRes; }
	public void setRamRes(Res ramRes) { this.ramRes = ramRes; }
	public Res getCpuRes() { return cpuRes; }
	public void setCpuRes(Res cpuRes) { this.cpuRes = cpuRes; }
	public Res getNetRes() { return netRes; }
	public void setNetRes(Res netRes) { this.netRes = netRes; }
	public Long getSubjectiveCpuBill() { return subjectiveCpuBill; }
	public void setSubjectiveCpuBill(Long subjectiveCpuBill) { this.subjectiveCpuBill = subjectiveCpuBill; }
	public List<Permission> getPermissions() { return permissions; }
	public void setPermissions(List<Permission> permissions) { this.permissions = permissions; }
	public Object getRefundRequest() { return refundRequest; }
	public void setRefundRequest(Object refundRequest) { this.refundRequest = refundRequest; }
	public Object getVoterInfo() { return voterInfo; }
	public void setVoterInfo(Object voterInfo) { this.voterInfo = voterInfo; }
	public Limit getSubjectiveCpuBillLimit() { return subjectiveCpuBillLimit; }
	public void setSubjectiveCpuBillLimit(Limit subjectiveCpuBillLimit) { this.subjectiveCpuBillLimit = subjectiveCpuBillLimit; }
	public List<Object> getEosioAnyLinkedActions() { return eosioAnyLinkedActions; }
	public void setEosioAnyLinkedActions(List<Object> eosioAnyLinkedActions) { this.eosioAnyLinkedActions = eosioAnyLinkedActions; }
}