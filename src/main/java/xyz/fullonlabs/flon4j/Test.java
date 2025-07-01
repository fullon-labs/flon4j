package xyz.fullonlabs.flon4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import xyz.fullonlabs.flon4j.api.exception.ApiException;
import xyz.fullonlabs.flon4j.api.vo.transaction.Transaction;
public class Test {

	// eosjs 打包结果用于校验兼容性
	static final String eosjs_transfer_seriz = "00f2d4142123e95d0000c85353840ccdb486010000000000045359530000000019e6b58be8af95313233616263646f2e2f2c2e2f214023232425";
	static final String eosjs_account_seriz = "0000000000ea30550002a2f164772b5601000000010003ee4221c9c3f4f62646e3c758dbb8abaae506a559f67148a76968fa6b0f0868140100000001000000010003ba8de2f029cae85e7ca5c9f591bb17b86d750c5116cec30d94100e16e446d41501000000";

	public static void main(String[] args){
		System.out.println("******************* Ecc Start *******************\n");

		System.out.println("============= 通过种子生成私钥 ===============");
		String pk = Ecc.seedPrivate("!@#...（省略）...98012");
		System.out.println("private key :" + pk +"\n");

		System.out.println("============= 通过私钥生成公钥 ===============");
		String pu = Ecc.privateToPublic(pk);
		System.out.println("public key :" + pu + " \n ");

		System.out.println("============= 自定义数据签名 ===============");
		String sign = Ecc.sign(pk,"is京東價as看到可可是是是@#￥%……&*（CVBNM《d ");
		System.out.println("sign :" + sign + " \n ");

		System.out.println("============= 转账数据序列化 ===============");
		String data = Ecc.parseTransferData("fromaccount", "toaccount", "10.0020 SYS", "测试123abcdo./,./!@##$%");
		System.out.println("seriz data :" + data);
		System.out.println("transfer eq eosjs seriz " + data.equals(eosjs_transfer_seriz)+" \n ");

		System.out.println("============= 创建账户数据序列化 ===============");
		String data1 = Ecc.parseAccountData("flonian", "espritbloc1.","FU7PowBS8TPatxjEhBDFqHkpGDCnPS3atB6fuQnzoCtjFJ6ykzeQ","FU7PowBS8TPatxjEhBDFqHkpGDCnPS3atB6fuQnzoCtjFJ6ykzeQ");
		System.out.println("seriz data :" + data1);
		System.out.println("account eq eosjs seriz " + data1.equals(eosjs_account_seriz));

		System.out.println("\n******************* Ecc End *******************\n\n\n");
		System.out.println("******************* Rpc Start *******************\n");

		Rpc rpc = new Rpc("https://t.flonscan.io");  // 测试网RPC

//		// 1. 转账
//		System.out.println("============= 转账 ===============");
//		try {
//			Transaction t1 = rpc.transfer(
//					"5JpjyFBd4VGeSQ9LUP4dGaaxoiofUGcMWoLihJZfrQbpp9nxq7X",
//					"flon.token",
//					"testtest",
//					"flontest",
//					"0.28210000 FLON", ""
//			);
//			System.out.println("转账成功 = " + t1.getTransactionId()+" \n ");
//		} catch(Exception ex) {
//			// 建议输出原始 response body 便于链端错误排查
//			ex.printStackTrace();
//		}

		System.out.println(Ecc.privateToPublic("5JpjyFBd4VGeSQ9LUP4dGaaxoiofUGcMWoLihJZfrQbpp9nxq7X"));
		// 2. 创建账户
		System.out.println("============= 创建账户===============");
		try {
			Transaction t2 = rpc.createAccount(
					"5JpjyFBd4VGeSQ9LUP4dGaaxoiofUGcMWoLihJZfrQbpp9nxq7X",
					"testtest",
					"testtesttesc",
					"FU7PowBS8TPatxjEhBDFqHkpGDCnPS3atB6fuQnzoCtjFJ6ykzeQ",
					"FU7PowBS8TPatxjEhBDFqHkpGDCnPS3atB6fuQnzoCtjFJ6ykzeQ",
					"0.01000000 FLON"
			);
			System.out.println("创建成功 = " + t2.getTransactionId()+" \n ");
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("\n******************* Rpc End *******************");
	}
}