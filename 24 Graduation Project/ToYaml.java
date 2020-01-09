
import java.io.*;
import java.util.*;

import java.lang.reflect.Field;
//import org.ho.yaml.Yaml;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.yaml.snakeyaml.Yaml;



class Elements {
    String url,tag,id,name,text,instance,depth,valid,selected,xpath,ancestor,action;
    int x,y,width,height;

    public String getUrl() {        return url;    }
    public void setUrl(String url) {        this.url = url;    }
    public String getTag() {        return tag;    }
    public void setTag(String tag) {        this.tag = tag;    }
    public String getId() {        return id;    }
    public void setId(String id) {        this.id = id;    }
    public String getName() {        return name;    }
    public void setName(String name) {        this.name = name;    }
    public String getText() {        return text;    }
    public void setText(String text) {        this.text = text;    }
    public String getInstance() {        return instance;    }
    public void setInstance(String instance) {        this.instance = instance;    }
    public String getDepth() {        return depth;    }
    public void setDepth(String depth) {        this.depth = depth;    }
    public String getValid() {        return valid;    }
    public void setValid(String valid) {        this.valid = valid;    }
    public String getSelected() {        return selected;    }
    public void setSelected(String selected) {        this.selected = selected;    }
    public String getXpath() {        return xpath;    }
    public void setXpath(String xpath) {        this.xpath = xpath;    }
    public String getAncestor() {        return ancestor;    }
    public void setAncestor(String ancestor) {        this.ancestor = ancestor;    }
    public String getAction() {        return action;    }
    public void setAction(String action) {        this.action = action;    }
    public int getX() {        return x;    }
    public void setX(int x) {        this.x = x;    }
    public int getY() {        return y;    }
    public void setY(int y) {        this.y = y;    }
    public int getWidth() {        return width;    }
    public void setWidth(int width) {        this.width = width;    }
    public int getHeight() {        return height;    }
    public void setHeight(int height) {        this.height = height;    }
    public String toString() {
        return "Elements [url:" + getUrl() + "|text:" + getText() + "|action:" + getAction() + "]";
    }
}
class ElementStore{
    String ElementStoreName;
    String reqDom,resDom,reqHash,resHash,reqImg,resImg,reqTime,resTime,action;
    int clickedIndex;
    Elements element;
    public void setElement(Elements element) {
        this.element = element;
    }
    public Elements getElement(){return element;}
    public String getElementStoreName() {        return ElementStoreName;    }
    public void setElementStoreName(String elementStoreName) {        ElementStoreName = elementStoreName;    }
    public String getReqDom() {        return reqDom;    }
    public void setReqDom(String reqDom) {        this.reqDom = reqDom;    }
    public String getResDom() {        return resDom;    }
    public void setResDom(String resDom) {        this.resDom = resDom;    }
    public String getReqHash() {        return reqHash;    }
    public void setReqHash(String reqHash) {        this.reqHash = reqHash;    }
    public String getResHash() {        return resHash;    }
    public void setResHash(String resHash) {        this.resHash = resHash;    }
    public String getReqImg() {        return reqImg;    }
    public void setReqImg(String reqImg) {        this.reqImg = reqImg;    }
    public String getResImg() {        return resImg;    }
    public void setResImg(String resImg) {        this.resImg = resImg;    }
    public String getReqTime() {        return reqTime;    }
    public void setReqTime(String reqTime) {        this.reqTime = reqTime;    }
    public String getResTime() {        return resTime;    }
    public void setResTime(String resTime) {        this.resTime = resTime;    }
    public String getAction() {        return action;    }
    public void setAction(String action) {        this.action = action;    }
    public int getClickedIndex() {        return clickedIndex;    }
    public void setClickedIndex(int clickedIndex) {        this.clickedIndex = clickedIndex;    }
    public String toString() {
        return "ElementStore {StoreName:" + getElementStoreName() + "|reqHash:" + getReqHash() + "|element:" + element.toString() + "}";
    }
    public String toString2() {
        return "ElementStore {StoreName:" + getElementStoreName() + "|reqHash:" + getReqHash() + "}";
    }
    public String toString3() {
        return getElement().getAncestor();
    }
}



public class ToYaml {

    static String path="E:\\IdeaProjects\\NeoTest\\file\\";
    static String testYml1=path+"1.yml";//结构完整、数据完整的yml文件
    static String testYml2=path+"2.yml";//结构完整、数据简略的yml文件
    static String testYml3=path+"3.yml";//结构不完整、数据简略的yml文件
    static String testYml4=path+"4.yml";//结构完整、数据极简略的yml文件
    static String y20191225162609 =path+"y2019122516260.yml";//临时的yml文件
    static String y20201231104305 =path+"y20201231104305.yml";//临时的yml文件
    static String ElementsCache=path+"ElementsCache.yml";//读取yml中途生成的文件
    static String ElementsOut=path+"ElementsOut.yml";//可以查看xml格式的yaml文

    //Map<String, List<Map<String,,Object>>> map= new HashMap<>();
    @Deprecated
    void usrYaml() throws FileNotFoundException{
        //with import org.ho.yaml.Yaml;
        //初始化yaml解析器
        Yaml yaml = new Yaml();
        File f = new File("C:\\Users\\54251\\桌面\\elements.yml");
        // 加载yaml文件
        Object result = yaml.load(new FileInputStream(f));
        //打印出来将会是HashMap
        System.out.println(result.getClass());
        System.out.println(result);
    }

    @Deprecated
    public Map<String,Object> Obj2Map(Object obj) throws Exception{//没有用到
        Map<String,Object> map=new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
            System.out.println("@@"+field.getName()+"@@"+field.get(obj));
        }
        return map;
    }


    public static Map<String,Object> getStringToMap(String arr){
        //判断arr是否有值
        if(null == arr || "".equals(arr)){
            return null;
        }
        String arr2 = arr.substring(1, arr.length()-1);//去掉首尾的{}
        Map map = new HashMap();
        String[] param = arr2.split(", ");
        for (int i = 0; i < param.length; i++) {
            int index = param[i].indexOf('=');
            map.put(param[i].substring(0,index), param[i].substring((index + 1)));
        }

        return map;
    }

    //第一次读取element.yml文件，把所有换行给去掉，代替为&符号
    public static void readFileByLines(String fileName){ //https://www.cnblogs.com/JonaLin/p/11057398.html
        File file = new File(fileName);
        File fileWrite = new File(ElementsCache);
        BufferedReader reader = null;
        try {
            if(!fileWrite.exists()){       fileWrite.createNewFile();         }
            FileWriter fileWriter = new FileWriter(fileWrite.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {// 显示行号
                bw.write(tempString);
                bw.write("&");//作为回车
            }
            reader.close();
            bw.close();
        }   catch (IOException e) { e.printStackTrace(); }
        finally { if (reader != null)
        { try { reader.close(); } catch (IOException e1) {     }
        }
        }


    }
    //第二次读取文件，替换所有转移字符
    public static void readFileByLines2(){
        File file = new File(ElementsCache);
        File fileWrite = new File(ElementsOut);
        BufferedReader reader = null;
        try {
            if(!fileWrite.exists()){       fileWrite.createNewFile();         }
            FileWriter fileWriter = new FileWriter(fileWrite.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {// 显示行号
                String afterString= tempString.replace("\\&      ","");
                afterString=afterString.replace("\\&     ","");
                afterString=afterString.replace("\\\"","\"");// [\"]->["] [\r\n]->[回车] [\      ]->[]
                afterString=afterString.replace("\\r\\n","\r\n");
                afterString=afterString.replace("\\ "," ");
                afterString=afterString.replace("&","\n");
                bw.write(afterString);

            }
            reader.close();
            bw.close();
        }   catch (IOException e) { e.printStackTrace(); }
        finally { if (reader != null)
        { try { reader.close(); } catch (IOException e1) {     }
        }
        }
    }


    @Deprecated
    public Map<Integer,String> getclickedElementsListYaml()    {//没有用到，因为数据没有保存
        Map<Integer,String> mapout = new  HashMap<>();//获取每个的url数据
        int mapoutnum=0;//数量
        try {
            Yaml yaml = new Yaml();
            File f = new File(testYml3);
            if (f != null) {
                //获取test.yaml文件中的配置数据，然后转换为obj，
                Object obj =yaml.load(new FileInputStream(f));
                //也可以将值转换为Map
                Map<String, List<Map<String,Object>>> map =(Map)yaml.load(new FileInputStream(f));//第一层，最外层
                //System.out.println(map);
                //通过map取值
                List<Map<String,Object>> list = map.get("clickedElementsList");//第二层

                for(Map<String,Object> map2 : list){//第三层
                    //keySet()方法，获取map集合的所有键的set集合
                    Iterator<String> it = map2.keySet().iterator();
                    while(it.hasNext()){
                        String key = it.next();//迭代器取出key值
                        // System.out.println(map2.get(key));//根据key值获得相应的value值
                    }
                    mapout.put(mapoutnum,map2.get("url").toString()+map2.get("text").toString());
                    mapoutnum++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(mapout);
        return mapout;

    }
    public Map<Integer,Elements> getYamlclickedElementsListWithClass(String YML)    {
        Map<Integer,Elements> mapout = new  HashMap<>();//获取每个的url数据
        int mapoutnum=0;//数量
        try {
            Yaml yaml = new Yaml();
            File f = new File(YML);
            if (f != null) {
                //获取test.yaml文件中的配置数据，然后转换为obj，
                Object obj =yaml.load(new FileInputStream(f));
                //也可以将值转换为Map
                Map<String, List<Map<String,Object>>> map =(Map)yaml.load(new FileInputStream(f));//第一层，最外层
                //System.out.println(map);
                //通过map取值
                List<Map<String,Object>> list = map.get("clickedElementsList");//第二层
                //System.out.println(list);
                //list为[{url=com.zol.android.首页, tag=Start , action=_Start}, {url=Steps, tag=android.widget.TextView, action=click}]
                for(Map<String,Object> map2 : list){//第三层
                    //keySet()方法，获取map集合的所有键的set集合
               /*     Iterator<String> it = map2.keySet().iterator();
                    while(it.hasNext()){
                        String key = it.next();//迭代器取出key值
                        // System.out.println(map2.get(key));//根据key值获得相应的value值
                    }*/
                    Elements e =new Elements();

                    e.setUrl(map2.get("url").toString());
                    e.setTag(map2.get("tag").toString());
                    e.setId(map2.get("id").toString());
                    e.setName(map2.get("name").toString());
                    e.setText(map2.get("text").toString());
                    e.setInstance(map2.get("instance").toString());
                    e.setDepth(map2.get("depth").toString());
                    e.setValid(map2.get("valid").toString());
                    e.setSelected(map2.get("selected").toString());
                    e.setXpath(map2.get("xpath").toString());
                    e.setAncestor(map2.get("ancestor").toString());
                    e.setAction(map2.get("action").toString());
                    e.setX(Integer.valueOf(map2.get("x").toString()));
                    e.setY(Integer.valueOf(map2.get("y").toString()));
                    e.setWidth(Integer.valueOf(map2.get("width").toString()));
                    e.setHeight(Integer.valueOf(map2.get("height").toString()));
                    mapout.put(mapoutnum,e);
                    mapoutnum++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(mapout);
        return mapout;
    }



    public Map<Integer,ElementStore> getYamlelementStoreMapWithClass(String YML)    {
        Map<Integer,ElementStore> mapout = new  HashMap<>();//获取每个的url数据
        int mapoutnum=0;//数量
        try {
            Yaml yaml = new Yaml();
            File f = new File(YML);
            if (f != null) {
                Map<String, Map<String,Map<String,Object>>> map =(Map)yaml.load(new FileInputStream(f));//第一层，最外层，还带有clickedElementsList
                // System.out.println(map);
                Map<String,Map<String,Object>> map2=map.get("elementStoreMap");//第二层
                //System.out.println(map2);
                //System.out.println("***********************************");
                //Iterator<String> it = map2.keySet().iterator();
                Iterator<Map.Entry<String ,Map<String,Object>>>  it2 = map2.entrySet().iterator();
                while (it2.hasNext()) {
                    //it.next()获取嵌套对象
                    Map.Entry<String,Map<String ,Object>> map3= it2.next();//第三层
                   // System.out.println(map3);
                   // System.out.println("KEYYYYYY"+map3.getKey());
                  //  System.out.println("VAAAAAAA"+map3.getValue());
                    ElementStore es =new ElementStore();
                    es.setElementStoreName(map3.getKey());
                    //  System.out.println(map3.getValue().get("reqDom"));
                  //  System.out.println(map3.getValue().get("reqHash").toString());
                    es.setReqDom(map3.getValue().get("reqDom").toString());
                    es.setResDom(map3.getValue().get("resDom").toString());
                    es.setReqHash(map3.getValue().get("reqHash").toString());
                    es.setResHash(map3.getValue().get("resHash").toString());
                    es.setReqImg(map3.getValue().get("reqImg").toString());
                    es.setResImg(map3.getValue().get("resImg").toString());
                    es.setReqTime(map3.getValue().get("reqTime").toString());
                    es.setResTime(map3.getValue().get("resTime").toString());
                    es.setClickedIndex(Integer.valueOf(map3.getValue().get("clickedIndex").toString()));
                    es.setAction(map3.getValue().get("action").toString());
                    //System.out.println(map3.getValue().get("element").toString());
                    Map<String,Object> map6= getStringToMap(map3.getValue().get("element").toString());//使用了string分割转成map的方法
                    //System.out.println(map6.toString());
                    Elements e =new Elements();
                    e.setUrl(map6.get("url").toString());
                    e.setTag(map6.get("tag").toString());
                    e.setId(map6.get("id").toString());
                    e.setName(map6.get("name").toString());
                    e.setText(map6.get("text").toString());
                    e.setInstance(map6.get("instance").toString());
                    e.setDepth(map6.get("depth").toString());
                    e.setValid(map6.get("valid").toString());
                    e.setSelected(map6.get("selected").toString());
                    e.setXpath(map6.get("xpath").toString());
                    e.setAncestor(map6.get("ancestor").toString());
                    e.setAction(map6.get("action").toString());
                    e.setX(Integer.valueOf(map6.get("x").toString()));
                    e.setY(Integer.valueOf(map6.get("y").toString()));
                    e.setWidth(Integer.valueOf(map6.get("width").toString()));
                    e.setHeight(Integer.valueOf(map6.get("height").toString()));
                    es.setElement(e);
                    mapout.put(mapoutnum,es);
                    mapoutnum++;
                }
                   /*

               //     System.out.println("【】【】"+e.toString());

                   /* for(Map.Entry<String,Object> map5 : map3.getValue().get("element").entrySet())
                    {
                        e.setTag(map3.getValue().get("element").get("tag").toString());
                        System.out.println("【key】"+map5.getKey());
                        System.out.println("【val】"+map5.getValue());
                    }
*/
                    /*
       /*         for(Map.Entry<String,Map<String,Map<String,Object>>> entry : map2.entrySet()){//第三层，key为com.zol.android.text=标题，value为reqDom等
                    String mapKey = entry.getKey();
                    Map<String,Map<String,Object>> mapValue = entry.getValue();
                    System.out.println(mapKey+"&&&&"+mapValue);

                    for(Map.Entry<String,Map<String,Object>> entry2 : mapValue.entrySet()){
                        ElementStore es =new ElementStore();
                        String mapKey2 = entry2.getKey();
                        es.setElementStoreName(mapKey2);
                      //  es.setReqDom(entry2.getValue());
                        Map<String,Object> m3=entry2.getValue();
                        es.setReqDom(m3.get("reqDom").toString());
                        System.out.println("@@@@@@@@");
                        System.out.println(mapKey2);
                        System.out.println(m3.get("reqDom").toString());
                        System.out.println("@@@@@@@@");

                    }
                }*/
              /*  for(Map<String,Map<String,Object>> map3 : map2.values()){
                    System.out.println(map3);
                    ElementStore es =new ElementStore();
                    Map<String,Object> map4=map3.get("reqDom");
                    System.out.println(map3.get("reqDom"));
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(map3.get("reqDom"));
                    System.out.println(map4);
                    System.out.println(map3.get("reqDom").values());
                    System.out.println(map3.get("reqDom").values().toString());
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    es.toString2();
                    mapout.put(mapoutnum,es);
                }*/
              /*  for(Map<String,Map<String,Map<String,Object>>> map3 :  map2){//第三层
                    //keySet()方法，获取map集合的所有键的set集合
                    Iterator<String> it = map3.keySet().iterator();
                    while(it.hasNext()){
                        String key = it.next();//迭代器取出key值
                         System.out.println(map2.get(key));//根据key值获得相应的value值
                    }
                    ElementStore es =new ElementStore();
                    mapout.put(mapoutnum,es);
                    mapoutnum++;
                }*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=================");
      //  System.out.println(mapout.toString());
        return mapout;

    }

    public static Map<Integer,ElementStore> getOrderFromClickElmentAndgetInfoFromElementStore(Map<Integer,Elements> mapCE,Map<Integer,ElementStore> mapES) {
        Map<Integer,ElementStore> mapout = new  HashMap<>();
        int num=0;

        for(Elements mapce : mapCE.values()){//遍历ClickElment的map
       //     System.out.println(mapce);
            String ancestorCE =mapce.getAncestor();
            for(ElementStore mapes : mapES.values()){//遍历ElementStore的map
       //         System.out.println(mapes);
                String ancestorES =mapes.getElement().getAncestor();
                if(ancestorCE.equals(ancestorES))//两者的ances相同
                {
                    mapout.put(num,mapes);
                    num++;
                    break;
                }
            }
        }

        return mapout;
    }

    public static void  printInfo(Map<Integer,ElementStore> map)
    {
        for(ElementStore map2 : map.values()){//遍历ClickElment的map
              //   System.out.println(map2.toString3()+"\n");
            System.out.println(map2.getElement().getId());
        }

    }

    public static void  printCombinedInfo(Map<Integer,Elements> map)//拼接clickedElements中的信息从而与ElementStore的name比较
    {
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}（【】‘；：”“’。， 、？]";
        for(Elements map2 : map.values()){//遍历ClickElment的map
            String tag2=map2.getTag().replace("android.widget.","");
            String id2=map2.getId().substring(map2.getId().lastIndexOf("/")+1);
            String text2=map2.getText().replaceAll(regEx,"");
            //System.out.println(id2);
            //System.out.println(map2.getId());
            String combined= map2.getUrl()+".tag="+tag2+".depth="+map2.getDepth();//+".id="+map2.getId();+".text="+map2.getText();
            if(!map2.getId().isEmpty())//仍然是getid
                combined=combined+".id="+id2;
            if(!map2.getText().isEmpty())
                combined=combined+".text="+text2;
            if(!map2.getName().isEmpty())
                combined=combined+".name="+map2.getName();
          //  System.out.println(combined);
        }

    }
    public static void main(String[] args) {

        ToYaml ty2 = new ToYaml();

     /*   ty2.readFileByLines(testYml1);
        ty2.readFileByLines2();*/

        //获取clickedElements并保存到Elements类中
        //Map<Integer,Elements> map=ty2.getYamlclickedElementsListWithClass(testYml);
        //System.out.println(map);
        //获取elementStoreMap并保存到ElementStore类中
     //   Map<Integer,ElementStore> map2=t.y2.getYamlelementStoreMapWithClass();
     //  System.out.println(map2);

        //测试图中结点建立情况
        /*
        Map<Integer,String> map3=ty2.getclickedElementsListYaml();
        System.out.println(map3);
        ToGraph tp = new ToGraph();
        tp.connect();
        tp.addNode(map3);
*/
/*
        //正式读取elements.yml文件并尝试在图中建立结点
        Map<Integer,ElementStore> map2=ty2.getYamlelementStoreMapWithClass();
        System.out.println(map2);
        ToGraph tp = new ToGraph();
        tp.connect();
        tp.addNodeWithElementStoreMap(map2);*/

        //获取clickedElements并保存到Elements类中
        Map<Integer,Elements> map=ty2.getYamlclickedElementsListWithClass(y20201231104305);
      //  System.out.println(map);
        //读取elements.yml文件的ElementStore并尝试在图中建立结点
        Map<Integer,ElementStore> map2=ty2.getYamlelementStoreMapWithClass(y20201231104305);
     //   System.out.println(map2);
        //按照clickedElements中的顺序填充数据
        ToGraph tp = new ToGraph();
        tp.connect();
        //并建立图结构的连接
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    //    printInfo(map2);
        printCombinedInfo(map);
       // System.out.println(getOrderFromClickElmentAndgetInfoFromElementStore(map,map2));
       // tp.addNodeWithElementStoreMap(getOrderFromClickElmentAndgetInfoFromElementStore(map,map2));

    }

}
