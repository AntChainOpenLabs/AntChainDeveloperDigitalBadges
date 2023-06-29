package com.antgroup.antchain.xbuilders.dal.mapper;

import com.antgroup.antchain.xbuilders.model.entity.Asset;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetMapper {
    /**
     * 根据group id获取组内所有asset
     *
     * @param groupId 资产组id
     * @return
     */
    List<Asset> getAssetsByGroupId(@Param("groupId") String groupId);

    /**
     * 获取所有资产
     *
     * @return
     */
    List<Asset> getAllAssets();

    /**
     * 根据资产id获取资产详情
     *
     * @param assetId 资产id
     * @return
     */
    Asset getAsset(@Param("assetId") String assetId);

    /**
     * 增加资产已兑换数量记录
     *
     * @param assetId 资产id
     * @return
     */
    int increaseAssetRedeemAmount(@Param("assetId") String assetId);

    /**
     * 查询指定资产组有多少个资产
     *
     * @param groupId 资产组id
     * @return 资产数量
     */
    Integer countAssetsByGroupId(@Param("groupId") String groupId);
}
