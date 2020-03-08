package org.dormi.learn.pojo.po;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LicenseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LicenseInfo record);

    LicenseInfo selectByPrimaryKey(Integer id);

    List<LicenseInfo> selectAll();

    int updateByPrimaryKey(LicenseInfo record);

    LicenseInfo selectByAccountAndProduct(@Param("account") String account, @Param("product") String product);
}