package com.mf.center.system.rolemenu;

import com.mf.common.domain.rolemenu.RoleMenu;
import com.mf.common.domain.rolemenu.RoleMenuCond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author lijianan
 * @email 4141914@gmail.com
 * @date 2018-04-10 22:16:59
 */
@RestController
@RequestMapping("api/roleMenu")
public class RoleMenuAction {

	private static Logger logger = LoggerFactory.getLogger(RoleMenuAction.class);

	@Autowired
	private RoleMenuBus bus; // 角色与菜单对应关系Service

	/**
	 * @方法说明:按条件查询列表页面
	 **/
	@RequestMapping("/queryList")
	public List<RoleMenu> queryList(@RequestBody RoleMenuCond cond) {
		return bus.queryList(cond);
	}
}