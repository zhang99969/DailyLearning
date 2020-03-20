#include <iostream>
using namespace std;



class MyExample
{
	public:
		MyExample( ) { pBuffer=NULL; nSize=0; }
		~MyExample( ) { delete pBuffer; }
		MyExample( const MyExample & ); //�������캯��
		MyExample& operator=( const MyExample & ); //��ֵ������
		void Init(int n) { pBuffer= new char[n]; nSize=n; }
		void Out( ){ cout<<nSize<<pBuffer<<endl; }
	private:
		char *pBuffer; //��Ķ����а���ָ��,ָ��̬������ڴ���Դ
		int nSize;
} ;
/*
MyExample:: MyExample ( const MyExample &RightSides) //�������캯���Ķ���
{
	nSize= RightSides.nSize; //���Ƴ����Ա
	pBuffer= new char[nSize]; //����ָ��ָ�������
	memcpy ( pBuffer , RightSides.pBuffer , nSize * sizeof( char ) ) ;
}
*/
MyExample & MyExample::operator=( const MyExample &RightSides)//��ֵ����������
{
	nSize= RightSides.nSize; //���Ƴ����Ա
	char *temp= new char[nSize]; //����ָ��ָ�������
	memcpy(temp, RightSides.pBuffer, nSize*sizeof( char ) ) ;
	delete []pBuffer; //ɾ��ԭָ��ָ������(��ɾ���������ں���,����X= X���������,���ݵĶ�ʧ)
	pBuffer= temp; //������ָ��
	return * this;
}

MyExample::MyExample ( const MyExample &RightSides)//�������캯��ʹ�ø�ֵ��������صĴ���
{
	pBuffer= NULL;
	*this= RightSides; //�������غ�ġ� = ��
}

int main()
{
	MyExample theObjone;
	theObjone.Init(4) ;
	MyExample theObjthree;
	theObjthree.Init(6);
	theObjone.Out();
	theObjthree.Out();
	//������Ҫһ������ֵ����,����ֵ�����ԭ���ݱ����,�����ұ߶����������䡣
	theObjthree=theObjone;
	theObjone.Out();
	theObjthree.Out();
	return 0;
}