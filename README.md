# Basic Magical Domain 魔法领域模组

## 简介 | Introduction

**Basic Magical Domain** 是一个为 Minecraft 设计的轻量级魔法模组，作者：QiChen。该模组为游戏引入了五行符文系统（木、水、火、金、土）和魔法祭坛，每种符文都拥有独特的魔法效果，既可以手持使用，也可以通过魔法祭坛实现自动化效果，极大丰富了游戏的玩法与策略。

**Basic Magical Domain** is a lightweight magic mod for Minecraft, created by QiChen. It introduces the Five Element Runes System (Wood, Water, Fire, Metal, Earth) and Magical Altar, each with unique magical effects. Runes can be used both handheld and through the Magical Altar for automated effects, greatly enriching gameplay and strategy.

---

## 主要功能 | Main Features

### 🏛️ 魔法祭坛系统 | Magical Altar System

**魔法祭坛**是本模组的核心功能，允许玩家将符文放入其中实现自动化魔法效果。

- **建造方式**：在世界中放置魔法祭坛方块
- **使用方法**：右键点击祭坛打开GUI界面，将符文放入槽位
- **自动化效果**：祭坛会每秒自动触发符文效果，无需玩家操作
- **特殊机制**：金之符文需要在祭坛旁边放置容器（如箱子、漏斗等）来接收挖掘到的矿石
- **发光效果**：祭坛提供7级光照，既实用又美观

### ⚡ 五行符文系统 | Five Element Runes System

每种符文都有**两种使用方式**：

#### 🌍 土之符文 Earth Rune - 大地之力
- **手持使用**：右键蓄力3秒后释放，在16格范围内加速植物生长，持续60秒
- **祭坛使用**：每秒自动对16格范围内的植物进行10次额外生长刻
- **适用植物**：农作物、树苗、甘蔗、仙人掌、竹子、紫颂花、海带、地狱疣等
- **效果表现**：绿色粒子特效，显著提升农业效率

#### 🔥 火之符文 Fire Rune - 烈焰之怒
- **手持使用**：右键蓄力3秒后释放，对16格范围内敌对生物造成火焰爆发，持续60秒
- **祭坛使用**：每秒自动对16格范围内的敌对生物进行火焰攻击
- **攻击效果**：
  - 根据距离造成2-8点火焰伤害
  - 点燃敌人5秒
  - 施加混乱效果（5秒）
- **效果表现**：火焰粒子特效和爆炸音效

#### 💧 水之符文 Water Rune - 流水之力
- **手持使用**：右键蓄力3秒后释放，释放水冲击波防御，持续60秒
- **祭坛使用**：每秒自动对16格范围内的敌对生物进行冲击波攻击
- **防御效果**：
  - 击退敌对生物
  - 施加缓慢效果（5秒）
  - 施加虚弱效果（5秒）
- **效果表现**：水滴粒子特效和溅水音效

#### 🌳 木之符文 Wood Rune - 生命活力
- **手持使用**：右键蓄力3秒后释放，为16格范围内所有玩家提供治疗光环，持续60秒
- **祭坛使用**：每秒自动为16格范围内的所有玩家提供治疗效果
- **治疗效果**：
  - 再生效果（持续恢复生命值）
  - 饱和效果（恢复饥饿值和饱和度）
- **效果表现**：爱心粒子特效

#### ⚒️ 金之符文 Metal Rune - 金属财富
- **手持使用**：右键蓄力3秒后释放，探测并挖掘16格范围内的矿石，持续60秒
- **祭坛使用**：每秒自动探测30格范围内的矿石并挖掘（需要在祭坛旁边放置容器）
- **挖掘机制**：
  - 50%成功率挖掘矿石
  - 生成随机矿石掉落物
  - 自动将掉落物放入邻近的容器中
- **效果表现**：闪闪发光的粒子特效

---

## 玩法策略 | Gameplay Strategies

### 🏗️ 基础建设期
1. **制作魔法祭坛**：优先制作祭坛，实现自动化效果
2. **农业自动化**：使用土之符文+祭坛建立自动化农场
3. **防御体系**：在基地周围放置装有火之符文或水之符文的祭坛进行防御

### ⚔️ 战斗应用
1. **攻击符文**：火之符文适合主动攻击，清理怪物
2. **防御符文**：水之符文适合被动防御，保护基地
3. **治疗符文**：木之符文在团队战斗中提供持续治疗

### 💎 资源获取
1. **自动挖矿**：金之符文配合祭坛和存储系统实现自动挖矿
2. **容器布置**：在祭坛旁边放置箱子、漏斗等容器接收矿石
3. **效率提升**：多个金之符文祭坛可以并行工作

### 🔧 高级玩法
1. **符文组合**：不同符文的祭坛可以组合使用，形成多功能区域
2. **红石自动化**：配合红石电路实现符文的自动更换
3. **基地规划**：合理布置祭坛位置，最大化覆盖范围

---

## 制作配方 | Crafting Recipes

> **注意**：具体的制作配方可能因版本而异，请在游戏中查看JEI等MOD获取最新配方。

### 基础物品
- **魔法祭坛**：[待补充具体配方]
- **各种符文**：[待补充具体配方]

---

## 使用技巧 | Tips & Tricks

### 💡 基础技巧
1. **蓄力指示**：手持符文时会有音效提示蓄力开始
2. **成功音效**：蓄力成功后会播放附魔台音效
3. **创造模式**：在创造模式下使用符文不会被消耗

### 🎯 进阶技巧
1. **范围规划**：16格范围约为直径33格的圆形区域
2. **高度影响**：符文效果有垂直范围限制，注意Y轴坐标
3. **容器兼容**：金之符文支持所有具有物品存储能力的方块

### ⚠️ 注意事项
1. **符文消耗**：手持使用符文会消耗物品（创造模式除外）
2. **祭坛保护**：祭坛中的符文不会被消耗，但需要手动放入
3. **效果范围**：确保目标区域在符文效果范围内

---

## 安装方法 | Installation

### 📋 前置要求 | Prerequisites
- **Minecraft 1.21.1**
- **[NeoForge](https://neoforged.net/) 21.1.184** 及以上版本

### 📥 安装步骤 | Installation Steps
1. 下载本模组的 jar 文件
2. 将 jar 文件放入游戏目录的 `mods` 文件夹
3. 启动游戏并确保已正确安装 NeoForge
4. 在创造模式物品栏中寻找"基础魔法领域"分类

---

## 开发与构建 | Development & Build

### 🛠️ 开发环境
- **推荐IDE**：IntelliJ IDEA 或 Eclipse
- **依赖管理**：如遇依赖缺失，运行 `./gradlew --refresh-dependencies`
- **构建命令**：`./gradlew build`

### 📝 版本信息
- **当前版本**：开发中
- **目标版本**：Minecraft 1.21.1
- **框架**：NeoForge 21.1.184+

---

## 常见问题 | FAQ

### ❓ 使用问题
**Q: 符文蓄力失败怎么办？**
A: 确保按住右键满3秒，听到附魔台音效后再释放。

**Q: 祭坛没有效果？**
A: 检查是否正确放入符文，确保祭坛周围有足够的空间。

**Q: 金之符文挖不到矿石？**
A: 确保祭坛旁边有容器（箱子、漏斗等），且周围30格范围内有矿石。

### 🔧 技术问题
**Q: 模组冲突怎么办？**
A: 尝试移除其他魔法类模组，或查看崩溃日志确定冲突源。

**Q: 如何重置配置？**
A: 删除 `config` 文件夹中的相关配置文件，重新启动游戏。

---

## 更新日志 | Changelog

### 最新更新
- ✅ 实现五行符文系统
- ✅ 添加魔法祭坛功能
- ✅ 完善符文效果和粒子特效
- ✅ 优化性能和稳定性

### 计划功能
- 🔄 更多符文类型
- 🔄 符文升级系统
- 🔄 更多魔法方块
- 🔄 符文合成配方

---

## 鸣谢 | Credits

- **[NeoForge](https://neoforged.net/)** - 提供强大的模组开发框架
- **Minecraft社区** - 提供丰富的开发文档和教程
- **测试玩家** - 感谢所有提供测试反馈的玩家！

---

## 联系方式 | Contact

- **GitHub Issues**: [BasicMagicalDomain Issues](https://github.com/QiChenSn/BasicMagicalDomain/issues)
- **作者 Author**: QiChen
- **版本 Version**: 1.21.1-NeoForge

---

## 许可证 | License

本项目采用 [MIT License](LICENSE) 开源许可证。

---

*享受魔法的世界，探索五行的奥秘！*  
*Enjoy the magical world and explore the mysteries of the Five Elements!*
