package com.hrtp.system.common;

import com.hrtp.salesAppService.exception.AppException;
import org.hibernate.transform.ResultTransformer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IGenericDao
 * @param <T>
 * @desc DAO顶级接口
 */
public interface IGenericDao<T> {

    public boolean insert(T obj) throws AppException;

    public boolean update(T obj) throws AppException;

    public boolean delete(T obj) throws AppException;

    /**
     * @param clz
     * @param id
     * @return
     */
    public T get(Class<T> clz, Serializable id) throws AppException;

    public String querySingleValueByNativeSql(String sql)
            throws AppException;

    public Map<String, String> querySingleRowByNativeSql(String sql)
            throws AppException;

    public List<Object> queryByNativeSql(String sql)
            throws AppException;

    public String querySingleValueByStoredProcedures(
            String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException;

    public Map<String, String> querySingleRowByStoredProcedures(
            String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException;

    public Connection getConnection() throws AppException;

    public void releaseConnection(Connection conn, Statement stmt)
            throws AppException;

    public String querySingleValueByNativeSqlSimple(String sql)
            throws AppException;

    public Map<String, String> querySingleRowByNativeSqlNativeConnection(
            String sql) throws AppException;

    public List<String> querySingleColumnByNativeSql(String sql)
            throws AppException;

    public int executeSql(String sql) throws AppException;

    /**
     * @param sql
     * @param clazz
     * @return
     */
    public List<T> query(String sql, Class clazz) throws AppException;

    // 防止SQL注入

    public String querySingleValueByNativeSql(String sql,
                                              HashMap<String, Object> paramMap) throws AppException;

    public String querySingleValueByNativeSqlSimple(String sql,
                                                    HashMap<String, Object> paramMap) throws AppException;

    public Map<String, String> querySingleRowByNativeSql(String sql,
                                                         HashMap<String, Object> map) throws AppException;

    public List<Object[]> queryByNativeSqlWithPaging(String sql,
                                                     int startLineNum, int endLineNum, HashMap<String, Object> paramMap)
            throws AppException;

    public List<Object> queryByNativeSql(String sql,
                                         HashMap<String, Object> paramMap) throws AppException;

    /**
     * 说明：使用存储过程查询数据，参数都可以是字符类型，但如果是参数本来应该是日期或整形时，内容格式不能不匹配
     *
     * @param storedProceduresStatemen 存储过程调用语句
     * @param paramMap                 存储过程参数
     */
    public List<Object> queryByStoredProcedures(
            String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException;

    public int executeSql(String sql, HashMap<String, Object> paramMap)
            throws AppException;

    public List<T> queryByNativeSqlWithPaging(String sql,
                                              int startLineNum, int endLineNum, HashMap<String, Object> paramMap,
											  Class clazz)
            throws AppException;

    String executeStoredProcedures(String storedProceduresStatemen,
                                   HashMap<String, Object> paramMap) throws AppException;


    String executeStoredProcedure(String storedProceduresName)
            throws AppException;

    Map<String, String> querySingleRowByStoredProceduresForOracle(
            String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException;

    Map<String, String> queryByStoredProceduresForOracle(
            String storedProceduresStatement, HashMap<String, Object> paramMap)
            throws AppException;

    List<String[]> queryByStoredProceduresForOracle2(
            String storedProceduresStatement, HashMap<String, Object> paramMap)
            throws AppException;

    String executeStoredProcedureUpdate(String storedProceduresName,
                                        HashMap<String, Object> paramMap) throws AppException;

    List<Object> queryByNativeSqlWithPaging(String sql, int startLineNum, int endLineNum,
                                            HashMap<String, Object> paramMap, ResultTransformer transformer);

    boolean insert2(T t) throws AppException;

    Date querySqlDtae();

    public List<Object[]> queryByNativeSqlWithPageAndRows(String sql, Integer page, Integer rows, HashMap<String,
            Object>param);
}
