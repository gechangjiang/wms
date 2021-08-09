
package com.tdt.sys.core.properties;

import cn.stylefeng.roses.core.util.ToolUtil;
import lombok.Data;

import java.io.File;

import static cn.stylefeng.roses.core.util.ToolUtil.getTempPath;

/**
 * tdt项目配置
 *
 * @author gcj
 * @Date 2017/5/23 22:31
 */
@Data
public class TDTProperties {

    public static final String PREFIX = "tdt";

    private String fileUploadPath;

    private Boolean haveCreatePath = false;

    private Boolean springSessionOpen = false;

    /**
     * session 失效时间（默认为30分钟 单位：秒）
     */
    private Integer sessionInvalidateTime = 30 * 60;

    /**
     * session 验证失效时间（默认为15分钟 单位：秒）
     */
    private Integer sessionValidationInterval = 15 * 60;

    public String getFileUploadPath() {

        //如果没有写文件上传路径,保存到临时目录
        if (ToolUtil.isEmpty(fileUploadPath)) {
            return getTempPath();
        } else {

            //判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }

            //判断目录存不存在,不存在得加上
            if (!haveCreatePath) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

}
