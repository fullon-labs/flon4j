package xyz.fullonlabs.flon4j.api.service;

import java.util.Map;

import xyz.fullonlabs.flon4j.api.vo.Block;
import xyz.fullonlabs.flon4j.api.vo.TableRows;
import xyz.fullonlabs.flon4j.api.vo.ChainInfo;
import xyz.fullonlabs.flon4j.api.vo.TableRowsReq;
import xyz.fullonlabs.flon4j.api.vo.account.Account;
import xyz.fullonlabs.flon4j.api.vo.transaction.Transaction;
import xyz.fullonlabs.flon4j.api.vo.transaction.push.TxRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 
 * @author espritblock http://eblock.io
 *
 */
public interface RpcService {

	@GET("/v1/chain/get_info")
	Call<ChainInfo> getChainInfo();

	@POST("/v1/chain/get_block")
	Call<Block> getBlock(@Body Map<String, String> requestFields);

	@POST("/v1/chain/get_account")
	Call<Account> getAccount(@Body Map<String, String> requestFields);

	@POST("/v1/chain/push_transaction")
	Call<Transaction> pushTransaction(@Body TxRequest request);

	@POST("/v1/chain/get_table_rows")
	Call<TableRows> getTableRows(@Body TableRowsReq request);

}
