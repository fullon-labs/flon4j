package xyz.fullonlabs.flon4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.fullonlabs.flon4j.api.vo.SignParam;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.Tx;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxAction;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxRequest;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxSign;
import xyz.fullonlabs.flon4j.ese.Action;
import xyz.fullonlabs.flon4j.ese.DataParam;
import xyz.fullonlabs.flon4j.ese.DataType;
import xyz.fullonlabs.flon4j.ese.Ese;

/**
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
public class OfflineSign {

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public OfflineSign() {
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * 
	 * @param compression
	 * @param pushTransaction
	 * @param signatures
	 * @return
	 * @throws Exception
	 */
	public String pushTransaction(String compression, Tx pushTransaction, String[] signatures) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String mapJakcson = mapper.writeValueAsString(new TxRequest(compression, pushTransaction, signatures));
		return mapJakcson;
	}

	/**
	 * 离线签名转账
	 * 
	 * @param signParam
	 * @param pk
	 * @param contractAccount
	 * @param from
	 * @param to
	 * @param quantity
	 * @param memo
	 * @return
	 * @throws Exception
	 */
	public String transfer(SignParam signParam, String pk, String contractAccount, String from, String to,
			String quantity, String memo) throws Exception {
		Tx tx = new Tx();
		tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
		tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(signParam.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// data
		Map<String, Object> dataMap = new LinkedHashMap<>();
		dataMap.put("from", from);
		dataMap.put("to", to);
		dataMap.put("quantity", new DataParam(quantity, DataType.asset, Action.transfer).getValue());
		dataMap.put("memo", memo);
		// action
		TxAction action = new TxAction(from, contractAccount, "transfer", dataMap);
		actions.add(action);
		tx.setActions(actions);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
		// data parse
		String data = Ecc.parseTransferData(from, to, quantity, memo);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 离线签名创建账户
	 * 
	 * @param signParam
	 * @param pk
	 * @param creator
	 * @param newAccount
	 * @param owner
	 * @param active
	 * @param buyRam
	 * @return
	 * @throws Exception
	 */
	public String createAccount(SignParam signParam, String pk, String creator, String newAccount, String owner,
			String active, Long buyRam) throws Exception {
		Tx tx = new Tx();
		tx.setExpiration(signParam.getHeadBlockTime().getTime() / 1000 + signParam.getExp());
		tx.setRef_block_num(signParam.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(signParam.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		tx.setActions(actions);
		// create
		Map<String, Object> createMap = new LinkedHashMap<>();
		createMap.put("creator", creator);
		createMap.put("name", newAccount);
		createMap.put("owner", owner);
		createMap.put("active", active);
		TxAction createAction = new TxAction(creator, "flon", "newaccount", createMap);
		actions.add(createAction);
		// buyram
		// Map<String, Object> buyMap = new LinkedHashMap<>();
		// buyMap.put("payer", creator);
		// buyMap.put("receiver", newAccount);
		// buyMap.put("bytes", buyRam);
		// TxAction buyAction = new TxAction(creator, "flonian", "buyrambytes", buyMap);
		// actions.add(buyAction);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(signParam.getChainId(), tx));
		// data parse
		String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
		createAction.setData(accountData);
		// data parse
		// String ramData = Ese.parseBuyRamData(creator, newAccount, buyRam);
		// buyAction.setData(ramData);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}
}
