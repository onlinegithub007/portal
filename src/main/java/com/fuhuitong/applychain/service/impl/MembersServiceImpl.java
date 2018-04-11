package com.fuhuitong.applychain.service.impl;

import com.fuhuitong.applychain.dao.*;
import com.fuhuitong.applychain.model.*;
import com.fuhuitong.applychain.service.IMembersService;
import com.fuhuitong.applychain.utils.MyUUID;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

@Service
public class MembersServiceImpl extends BaseService implements IMembersService{

	@Resource
	private ClientLevelMapper clientLevelMapper;
	
	@Resource
	private MembersMapper membersMapper;
	
	@Resource
	private CardCouponMapper cardCouponMapper;
	
	@Resource
	private MemberCardCouponsMapper memberCardCouponsMapper;
	
	@Resource
	private DiscountItemsMapper discountItemsMapper;
	
	@Resource
	private DiscountsMapper discountsMapper;
	
	@Resource
	private GoodsInfoMapper goodsInfoMapper;
	
	@Resource
	private DiscountStoresMapper discountStoresMapper;
	
	@Override
	public String clientLevel(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/client_level";
	}

	@Override
	public String clientLevelData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		Integer clientType = (Integer) session.getAttribute("ClientLevelType");
		
		ClientLevel query = new ClientLevel();
		query.setMerId(this.merUser.getMerId());
		query.setLevelType(clientType);
		
		ArrayList<ClientLevel> clientLevels = this.clientLevelMapper.selectAll(query);
		if (clientLevels != null && !clientLevels.isEmpty())
		{
			return getTableData(clientLevels, "", clientLevels.size());
		}
		else
		{
			return getTableData(null, null, 0);
		}
	}

	@Override
	public String clientLevelInfo(HttpServletRequest request, HttpSession session, ClientLevel param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		session.removeAttribute("CurrentClientLevel");
		
		if (!StringUtils.isEmpty(param.getClientLevelId()))
		{
			ClientLevel currentLevel = this.clientLevelMapper.selectByPrimaryKey(param.getClientLevelId());
			if (currentLevel != null)
			{
				session.setAttribute("CurrentClientLevel", currentLevel);
			}
		}
		
		return "page/client_level_info";
	}

	@Override
	public String saveClientLevel(HttpServletRequest request, HttpSession session, ClientLevel param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(param.getLevelName()))
		{
			return getRetCodeWithJson(1, getMessage("saveClientLevel.levelNameNotNull"));
		}
		
		if (param.getLevelValue() == null)
		{
			return getRetCodeWithJson(1, getMessage("saveClientLevel.levelValueNotNull"));
		}
		
		Integer clientType = (Integer) session.getAttribute("ClientLevelType");
		
		if (StringUtils.isEmpty(param.getClientLevelId()))
		{
			param.setClientLevelId(UUID.randomUUID().toString());
			param.setMerId(this.merUser.getMerId());
			param.setLevelType(clientType);
			
			this.clientLevelMapper.insertSelective(param);
			
			return getRetCodeWithJson(0, getMessage("saveClientLevel.createSuccess"));
		}
		else
		{
			param.setMerId(this.merUser.getMerId());
			param.setLevelType(clientType);
			this.clientLevelMapper.updateByPrimaryKeySelective(param);
			
			session.removeAttribute("CurrentClientLevel");
			
			return getRetCodeWithJson(0, getMessage("saveClientLevel.updateSuccess"));
		}
	}

	@Override
	public String deleteClientLevel(HttpServletRequest request, HttpSession session, ClientLevel param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(param.getClientLevelId()))
		{
			this.clientLevelMapper.deleteByPrimaryKey(param.getClientLevelId());
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String memberPriceSelect(HttpServletRequest request, HttpSession session) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/member_price_select";
	}

	@Override
	public String members(HttpServletRequest request, HttpSession session, ClientLevel param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 查询会员级别
		ClientLevel query = new ClientLevel();
		query.setMerId(this.merUser.getMerId());
		query.setLevelType(3);
		
		ArrayList<ClientLevel> memberLevels = this.clientLevelMapper.selectAll(query);
		session.setAttribute("MemberClientLevel", memberLevels);
		
		session.removeAttribute("CurrMemberClientLevel");
		
		return "page/member/members";
	}

	@Override
	public String membersData(HttpServletRequest request, HttpSession session, Members param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, "", 0);
		}
		
		param.setMerId(this.merUser.getMerId());
		if (StringUtils.isEmpty(param.getMemberCode()))
		{
			param.setMemberCode(null);
		}
		
		Integer totalCount = this.membersMapper.selectCountByLevel(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<Members> memberData = this.membersMapper.selectByLevel(param);
			if (memberData != null && !memberData.isEmpty())
			{
				return getTableData(memberData, "", totalCount);
			}
		}
		
		return getTableData(null, "", 0);
	}

	@Override
	public String membersInfo(HttpServletRequest request, HttpSession session, Members param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		session.removeAttribute("CurrentMember");
		
		// 查询会员级别
		ClientLevel query = new ClientLevel();
		query.setMerId(this.merUser.getMerId());
		query.setLevelType(3);
		
		ArrayList<ClientLevel> memberLevels = this.clientLevelMapper.selectAll(query);
		session.setAttribute("CurrMemberClientLevel", memberLevels);
		
		if (param.getMemberId() != null)
		{
			Members member = this.membersMapper.selectByPrimaryKey(param.getMemberId());
			if (member != null)
			{
				for (ClientLevel level : memberLevels)
				{
					if (member.getClientLevelId() != null && member.getClientLevelId().equalsIgnoreCase(level.getClientLevelId()))
					{
						level.setSelected(" selected ");
					}
					else
					{
						level.setSelected(" ");
					}
				}
				
				session.setAttribute("CurrentMember", member);
			}
		}
		
		return "page/member/member_info";
	}

	@Override
	public String membersSaveData(HttpServletRequest request, HttpSession session, Members param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(param.getMemberCode()))
		{
			return getRetCodeWithJson(1, getMessage("membersSaveData.memberCodeNotNull"));
		}
		
		if (StringUtils.isEmpty(param.getMemberName()))
		{
			return getRetCodeWithJson(1, getMessage("membersSaveData.memberNameNotNull"));
		}
		
		if (StringUtils.isEmpty(param.getClientLevelId()))
		{
			return getRetCodeWithJson(1, getMessage("membersSaveData.clientLevelIdNotNull"));
		}
		
		if (param.getMemberId() == null)
		{
			// 新建
			param.setMerId(this.merUser.getMerId());

			// 判断手机号是否存在
			Members existed = this.membersMapper.selectByCode(param);
			if (existed != null)
			{
				return getRetCodeWithJson(1, getMessage("membersSaveData.memberExists"));
			}
			
			// 默认关注当前门店
			param.setStoreId(this.merUser.getMerGroupId());
			param.setCreateDate(new Date());
			param.setAttentionDate(new Date());
			
			this.membersMapper.insertSelective(param);
			return getRetCodeWithJson(0, getMessage("membersSaveData.saveSuccess"));
		}
		else
		{
			// 修改
			this.membersMapper.updateByPrimaryKeySelective(param);
			return getRetCodeWithJson(0, getMessage("membersSaveData.modifySuccess"));
		}
	}

	@Override
	public String membersDeleteData(HttpServletRequest request, HttpSession session, Members param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (param.getMemberId() != null)
		{
			this.membersMapper.deleteByPrimaryKey(param.getMemberId());
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String cardCoupons(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 查询会员级别
		ClientLevel query = new ClientLevel();
		query.setMerId(this.merUser.getMerId());
		query.setLevelType(3);
		
		ArrayList<ClientLevel> memberLevels = this.clientLevelMapper.selectAll(query);
		session.setAttribute("MemberClientLevel", memberLevels);
		
		return "page/member/card_coupons";
	}

	@Override
	public String cardCouponsData(HttpServletRequest request, HttpSession session, CardCoupon param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		param.setMerId(this.merUser.getMerId());
		if (StringUtils.isEmpty(param.getCreateDateText()))
		{
			param.setCreateDateText(null);
		}
		
		Integer totalCount = this.cardCouponMapper.selectAllCount(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<CardCoupon> cardCoupons = this.cardCouponMapper.selectAll(param);
			if (cardCoupons != null && !cardCoupons.isEmpty())
			{
				return getTableData(cardCoupons, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String cardCouponInfo(HttpServletRequest request, HttpSession session, CardCoupon param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/member/card_coupon_info";
	}

	@Override
	public String cardCouponsSaveData(HttpServletRequest request, HttpSession session, CardCoupon param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(param.getMemberLevelId()))
		{
			return getRetCodeWithJson(1, getMessage("cardCouponsSaveData.memberLevelIdNotNull"));
		}
		
		if (StringUtils.isEmpty(param.getCreateDateText()))
		{
			return getRetCodeWithJson(1, getMessage("cardCouponsSaveData.createDateTextNotNull"));
		}
		
		if (StringUtils.isEmpty(param.getExpiredDateText()))
		{
			return getRetCodeWithJson(1, getMessage("cardCouponsSaveData.expiredDateTextNotNull"));
		}
		
		if (param.getTotalCount() == null || param.getTotalCount() == 0)
		{
			return getRetCodeWithJson(1, getMessage("cardCouponsSaveData.totalCountNotNull"));
		}
		
		if (param.getCardCouponId() == null)
		{
			// 新增
			param.setMerId(this.merUser.getMerId());
			param.setCardCouponId(MyUUID.randomUUID());
			param.setValidStatus(0);
			param.setUnenabledCount(param.getTotalCount());
			
			try {
				param.setCreateDate(DateUtils.parseDate(param.getCreateDateText(), new String[]{"yyyy-MM-dd"}));
				param.setExpiredDate(DateUtils.parseDate(param.getExpiredDateText(), new String[]{"yyyy-MM-dd"}));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.cardCouponMapper.insertSelective(param);
			
			// 批量生成会员卡券
			int totalCount = param.getTotalCount();
			logger.info("Create " + totalCount + " member card coupons.");
			
			ArrayList<MemberCardCoupons> memberCardCoupons = new ArrayList<MemberCardCoupons>();
			
			for (int i = 0; i < totalCount; i++)
			{
				// 每50个插入库一次
				MemberCardCoupons coupon = new MemberCardCoupons();
				coupon.setCardCouponId(param.getCardCouponId());
				coupon.setCouponAmount(param.getCouponAmount());
				coupon.setCouponBalance(param.getCouponAmount());
				coupon.setCreateDate(param.getCreateDate());
				coupon.setExpiredDate(param.getExpiredDate());
				coupon.setMerId(this.merUser.getMerId());
				
				memberCardCoupons.add(coupon);
				
				if (memberCardCoupons.size() >= 50)
				{
					logger.info("Create " + memberCardCoupons.size() + "  member card coupons...");
					this.memberCardCouponsMapper.insertBatch(memberCardCoupons);
					memberCardCoupons.clear();
				}
			}
			
			if (memberCardCoupons.size() > 0)
			{
				logger.info("Create " + memberCardCoupons.size() + "  member card coupons...");
				this.memberCardCouponsMapper.insertBatch(memberCardCoupons);
				memberCardCoupons.clear();
			}
		}
		
		return getRetCodeWithJson(0, getMessage("cardCouponsSaveData.saveSuccess"));
	}

	@Override
	public String cardCouponsDeleteData(HttpServletRequest request, HttpSession session, CardCoupon param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(param.getCardCouponId()))
		{
			// 先删除未激活的会员卡券明细
			this.memberCardCouponsMapper.deleteByCardCouponId(param.getCardCouponId());
			
			// 再删除卡券
			this.cardCouponMapper.deleteByPrimaryKey(param.getCardCouponId());
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String cardCouponsChangeValidStatus(HttpServletRequest request, HttpSession session, CardCoupon param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(param.getCardCouponId()))
		{
			MemberCardCoupons memberCard = new MemberCardCoupons();
			memberCard.setCardCouponId(param.getCardCouponId());
			memberCard.setFinalStatus(param.getValidStatus());
			
			// 改变会员卡券激活状态
			this.cardCouponMapper.updateValidStatus(param);
			
			if (param.getValidStatus() == 1)
			{
				return getRetCodeWithJson(0, getMessage("cardCouponsChangeValidStatus.enableSuccess"));
			}
			else
			{
				return getRetCodeWithJson(0, getMessage("cardCouponsChangeValidStatus.disableSuccess"));
			}
		}
		
		return getRetCodeWithJson(1, getMessage("cardCouponsChangeValidStatus.changeFailed"));
	}

	@Override
	public String memberCardCoupons(HttpServletRequest request, HttpSession session, CardCoupon param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		session.setAttribute("CurrentCardCouponId", param.getCardCouponId());
		
		return "page/member/member_card_coupons";
	}

	@Override
	public String memberCardCouponsData(HttpServletRequest request, HttpSession session, MemberCardCoupons param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		String cardCouponId = (String) session.getAttribute("CurrentCardCouponId");
		
		if (!StringUtils.isEmpty(cardCouponId))
		{
			param.setCardCouponId(cardCouponId);
			Integer totalCount = this.memberCardCouponsMapper.selectCountByCardCoupon(param);
			if (totalCount != null && totalCount > 0)
			{
				ArrayList<MemberCardCoupons> memberCards = this.memberCardCouponsMapper.selectByCardCoupon(param);
				if (memberCards != null && !memberCards.isEmpty())
				{
					return getTableData(memberCards, null, totalCount);
				}
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String discountItems(HttpServletRequest request, HttpSession session, Discounts param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		Discounts currentDiscounts = this.discountsMapper.selectByPrimaryKey(param.getDiscountId());
		session.setAttribute("CurrentDiscounts", currentDiscounts);
		
		if (param.getDiscountType() == 0)
		{
			return "page/member/discount_items1";
		}
		else if (param.getDiscountType() == 1)
		{
			return "page/member/discount_items2";
		}
		else if (param.getDiscountType() == 2)
		{
			return "page/member/discount_items3";
		}
		else if (param.getDiscountType() == 3)
		{
			return "page/member/discount_items4";
		}
		else if (param.getDiscountType() == 4)
		{
			return "page/member/discount_items5";
		}
		else if (param.getDiscountType() == 5)
		{
			return "page/member/discount_items6";
		}
		
		return "page/member/discount_items1";
	}

	@Override
	public String discountItems1Data(HttpServletRequest request, HttpSession session, DiscountItems param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		
		param.setDiscountId(currentDiscounts.getDiscountId());
		Integer totalCount = this.discountItemsMapper.selectAllCountGoodsDiscount(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<DiscountItems> discountItems = this.discountItemsMapper.selectAllGoodsDiscount(param);
			if (discountItems != null && !discountItems.isEmpty())
			{
				return getTableData(discountItems, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}
	
	@Override
	public String discountItems2Data(HttpServletRequest request, HttpSession session, DiscountItems param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		
		param.setDiscountId(currentDiscounts.getDiscountId());
		Integer totalCount = this.discountItemsMapper.selectAllCountOrderDiscount(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<DiscountItems> discountItems = this.discountItemsMapper.selectAllOrderDiscount(param);
			if (discountItems != null && !discountItems.isEmpty())
			{
				return getTableData(discountItems, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}
	
	@Override
	public String discountItems5Data(HttpServletRequest request, HttpSession session, DiscountItems param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		
		param.setDiscountId(currentDiscounts.getDiscountId());
		Integer totalCount = this.discountItemsMapper.selectAllCountGroupDiscount(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<DiscountItems> discountItems = this.discountItemsMapper.selectAllGroupDiscount(param);
			if (discountItems != null && !discountItems.isEmpty())
			{
				return getTableData(discountItems, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}
	
	@Override
	public String discountItemsData(HttpServletRequest request, HttpSession session, DiscountItems param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		
		param.setDiscountId(currentDiscounts.getDiscountId());
		Integer totalCount = this.discountItemsMapper.selectAllCountByDiscountType(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<DiscountItems> discountItems = this.discountItemsMapper.selectAllByDiscountType(param);
			if (discountItems != null && !discountItems.isEmpty())
			{
				return getTableData(discountItems, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String discountItemsInfo1(HttpServletRequest request, HttpSession session, DiscountItems param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		return "page/member/discount_items_info1";
	}

	@Override
	public String queryGoodsInfo(HttpServletRequest request, HttpSession session, String goodsCode) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (!StringUtils.isEmpty(goodsCode))
		{
			GoodsInfo param = new GoodsInfo();
			param.setMerId(this.merUser.getMerId());
			param.setGoodsCode(goodsCode);
			
			GoodsInfo goodsInfo = this.goodsInfoMapper.selectByGoodsCode(param);
			if (goodsInfo != null)
			{
				Hashtable<String, Object> values = new Hashtable<>();
				values.put("goodsName", goodsInfo.getGoodsName());
				values.put("goodsPrice", goodsInfo.getGoodsPrice());
				values.put("goodsId", goodsInfo.getGoodsId());
				
				return getRetCodeWithJson(0, values);
			}
		}
		
		return getRetCodeWithJson(1);
	}

	@Override
	public String discounts(HttpServletRequest request, HttpSession session, Discounts param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		// 查询会员级别
		ClientLevel query = new ClientLevel();
		query.setMerId(this.merUser.getMerId());
		query.setLevelType(3);
		
		ArrayList<ClientLevel> memberLevels = this.clientLevelMapper.selectAll(query);
		session.setAttribute("MemberClientLevel", memberLevels);
		
		session.removeAttribute("DiscountType");
		
		Discounts entryDiscounts = new Discounts();
		if (param.getDiscountType() == null || param.getDiscountType() == 0)
		{
			entryDiscounts.setDiscountType(0);
		}
		else
		{
			entryDiscounts.setDiscountType(param.getDiscountType());
		}
		
		if (param.getDiscountType() == null || param.getDiscountType() == 0)
		{
			entryDiscounts.setDiscountTypeName("商品降价促销");
			session.setAttribute("DiscountType", entryDiscounts);
			
			return "page/member/discounts1";
		}
		else if (param.getDiscountType() == 1)
		{
			entryDiscounts.setDiscountTypeName("商品整额折扣");
			session.setAttribute("DiscountType", entryDiscounts);
			
			return "page/member/discounts2";
		}
		else if (param.getDiscountType() == 2)
		{
			entryDiscounts.setDiscountTypeName("商品数量折扣");
			session.setAttribute("DiscountType", entryDiscounts);
			return "page/member/discounts3";
		}
		else if (param.getDiscountType() == 3)
		{
			entryDiscounts.setDiscountTypeName("商品买赠促销");
			session.setAttribute("DiscountType", entryDiscounts);
			return "page/member/discounts4";
		}
		else if (param.getDiscountType() == 4)
		{
			entryDiscounts.setDiscountTypeName("商品捆绑销售");
			session.setAttribute("DiscountType", entryDiscounts);
			return "page/member/discounts5";
		}
		else if (param.getDiscountType() == 5)
		{
			entryDiscounts.setDiscountTypeName("商品满减促销");
			session.setAttribute("DiscountType", entryDiscounts);
			return "page/member/discounts6";
		}
		
		return "page/member/discounts1";
	}

	@Override
	public String discountsData(HttpServletRequest request, HttpSession session, Discounts param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		logger.info("memberLevelId = " + param.getMemberLevelId());
		
		if (param.getDiscountType() == null || param.getDiscountType() == 0)
		{
			param.setDiscountType(0);
		}
		
		logger.info("discountType = " + param.getDiscountType());
		if (StringUtils.isEmpty(param.getDiscountType()))
		{
			param.setDiscountType(null);
		}
		
		logger.info("discountItemStatus = " + param.getDiscountItemStatus());
		logger.info("createDateText = " + param.getCreateDateText());
		
		if (StringUtils.isEmpty(param.getCreateDateText()))
		{
			param.setCreateDateText(null);
		}
		if (StringUtils.isEmpty(param.getMemberLevelId()))
		{
			param.setMemberLevelId(null);
		}
		
		param.setMerId(this.merUser.getMerId());
		Integer totalCount = this.discountsMapper.selectAllCountByDiscountType(param);
		if (totalCount != null && totalCount > 0)
		{
			ArrayList<Discounts> discountItems = this.discountsMapper.selectAllByDiscountType(param);
			if (discountItems != null && !discountItems.isEmpty())
			{
				return getTableData(discountItems, null, totalCount);
			}
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String discounts1Info(HttpServletRequest request, HttpSession session, Discounts param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		logger.info("DiscountType = " + param.getDiscountType());
		session.setAttribute("CurrDiscountTypeInForm", param.getDiscountType());
		
		if (!StringUtils.isEmpty(param.getDiscountId()))
		{
			// 修改
			Discounts discount = this.discountsMapper.selectByPrimaryKey(param.getDiscountId());
			if (discount != null)
			{
				session.setAttribute("CurrentDiscount", discount);
			}
		}
		
		return "page/member/discounts1_info";
	}

	@Override
	public String discounts1SaveInfo(HttpServletRequest request, HttpSession session, Discounts param) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(param.getCreateDateText()))
		{
			return getRetCodeWithJson(1, getMessage("discounts1SaveInfo.createDateTextNotNull"));
		}
		
		if (StringUtils.isEmpty(param.getEndDateText()))
		{
			return getRetCodeWithJson(1, getMessage("discounts1SaveInfo.expiredDateTextNotNull"));
		}
		
		if (StringUtils.isEmpty(param.getDiscountTypeName()))
		{
			return getRetCodeWithJson(1, getMessage("discounts1SaveInfo.discountTypeNameNotNull"));
		}
		
		logger.info("discountType = " + param.getDiscountType());
		
		if (StringUtils.isEmpty(param.getDiscountId()))
		{
			// 新增
			param.setMerId(this.merUser.getMerId());
			param.setDiscountId(MyUUID.randomUUID());
			if (!StringUtils.isEmpty(param.getMemberLevelId()))
			{
				// 会员
				param.setDiscountUserType(1);
			}
			
			try {
				param.setCreateDate(DateUtils.parseDate(param.getCreateDateText(), new String[]{"yyyy-MM-dd HH:mm:ss"}));
				param.setEndDate(DateUtils.parseDate(param.getEndDateText(), new String[]{"yyyy-MM-dd HH:mm:ss"}));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			this.discountsMapper.insertSelective(param);
			
			return getRetCodeWithJson(0, getMessage("discounts1SaveInfo.saveSuccess"));
		}
		else
		{
			// 修改
			this.discountsMapper.updateByPrimaryKeySelective(param);
			
			return getRetCodeWithJson(0, getMessage("discounts1SaveInfo.updateSuccess"));
		}
	}

	@Override
	public String saveGoodsDiscount(HttpServletRequest request, HttpSession session, DiscountItems item) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(item.getGoodsId()))
		{
			return getRetCodeWithJson(0, getMessage("saveGoodsDiscount.goodsIdNotNull"));
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		item.setDiscountId(currentDiscounts.getDiscountId());
		
		// 检查是否重复录入
		if (this.discountItemsMapper.selectByDiscountId(item) != null)
		{
			return getRetCodeWithJson(1, getMessage("saveGoodsDiscount.goodsIdExist"));
		}
		
		item.setMerId(this.merUser.getMerId());
		item.setCreateDate(new Date());
		
		this.discountItemsMapper.insertSelective(item);
		
		return getRetCodeWithJson(0, getMessage("saveGoodsDiscount.saveSuccess"));
	}

	@Override
	public String changeDiscountStatus(HttpServletRequest request, HttpSession session, Discounts discount) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(discount.getDiscountId()))
		{
			return getRetCodeWithJson(1, getMessage("changeDiscountStatus.Failed"));
		}
		
		this.discountsMapper.changeDiscountActive(discount);
		
		if (discount.getDiscountItemStatus() == 1)
		{
			return getRetCodeWithJson(0, getMessage("changeDiscountStatus.EnableSuccess"));
		}
		else
		{
			return getRetCodeWithJson(0, getMessage("changeDiscountStatus.DisableSuccess"));
		}
	}

	@Override
	public String deleteDiscounts(HttpServletRequest request, HttpSession session, Discounts discount) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(discount.getDiscountId()))
		{
			return getRetCodeWithJson(1, getMessage("changeDiscountStatus.Failed"));
		}
		
		// 先删除 子项
		this.discountItemsMapper.deleteByDiscountId(discount.getDiscountId());
		
		// 删除参与的门店信息
		this.discountStoresMapper.deleteByDiscountId(discount.getDiscountId());
		
		// 再删除促销活动
		this.discountsMapper.deleteByPrimaryKey(discount.getDiscountId());
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String discountStores(HttpServletRequest request, HttpSession session, Discounts discount) {
		if (!this.merUserHasAccessRight(request, session) || StringUtils.isEmpty(discount.getDiscountId())) 
		{
			logger.info("Current user was not logged in.");
			return "page/relogin";
		}
		
		Discounts currentDiscounts = this.discountsMapper.selectByPrimaryKey(discount.getDiscountId());
		session.setAttribute("CurrentDiscounts", currentDiscounts);
		
		return "page/member/discount_stores";
	}

	@Override
	public String discountStoresData(HttpServletRequest request, HttpSession session) {
		
		if (!this.merUserHasAccessRight(request, session)) 
		{
			logger.info("Current user was not logged in.");
			return getTableData(null, null, 0);
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		ArrayList<DiscountStores> stores = discountStoresMapper.selectByDiscountId(currentDiscounts.getDiscountId());
		if (stores != null && !stores.isEmpty())
		{
			return getTableData(stores, null, stores.size());
		}
		
		return getTableData(null, null, 0);
	}

	@Override
	public String discountSaveStores(HttpServletRequest request, HttpSession session, Discounts discount) {
		
		if (!this.merUserHasAccessRight(request, session)) 
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		
		if (!StringUtils.isEmpty(discount.getStoreIds()))
		{
			// 更新之前先删除
			this.discountStoresMapper.deleteByDiscountId(currentDiscounts.getDiscountId());
			
			String[] storeIds = discount.getStoreIds().split(",");
			
			for (String stroreId : storeIds)
			{
				DiscountStores store = new DiscountStores();
				store.setDiscountId(currentDiscounts.getDiscountId());
				store.setStoreId(stroreId);
				
				this.discountStoresMapper.insertSelective(store);
			}
			
			return getRetCodeWithJson(0, getMessage("discountSaveStores.Success"));
		}
		
		return getRetCodeWithJson(1, getMessage("discountSaveStores.Failed"));
	}

	@Override
	public String discountDeleteStores(HttpServletRequest request, HttpSession session, DiscountStores discountStore) {
		
		if (!this.merUserHasAccessRight(request, session)) 
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (discountStore.getDiscountStoreId() != null)
		{
			this.discountStoresMapper.deleteByPrimaryKey(discountStore.getDiscountStoreId());
		}
		
		return getRetCodeWithJson(0);
	}

	@Override
	public String discountItemsDelete(HttpServletRequest request, HttpSession session, DiscountItems param) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (param.getDiscountItemId() != null)
		{
			logger.info("delete DiscountItems " + param.getDiscountItemId());
			this.discountItemsMapper.deleteByPrimaryKey(param.getDiscountItemId());
			
			return getRetCodeWithJson(0, getMessage("discountItemsDelete.deleteSuccess"));
		}
		
		return getRetCodeWithJson(1, getMessage("discountItemsDelete.deleteFaulre"));
	}

	@Override
	public String saveOrderDiscount(HttpServletRequest request, HttpSession session, DiscountItems item) {
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		item.setDiscountId(currentDiscounts.getDiscountId());
		
		item.setMerId(this.merUser.getMerId());
		item.setCreateDate(new Date());
		
		this.discountItemsMapper.insertSelective(item);
		
		return getRetCodeWithJson(0, getMessage("saveOrderDiscount.saveSuccess"));
	}

	@Override
	public String saveGroupDiscount(HttpServletRequest request, HttpSession session, DiscountItems item) {
		
		if (!this.merUserHasAccessRight(request, session))
		{
			logger.info("Current user was not logged in.");
			return getRetCodeWithJson(1, getMessage("merUserNotLogin"));
		}
		
		if (StringUtils.isEmpty(item.getGroupGoodsIds()))
		{
			logger.info("group goods is null");
			return getRetCodeWithJson(1, getMessage("saveGroupDiscount.GroupGoodsNotNull"));
		}
		
		if (item.getGroupDiscountType() == 1)
		{
			if (item.getGroupGoodsIds().contains(item.getGroupPresentGoodsId()))
			{
				logger.info("present goods is contained in the grouped goods.");
				return getRetCodeWithJson(1, getMessage("saveGroupDiscount.PresentGoodsNotSameWithGroup"));
			}
		}
			
		// 解析捆绑的商品，排除重复
		String[] goodsIds = item.getGroupGoodsIds().split(",");
		String goodsIdsStr = "";
		int count = 0;
		for (String goodsId : goodsIds)
		{
			if (!goodsIdsStr.contains(goodsId))
			{
				goodsIdsStr += goodsId + ",";
				count++;
			}
		}
		
		logger.info("参与捆绑销售的商品数量 : " + count);
		item.setGroupGoodsIds(goodsIdsStr);
		
		Discounts currentDiscounts = (Discounts) session.getAttribute("CurrentDiscounts");
		item.setDiscountId(currentDiscounts.getDiscountId());
		
		item.setMerId(this.merUser.getMerId());
		item.setCreateDate(new Date());
		
		this.discountItemsMapper.insertSelective(item);
		
		return getRetCodeWithJson(0, getMessage("saveGroupDiscount.saveSuccess"));
	}

	
}
