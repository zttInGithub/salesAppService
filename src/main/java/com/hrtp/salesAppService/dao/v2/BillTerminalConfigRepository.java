package com.hrtp.salesAppService.dao.v2;

import com.hrtp.salesAppService.entity.ormEntity.v2.BillTerminalConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BillTerminalConfigRepository extends JpaRepository<BillTerminalConfigModel, Integer> {

    /**
     * 下发/回拨 终端
     * 更新终端表信息
     * @param subUnno
     * @param unno
     * @param start
     * @param end
     * @return
     */
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update bill_terminalinfo b set b.unno=?1,b.allotconfirmdate=sysdate,b.maintainuid=?2 where b.usedconfirmdate is null and b.unno=?3 and b.sn>=?4 and b.sn<=?5")
    Integer updateSelfBillTerminalInfoByStartAndEnd(String subUnno,Integer userId, String unno, String start, String end);

    /**
     * 按机构查询机构名称
     * @param unno
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT t.UN_NAME FROM SYS_UNIT t where t.UNNO=?")
    String findSelfUnnoNameByUnno(String unno);

    /**
     * 终端可分配的数量
     * @param unno
     * @param start
     * @param end
     * @return
     */
    @Query(nativeQuery = true,value = "select count(1) from bill_terminalinfo b where b.usedconfirmdate is null and b.unno=?1 and b.sn>=?2 and b.sn<=?3")
    Integer findSelfBillTerminalCount(String unno, String start, String end);

    /**
     * 区间的设备是否为同一机构号
     * @param start
     * @param end
     * @return
     */
    @Query(nativeQuery = true,value = "select distinct(b.unno) unno from bill_terminalinfo b where b.sn>=?1 and b.sn<=?2")
    List<String> findSelfBillTerminalUnnoList(String start, String end);

    /**
     * 区间的设备活动列表
     * @param start
     * @param end
     * @return
     */
    @Query(nativeQuery = true,value = "select distinct(b.rebatetype) from bill_terminalinfo b where b.sn>=?1 and b.sn<=?2")
    List<String> findSelfBillTerminalRebateTypeList(String start, String end);

    /**
     * 查询当前机构的下发/回拨记录
     * @param unno
     * @param type
     * @return
     */
    List<BillTerminalConfigModel> findAllByUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(String unno, Integer type);

    /**
     * findAllBySubUnnoEqualsAndTypeEquals
     * @param subUnno
     * @param type
     * @return
     */
    List<BillTerminalConfigModel> findAllBySubUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(String subUnno, Integer type);

    /**
     * findAllByUpperUnnoEqualsAndTypeEquals
     * @param upperUnno
     * @param type
     * @return
     */
    List<BillTerminalConfigModel> findAllByUpperUnnoEqualsAndTypeEqualsOrderByCreateDateDesc(String upperUnno, Integer type);
}
