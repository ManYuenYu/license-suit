package org.dormi.learn.pojo.po;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClientInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClientInfo record);

    ClientInfo selectByPrimaryKey(Integer id);

    List<ClientInfo> selectAll();

    int updateByPrimaryKey(ClientInfo record);
}