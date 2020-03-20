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
		CPerson(CPerson &one)//�������캯��
		{
			strName=(char*)new char[strlen(one.strName)+1];//�������ڴ�ռ�
			strcpy(strName,one.strName);//�ַ�������
		}
		CPerson &operator=(const CPerson &one)//���صĸ�ֵ���������
		{
			if(this==&one) return *this;//��ֹone=one �ĸ�ֵ
			delete []strName;//�ͷŵ�ԭ�ڴ�ռ�
			strName=new char[strlen(one.strName)+1];//�������ڴ�ռ�
			strcpy(strName,one.strName);//�ַ�������
			return *this;//���ض��������
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
	CPerson two(one);//����Ĭ�ϵĿ������캯��
	two.print();
	one.print();
	CPerson four("Marc");
	CPerson three("Martin");
	three.print();
	four.print();
	three=four; //����Ĭ�ϵĸ�ֵ���������
	three.print();
	four.print();
}