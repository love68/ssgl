package com.ssgl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.Page;
import com.ssgl.bean.Room;
import com.ssgl.bean.RoomExample;
import com.ssgl.mapper.RoomMapper;
import com.ssgl.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    public RoomMapper roomMapper;

    @Override
    public Page<Room> selectRoomPage(Integer page, Integer rows) throws Exception {
        PageHelper.startPage(page,rows);
        RoomExample example = new RoomExample();
        List<Room> roomList = roomMapper.selectByExample(example);
        PageInfo<Room> pageInfo = new PageInfo<Room>(roomList);
        Page<Room> result = new Page<>();
        result.setList(pageInfo.getList());
        result.setTotalRecord((int) pageInfo.getTotal());
        return result;
    }

    @Override
    public void updateRoom(Room room) throws Exception {
        Room oldRoom = roomMapper.selectByPrimaryKey(room.getId());

        //更新数据
        oldRoom.setStarLevel(room.getStarLevel());
        oldRoom.setScore(room.getScore());

        //写回到数据库
        int r = roomMapper.updateByPrimaryKey(oldRoom);
        if(r!=1){
            throw new RuntimeException();
        }
    }
}
