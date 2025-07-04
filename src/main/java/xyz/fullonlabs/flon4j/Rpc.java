package xyz.fullonlabs.flon4j;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.fullonlabs.flon4j.api.service.RpcService;
import xyz.fullonlabs.flon4j.api.utils.Generator;
import xyz.fullonlabs.flon4j.api.vo.Block;
import xyz.fullonlabs.flon4j.api.vo.ChainInfo;
import xyz.fullonlabs.flon4j.api.vo.SignParam;
import xyz.fullonlabs.flon4j.api.vo.TableRows;
import xyz.fullonlabs.flon4j.api.vo.TableRowsReq;
import xyz.fullonlabs.flon4j.api.vo.account.Account;
import xyz.fullonlabs.flon4j.api.vo.transaction.Transaction;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.Tx;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxAction;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxRequest;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxSign;
import xyz.fullonlabs.flon4j.ese.Action;
import xyz.fullonlabs.flon4j.ese.DataParam;
import xyz.fullonlabs.flon4j.ese.DataType;
import xyz.fullonlabs.flon4j.ese.Ese;

public class Rpc {

	private final RpcService rpcService;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public Rpc(String baseUrl) {
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		rpcService = Generator.createService(RpcService.class, baseUrl);
	}

	/**
	 * 获得链信息
	 * 
	 * @return
	 */
	public ChainInfo getChainInfo() {
		return Generator.executeSync(rpcService.getChainInfo());
	}

	/**
	 * 获得区块信息
	 * 
	 * @param blockNumberOrId
	 *            区块ID或者高度
	 * @return
	 */
	public Block getBlock(String blockNumberOrId) {
		return Generator.executeSync(rpcService.getBlock(Collections.singletonMap("block_num_or_id", blockNumberOrId)));
	}

	/**
	 * 获得账户信息
	 * 
	 * @param account
	 *            账户名称
	 * @return
	 */
	public Account getAccount(String account) {
		return Generator.executeSync(rpcService.getAccount(Collections.singletonMap("account_name", account)));
	}

	/**
	 * 获得table数据
	 * 
	 * @param req
	 * @return
	 */
	public TableRows getTableRows(TableRowsReq req) {
		return Generator.executeSync(rpcService.getTableRows(req));
	}

	/**
	 * 发送请求
	 * 
	 * @param compression
	 *            压缩
	 * @param pushTransaction
	 *            交易
	 * @param signatures
	 *            签名
	 * @return
	 * @throws Exception
	 */
	public Transaction pushTransaction(String compression, Tx pushTransaction, String[] signatures) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String mapJakcson = mapper.writeValueAsString(new TxRequest(compression, pushTransaction, signatures));
		System.out.println(mapJakcson);
		return Generator
				.executeSync(rpcService.pushTransaction(new TxRequest(compression, pushTransaction, signatures)));
	}

	/**
	 * 发送交易
	 * 
	 * @param tx
	 * @return
	 * @throws Exception
	 */
	public Transaction pushTransaction(String tx) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TxRequest txObj = mapper.readValue(tx, TxRequest.class);
		return Generator.executeSync(rpcService.pushTransaction(txObj));
	}

	/**
	 * 获取离线签名参数
	 * 
	 * @param exp
	 *            过期时间秒
	 * @return
	 */
	public SignParam getOfflineSignParams(Long exp) {
		SignParam params = new SignParam();
		ChainInfo info = getChainInfo();
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		params.setChainId(info.getChainId());
		params.setHeadBlockTime(info.getHeadBlockTime());
		params.setLastIrreversibleBlockNum(info.getLastIrreversibleBlockNum());
		params.setRefBlockPrefix(block.getRefBlockPrefix());
		params.setExp(exp);
		return params;
	}

	/**
	 * 转账
	 * 
	 * @param pk
	 *            私钥
	 * @param contractAccount
	 *            合约账户
	 * @param from
	 *            从
	 * @param to
	 *            到
	 * @param quantity
	 *            币种金额
	 * @param memo
	 *            留言
	 * @return
	 * @throws Exception
	 */
	public Transaction transfer(String pk, String contractAccount, String from, String to, String quantity, String memo)
			throws Exception {
		// get chain info
		ChainInfo info = getChainInfo();
//		info.setChainId("cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f");
//		info.setLastIrreversibleBlockNum(826366l);
//		info.setHeadBlockTime(dateFormatter.parse("2018-08-22T09:19:01.000"));
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
//		block.setRefBlockPrefix(2919590658l);
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
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
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String data = Ecc.parseTransferData(from, to, quantity, memo);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 创建账户
	 * 
	 * @param pk
	 *            私钥
	 * @param creator
	 *            创建者
	 * @param newAccount
	 *            新账户
	 * @param owner
	 *            公钥
	 * @param active
	 *            公钥
	 * @param buyGasAmount
	 *            ram
	 * @return
	 * @throws Exception
	 */
	public Transaction createAccount(String pk, String creator, String newAccount, String owner, String active, String buyGasAmount) throws Exception {
		// get chain info
		ChainInfo info = getChainInfo();
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// create
		Map<String, Object> createMap = new LinkedHashMap<>();
		createMap.put("creator", creator);
		createMap.put("name", newAccount);
		createMap.put("owner", owner);
		createMap.put("active", active);
		TxAction createAction = new TxAction(creator, "flon", "newaccount", createMap);
		actions.add(createAction);

		// buygas
		Map<String, Object> buyMap = new LinkedHashMap<>();
		buyMap.put("payer", creator);
		buyMap.put("receiver", newAccount);
		buyMap.put("quant", new DataParam(buyGasAmount, DataType.asset, Action.buyGas).getValue());
		TxAction buyAction = new TxAction(creator, "flon", "buygas", buyMap);
		actions.add(buyAction);
		tx.setActions(actions);

		// data
//		Map<String, Object> dataMap = new LinkedHashMap<>();
//		dataMap.put("from", creator);
//		dataMap.put("to", newAccount);
//		dataMap.put("quantity", new DataParam(buyGasAmount, DataType.asset, Action.transfer).getValue());
//		dataMap.put("memo", "");
//		// action
//		TxAction action = new TxAction(creator, "flon.token", "transfer", dataMap);
//		actions.add(action);
//		tx.setActions(actions);

		System.out.println("签名私钥: " + pk);
		System.out.println("对应公钥: " + Ecc.privateToPublic(pk));
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));

//		byte[] digest = Ecc.getTxDigest(info.getChainId(), tx); // 若没有 getTxDigest 方法，你需要自己算 SHA256(序列化tx)
//		String recoveredPubKey = Ecc.recoverPublicKey(sign, digest);
//		System.out.println("由签名恢复出的公钥: " + recoveredPubKey);
		// data parse
		String accountData = Ese.parseAccountData(creator, newAccount, owner, active);
		createAction.setData(accountData);
		// data parse
		String gasData = Ese.parseBuyGasData(creator, newAccount, buyGasAmount);
		buyAction.setData(gasData);

//		// data parse
//		String data = Ecc.parseTransferData(creator, newAccount, buyGasAmount, "");
//		// reset data
//		action.setData(data);

		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}

	/**
	 * 
	 * @param pk
	 * @param voter
	 * @param proxy
	 * @param producers
	 * @return
	 * @throws Exception
	 */
	public Transaction voteproducer(String pk,String voter,String proxy,List<String> producers) throws Exception {
		Comparator<String> comparator = (h1, h2) -> h2.compareTo(h1);
		producers.sort(comparator.reversed());
		// get chain info
		ChainInfo info = getChainInfo();
		// get block info
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		// tx
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// data
		Map<String, Object> dataMap = new LinkedHashMap<>();
		dataMap.put("voter", voter);
		dataMap.put("proxy", proxy);
		dataMap.put("producers",producers);
		// action
		TxAction action = new TxAction(voter, "flon", "voteproducer", dataMap);
		actions.add(action);
		tx.setActions(actions);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String data = Ecc.parseVoteProducerData(voter, proxy, producers);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}
	
	/**
	 * token close
	 * @param owner
	 * @param symbol
	 * @return
	 * @throws Exception
	 */
	public Transaction close(String pk,String contract,String owner, String symbol)throws Exception {
		ChainInfo info = getChainInfo();			
		Block block = getBlock(info.getLastIrreversibleBlockNum().toString());
		Tx tx = new Tx();
		tx.setExpiration(info.getHeadBlockTime().getTime() / 1000 + 60);
		tx.setRef_block_num(info.getLastIrreversibleBlockNum());
		tx.setRef_block_prefix(block.getRefBlockPrefix());
		tx.setNet_usage_words(0l);
		tx.setMax_cpu_usage_ms(0l);
		tx.setDelay_sec(0l);
		// actions
		List<TxAction> actions = new ArrayList<>();
		// data
		Map<String, Object> dataMap = new LinkedHashMap<>();
		dataMap.put("close-owner", owner);
		dataMap.put("close-symbol",  new DataParam(symbol, DataType.symbol, Action.close).getValue());
		// action
		TxAction action = new TxAction(owner,contract,"close",dataMap);
		actions.add(action);
		tx.setActions(actions);
		// sgin
		String sign = Ecc.signTransaction(pk, new TxSign(info.getChainId(), tx));
		// data parse
		String data = Ecc.parseCloseData(owner, symbol);
		// reset data
		action.setData(data);
		// reset expiration
		tx.setExpiration(dateFormatter.format(new Date(1000 * Long.parseLong(tx.getExpiration().toString()))));
		return pushTransaction("none", tx, new String[] { sign });
	}
}
