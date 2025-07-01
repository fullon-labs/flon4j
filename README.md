# flon4j

flon 1.0.1 for java 

change writeName add writeUint64 support custom contract

# use

## voteproducer

 ```
 voteproducer(String pk,String voter,String proxy,List<String> producers)
 	
 ```

## support offline sign
 
 ```
OfflineSign sign = new OfflineSign();
String content =sign.transfer(params,"5KViPHaWrpRHmChKkD9i1gUGLVNC6eRSHzP33ZadFuBuGeecLFz","flon.token","eeeeeeeeeeee", "555555555551", "372.01234567 FLON", "test");
	
 ```

## transfer

```
/**
 * 转账
 * @param pk 私钥
 * @param contractAccount 合约账户
 * @param from 从
 * @param to 到
 * @param quantity 转账币种金额
 * @param memo 备注
 */

 rpc.transfer(
 	"5KViPHaWrpRHmChKkD9i1gUGLVNC6eRSHzP33ZadFuBuGeecLFz",
 	"flon.token",
 	"flonian",
 	"flonflonflon",
 	"100 FLON",
 	"");

```
## createAccount

```
/**
 * 创建账户
 * @param pk 创建者私钥
 * @param creator 创建者
 * @param newAccount 新账户名
 * @param owner 公钥
 * @param active 公钥
 */
 rpc.createAccount(
 	"5KViPHaWrpRHmChKkD9i1gUGLVNC6eRSHzP33ZadFuBuGeecLFz", 
 	"flonian",
 	"newaccount22",
 	"FU53TXSgCaU6uKrCbiZj3nazPkpCmYLZfhVhLRaCtvjXiwLHMCjq",
 	"FU53TXSgCaU6uKrCbiZj3nazPkpCmYLZfhVhLRaCtvjXiwLHMCjq" );
 	
```
## seedPrivate

```

Ecc.seedPrivate("test");

```


## seedPrivate

```

Ecc.seedPrivate("test");

```

## privateToPublic

```

Ecc.privateToPublic(privateKey);

```

## sign

```
Ecc.sign(pk, "test");

```

## data serializa

```
//transfer parse
String data = Ecc.parseTransferData(
	"fromaccount", 
	"toaccount", 
	"10.0020 SYS", 
	"memo"
);

//account parse
String data1 = Ecc.parseAccountData(
	"flon",
	"flonian",
	"FU53TXSgCaU6uKrCbiZj3nazPkpCmYLZfhVhLRaCtvjXiwLHMCjq",
	"FU53TXSgCaU6uKrCbiZj3nazPkpCmYLZfhVhLRaCtvjXiwLHMCjq");

```

# join eos open source 

wechat hl_294944589
 
# License

flon4j is released under GNU/GPL Version 3
