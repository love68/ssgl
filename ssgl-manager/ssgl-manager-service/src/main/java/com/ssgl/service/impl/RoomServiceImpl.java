package com.ssgl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.*;
import com.ssgl.mapper.*;
import com.ssgl.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    public RoomMapper roomMapper;
    @Autowired
    public CustomerRoomMapper customerRoomMapper;
    @Autowired
    public DormitoryMapper dormitoryMapper;
    @Autowired
    public CustomFloorMapper customFloorMapper;
    @Autowired
    public FloorMapper floorMapper;
    @Autowired
    public CustomerStudentMapper customerStudentMapper;
    @Autowired
    public StudentMapper studentMapper;

    @Override
    public Page<Room> selectRoomPage(Integer page, Integer rows, HttpServletRequest request) throws Exception {
        PageHelper.startPage(page, rows);
        RoomExample example = new RoomExample();
        String roomNumber = request.getParameter("roomNumber");
        if (null != roomNumber && roomNumber.length()>0) {
            example.createCriteria().andRoomNumberEqualTo(roomNumber);
        }
        example.setOrderByClause("room_number ASC");
        List<Room> roomList = roomMapper.selectByExample(example);
        PageInfo<Room> pageInfo = new PageInfo<Room>(roomList);
        Page<Room> result = new Page<>();
        result.setList(pageInfo.getList());
        result.setTotalRecord((int) pageInfo.getTotal());
        return result;
    }

    @Override
    public List<Room> exportRoom() {
        RoomExample example = new RoomExample();
        return roomMapper.selectByExample(example);
    }

    @Override
    public Result deleteRooms(List<String> ids) throws Exception {
        List<String> list = customerRoomMapper.selectRoomById(ids);
        DormitoryExample example = new DormitoryExample();
        for(String l : list){
            String ll = l.substring(0,1);
            example.createCriteria().andBuildingNoEqualTo(Integer.parseInt(ll));
            Dormitory dormitory = dormitoryMapper.selectByExample(example).get(0);
            dormitory.setSurplus(dormitory.getSurplus()+1);
            dormitoryMapper.updateByPrimaryKey(dormitory);
        }
        List<Student> students = customerStudentMapper.selectStudentsByRoomNumber(list);
        for(Student student : students){
            student.setRoomNumber(null);
            studentMapper.updateByPrimaryKey(student);
        }
        customerRoomMapper.deleteRooms(ids);
        return new Result("ok", "删除成功");
    }

    @Override
    public Result deleteRoom(String id) throws Exception {
        customerRoomMapper.deleteRoom(id);
        return new Result("ok", "删除成功");
    }

    @Override
    public void updateRoom(Room room) throws Exception {
        Room oldRoom = roomMapper.selectByPrimaryKey(room.getId());

        //更新数据
        oldRoom.setStarLevel(room.getStarLevel());//更改星级
        oldRoom.setPeopleNum(room.getPeopleNum());//更改实际人数
        oldRoom.setScore(room.getScore());//更改分数
        oldRoom.setCapacity(room.getCapacity());//更改容量

        //写回到数据库
        int r = roomMapper.updateByPrimaryKey(oldRoom);
        if (r != 1) {
            throw new RuntimeException();
        }
    }

    @Override
    public void addRoom(Room room) throws Exception {
        //得到宿舍楼号
        String s = room.getRoomNumber().substring(0,1);
        String s1 = room.getRoomNumber().substring(1,2);

        DormitoryExample example = new DormitoryExample();
        example.createCriteria().andBuildingNoEqualTo(Integer.parseInt(s));
        Dormitory dormitory = dormitoryMapper.selectByExample(example).get(0);
        //更新宿舍楼房间数
        dormitory.setSurplus(dormitory.getSurplus()-1);
        //更新宿舍楼人数
        dormitory.setStudents(dormitory.getStudents()+room.getCapacity());
        dormitoryMapper.updateByPrimaryKey(dormitory);

//        Floor floor = customFloorMapper.getFloor(Integer.parseInt(s),Integer.parseInt(s1));
//        floor.setStudents(floor.getStudents()+room.getCapacity());
//        floor.setSpaces(floor.getSpaces()-1);
//        floorMapper.updateByPrimaryKey(floor);

        roomMapper.insert(room);
    }

    @Override
    public String getRooms(Integer buildingNo) {
        List<Room> rooms = customerRoomMapper.getRooms(buildingNo);
        return JSONObject.toJSONString(rooms);
    }
}
