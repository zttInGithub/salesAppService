package com.hrtp.system.common;

import com.hrtp.salesAppService.exception.AppException;
import org.hibernate.SQLQuery;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 说明：通用的JAP操作基类，可以继承此类实现基本的CRUD功能，不能完成的功能，可在子类中实现�?建议复杂查询都用原生SQL实现
 * <p>
 * 修改说明 修改 日期 修改功能
 * <p>
 * 完善原生SQL的功能和添加了分页
 */
@Repository("genericJpaDao")
public class GenericJpaDao<T> implements IGenericDao<T> {

    private Logger logger = LoggerFactory.getLogger(GenericJpaDao.class);

    /**
     * @param
     * @Description 获取实体管理器
     */
    public EntityManager getEntityManager() {
        return em;
    }

    @PersistenceContext
    protected EntityManager em;


    /**
     * 说明：对象插入到数据库持久化
     *
     * @param t 要持久化的对象
     * @return 返回是否持久化成功的标识
     */
    @Override
    public boolean insert(T t) throws AppException {
        // em.persist(t);
        try {
            em.persist(t);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return true;
    }

    @Override
    public boolean insert2(T t) throws AppException {
        // em.persist(t);
        try {
            em.persist(t);
            em.flush();
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return true;
    }


    /**
     * 说明：对象删除
     *
     * @param t 要删除的对象
     * @return 删除是否成功的标识
     */
    @Override
    public boolean delete(T t) throws AppException {
        // em.remove(t);
        try {
            em.remove(t);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return true;
    }

    /**
     * 说明：按对象ID查找对象
     *
     * @param clz 对象的类
     * @param id  对象主键ID
     * @return 查找到的对象
     */
    @Override
    public T get(Class<T> clz, Serializable id) throws AppException {
        T t;
        try {
            t = em.find(clz, id);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return t;
    }

    /**
     * 说明：按原生sql查询，返回对象
     *
     * @param sql   原生sql
     * @param clazz 该sql对应的对象
     * @return 对象列表
     */
    @Override
    public List<T> query(String sql, Class clazz) throws AppException {

        logger.info("input sql  = " + sql);
        List<T> ls = new ArrayList<T>();
        Query query;
        try {
            query = em.createNativeQuery(sql, clazz);
            ls = query.getResultList();
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return ls;

    }

    /**
     * 说明：对象更新
     */
    @Override
    public boolean update(T obj) throws AppException {

        try {
            em.merge(obj);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return true;
    }

    /**
     * @param sql 更新SQL语句（仅能执行update，delete语句)
     * @return 受影响记录条数
     */
    @Override
    public int executeSql(String sql, HashMap<String, Object> paramMap) throws AppException {

        logger.info("input sql  = " + sql);
        Query query;
        int result;
        try {
            query = em.createNativeQuery(sql);
            initSQLParameter(paramMap, query);
            result = query.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return result;
    }

    @Override
    public int executeSql(String sql) throws AppException {
        logger.info("input sql  = " + sql);

        Query query;
        int result;
        try {
            query = em.createNativeQuery(sql);
            result = query.executeUpdate();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return result;
    }

    /**
     * 说明：仅用于返回单值情况的sql语句，传入其他值无效果
     *
     * @param sql 原生SQL语句，不要传入JPQL
     * @return 单个值
     */
    @Override
    public String querySingleValueByNativeSql(String sql) throws AppException {

        logger.info("input sql  = " + sql);

        String result = "";
        Query query;
        try {

            query = em.createNativeQuery(sql);
            List<Object[]> objArytList = query.getResultList();

            if (objArytList != null && objArytList.size() > 0) {
                Object[] objAry = objArytList.get(0);

                if (objAry != null && objAry.length > 0) {
                    result = objAry[0].toString();
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return result;

    }

    /**
     * 说明：仅用于返回单值情况的sql语句，传入其他值无效果
     *
     * @param sql 原生SQL语句，不要传入JPQL
     * @return 单个值
     */
    @Override
    public String querySingleValueByNativeSql(String sql, HashMap<String, Object> paramMap)
            throws AppException {

        logger.info("input sql  = " + sql);

        String result = "";

        try {
            Query query = em.createNativeQuery(sql);

            initSQLParameter(paramMap, query);

            List<Object[]> objArytList = query.getResultList();

            if (objArytList != null && objArytList.size() > 0) {
                Object[] objAry = objArytList.get(0);

                if (objAry != null && objAry.length > 0) {
                    result = objAry[0].toString();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return result;

    }

    /**
     * 说明：功能同querySingleValueByNativeSql，仅用于返回单值情况的sql语句，传入其他值无效果,如果确认只返回一个值，
     * 且肯定能返回一个值时，可以使用本函数
     *
     * @param sql 原生SQL语句，不要传入JPQL
     * @return 单个值
     */
    @Override
    public String querySingleValueByNativeSqlSimple(String sql) throws AppException {

        logger.info("input sql  = " + sql);

        String result = "";

        try {
            Query query = em.createNativeQuery(sql);

            Object obj = query.getSingleResult();

            if (obj != null) {
                result = obj.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return result;

    }

    /**
     * 说明：功能同querySingleValueByNativeSql，仅用于返回单值情况的sql语句，传入其他值无效果,如果确认只返回一个值，
     * 且肯定能返回一个值时，可以使用本函数
     *
     * @param sql 原生SQL语句，不要传入JPQL
     * @return 单个值
     */
    @Override
    public String querySingleValueByNativeSqlSimple(String sql, HashMap<String, Object> paramMap)
            throws AppException {

        logger.info("input sql  = " + sql);

        String result = "";

        try {
            Query query = em.createNativeQuery(sql);

            initSQLParameter(paramMap, query);

            Object obj = query.getSingleResult();

            if (obj != null) {
                result = obj.toString();
            }

        } catch (NoResultException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return result;

    }

    private void initSQLParameter(HashMap<String, Object> paramMap, Query query) throws AppException {

        if (paramMap != null && paramMap.size() > 0) {
            Set<String> set = new HashSet<String>();

            set = paramMap.keySet();
            for (String key : set) {
                // query.setParameter(key, paramMap.get(key).toString());
                query.setParameter(key, paramMap.get(key));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
            java.util.Date date = null;

            for (String key : set) {

                if (paramMap.get(key) instanceof Date) {// 如果参数是日期类型，则转换，如果是其他类型，则统一转换为String
                    try {
                        date = sdf.parse(paramMap.get(key).toString());
                    } catch (ParseException e) {
                        logger.error(e.getMessage(), e);
                        throw new AppException(e);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new AppException(e);
                    }
                    query.setParameter(key, date);
                } else if (paramMap.get(key) instanceof Integer) {
                    query.setParameter(key, paramMap.get(key));
                } else if (paramMap.get(key) instanceof BigDecimal) {
                    query.setParameter(key, new BigDecimal(paramMap.get(key).toString()));
                } else if (paramMap.get(key) instanceof Collection) {
                    query.setParameter(key, paramMap.get(key));
                } else {
                    query.setParameter(key, paramMap.get(key).toString());
                }
            }
        }
    }

    /**
     * 说明：仅用于查询单行返回单行返回结果的情况，即只取第一行
     *
     * @param sql
     * @return 以数字为key的map
     */
    @Override
    public Map<String, String> querySingleRowByNativeSql(String sql) throws AppException {
        Map<String, String> map = new HashMap<String, String>();
        logger.info("input sql  = " + sql);

        try {
            Query query = em.createNativeQuery(sql);

            List<Object[]> objArytList = query.getResultList();

            if (objArytList != null && objArytList.size() > 0) {
                Object[] objAry = objArytList.get(0);

                if (objAry != null && objAry.length > 0) {

                    int i = 0;
                    for (Object object : objAry) {
                        map.put(String.valueOf(i), object != null ? object.toString() : "");
                        i++;
                    }

                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return map;
    }

    /**
     * 说明：仅用于查询单行返回单行返回结果的情况，即只取第一行
     *
     * @param sql
     * @return 以数字为key的map
     */
    @Override
    public Map<String, String> querySingleRowByNativeSql(String sql, HashMap<String, Object> paramMap)
            throws AppException {
        Map<String, String> map = new HashMap<String, String>();
        logger.info("input sql  = " + sql);
        try {
            Query query = em.createNativeQuery(sql);
            initSQLParameter(paramMap, query);
            List<Object[]> objArytList = query.getResultList();
            if (objArytList != null && objArytList.size() > 0) {
                Object[] objAry = objArytList.get(0);
                if (objAry != null && objAry.length > 0) {
                    int i = 0;
                    for (Object object : objAry) {
                        map.put(String.valueOf(i), object != null ? object.toString() : "");
                        i++;
                    }
                }
                return map;
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
    }

    /**
     * 说明：查询单列值用原生SQL
     */
    public List<String> querySingleColumnByNativeSql(String sql) throws AppException {

        logger.info("input sql  = " + sql);
        List<String> list = new ArrayList<String>();

        Query query = em.createNativeQuery(sql);
        list = query.getResultList();
        // TODO 需要完

        return list;
    }

    /**
     * 说明：仅用于查询单行返回单行返回结果的情况，即只取第一行查询列表中的列名即是map的key值
     *
     * @param sql
     * @return 以数字为key的map
     */

    @Override
    public Map<String, String> querySingleRowByNativeSqlNativeConnection(String sql) throws AppException {

        logger.info("input sql  = " + sql);
        Map<String, String> map = new HashMap<String, String>();

        Connection conn = getConnection();

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            int i = 1;
            while (rs.next()) {
                map.put(rsMetaData.getColumnLabel(i), rs.getString(i));
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, pstmt);
            throw new AppException(e);
        }

        return map;
    }

    /**
     * 说明：原生SQL查询
     *
     * @param sql 任意的查询SQL语句，使用这个方法前，请先查看本类内的其他方法是否已经能满足你的要求
     * @return List
     */
    @Override
    public List<Object> queryByNativeSql(String sql) throws AppException {

        logger.info("input sql  = " + sql);

        List<Object> lsList = new ArrayList<Object>();
        try {
            lsList = em.createNativeQuery(sql).getResultList();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return lsList;
    }

    /**
     * 说明：原生SQL查询
     *
     * @param sql 任意的查询SQL语句，使用这个方法前，请先查看本类内的其他方法是否已经能满足你的要求
     * @return List
     */
    @Override
    public List<Object> queryByNativeSql(String sql, HashMap<String, Object> paramMap) throws AppException {

        logger.info("input sql  = " + sql);

        List<Object> lsList = new ArrayList<Object>();
        try {
            Query query = em.createNativeQuery(sql);

            initSQLParameter(paramMap, query);

            lsList = query.getResultList();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return lsList;
    }

    /**
     * 说明：适用于返回多行多列的需要分页功能的原生SQL语句
     *
     * @param sql          未分页的SQL语句
     * @param startLineNum 开始行号
     * @param endLineNum   结束行号
     * @param orderClause  order子句 如order by lastName asc，firstName desc
     * @param primaryKey   单个主键字段 ID
     * @return 对象数组的List
     */
    // @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> queryByNativeSqlWithPagingForSQLServer(String sql, int startLineNum, int endLineNum,
                                                                 String orderClause, String primaryKey) throws
			AppException {

        StringBuffer sbPagingSql = new StringBuffer();
        sbPagingSql.append("select * from");
        sbPagingSql.append("(");
        sbPagingSql.append(sql);
        sbPagingSql.append(") Res1, ");
        sbPagingSql.append("(select top ");
        sbPagingSql.append(endLineNum);
        sbPagingSql.append(" ROW_NUMBER() OVER(");
        sbPagingSql.append(orderClause);
        sbPagingSql.append(") RowNum,* from ");
        sbPagingSql.append("(");
        sbPagingSql.append(sql);
        sbPagingSql.append(") tag) Res2");
        sbPagingSql.append(" where Res1.");
        sbPagingSql.append(primaryKey);
        sbPagingSql.append("=Res2.");
        sbPagingSql.append(primaryKey);
        sbPagingSql.append(" and Res2.RowNum > ");
        sbPagingSql.append(startLineNum);

        return em.createNativeQuery(sbPagingSql.toString()).getResultList();
    }

    /**
     * 说明：分页功能的适用oracle11g及以下版本
     *
     * @param sql          为分页的SQL语句
     * @param startLineNum 开始行号
     * @param endLineNum   结束行号
     * @return 对象数组的List
     */
    @SuppressWarnings("unchecked")
    public List<Object[]> queryByNativeSqlWithPagingForOracle11g(String sql, int startLineNum, int endLineNum)
            throws AppException {

        StringBuffer sbPagingSql = new StringBuffer();
        sbPagingSql.append("select  * ");
        sbPagingSql.append(" from (select t.*, rownum as rnum ");
        sbPagingSql.append(" from (");
        sbPagingSql.append(sql);
        sbPagingSql.append(") t ");
        sbPagingSql.append(" where rownum <= ");
        sbPagingSql.append(endLineNum);
        sbPagingSql.append(" ) where rnum > ");
        sbPagingSql.append(startLineNum);

        return em.createNativeQuery(sbPagingSql.toString()).getResultList();
    }

    @Override
    public List<Object[]> queryByNativeSqlWithPaging(String sql, int startLineNum, int endLineNum,
                                                     HashMap<String, Object> paramMap) throws AppException {
        Query query = null;
        List<Object[]> ls = new ArrayList<Object[]>();
        try {
            query = em.createNativeQuery(sql);
            query.setFirstResult(startLineNum);
            query.setMaxResults(endLineNum - startLineNum);

            initSQLParameter(paramMap, query);

            logger.info("input sql  = " + sql);
            ls = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return ls;
    }

    /**
     * 说明：仅用于返回单值情况的sql语句，传入其他值无效果
     *
     * @param storedProceduresStatemen 原生SQL语句
     * @return 单个
     */
    @Override
    public String querySingleValueByStoredProcedures(String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException {

        String result = null;
        Query query = null;
        List<Object[]> objArytList = null;

        logger.info(storedProceduresStatemen + paramMap.toString());

        try {
            query = em.createNativeQuery(storedProceduresStatemen);
            initSQLParameter(paramMap, query);
            objArytList = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        if (objArytList != null && objArytList.size() > 0) {
            Object[] objAry = objArytList.get(0);

            if (objAry != null && objAry.length > 0) {
                result = objAry[0].toString();
            }
        }

        return result;
    }

    @Override
    public String executeStoredProcedures(String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException {

        String result = null;
        Query query = null;
        List<Object[]> objArytList = null;

        logger.info(storedProceduresStatemen + paramMap.toString());

        try {
            query = em.createNativeQuery(storedProceduresStatemen);
            initSQLParameter(paramMap, query);
            objArytList = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        if (objArytList != null && objArytList.size() > 0) {
            Object[] objAry = objArytList.get(0);

            if (objAry != null && objAry.length > 0) {
                result = objAry[0].toString();
            }
        }

        return result;
    }

    /**
     * 说明：仅用于返回单行情况的sql语句，传入其他值无效果
     *
     * @param storedProceduresStatemen 原生SQL语句
     * @return
     */
    @Override
    public Map<String, String> querySingleRowByStoredProcedures(String storedProceduresStatemen,
                                                                HashMap<String, Object> paramMap) throws AppException {

        Query query = em.createNativeQuery(storedProceduresStatemen);

        initSQLParameter(paramMap, query);

        List<Object[]> objArytList = query.getResultList();

        Map<String, String> map = new HashMap<String, String>();

        if (objArytList != null && objArytList.size() > 0) {
            Object[] objAry = objArytList.get(0);

            if (objAry != null && objAry.length > 0) {

                int i = 0;
                for (Object object : objAry) {
                    map.put("key" + i, object.toString());
                    i++;
                }
                return map;
            }
        }

        return map;
    }

    @Override
    public List<String[]> queryByStoredProceduresForOracle2(String storedProceduresStatement,
                                                            HashMap<String, Object> paramMap) throws AppException {

        Connection conn = getConnection();
        CallableStatement cstmt = null;
        String result = null;
        List<String[]> objArytList = null;

        try {
            cstmt = conn.prepareCall(storedProceduresStatement);

            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();
            result = cstmt.getString(1);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, cstmt);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, cstmt);
            throw new AppException(e);
        }

        // initSQLParameter(paramMap, query);
        //
        // List<Object[]> objArytList = query.getResultList();
        //
        // Map<String, String> map = new HashMap<String, String>();
        //
        // if (objArytList != null && objArytList.size() > 0) {
        // Object[] objAry = objArytList.get(0);
        //
        // if (objAry != null && objAry.length > 0) {
        //
        // int i = 0;
        // for (Object object : objAry) {
        // map.put("key" + i, object.toString());
        // i++;
        // }
        // return null;
        // }
        // }

        return objArytList;
    }

    /**
     * 说明：使用存储过程查询数据，参数都可以是字符类型，但如果是参数本来应该是日期或整形时，内容格式不能不匹配
     *
     * @param
     * @param
     * @return 对象列表
     */
    @Override
    public List<Object> queryByStoredProcedures(String storedProceduresStatemen, HashMap<String, Object> paramMap)
            throws AppException {

        logger.info(storedProceduresStatemen + paramMap.toString());

        Query query = em.createNativeQuery(storedProceduresStatemen);

        initSQLParameter(paramMap, query);

        return query.getResultList();

    }

    /**
     * 说明：执行一个无输入参数的存储过程，通过out参数返回存储过程执行成功或失败的结果
     *
     * @param storedProceduresName 存储过程名字
     * @return 存储过程执行成功或失败的结果消息
     */
    @Override
    public String executeStoredProcedure(String storedProceduresName) throws AppException {

        Connection conn = getConnection();
        CallableStatement cstmt = null;
        String result = null;

        try {
            cstmt = conn.prepareCall(storedProceduresName);

            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.execute();
            result = cstmt.getString(1);

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, cstmt);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, cstmt);
            throw new AppException(e);
        }

        return result;

    }

    /**
     * 说明：执行一个无输入参数的存储过程，通过out参数返回存储过程执行成功或失败的结果
     *
     * @param storedProceduresName 存储过程名字
     * @return 存储过程执行成功或失败的结果消息
     */
    @Override
    public String executeStoredProcedureUpdate(String storedProceduresName, HashMap<String, Object> paramMap)
            throws AppException {

        Connection conn = getConnection();
        CallableStatement cstmt = null;
        String result = "";

        try {
            cstmt = conn.prepareCall(storedProceduresName);

            cstmt.setString(1, paramMap.get("cellphone").toString());
            cstmt.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, cstmt);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            releaseConnection(conn, cstmt);
            throw new AppException(e);
        }

        return result;

    }

    /**
     * 获取原始数据库连接使用后注意关闭连接
     *
     * @return 原始数据库连接
     */
    @Override
    public Connection getConnection() throws AppException {
        logger.info("in fetch native connection...");
        Connection conn = null;
        try {
            SessionImplementor session = em.unwrap(SessionImplementor.class);
            conn = session.connection();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        logger.info("out fetch native connection.");
        return conn;
    }

    /**
     * 说明：释放数据库连接和Statement相关资源
     *
     * @param conn
     * @param stmt
     * @return 无返回值
     */
    @Override
    public void releaseConnection(Connection conn, Statement stmt) throws AppException {

        logger.info("... native connection release start  ...");

        try {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        logger.info("... native connection release end  ...");
    }

    @Override
    public List<T> queryByNativeSqlWithPaging(String sql, int startLineNum, int endLineNum,
                                              HashMap<String, Object> paramMap, Class clazz) throws AppException {
        Query query = null;
        List<T> ls = new ArrayList<T>();
        try {
            query = em.createNativeQuery(sql, clazz);
            query.setFirstResult(startLineNum);
            query.setMaxResults(endLineNum - startLineNum);

            initSQLParameter(paramMap, query);
            ls = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return ls;
    }

    /**
     * 说明:检查输入参数 ,防止sql注入
     *
     * @param str 输入参数
     * @return 检查后的输入参数
     */
    private static String TransactSQLInjection(String str) {
        return str.replaceAll(".*([';]+|(--)+).*", "");
    }

    @Override
    public Map<String, String> querySingleRowByStoredProceduresForOracle(String storedProceduresStatemen,
                                                                         HashMap<String, Object> paramMap) throws
			AppException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, String> queryByStoredProceduresForOracle(String storedProceduresStatement,
                                                                HashMap<String, Object> paramMap) throws AppException {
        // TODO Auto-generated method stub
        return null;
    }

    /*************************************************************************************************/
    /**
     * 获取行业编号
     *
     * @param sql 原生sql语句，不可传入jpql语句
     * @return
     */
    public String selectOrgCode(String sql) {

        Query query = em.createNativeQuery(sql);
        String orgCode = (String) query.getSingleResult();
        return orgCode;
    }

    /**
     * 获取当前用户可操作的所有父机构
     *
     * @param sql
     */
    public List<Object[]> queryPidOrg(String sql) {

        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    /**
     * 根据id获取该组织下的所有子组织
     *
     * @param sql
     * @return
     */
    public List<Object> getAllSubOrgId(String sql) {

        Query query = em.createNativeQuery(sql);
        List<Object> list = query.getResultList();
        return list;
    }

    /**
     * 功能说明:查询数据总条数
     *
     * @return count
     */
    public Integer getRowsCount(String sql) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) from (");
        sb.append(sql);
        sb.append(") rs");
        String count = querySingleValueByNativeSqlSimple(sb.toString());
        return Integer.parseInt(count);
    }

    /**
     * 功能说明:查询数据总条数，带参数
     *
     * @return count
     */
    public Integer getRowsCount(String sql, HashMap<String, Object> paramMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) from (");
        sb.append(sql);
        sb.append(") rs");
        String count = querySingleValueByNativeSqlSimple(sb.toString(), paramMap);
        return Integer.parseInt(count);
    }

    public List queryByJPASqlWithPaging(String sql, Class t, int startLineNum, int endLineNum,
                                        HashMap<String, Object> paramMap) throws AppException {
        Query query = null;
        List ls = new ArrayList();
        try {
            query = em.createQuery(sql, t);
            query.setFirstResult(startLineNum);
            query.setMaxResults(endLineNum - startLineNum);
            initSQLParameter(paramMap, query);
            ls = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return ls;
    }

    public List queryByJPASqlWithPaging(String sql, int startLineNum, int endLineNum, HashMap<String, Object> paramMap)
            throws AppException {
        Query query = null;
        List ls = new ArrayList();
        try {
            query = em.createQuery(sql);
            query.setFirstResult(startLineNum);
            query.setMaxResults(endLineNum - startLineNum);
            initSQLParameter(paramMap, query);
            ls = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return ls;
    }

    public List<T> queryByJPASql(String sql, Class<T> t, HashMap<String, Object> paramMap) {
        Query query = null;
        List<T> ls = new ArrayList<>();
        try {
            query = em.createQuery(sql, t);
            initSQLParameter(paramMap, query);
            ls = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return ls;
    }

    /**
     * 返回单行数据
     *
     * @param sql
     * @return
     */
    public Object querySingleResultByJPA(String sql) {
        return em.createQuery(sql).getSingleResult();
    }

    public Object querySingleResultByJPA(String sql, HashMap<String, Object> paramMap) throws NoResultException {
        logger.info(sql);
        Query query = em.createQuery(sql);
        initSQLParameter(paramMap, query);
        return query.getSingleResult();
    }

    public T querySingleResultByJPA(String sql, Class<T> t, HashMap<String, Object> paramMap) {
        Query query = em.createQuery(sql, t);
        initSQLParameter(paramMap, query);
        return (T) query.getSingleResult();
    }

    public Integer getRowsCountByJPA(String sql) {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from (");
        sb.append(sql);
        sb.append(") rs");
        String count = querySingleResultByJPA(sb.toString()).toString();
        return Integer.parseInt(count);
    }

    public Integer getRowsCountByJPA(String sql, HashMap<String, Object> hashMap) {
        logger.info(sql);
        StringBuffer sb = new StringBuffer();
        sb.append("select count(*) from ( ");
        sb.append(sql);
        sb.append(" ) ");
        String count = querySingleResultByJPA(sb.toString(), hashMap).toString();
        return Integer.parseInt(count);
    }

    /**
     * 原生sql,自定义转换类型,分页
     */
    @Override
    public List<Object> queryByNativeSqlWithPaging(String sql, int startLineNum, int endLineNum,
                                                   HashMap<String, Object> paramMap, ResultTransformer transformer) {
        logger.info("input sql  = " + sql);
        List<Object> lsList = new ArrayList<Object>();
        try {
            Query query = em.createNativeQuery(sql);
            query.setFirstResult(startLineNum);
            query.setMaxResults(endLineNum - startLineNum);
            query.unwrap(SQLQuery.class).setResultTransformer(transformer);
            initSQLParameter(paramMap, query);
            lsList = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return lsList;
    }

    @Override
    public java.util.Date querySqlDtae() {

        SQLQuery sqlQuery = em.createNativeQuery("SELECT sysdate FROM dual").unwrap(SQLQuery.class)
                .addScalar("sysdate", TimestampType.INSTANCE);
        return (java.util.Date) sqlQuery.uniqueResult();
    }

    /**
     * 20180824
     * sql	page，rows分页查询
     *
     * @param sql
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public List<Object[]> queryByNativeSqlWithPageAndRows(String sql, Integer page, Integer rows, HashMap<String,
            Object> param) {
        Query query = null;
        List<Object[]> ls = new ArrayList<Object[]>();
        try {
            query = em.createNativeQuery(sql);
            int startLineNum = (page == 1) ? 0 : (page - 1) * rows;
            query.setFirstResult(startLineNum);
            query.setMaxResults(rows);

            initSQLParameter(param, query);

            logger.info("input sql  = " + sql);
            ls = query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }

        return ls;
    }

}
