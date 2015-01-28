## About BeautyEye
BeautyEye is a Java Swing cross-platform look and feel. Thanks to NinePatch technology, BeautyEye is so different.<br>BeautyEye is free, you can study, learn, even for commercial use. Enjoy it, thanks.

## Latest Release
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/release_notes/v3.5_release_note.png)

## <font color="#ff0000">特别说明</font>
各种开发问题及技巧请参见：[BeautyEye L&F简明开发者指南](http://code.google.com/p/beautyeye/wiki/Introduction)。

另，<font color="#2A779D">关于“切换输入法导致白屏的问题”请见指南之“附录10”</font>。

## Compatibility
BeautyEye 可运行于java 1.5、1.6以及1.7之上，但推荐至少应运行在[java1.6.0_12或更新版本](http://www.java.com/zh_CN/download/) <br><font style="font-size: 11px" color="#FF6600">([为何java1.6.0_10或u11版不行？](https://code.google.com/p/beautyeye/wiki/java_1_6_0_u10_BUG_6750920 ))</font>，因为这些版本将能带来窗口透明特性，更重要的是Swing的性能提升。

另附：[BeautyEye外观(look and feel)兼容性测试结果](http://code.google.com/p/beautyeye/wiki/Compatibility_test_results).

## Feature
<ul>
<li>更好的兼容性，可运行于java 1.5、1.6、1.7，SUN的非公开API被移除？木有关系</li>
<li>遵从当前主流审美，与时俱进</li>
<li>跨平台</li>
<li>使用NinePatch技术，用最少的代码实现最满意的外观，Synth、Nimbus都是浮云</li>
</ul>

## Demos
<b>提示:</b>  请确保已安装JRE(最低java1.5版)，如需BeautyEye外观支持透明效果，则推荐java1.6.0\_12或更新版本<br><font style="font-size: 11px" color="#FF6600">([为何java1.6.0_10或u11版不行？](https://code.google.com/p/beautyeye/wiki/java_1_6_0_u10_BUG_6750920))</font>，这些版本才能支持窗口透明特性.
<ul>
<li>
[Demo在线加载1](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/demo/applet/SwingSet2_for_be_lnf.html)_(applet方式）_ <font color="#FF6600">[注：如浏览器不能正常解析，请“另存为...”后本地打开]</font></li>
<li>or 
[Demo在线加载2](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/demo/jnlp/launch.jnlp)_(Java Web Start方式)_ <font color="#FF6600">[注：如浏览器不能正常解析，请“另存为...”后本地打开]</font></li>
<li>or 
[下载可执行jar包(Swingsets2)](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/demo/excute_jar/SwingSets2(BeautyEyeLNFDemo).jar)</li>
<li>or 
[下载可执行jar包(Swingsets3)](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/demo/excute_jar/swingset3_beautyeye.jar) <font color="#FF6600">[推荐]</font></li>
</ul>

## Download
all_in_one.zip压缩包：[http://code.google.com/p/beautyeye/downloads/list Click HERE]（内含demo、api文档、核心分发jar包等）.<br><br>
<font color="#2A779D">友情提示：核心分发jar包 *`beautyeye_lnf.jar`* 位于all_in_one.zip包中的位置是：*“`all_in_one/dist/`”*</font>

## Development Guide
<font color="#2A779D">加入以下代码，即可将你的Java程序界面更换成Beauty Eye的外观：</font>
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

关于开发详情请查看：[BeautyEye L&F简明开发者指南](http://code.google.com/p/beautyeye/wiki/Introduction).

## Contact
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/screenshots/js2.png)

* 如有bug及建议等，请直接提交`Issues`或邮件至：`jb2011@163.com`；</li>
* 另有一QQ群供Swing爱好者讨论交流，1群：`259448663`  <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=9971fb1d1845edc87bdec92ad03f329c1d1f280b1cfe73b6d03c13b0f7f8aba1"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="Java Swing技术交流" title="Java Swing技术交流"></a>；
* 另：有偿提供应用软件整体或局部美化咨询、方案制作、编码实现等，如有需求请联系QQ：`413980957`；
* 你也可前往 [Jack Jiang的微博](http://t.qq.com/jackjiang_is_here/) 进行交流。

## License
你可永久免费且自由地使用BeautyEye外观(look and feel)，如：用于研究、学习、甚至商业用途，但禁止在未经授权的情况下用于商业用途等，请尊重知识产权。

## Preview
*Part 1/2：* 欲查看清晰原图请["另存为..."](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview.png )<br>
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview.png)

*Part 2/2：* 欲查看清晰原图请["另存为..."](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview2.png)<br>
![](https://raw.githubusercontent.com/JackJiang2011/beautyeye/master/preview/be_lnf_preview2.png)

## More Screenshots
[Click here](http://code.google.com/p/beautyeye/wiki/screenshots_all_in_one)
