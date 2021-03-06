package com.ezhiyang.sdk.core.excutor.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ezhiyang.sdk.core.excutor.AbstractExcuteHandler;
import com.ezhiyang.sdk.core.excutor.ret.QueryAccountDetailVo;
import com.ezhiyang.sdk.core.excutor.ret.QueryAccountDetailVo.EntityListVo;
/**
 * 
 * @author Theo Zhou
 *
 */
public final class QueryAccountDetailExcutor extends AbstractExcuteHandler<QueryAccountDetailVo,Map<String,Object>>{

  private static final long serialVersionUID = -4984012293112519874L;
  private static final String ENTITY_LIST = "entityList";
  
  private String companyCode;
  private Long taxAreaId;
  private Date queryStartDate;
  private Date queryEndDate;
  private Integer pageSize;
  private Integer pageNo;
  
  
  public String getCompanyCode() {
    return companyCode;
  }

  public QueryAccountDetailExcutor setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
    return this;
  }

  public Long getTaxAreaId() {
    return taxAreaId;
  }

  public QueryAccountDetailExcutor setTaxAreaId(Long taxAreaId) {
    this.taxAreaId = taxAreaId;
    return this;
  }

  public Date getQueryStartDate() {
    return queryStartDate;
  }

  public QueryAccountDetailExcutor setQueryStartDate(Date queryStartDate) {
    this.queryStartDate = queryStartDate;
    return this;
  }

  public Date getQueryEndDate() {
    return queryEndDate;
  }

  public QueryAccountDetailExcutor setQueryEndDate(Date queryEndDate) {
    this.queryEndDate = queryEndDate;
    return this;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public QueryAccountDetailExcutor setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public QueryAccountDetailExcutor setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
    return this;
  }

  @Override
  public String toString() {
    return "QueryAccountDetailExcutor [companyCode=" + companyCode + ", taxAreaId=" + taxAreaId + ", queryStartDate="
        + queryStartDate + ", queryEndDate=" + queryEndDate + ", pageSize=" + pageSize + ", pageNo=" + pageNo + "]";
  }

  @Override
  protected String getTypeCode() {
    return "ralph.queryAccountDetail";
  }
  
  @Override
  protected QueryAccountDetailVo wrapResponse(Map<String,Object> data) {
    QueryAccountDetailVo vo = new QueryAccountDetailVo();
    if(data != null) {
      vo.setPageSize(MapUtils.getInteger(data, "pageSize"))
      .setPageNo(MapUtils.getInteger(data, "pageNo"))
      .setPageCount(MapUtils.getInteger(data, "pageCount"))
      .setTotalCount(MapUtils.getLong(data, "pageCount"));
      if(data.get(ENTITY_LIST) != null) {
        List<Map<String,Object>> entityList = (List<Map<String,Object>>)data.get("entityList");
        for(Map<String,Object> entity: entityList) {
          EntityListVo entityVo = new EntityListVo();
          entityVo.setTransTime(entity.get("transTime") == null ? null : parseDate(entity.get("transTime").toString()))
          .setDcFlag(MapUtils.getInteger(entity, "dcFlag"))
          .setTransAmount(BigDecimal.valueOf(MapUtils.getDouble(
              entity, "transAmount", 0d)))
          .setAcctTotalBalance(BigDecimal.valueOf(MapUtils.getDouble(
              entity, "acctTotalBalance", 0d)))
          .setAcctBalance(BigDecimal.valueOf(MapUtils.getDouble(
              entity, "acctBalance", 0d)))
          .setAcctFreeze(BigDecimal.valueOf(MapUtils.getDouble(
              entity, "acctFreeze", 0d)))
          .setRemark(MapUtils.getString(entity, "remark"));
          vo.addEntityVo(entityVo);
        }
      }
    }
    return vo;
  }
  
  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static Date parseDate(String str) {
    try {
      return sdf.parse(str);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
