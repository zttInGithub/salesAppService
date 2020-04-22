package com.hrtp.salesAppService.service;

import java.util.HashMap;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hrtp.salesAppService.dao.StockDao;
import com.hrtp.salesAppService.entity.appEntity.LoginRespEntity;
import com.hrtp.salesAppService.entity.appEntity.RespEntity;
import com.hrtp.salesAppService.entity.appEntity.StockEntity;
import com.hrtp.system.costant.ReturnCode;

@Service
public class StockService {
	
	private static Logger logger = LoggerFactory.getLogger(StockService.class);
	
	@Autowired
	private StockDao stockDao;
	
	public RespEntity getStockInfo(StockEntity stockEntity) {
		if (StringUtils.isEmpty(stockEntity.getUnno())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
        if (StringUtils.isEmpty(stockEntity.getType())) {
            return new RespEntity(ReturnCode.FALT,"参数错误");
        }
		StockEntity respEntity = new StockEntity();
        RespEntity rs = new RespEntity();
        String sql ="select tertotal,useter,nouseter,jhnum,(tertotal-jhnum)nojhnum,fxnum,(tertotal-fxnum)nofxnum from( "+
        " select * from (select nvl((sum(useter)+sum(nouseter)),0)tertotal,nvl(sum(useter),0)useter,nvl(sum(nouseter),0)nouseter from ( "+
        " select (case when a.status=2 then 1 else 0 end)useter,(case when a.status<>2 then 1 else 0 end)nouseter from ( "+
        " select (case when (bt.sn like 'HYB1%' or bt.sn like 'HYB4%' or bt.sn like 'HYB5%' or bt.sn like 'HYB0%' "+
        " or bt.sn like 'HYB7%' or bt.sn like 'HYB8%' or bt.sn like 'AACA%' or bt.sn like 'BBCA%' or bt.sn like 'HYB2%' "+
        " or bt.sn like 'HYB3%')then 1 else 2 end)sn_type,bt.status from bill_terminalinfo bt where bt.unno in( "+
        " select UNNO from sys_unit start with unno =:UNNO and status = 1 connect by NOCYCLE prior unno = upper_unit) "+
        " )a where a.sn_type=:TYPE)),(select nvl(sum(s.cashbacknum),0)fxnum,nvl(sum(s.actcashbacknum),0)jhnum "+
        " from pg_unnocashback_sum s where s.unno=:UNNO) "+
        " )";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("UNNO", stockEntity.getUnno());
        param.put("TYPE", stockEntity.getType());

        return Optional.ofNullable(stockDao.querySingleRowByNativeSql(sql,param)).map(map -> {
            logger.info("库存查询成功");
            respEntity.setUnno(stockEntity.getUnno());
            respEntity.setType(stockEntity.getType());
            respEntity.setTerminalTotal((map.get("0")+""));
            respEntity.setUseTer(map.get("1")+"");
            respEntity.setNoUseTer(map.get("2")+"");
            respEntity.setActivaTer(map.get("3")+"");
            respEntity.setNoActivaTer(map.get("4")+"");
            respEntity.setBackTer(map.get("5")+"");
            respEntity.setNoBackTer(map.get("6")+"");
            rs.setReturnCode(ReturnCode.SUCCESS);
            rs.setReturnMsg("查询成功");
            rs.setReturnBody(respEntity);
            return rs;
        }).orElseGet(() -> {
            logger.info("库存查询失败");
            rs.setReturnMsg("查询失败");
            rs.setReturnCode(ReturnCode.FALT);
            return rs;
        });
    }
}
