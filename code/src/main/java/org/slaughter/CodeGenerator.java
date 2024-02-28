package org.slaughter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


public class CodeGenerator {

    @Value("${mysql.jdbc.user}")
    private static String user = "root";
    @Value("${mysql.jdbc.password}")
    private static String password = "123456";
    @Value("${mysql.jdbc.driverClassName}")
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
    @Value("${mysql.jdbc.url}")
    private static String url = "jdbc:mysql://localhost:3306/sys_user?characterEncoding=utf8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    public static void main(String[] args) {
        generator("sys_user");
    }

    public static void generator(String tableName){
//        Environment environment ;
        // 创建代码生成器
        AutoGenerator generator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        // 编辑生成位置
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/code/src/main/java");
        globalConfig.setAuthor(System.getProperty("user.name"));
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(true);
        globalConfig.setSwagger2(true);
        globalConfig.setEntityName("%sEntity");
        globalConfig.setServiceName("%sService");
        generator.setGlobalConfig(globalConfig);
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUsername(user);
        dataSourceConfig.setDriverName(driverClassName);
        dataSourceConfig.setDbType(DbType.MYSQL);
        generator.setDataSource(dataSourceConfig);
        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("org.slaughter");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setXml("mapper.xml");
        generator.setPackageInfo(packageConfig);
        // 策略设置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tableName);
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //生成实体时去掉表前缀
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        //数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityLombokModel(true);
        // 实体继承的类
        strategy.setSuperEntityClass(org.slaughter.utils.BaseEntity.class);

        //restful api风格控制器
        strategy.setRestControllerStyle(true);
        //url中驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 设置创建时间和更新时间自动填充策略
        TableFill createdDate = new TableFill("created_date", FieldFill.INSERT);
        TableFill updatedDate = new TableFill("updated_date", FieldFill.INSERT_UPDATE);
        List<TableFill> tableFills = Lists.newArrayList();
        tableFills.add(createdDate);
        tableFills.add(updatedDate);
        strategy.setTableFillList(tableFills);
        // 乐观锁策略
        strategy.setVersionFieldName("version");
        generator.setStrategy(strategy);
        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setXml(null);
        generator.setTemplate(templateConfig);
        // 执行生成任务
        generator.execute();
    }
}
