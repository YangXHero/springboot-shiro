package com.liuzw.springbootshiro.service.impl;

import com.liuzw.springbootshiro.bean.SysDept;
import com.liuzw.springbootshiro.bean.SysDeptQuery;
import com.liuzw.springbootshiro.mapper.SysDeptMapper;
import com.liuzw.springbootshiro.model.SysDeptModel;
import com.liuzw.springbootshiro.service.SysDeptService;
import com.liuzw.springbootshiro.utils.CopyDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 *  SysDeptServiceImpl
 *
 * @author liuzw
 */
@Service("sysDeptService")
public class SysDeptServiceImpl  implements SysDeptService {
		
		
	@Autowired
	private SysDeptMapper sysDeptMapper;


	@Override
	public List<SysDeptModel> getList(SysDeptQuery dto) {
		//PageHelper.startPage(dto.getPageNumberByDefault(), dto.getPageSizeByDefault());
    	return sysDeptMapper.getList(dto);
    }

	@Override
	public Boolean checkDeptIsExist(String deptNum) {
		SysDeptModel model = SysDeptModel.builder().deptNum(deptNum).build();
		return sysDeptMapper.selectOne(model) != null;
	}

	@Override
    public List<SysDeptModel> getDeptSelectList() {
    	return sysDeptMapper.getDeptSelectList();
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean insert(SysDept sysDept) {
		SysDeptModel insertObj = CopyDataUtil.copyObject(sysDept, SysDeptModel.class);
		insertObj.setId(null);
		return sysDeptMapper.insertSelective(insertObj) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean update(SysDept sysDept) {
		SysDeptModel updateObj = CopyDataUtil.copyObject(sysDept, SysDeptModel.class);
		return sysDeptMapper.updateByPrimaryKeySelective(updateObj) > 0;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean delete(Long id) {
		return sysDeptMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,
			rollbackFor = {RuntimeException.class, Exception.class})
	public Boolean batchRemove(String ids) {
		String[] str = ids.split(",");
		List<Long> list = Arrays.stream(str).map(Long::valueOf).collect(Collectors.toList());
		return sysDeptMapper.batchRemove(list) > 0;
	}

}