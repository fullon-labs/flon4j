package xyz.fullonlabs.flon4j;

import java.security.MessageDigest;
import java.util.List;

import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxSign;
import xyz.fullonlabs.flon4j.ecc.EccTool;
import xyz.fullonlabs.flon4j.ese.Ese;

/**
 * Ecc,用户生成公私钥，签名，数据序列化
 * 
 * @author fullonlabs https://fullonlabs.xyz
 *
 */
public class Ecc {

	/**
	 * 通过种子生成私钥
	 * 
	 * @param seed
	 *            种子
	 * @return
	 */
	public static String seedPrivate(String seed) {
		return EccTool.seedPrivate(seed);
	}

	/**
	 * 通过私钥生成公钥
	 * 
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static String privateToPublic(String privateKey) {
		return EccTool.privateToPublic(privateKey);
	}

	/**
	 * 普通数据签名
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            需要签名的数据
	 * @return
	 */
	public static String sign(String privateKey, String data) {
		return EccTool.sign(privateKey, data);
	}
	
	/**
	 * 交易签名
	 * 
	 * @param privateKey
	 *            私钥
	 * @param data
	 *            需要签名的对象
	 * @return
	 */
	public static String signTransaction(String privateKey, TxSign sign) {
		return EccTool.signTransaction(privateKey, sign);
	}

	/**
	 * 转账数据序列化
	 * 
	 * @param from
	 *            从
	 * @param to
	 *            到
	 * @param quantity
	 *            转账金额和币种
	 * @param memo
	 *            备注留言
	 * @return
	 */
	public static String parseTransferData(String from, String to, String quantity, String memo) {
		return Ese.parseTransferData(from, to, quantity, memo);
	}
	
	/**
	 * 
	 * @param voter
	 * @param proxy
	 * @param producers
	 * @return
	 */
	public static String parseVoteProducerData(String voter, String proxy, List<String> producers) {
		return Ese.parseVoteProducerData(voter, proxy, producers);
	}

	/**
	 * 创建账户数据序列化
	 * 
	 * @param creator
	 *            创建者
	 * @param name
	 *            账户名
	 * @param onwe
	 *            onwer公钥
	 * @param active
	 *            active公钥
	 * @return
	 */
	public static String parseAccountData(String creator, String name, String onwer, String active) {
		return Ese.parseAccountData(creator, name, onwer, active);
	}
	
	/**
	 * 关闭token
	 * @param owner
	 * @param symble
	 * @return
	 */
	public static String parseCloseData(String owner, String symbol) {
		return Ese.parseCloseData(owner, symbol);
	}

}
