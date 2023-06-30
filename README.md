<div align="center">
  <img alt="am logo" src="https://antchainbridge.oss-cn-shanghai.aliyuncs.com/antchainbridge/document/picture/antchain.png" width="250" >
  <h1 align="center">AntChain Developer Digital Badges</h1>
  <p align="center">
    <a href="http://makeapullrequest.com">
      <img alt="pull requests welcome badge" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat">
    </a>
    <a href="https://www.java.com">
      <img alt="Language" src="https://img.shields.io/badge/Language-Java-blue.svg?style=flat">
    </a>
    <a href="https://github.com/AntChainOpenLab/AntChainDeveloper DigitalBadges/graphs/contributors">
      <img alt="GitHub contributors" src="https://img.shields.io/github/contributors/AntChainOpenLab/AntChainDeveloperDigitalBadges">
    </a>
    <a href="https://www.apache.org/licenses/LICENSE-2.0">
      <img alt="License" src="https://img.shields.io/github/license/AntChainOpenLab/AntChainDeveloperDigitalBadges?style=flat">
    </a>
  </p>
</div>


# 介绍

AntChain Developer Digital Badges是蚂蚁链(开放联盟链)的Web3示范应用, 可以与 Web3 开发者共同探索、构建数字世界。通过为熟悉传统应用开发却不熟悉Web3应用架构广大的开发者提供Web3示范应用，帮助广大开发者根据自己的业务场景去定制开发，同时将开发者引入到Web3世界中。
通过个性化定制开发，可以实现例如数字头像、数字门票、数字徽章(POAP)、权益（优惠券）发放和核销等功能，满足各类数字化场景的基本需求，也可支持会场交互需求。


在AntChain Developer Digital Badges的架构中，需要与区块链进行交互，而链的版本、连接方式类型繁多，无法统一适配，因此AntChain Developer Digital Badges示例中使用蚂蚁链BaaS平台官方提供的[REST接入服务](https://antchain.antgroup.com/docs/11/204700)，从而实现易用、高效、可靠的统一区块链接入服务。


AntChain Developer Digital Badges为开发者提供了数字头像合约模板，来帮助开发者完成插件和合约的开发，需要使用蚂蚁链合约工具[myfish](contracts/README.md)进行编译部署。


以下介绍了AntChain Developer Digital Badges的整体架构：

![](https://mdn.alipayobjects.com/huamei_3x8d2m/afts/img/A*3tOZSIhIG4wAAAAAAAAAAAAADjmWAQ/original)

插件SDK共有四个部分，包括：

在当前的工程实现中，AntChain Developer Digital Badges核心部分是以传统的 MVC 三层架构思想的分层模型实现的。
- **bootstrap 层（启动层**
该层是 SOFABoot 项目中的启动模块，其中包含 SOFABoot 应用的启动类、配置文件、打包插件等，其测试目录中还提供了集成测试的基类，可支持继承和扩展。
该模块可通过直接或间接依赖引用其他各模块的代码。

- **ervice 层（服务层）**
service 层作为 web 层或 facade 层与 model 层之间的桥梁，是实现主要业务逻辑的模块（如提供访问数据库之前的数据校验，处理数据库返回的值，发布 RPC 服务等）。
- **model 层（模型层）**
model 层用于存放实体类，即定义业务逻辑的领域对象。
- **dal 层（数据库层）**
dal 层主要是用于访问数据库，即编写对数据库的增删改查的 sql 语句实现。
- **utils 层 (工具层)**
utils 层提供了一些公共的工具库类，这些类可以被应用中的所有类使用。

  

# 构建

**在开始之前，请您确保安装了maven和JDK，这里推荐使用openjdk-1.8版本*
- **下载并安装REST SDK**

自带依赖的下载链接：[rest-client-2.16.5-with-dependencies.jar](https://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/file-manage-files/zh-CN/20230512/sghb/rest-client-2.16.5-with-dependencies.jar)

下载成功后在server目录中运行
mvn install:install-file -Dfile=jar包的位置 -DgroupId=com.antfinancial.antchain.baas.tool -DartifactId=rest-client -Dversion=2.16.5 -Dpackaging=jar

# 快速开始

- **申请区块链账号**
1. 在开放联盟链官网进行账号、AK等申请，具体步骤请参考[蚂蚁链服务获取](https://opendocs.antchain.antgroup.com/docs/intro#-%E8%8E%B7%E5%8F%96%E6%9C%8D%E5%8A%A1)
2. 数字藏品需要存储图像以及MetaData数据， 用户可以自由选择数据服务平台。 本应用中使用[阿里云oss服务](https://www.aliyun.com/product/oss)作为示例, 使用其他数据存储服务需要进行个性化定制开发。

- **部署合约**
本应用中提供两个不同的数字头像合约实现， 所使用的合约语言均为Soldity语言,[蚂蚁链合约平台与原生 Solidity 具有不一样的特性](https://antchain.antgroup.com/docs/11/101909)。部署合约需要使用蚂蚁链合约工具[myfish](contracts/README.md)进行编译部署。两个合约不同之处在于， [index.sol](contracts/solidity/index.sol) 中设置了baseURI， 即所有数字藏品metadata URI均在此基础上进行递增。 而[indexCustomizedURI.sol](contracts/solidity/indexCustomizedURI.sol)合约可以为每一个数字藏品设置完全不同的URI，可定制程度更高。

- **应用配置**
1. 将申请好的区块链账号信息填入[xbuilders_chain_config](server/src/main/resources/data.sql)中， 需要填入`chain_id`,`chain_name`,`access_key`,`access_secret`,`admin_account_name`,`admin_account_kms_id`,`del_status`,`gmt_create`,`gmt_modified`,`tenant`,`chain_browser_url`等字段。
2. 将部署好的合约信息填入[lab_dev_community_asset](server/src/main/resources/data.sql)中， 需要填入`id`, `group_id`, `chain_id`, `contract_id`, `contract_name`, `contract_type`, `sub_id`, `asset_type`, `name`, `redeemed_amount`, `total_amount` 等字段。
3. 将数据库以及其他配置信息填入[application.properties](server/src/main/resources/application.properties)中



- **启动应用**
启动方式与传统spring-boot应用完全相同，在完成配置文件以及依赖安装后，运行如下命令
    ```bash
    mvn spring-boot:run
    ```

- **A-Pocket服务使用**
A-Pocket是蚂蚁链数字钱包，支持密钥托管，链上NFT资产展示，并开放提供对外部DApp的调用。在支付宝小程序搜索A-Pocket即可使用，在开放联盟链中铸造的数字藏品， 可以接入A-Pocket进行展示， 目前A-Pocket数字藏品接入还处于白名单阶段，用户可以在issue以"A-Pocket白名单申请作为标题"， 附带上合约地址等相关信息，我们会尽快审核。有相关疑问可以加入下方社区治理钉群中进行提问&讨论。

# 社区治理

AntChain Developer Digital Badges 欢迎您以任何形式参与社区建设。

您可以通过以下方式参与社区讨论

- 钉钉

<div align="left">
	<img src="https://mdn.alipayobjects.com/huamei_3x8d2m/afts/img/A*t9hxRp0UHSMAAAAAAAAAAAAADjmWAQ/original" alt="Editor" width="200">
</div>

- 邮件

发送邮件到`openlab.antchain@member.alibaba.com`

# License

详情参考[LICENSE](./LICENSE)。