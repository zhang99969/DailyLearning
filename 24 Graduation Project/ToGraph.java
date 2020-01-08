import java.io.File;
import java.util.*;
import java.util.Map;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class ToGraph {

    //int TotalNodeNum=0;
    private static final File DB_PATH = new File("D:\\Program Files\\neo4j-community-3.5.9\\data\\databases\\graph.db");
    private static GraphDatabaseService graphDb;

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    private static enum RelTypes implements RelationshipType {
        RELEASED,JUMPTO,JUMPTOWITHES;
    }

    private static boolean creatNodeFromMap(int num, String name){//添加一个节点到图中
        Label label1;
        label1 = Label.label("Page");
        Node node1;
        try (Transaction tx = graphDb.beginTx()) {
            //ResourceIterator<Node> result = graphDb.findNodes(label1,"Number",num,"UrlName",name);
            ResourceIterator<Node> result = graphDb.findNodes(label1,"UrlName",name);
            if (result.hasNext()){//
                System.out.println(name+"节点已经存在");
                return false;
             }
            else{
                node1 = graphDb.createNode(label1);
                node1.setProperty("Number",num);
                node1.setProperty("UrlName",name);
                System.out.println(name+"节点创建成功");}
                // 提交事务
                tx.success();
                return true;
        }
    }
    private void creatRalationByNode(Map<Integer,String> map){//图中的节点按顺序连接
        Relationship relationship;
        try (Transaction tx = graphDb.beginTx()) {
            // 查询节点
            Label label = Label.label("Page");
            Node nodeStart,nodeEnd;//关系两侧的节点

            for (int num=0; num<map.size()-1; num++) {
                nodeStart = graphDb.findNode(label, "UrlName", map.get(num) );
                nodeEnd= graphDb.findNode(label, "UrlName", map.get(num+1) );//按照map中的顺序进行连接
                boolean hasRela=false;
            /*for(int num=0;num<TotalNodeNum;num++) {
                nodeStart = graphDb.findNode(label, "Number", num);
                nodeEnd = graphDb.findNode(label, "Number", num+1); 旧的方法，会遗漏重复的*/

                //判断nodeStart和nodeEnd节点之间是否已经存在关系
              //  Relationship relationshipifexisted = nodeStart.getSingleRelationship(RelTypes.JUMPTO, Direction.INCOMING);//只能获取一个关系
                if(nodeStart.hasRelationship(Direction.OUTGOING)&&nodeEnd.hasRelationship(Direction.INCOMING)){
                   /* if(nodeStart.getSingleRelationship(RelTypes.JUMPTO,Direction.OUTGOING).equals(nodeEnd.getSingleRelationship(RelTypes.JUMPTO,Direction.INCOMING))){
                        hasRela=true;        }else { hasRela=false;           }//只能获取一个关系，节点有多个关系时会出错*/
                    for ( Relationship rStart : nodeStart.getRelationships( RelTypes.JUMPTO,Direction.OUTGOING ) )
                    {
                        for ( Relationship rEnd : nodeEnd.getRelationships( RelTypes.JUMPTO,Direction.INCOMING ) )
                        {
                            //System.out.println(nodeStart.getProperty("UrlName")+" rSt="+rStart.toString()+" ||||  "+nodeEnd.getProperty("UrlName")+" rEn="+ rEnd.toString());
                            if(rStart.equals( rEnd ))//是同一个关系，说明关系已存在
                            {
                                hasRela=true;
                                break;
                            }
                        }
                        if(hasRela)
                        {break;}//结束循环
                    }

                }else {
                    hasRela=false;
                }

                if(!hasRela){//判定
                    relationship = nodeStart.createRelationshipTo(nodeEnd, RelTypes.JUMPTO);
                    relationship.setProperty("JumpBy", "Clicked");
                    System.out.println(nodeStart.getProperty("UrlName") + "→" + nodeEnd.getProperty("UrlName") + "已连接");
                }
                else{
                    System.out.println(nodeStart.getProperty("UrlName") + "→" + nodeEnd.getProperty("UrlName") + "的连接已经存在");
                    //return false;
                }
                tx.success();
                //return true;
            }
        //graphDb.shutdown();
        }
    }


    public void addNode(Map<Integer,String> map){//从Map中读取节点，创建节点和关系
        for (int num : map.keySet()) {
            String urlName1 =map.get(num);//+num;
            System.out.println("num= " + num + " and url= " + urlName1);
        }
        Set set = map.entrySet();
        Iterator i = set.iterator();
        int SameNum=0;
        while(i.hasNext()){
            Map.Entry<Integer, String> entry1=(Map.Entry<Integer, String>)i.next();
            String urlName1 = entry1.getValue();//+entry1.getKey();//加getKey防止重复
            int urlNum1 = entry1.getKey()-SameNum;
            System.out.print(urlNum1+"=="+urlName1+"  ");
            //TotalNodeNum=urlNum1;//值为节点总数-1
            //创建节点
            boolean ifCreatSucc = creatNodeFromMap(urlNum1, urlName1);//创建节点
            if (!ifCreatSucc)//节点已存在
            {
                SameNum++;//出现已经创建过的节点，不计数。即重复节点的num和map中下一个num相同。
                //TotalNodeNum--;
            }
        }
        creatRalationByNode(map);//创建节点间关系
    }


    private static boolean creatNodeOfAllInformationWithElementsStoreMap()//确定可以创建，把map中所有的信息进行创建
    {
        return false;
    }

    private static boolean creatNodeFromMapWithElementStoreMap(int num, ElementStore es){//添加一个节点到图中
        Label label1;
        label1 = Label.label("PageWithES");
        Node node1;
        String name= es.getElementStoreName();
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterator<Node> result = graphDb.findNodes(label1,"ElementStoreName",name);
            if (result.hasNext()){
                System.out.println(name+"节点已经存在");
                return false;
            }
            else{
                node1 = graphDb.createNode(label1);
                node1.setProperty("Number",num);
                //elementStoreMap
                node1.setProperty("ElementStoreName",name);
                node1.setProperty("reqDom",es.getReqDom());
                node1.setProperty("resDom",es.getResDom());
                node1.setProperty("reqHash",es.getReqHash());
                node1.setProperty("resHash",es.getResHash());
                node1.setProperty("reqImg",es.getReqImg());
                node1.setProperty("resImg",es.getResImg());
                node1.setProperty("reqTime",es.getReqTime());
                node1.setProperty("resTime",es.getResTime());
                node1.setProperty("action",es.getAction());
                node1.setProperty("clickedIndex",es.getClickedIndex());
                //clickedElements
                node1.setProperty("Elements_url",es.getElement().getUrl());
                node1.setProperty("Elements_tag",es.getElement().getTag());
                //System.out.println("---------------"+es.getElement().getId()+"|"+es.getElement().getTag()+"|"+es.getElement().getUrl()+"|"+es.getElement().getText());
                node1.setProperty("Elements_id",es.getElement().getId());
                node1.setProperty("Elements_name",es.getElement().getName());
                node1.setProperty("Elements_text",es.getElement().getText());
                node1.setProperty("Elements_instance",es.getElement().getInstance());
                node1.setProperty("Elements_depth",es.getElement().getDepth());
                node1.setProperty("Elements_valid",es.getElement().getValid());
                node1.setProperty("Elements_selected",es.getElement().getSelected());
                node1.setProperty("Elements_xpath",es.getElement().getXpath());
                node1.setProperty("Elements_ancestor",es.getElement().getAncestor());
                node1.setProperty("Elements_action",es.getElement().getAction());
                node1.setProperty("Elements_x",es.getElement().getX());
                node1.setProperty("Elements_y",es.getElement().getY());
                node1.setProperty("Elements_width",es.getElement().getWidth());
                node1.setProperty("Elements_height",es.getElement().getHeight());
                System.out.println(name+"节点创建成功");
            }
            // 提交事务
            tx.success();
            return true;
        }
    }


    private void creatRalationByNodeWithElementStoreMap(Map<Integer,ElementStore> map){//图中的节点按顺序连接
        Relationship relationship;
        try (Transaction tx = graphDb.beginTx()) {
            // 查询节点
            Label label = Label.label("PageWithES");
            Node nodeStart,nodeEnd;//关系两侧的节点


            for (int num=0; num<map.size()-1; num++) {
                System.out.println("#########"+ map.get(num).getElementStoreName());
                
                nodeStart = graphDb.findNode(label, "ElementStoreName", map.get(num).getElementStoreName());
                nodeEnd= graphDb.findNode(label, "ElementStoreName", map.get(num+1).getElementStoreName() );//按照map中的顺序进行连接
                boolean hasRela=false;
                if(nodeStart.hasRelationship(Direction.OUTGOING)&&nodeEnd.hasRelationship(Direction.INCOMING)){
                    for ( Relationship rStart : nodeStart.getRelationships( RelTypes.JUMPTOWITHES,Direction.OUTGOING ) )
                    {
                        for ( Relationship rEnd : nodeEnd.getRelationships( RelTypes.JUMPTOWITHES,Direction.INCOMING ) )
                        {
                            if(rStart.equals( rEnd ))//是同一个关系，说明关系已存在
                            {
                                hasRela=true;
                                break;
                            }
                        }
                        if(hasRela)
                        {break;}//结束循环
                    }
                }else {
                    hasRela=false;
                }
                if(!hasRela){//判定
                    relationship = nodeStart.createRelationshipTo(nodeEnd, RelTypes.JUMPTOWITHES);
                    relationship.setProperty("JumpByWithES", "跳转到");
                    System.out.println(nodeStart.getProperty("ElementStoreName") + "-----→" + nodeEnd.getProperty("ElementStoreName") + "已连接");
                }
                else{
                    System.out.println(nodeStart.getProperty("ElementStoreName") + "→" + nodeEnd.getProperty("ElementStoreName") + "的连接已经存在");
                    //return false;
                }
                tx.success();
                //return true;
            }
            //graphDb.shutdown();
        }
    }

    public void addNodeWithElementStoreMap(Map<Integer,ElementStore> map){//从Map中读取节点，创建节点和关系
        for (int num : map.keySet()) {  //只是用作展示输出，没有用
            ElementStore es0 =map.get(num);//+num;
            System.out.println("num= " + num + " and ElementStore= " + es0.toString());
        }
        Set set = map.entrySet();
        Iterator i = set.iterator();
        int SameNum=0;

        while(i.hasNext()){
            Map.Entry<Integer, ElementStore> entry1=(Map.Entry<Integer, ElementStore>)i.next();
            ElementStore EsName1 = entry1.getValue();//+entry1.getKey();//加getKey防止重复
            int EsNum1 = entry1.getKey()-SameNum;
            System.out.print(EsNum1+"=="+EsName1.toString()+"  ");
            //创建节点
            boolean ifCreatSucc = creatNodeFromMapWithElementStoreMap(EsNum1, EsName1);//创建节点
            if (!ifCreatSucc)//节点已存在
            {
                SameNum++;//出现已经创建过的节点，不计数。即重复节点的num和map中下一个num相同。
            }
        }
        creatRalationByNodeWithElementStoreMap(map);//创建节点间关系
    }

    @SuppressWarnings("unused")
    private static void DEMOaddData() {
        Node node1;
        Node node2;
        Label label1;
        Label label2;
        Relationship relationship;

        try (Transaction tx = graphDb.beginTx()) {
            // 创建标签
            label1 = Label.label("Musician");
            label2 = Label.label("Album");
            // 创建节点
            node1 = graphDb.createNode(label1);
            node1.setProperty("name", "JayChou");
            node2 = graphDb.createNode(label2);
            node2.setProperty("name", "Fantasy");
            // 创建关系及属性
            relationship = node1.createRelationshipTo(node2, RelTypes.RELEASED);
            relationship.setProperty("date", "2001-09-14");
            // 结果输出
            System.out.println("created node name is " + node1.getProperty("name"));
            System.out.println(relationship.getProperty("date"));
            System.out.println("created node name is " + node2.getProperty("name"));
            // 提交事务
            tx.success();
        }
        graphDb.shutdown();
    }

    @SuppressWarnings("unused")
    private static void DEMOqueryAndUpdate() {
        try (Transaction tx = graphDb.beginTx()) {
            // 查询节点
            Label label = Label.label("Musician");
            Node node = graphDb.findNode(label, "name", "JayChou");
            System.out.println("query node name is " + node.getProperty("name"));
            // 更新节点
            node.setProperty("birthday", "1979-01-18");
            System.out.println(node.getProperty("name") + "'s birthday is " + node.getProperty("birthday", new String()));
            // 提交事务
            tx.success();
        }
        graphDb.shutdown();
    }

    @SuppressWarnings("unused")
    private static void DEMOdelete() {
        try (Transaction tx = graphDb.beginTx()) {
            // 获得节点
            Label label = Label.label("Album");
            Node node = graphDb.findNode(label, "name", "Fantasy");
            // 获得关系
            Relationship relationship = node.getSingleRelationship(RelTypes.RELEASED, Direction.INCOMING);
            // 删除关系和节点
            relationship.delete();
            relationship.getStartNode().delete();
            node.delete();
            System.out.println("node and relationship deleted");
            tx.success();
        }
        graphDb.shutdown();
    }


    public void connect(){
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(graphDb);
    }

    @SuppressWarnings("unused")
    public void testConn(){//测试节点之间是否存在关系的方法
        Node node1,node2,node3;
        Label label1 = Label.label("TePage");
        Relationship relationship,relationship2;
/*
        try (Transaction tx = graphDb.beginTx()) {
            // 创建标签

            // 创建节点
            node1 = graphDb.createNode(label1);
            node1.setProperty("name", "P1");
            node2 = graphDb.createNode(label1);
            node2.setProperty("name", "P2");
            node3 = graphDb.createNode(label1);
            node3.setProperty("name", "P3");
            // 创建关系及属性
            relationship = node1.createRelationshipTo(node2, RelTypes.JUMPTO);
            relationship.setProperty("date", "2001-09-14");
            relationship2 = node2.createRelationshipTo(node3, RelTypes.JUMPTO);
            relationship2.setProperty("date", "2001-09-14");
            // 结果输出
            System.out.println("测试节点关系创建成功");
            // 提交事务
            tx.success();
        }*/
        try (Transaction tx = graphDb.beginTx()) {
        Node nodeStart = graphDb.findNode(label1, "name", "P1");
        Node nodeEnd = graphDb.findNode(label1, "name", "P3");
        //if(nodeStart.getSingleRelationship(RelTypes.JUMPTO,Direction.OUTGOING).getId()==nodeEnd.getSingleRelationship(RelTypes.JUMPTO,Direction.INCOMING).getId()) or
        if(nodeStart.getSingleRelationship(RelTypes.JUMPTO,Direction.OUTGOING).equals(nodeEnd.getSingleRelationship(RelTypes.JUMPTO,Direction.INCOMING)))
        {
            System.out.println("节点之间存在关系");
        }
        else{
            System.out.println("节点之间不存在关系并可以创建");
        }
            tx.success();
        }
    }


    public static void main(String[] args) {

        ToGraph tp = new ToGraph();
        tp.connect();
        //DEMOaddData();
        //DEMOqueryAndUpdate();
        // ();
        tp.testConn();


    }

}