package com.antgroup.antchain.xbuilders.dal.mapper;

import com.antgroup.antchain.xbuilders.dal.entity.MintAssetOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MintXBuildersAssetMapper {
    /**
     * 添加记录
     *
     * @param mintAssetOrder
     */
    int insert(MintAssetOrder mintAssetOrder);

    /**
     * 更新订单状态与交易哈希
     *
     * @param orderId
     */
    int updateStatusAndHashByOrderId(@Param("orderId") String orderId,
                                     @Param("txHash") String txHash,
                                     @Param("oldStatus") short oldStatus,
                                     @Param("newStatus") short newStatus);

    /**
     * 查询订单
     *
     * @param orderId
     * @return
     */
    MintAssetOrder getOrderByOrderId(@Param("orderId") String orderId);

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param oldStatus
     * @param newStatus
     * @return
     */
    int updateStatusByOrderId(@Param("orderId") String orderId,
                              @Param("oldStatus") short oldStatus,
                              @Param("newStatus") short newStatus);
}
