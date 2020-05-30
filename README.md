# ImageLoader
功能：  1.根据用户需求可以灵活配置（建造者模式） 2.支持高并发，图片加载的优先级 3.支持可以选择不同的加载策略，对加载策略进行扩展 4.二级缓存 加载图片时内存中已经加载了，则从内存中加载，不存在去外置卡中5.加载，外置还不存在则从网络下载 6.并对缓存策略可以扩展 7.支持从加载过程中显示默认加载图片 8.支持加载失败时 显示默认错误图片 9.图片显示自适应。从网络加载下来的图片经最佳比例压缩后显示不能失真变形 10.支持请求转发，下载  用到的模式： 1.生产者 消费者模式 2.建造者模式 3.单例模式 4.模板方法模式 5.策略模式  用到的知识点 1.内存缓存 LruCache技术 2.硬盘缓存技术DiskLruCache技术 3.图片下载时请求转发

#引入
在项目根目录build.gradle中添加
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
在app目录build.gradle中添加
```
implementation 'com.github.renzhenming:ImageLoader:1.0.0'

```
#初始化
```
        AbstractDisplay display = new DefaultDisplayConfig.Builder()
                .setLoadingImage(R.drawable.loading)
                .setErrorImage(R.drawable.not_found)
                .build();

        LoaderConfig config = new LoaderConfig.Builder()
                .setThreadCount(3) //线程数量
                .setLoadPolicy(new DefaultLoadPolicy()) //加载策略
                .setCachePolicy(new DefaultDiskCache(this)) //缓存策略
                .setDisplayConfig(display)
                .build();
        //初始化
        SimpleImageLoader.init(config);
```

#调用
```
SimpleImageLoader.getInstance().display(imageView,item);
```
