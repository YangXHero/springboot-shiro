package com.liuzw.springbootshiro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * TABLE_NAME:(t_sys_dept)
 *
 * @author liuzw
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="t_sys_dept")
public class SysDeptModel {


    /**
     * id
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;


    /**
     * 部门编码
	 */
    @Column(name = "dept_num")
	private String deptNum;


    /**
     * 上级部门ID，一级部门为0
	 */
    @Column(name = "parent_id")
	private Long parentId;


    /**
     * 部门名称
	 */
    @Column(name = "name")
	private String name;


    /**
     * 排序
	 */
    @Column(name = "order_num")
	private Integer orderNum;


    /**
     * 创建时间
	 */
    @Column(name = "create_time")
	private String createTime;

	

}