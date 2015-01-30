## 关于BeautyEye
BeautyEye是一款Java Swing跨平台外观（look and feel）。
得益于Android的GUI基础技术，BeautyEye的实现完全不同于其它外观实现。
BeautyEye是免费的，您可以研究、学习甚至商业用途。

## 最新版本
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/release_notes/v3.5_release_note.png)

## 兼容性
BeautyEye 可运行于java 1.5、1.6以及1.7之上，但推荐至少应运行在[java1.6.0_12或更新版本](http://www.java.com/zh_CN/download/) <br>([为何java1.6.0_10或u11版不行？](https://code.google.com/p/beautyeye/wiki/java_1_6_0_u10_BUG_6750920 ))，因为这些版本将能带来窗口透明特性，更重要的是Swing的性能提升。

另附：[BeautyEye兼容性测试结果](http://code.google.com/p/beautyeye/wiki/Compatibility_test_results).

## 主要特征
* 更好的兼容性，可运行于java 1.5、1.6、1.7，SUN的非公开API被移除？木有关
* 遵从主流审美，与时俱进
* 跨平台
* 源自Android基础技术，用最少的代码实现最满意的外观，Synth、Nimbus都是浮云

## 演示程序
<b>提示:</b>  请确保已安装JRE(最低java1.5版)，如需BeautyEye外观支持透明效果，则推荐java1.6.0\_12或更新版本<br>([为何java1.6.0_10或u11版不行？](https://code.google.com/p/beautyeye/wiki/java_1_6_0_u10_BUG_6750920))，这些版本才能支持窗口透明特性.

* [下载可执行jar包\(Swingsets2\)](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/demo/excute_jar/SwingSets2\(BeautyEyeLNFDemo\).jar)
* [下载可执行jar包\(Swingsets3\)](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/demo/excute_jar/swingset3_beautyeye.jar) <font color="#FF6600">\[推荐\]</font>

## 下载方式
正式版.zip包：[Click HERE](https://github.com/JackJiang2011/beautyeye/archive/3.5.zip)（内含demo、api文档、核心分发jar包等）.

## 开发指南
#### 第一步：引入*`beautyeye_lnf.jar`*包
核心分发jar包 *`beautyeye_lnf.jar`* 位于*“`/dist/`”*目录。

#### 第二步：在代码中使用BeautyEye
加入以下代码，即可将你的Java程序界面更换成BeautyEye的外观：
```Java
public static void main(String[] args)
{
    try
    {
        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
    }
    catch(Exception e)
    {
        //TODO exception
    }
    ..................... 你的程序代码 .........................
    ..................... 你的程序代码 .........................
}
```

详细开发者指南请查看：[BeautyEye L&F简明开发者指南](http://code.google.com/p/beautyeye/wiki/Introduction).

## 联系方式
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/screenshots/js2.png)

* 如有bug及建议等，请邮件至：`jb2011@163.com`；</li>
* 欢迎加入Java Swing爱好者讨论QQ群：`259448663`  <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=9971fb1d1845edc87bdec92ad03f329c1d1f280b1cfe73b6d03c13b0f7f8aba1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="Java Swing技术交流" title="Java Swing技术交流"></a>；
* 如需有偿提供应用软件整体或局部美化、方案制作、编码实现等，请联系QQ：`413980957`；
* 你也可前往 [Jack Jiang的微博](http://t.qq.com/jackjiang_is_here/) 进行交流。

## 授权方式
你可永久免费且自由地使用BeautyEye外观(look and feel)，如：用于研究、学习、甚至商业用途，但禁止在超越License约束内容的情况下用于商业用途等，请尊重知识产权。

## 特性预览
#### Part 1/2：[点击查看清晰原图](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview.png)
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview.png)

#### Part 2/2：[点击查看清晰原图](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview2.png)
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview2.png)

## 更多截屏
[Click here](http://code.google.com/p/beautyeye/wiki/screenshots_all_in_one)
