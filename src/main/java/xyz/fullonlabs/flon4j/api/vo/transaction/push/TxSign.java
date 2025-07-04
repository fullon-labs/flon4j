package xyz.fullonlabs.flon4j.api.vo.transaction.push;

import xyz.fullonlabs.flon4j.api.vo.BaseVo;

/**
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
public class TxSign extends BaseVo {

	public TxSign() {

	}

	public TxSign(String chain_id, Tx transaction) {
		this.chain_id = chain_id;
		this.transaction = transaction;
	}

	private String chain_id;

	private Tx transaction;

	public String getChain_id() {
		return chain_id;
	}

	public void setChain_id(String chain_id) {
		this.chain_id = chain_id;
	}

	public Tx getTransaction() {
		return transaction;
	}

	public void setTransaction(Tx transaction) {
		this.transaction = transaction;
	}

}
