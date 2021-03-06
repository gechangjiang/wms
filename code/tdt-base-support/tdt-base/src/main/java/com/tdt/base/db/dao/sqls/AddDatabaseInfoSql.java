package com.tdt.base.db.dao.sqls;

import lombok.Getter;

/**
 * 添加数据源sql
 *
 * @author gcj
 * @date 2019-07-16-13:06
 */
@Getter
public class AddDatabaseInfoSql extends AbstractSql {

    @Override
    protected String mysql() {
        return "INSERT INTO `database_info`(`db_id`, `db_name`, `jdbc_driver`, `user_name`, `password`, `jdbc_url`, `remarks`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String sqlServer() {
        return "INSERT INTO `database_info`(`db_id`, `db_name`, `jdbc_driver`, `user_name`, `password`, `jdbc_url`, `remarks`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String pgSql() {
        return "INSERT INTO `database_info`(`db_id`, `db_name`, `jdbc_driver`, `user_name`, `password`, `jdbc_url`, `remarks`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String oracle() {
        return "INSERT INTO database_info VALUES (?, ?, ?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd hh24:mi:ss'))";
    }
}
