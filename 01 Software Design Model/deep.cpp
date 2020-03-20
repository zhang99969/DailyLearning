#include <iostream>
using namespace std;



class MyExample
{
	public:
		MyExample( ) { pBuffer=NULL; nSize=0; }
		~MyExample( ) { delete pBuffer; }
		MyExample( const MyExample & ); //拷贝构造函数
		MyExample& operator=( const MyExample & ); //赋值符重载
		void Init(int n) { pBuffer= new char[n]; nSize=n; }
		void Out( ){ cout<<nSize<<pBuffer<<endl; }
	private:
		char *pBuffer; //类的对象中包含指针,指向动态分配的内存资源
		int nSize;
} ;
/*
MyExample:: MyExample ( const MyExample &RightSides) //拷贝构造函数的定义
{
	nSize= RightSides.nSize; //复制常规成员
	pBuffer= new char[nSize]; //复制指针指向的内容
	memcpy ( pBuffer , RightSides.pBuffer , nSize * sizeof( char ) ) ;
}
*/
MyExample & MyExample::operator=( const MyExample &RightSides)//赋值操作符重载
{
	nSize= RightSides.nSize; //复制常规成员
	char *temp= new char[nSize]; //复制指针指向的内容
	memcpy(temp, RightSides.pBuffer, nSize*sizeof( char ) ) ;
	delete []pBuffer; //删除原指针指向内容(将删除操作放在后面,避免X= X特殊情况下,内容的丢失)
	pBuffer= temp; //建立新指向
	return * this;
}

MyExample::MyExample ( const MyExample &RightSides)//拷贝构造函数使用赋值运算符重载的代码
{
	pBuffer= NULL;
	*this= RightSides; //调用重载后的“ = ”
}

int main()
{
	MyExample theObjone;
	theObjone.Init(4) ;
	MyExample theObjthree;
	theObjthree.Init(6);
	theObjone.Out();
	theObjthree.Out();
	//现在需要一个对象赋值操作,被赋值对象的原内容被清除,并用右边对象的内容填充。
	theObjthree=theObjone;
	theObjone.Out();
	theObjthree.Out();
	return 0;
}