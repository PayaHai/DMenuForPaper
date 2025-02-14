# DMenuForPaper
> 配置简单，极其灵活的 Paper 菜单插件，同时兼容 Folia。

### 安装
前往 [Release](https://github.com/PayaHai/DMenuForPaper/releases) 页面下载最新版本Jar，放入`plugins`文件夹中即可。

### 配置
配置文件位于`plugins/DMenuForPaper`文件夹下，其中每个YML文件为一个页面，下面是单个页面的示例：
```yaml
# 您可以使用&代替原版的颜色符号§，如果您想要表达原始的&含义，请输入两遍&&。
# & = §, && = &
# 如果两个按钮表示同一个物品，则后面的按钮会覆盖前面的按钮。

title: "&a&l菜单" # 箱子标题
rows: 3 # 箱子的行数

item: "minecraft:clock" # 触发此页面的物品类型，手持左键或右键即可打开此页面。可选
lock_item: true # 是否锁定触发物品，锁定后玩家无法丢弃此物品。可选，仅在 item 存在时有效

buttons: # 按钮列表
  -
    id: "0-26"
    name: "边框"
    icon: "minecraft:pink_stained_glass_pane"

  -
    id: "13" # 按钮所在的位置，可以为单个数字或者一个范围，范围请按照 "开始-结束" 的格式
    name: "前往测试" # 按钮物品名称。可选
    icon: "minecraft:stone" # 按钮物品的类型。可选
    lore: # 按钮物品的描述。可选
      - "&c&l点击前往测试"

    # 请注意，如果按钮没有设置 command 和 open，那么点击后不会执行任何操作，ui也不会关闭
    command: "say 你点击了按钮" # 玩家点击后执行的命令，以玩家身份执行，点击后ui自动关闭。可选
    open: "测试" # 点击后打开的页面，点击后ui自动关闭。可选
```

### 命令
- /dmenu reload: 重载配置文件
- /dmenu open <page>: 打开指定页面

### 结尾
遇到问题欢迎前往 [Issues](https://github.com/PayaHai/DMenuForPaper/issues) 页面或 QQ 群: [674416045](https://qm.qq.com/q/5wA9OBTY6Q) 反馈。