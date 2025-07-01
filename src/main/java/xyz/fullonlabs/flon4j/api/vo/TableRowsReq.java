package xyz.fullonlabs.flon4j.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRowsReq {

	private String code = "flon";

	private String scope;

	private String table;
	
	private Boolean json=true;

	private int limit = 10;

	private Integer index_position;

	private String lower_bound;

	private String upper_bound;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Boolean getJson() {
		return json;
	}

	public void setJson(Boolean json) {
		this.json = json;
	}

    public Integer getIndex_position() {
        return index_position;
    }

    public void setIndex_position(Integer index_position) {
        this.index_position = index_position;
    }

    public String getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(String lower_bound) {
        this.lower_bound = lower_bound;
    }

    public String getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(String upper_bound) {
        this.upper_bound = upper_bound;
    }
}
