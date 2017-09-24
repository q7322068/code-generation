# code-generation
根据sql（获取model设计），自己指定模板快速生成系统相关代码

# 使用说明
1. 创建一个test数据库，调整配置文件内的数据库配置(src/main/resources/application-local.properties)；
2. 修改配置文件（src/main/resources/application.properties）内：generator.project.path的值为当前项目路径，如D:/workspace/code-generator
3. 启动项目（运行GeneratorApplication的main方法）
4. 设置功能参数：http://localhost:8009/generate/setProperty?projectPath=D:/workspace/rest-dmp&packagePath=/src/main/java/com/hexun/bdc/dmp&templetePath=/src/main/resources/code-templete
	projectPath 要生成代码的项目的路径：  D:/workspace/code-generator
	packagePath 要生成代码的项目的包的根路径：/src/main/java/com/hexun/bdc/generator
	templetePath 本项目内代码模板路径： /src/main/resources/code-templete
	
5. 生成某个库下的所有表的模板代码：
	http://localhost:8009/generate/tables?dbname=project

6. 生成某个库下某个表的模板代码：
	http://localhost:8009/generate/table?dbname=project&tableName=auth_user
