package com.fuhuitong.applychain.dao;



import com.fuhuitong.applychain.model.Charge;

import java.util.List;

public interface ChargeMapper {

    //查询费率
    public List<Charge> findById(String chargeId);

    //查询用户费率

    Charge findByMerId(String merId);

    //添加费率
    public int addCharge(Charge charge);

    //删除费率
    public void deleteCharge(String merId);

    //修改费率
    public void updateCharge(Charge charge);

}
