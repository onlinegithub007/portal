package com.fuhuitong.applychain.dao;

import com.fuhuitong.applychain.model.DiscountItems;

import java.util.ArrayList;

public interface DiscountItemsMapper {
    int deleteByPrimaryKey(Integer discountItemId);
    
    int deleteByDiscountId(String discountId);

    int insert(DiscountItems record);

    int insertSelective(DiscountItems record);

    DiscountItems selectByPrimaryKey(Integer discountItemId);
    
    DiscountItems selectByDiscountId(DiscountItems record);

    int updateByPrimaryKeySelective(DiscountItems record);

    int updateByPrimaryKey(DiscountItems record);
    
    // 查询商品降价折扣数据
    ArrayList<DiscountItems> selectAllGoodsDiscount(DiscountItems record);
    // 查询商品降价折扣数据的数据条数
    Integer selectAllCountGoodsDiscount(DiscountItems record);
    
    // 查询订单整额折扣收
    ArrayList<DiscountItems> selectAllOrderDiscount(DiscountItems record);
    Integer selectAllCountOrderDiscount(DiscountItems record);
    
    // 捆绑销售数据
    ArrayList<DiscountItems> selectAllGroupDiscount(DiscountItems record);
    Integer selectAllCountGroupDiscount(DiscountItems record);
    
    ArrayList<DiscountItems> selectAllByDiscountType(DiscountItems record);
    
    Integer selectAllCountByDiscountType(DiscountItems record);
    
    // 查询可用的优惠活动
    ArrayList<DiscountItems> selectActiveDiscounts(DiscountItems record);
    
}