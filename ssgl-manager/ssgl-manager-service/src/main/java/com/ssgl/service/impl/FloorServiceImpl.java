package com.ssgl.service.impl;
/*
 * 功能:
 * User: jiajunkang
 * email:jiajunkang@outlook.com
 * Date: 2018/1/17 0017
 * Time: 21:21
 */

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssgl.bean.*;
import com.ssgl.mapper.*;
import com.ssgl.service.FloorService;
import com.ssgl.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    public FloorMapper floorMapper;
    @Autowired
    public DormitoryMapper dormitoryMapper;
    @Autowired
    public DormitoryFloorMapper dormitoryFloorMapper;
    @Autowired
    public CustomFloorMapper customFloorMapper;
    @Autowired
    public CustomerDormitoryMapper customerDormitoryMapper;

    @Override
    public Result addFloor(Floor floor, Integer building_no) throws Exception {
        floor.setId(Integer.parseInt(Util.makeRandom()));
        //添加楼层
        floorMapper.insert(floor);

        Integer rooms = floor.getRoomNumber();

        //宿舍楼房间数要相应减少
        DormitoryExample example = new DormitoryExample();
        example.createCriteria().andBuildingNoEqualTo(building_no);
        List<Dormitory> dormitoryList = dormitoryMapper.selectByExample(example);
        if (null != dormitoryList && dormitoryList.size() > 0) {
            Dormitory dormitory = dormitoryList.get(0);
            dormitory.setSurplus(dormitory.getSurplus() - rooms);//减少剩余房间数
            dormitory.setStudents(dormitory.getStudents() + floor.getStudents().intValue());//设置学生数
            dormitoryMapper.updateByPrimaryKey(dormitory);//更新数据库中的数据
            //插入宿舍楼-楼层表数据
            dormitoryFloorMapper.insert(new DormitoryFloor(floor.getId(), building_no));
            return new Result("ok", "添加成功");
        }
        return new Result("error", "添加失败");
    }

    @Override
    public String selectAllFloors() throws Exception {
        FloorExample example = new FloorExample();
        List<Floor> floors = floorMapper.selectByExample(example);
        if (null != floors && floors.size() > 0) {
            return JSONArray.toJSONString(floors);
        }
        return null;
    }

    @Override
    public com.ssgl.bean.Page<CustomFloor> selectFloorDormitories(Integer page, Integer pageSize,HttpServletRequest request) throws Exception {
        PageHelper.startPage(page, pageSize);
        String buildingNo = request.getParameter("building_no");
        List<CustomFloor> floors = customFloorMapper.selectFloorDormitories(buildingNo);
        if (null != floors && floors.size() > 0) {
            PageInfo<CustomFloor> pageInfo = new PageInfo<>(floors);
            com.ssgl.bean.Page result = new com.ssgl.bean.Page();
            result.setList(pageInfo.getList());
            result.setTotalRecord((int) pageInfo.getTotal());
            return result;
        }
        return null;
    }

    /**
     * 批量删除楼层
     *
     * @param ids 宿舍楼id
     * @return
     * @throws Exception
     */
    public Result deleteFloors(String ids) throws Exception {
        if (StringUtils.isNotBlank(ids)) {
            String[] floorIds = ids.split(",");
            FloorExample example = new FloorExample();
            for (String id:floorIds){
                example.createCriteria().andIdEqualTo(Integer.parseInt(id));
                Floor floor = floorMapper.selectByExample(example).get(0);
                if(floor.getRoomNumber()>floor.getSpaces() || floor.getStudents()>0){
                    throw new RuntimeException("出错");
                }
            }
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < floorIds.length; i++) {
                list.add(Integer.parseInt(floorIds[i]));
            }

            customFloorMapper.deleteFloorDormitories(list);//删除关联的楼层和宿舍楼
            customFloorMapper.deleteFloors(list);//删除楼层
            return new Result("ok", "删除成功");
        }
        return new Result("error", "删除失败");
    }

    @Override
    public String selectLayerByBuidingNo(Integer buildingNo) throws Exception {
        Integer floors = customerDormitoryMapper.selectLayerByBuidingNo(buildingNo);

        List<CustomFloor> list = new ArrayList<>();
        for (int i = 1; i <= floors; i++) {
            CustomFloor customFloor = new CustomFloor();
            customFloor.setBuildingNo(i);
            customFloor.setLayer(i);
            list.add(customFloor);
        }

        return JSONArray.toJSONString(list);
    }

    @Override
    public List<Floor> exportFloor() {
        FloorExample example = new FloorExample();
        return floorMapper.selectByExample(example);
    }


}
