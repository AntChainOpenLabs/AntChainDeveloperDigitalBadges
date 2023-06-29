package com.antgroup.antchain.xbuilders.integration;

import com.antgroup.antchain.xbuilders.integration.entity.*;

import javax.annotation.Nonnull;

public interface MyPocketService {
    /**
     * 获取指定did在指定链上的账户信息
     *
     * @param request 查询账户请求
     * @return
     */
    MyPocketGetChainAccountResponseBO getUserChainAccountByDid(MyPocketChainAccountRequestBO request);

    /**
     * 为指定did在指定链上创建账户
     *
     * @param request 创建账户请求
     * @return
     */
    MyPocketChainAccountBO createChainAccountForDid(MyPocketChainAccountRequestBO request);


    /**
     * 验证app did签名
     *
     * @param request 请求
     * @return
     */
    Boolean verifyAppDidSign(MyPocketSignVerifyRequestBO request);

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    MyPocketQueryUserInfoResponseBO getUserInfo(MyPocketQueryUserInfoRequestBO request);

    /**
     * 生成请求账户列表二维码
     * @param request
     * @return
     */
    @Nonnull
    MyPocketCreateQrCodeRequestAccountsResponse createQrCodeRequestAccounts(MyPocketCreateQrCodeRequestAccountsRequest request);

    /**
     * 查询请求账户列表二维码的结果
     * @param request
     * @return
     */
    @Nonnull
    MyPocketCheckQrCodeRequestAccountsResponse checkQrCodeRequestAccounts(MyPocketCheckQrCodeRequestAccountsRequest request);
}
