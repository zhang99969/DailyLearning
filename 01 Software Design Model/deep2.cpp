#include <iostream>
#include <string.h>
using namespace std;


class CPerson
{
	private:
		char *strName;
	public:
		CPerson(char *s)
		{
			strName=new char[strlen(s)+1];
			strcpy(strName,s);
		}
		CPerson(CPerson &one)//拷贝构造函数
		{
			strName=(char*)new char[strlen(one.strName)+1];//开辟新内存空间
			strcpy(strName,one.strName);//字符串拷贝
		}
		CPerson &operator=(const CPerson &one)//重载的赋值运算符函数
		{
			if(this==&one) return *this;//防止one=one 的赋值
			delete []strName;//释放掉原内存空间
			strName=new char[strlen(one.strName)+1];//开辟新内存空间
			strcpy(strName,one.strName);//字符串拷贝
			return *this;//返回对象的引用
		}
		~CPerson()
		{
			if(strName) delete []strName;
			strName=NULL;
		}
		void print()
		{
			cout<<strName<<endl;
		}
};


void main()
{
	CPerson one("Jack");
	CPerson two(one);//调用默认的拷贝构造函数
	two.print();
	one.print();
	CPerson four("Marc");
	CPerson three("Martin");
	three.print();
	four.print();
	three=four; //调用默认的赋值运算符函数
	three.print();
	four.print();
}