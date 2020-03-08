package org.dormi.learn.pojo.po;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.dormi.learn.pojo.po.CommonDict;

@Mapper
public interface CommonDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommonDict record);

    CommonDict selectByPrimaryKey(Integer id);

    List<CommonDict> selectAll();

    int updateByPrimaryKey(CommonDict record);
}