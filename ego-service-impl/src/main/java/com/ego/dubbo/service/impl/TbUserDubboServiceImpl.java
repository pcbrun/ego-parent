package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/10
 * @Description:com.ego.dubbo.service.impl
 * @version:1.0
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {
    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selByUser(TbUser user) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        List<TbUser> listUser = tbUserMapper.selectByExample(example);
        if (listUser != null && listUser.size()>0) {
            return listUser.get(0);
        }
        return null;
    }

    @Override
    public boolean selByUsername(String username) {
        TbUserExample example=new TbUserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<TbUser> listUser = tbUserMapper.selectByExample(example);
        if(listUser!=null&&listUser.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean selByPhone(String phone) {
        TbUserExample example=new TbUserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<TbUser> listUser = tbUserMapper.selectByExample(example);
        if(listUser!=null&&listUser.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public int insUser(TbUser tbUser) {
        int result = tbUserMapper.insertSelective(tbUser);
        if(result>0){
            return 1;
        }
        return 0;
    }
}
