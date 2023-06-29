package com.antgroup.antchain.xbuilders.dal.mapper;

import com.antgroup.antchain.xbuilders.dal.entity.ChainConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChainConfigMapper {
    /**
     * 获取所有链配置
     *
     * @return 所有链配置
     */
    List<ChainConfig> listAllChainConfigs();

    /**
     * 根据chain id获取对应链配置
     *
     * @param chainId 链id
     * @return
     */
    ChainConfig getChainConfigByChainId(@Param("chainId") String chainId);
}
