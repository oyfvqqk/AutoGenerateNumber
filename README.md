# AutoGenerateNumber（自动编号）
根据之前某个项目中编号模块的思路重新造的轮子，方便以后开发中直接使用。

## 使用说明
调用Grenerate接口的getNumberByRule方法，传入编号规则即可获取编号。
### 编号规则
传入：`'sdfs-';year u;month c;day c;'(';hour;minute;seconds;')-';num{step:2,size:3,unit:'c',name:'test1'}` 

返回：`sdfs-二千零一十七零五零九(103611)-零零一`
- 字段之间以`；`分割
- 固定字符串用单引号`''`包裹
- 日期和时间（大小写皆可）：
	- 年：year 
	- 月：month
	- 日：day
	- 时：hour
	- 分：minute
	- 秒：seconds
	- 可空格后输入c或u将数字转换为中文
		- c 不带单位
			- 如：二零一七
		- u 带单位
			- 如：二千零一十七
- 数字序号用num开头，可附带json字段，用于设定序号的形式，除name不建议省略外其余均可省略。
	- step: int型，序号步长。省略后步长为1。
	- size: int型，序号长度，不足的部分会用0或零补全，超过的部分会被省略。
	- unit: String型，数字中文化标识，c为不带单位，u为带单位。
	- name: String型，唯一，用于标识该序号的别名。
### 初始化序号
需调用OperateNum类的initNumMap方法，将别名和序号以map的方式传入，key为别名，value为序号。
### 序号持久化
目前为被动等待开发人员调用OperateNum类的getNum方法通过别名获取对应的序号，预计会在下个版本优化为主动调用持久化方法。
