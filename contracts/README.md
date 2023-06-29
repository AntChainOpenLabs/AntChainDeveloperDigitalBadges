X-Builders 合约

使用 Myfish https://opendocs.antchain.antgroup.com/docs/myfish/myfish-config


## 初始化

安装依赖

```sh
npm install # 安装 Myfish 依赖
```

## 编译

```sh
# 进入其中合约目录
npx myfish compile # 编译
```

## 部署

```sh
cd [the contract] # 进入其中某个合约目录
vim config/myfish.open.js # 新建一个配置文件，里面填写开放联盟链相关配置 具体参考 https://opendocs.antchain.antgroup.com/docs/myfish/myfish-config
MYFISH_ENV=open npx myfish deploy --gas 1000000  # 部署，通过 MYFISH_ENV 指定配置文件，会覆盖合并 myfish.js 中的值，指定的 gas 不能超过账户余额，不然会报错
```

## 调用

```sh
MYFISH_ENV=open npx myfish browse block # 查看最新一个区块数据
## 执行合约初始化方法
MYFISH_ENV=open npx myfish run -m 'grantRole("0x0000000000000000000000000000000000000000000000000000000000000000","0x0e0ee53e2fcf0751ea0fa61dc519921cd1edec42eb1247312dafecc02d26cb70")' --gas 1000000
```