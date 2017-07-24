package net.ewide.platform.modules.sys.service;


import java.util.List;

import net.ewide.platform.common.service.CrudService;
import net.ewide.platform.modules.sys.dao.UserDao;
import net.ewide.platform.modules.sys.dao.UserOfficeDao;
import net.ewide.platform.modules.sys.entity.User;
import net.ewide.platform.modules.sys.entity.UserOffice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户机构Service
 * @author TianChong
 * @version 2016年4月11日
 */
@Service
@Transactional(readOnly = true)
public class UserOfficeService extends CrudService<UserOfficeDao, UserOffice> {
}
