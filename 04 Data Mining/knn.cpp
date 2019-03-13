/*
 * KNN.

输入：
第一行输入依次为：k(k<=10000)，特征向量的长度L(L<=100)，训练数据行数M(M>k, M<=10000)，测试数据行数N(N<=10000)为测试数据行数。
之后是M行训练数据和N行测试数据。每行中数据使用空格分隔。
输出：
预测类别

测试数据：
3 5 16 2
0.19 0.04 0.06 0.22 0.11 A
0.28 0.42 0.38 0.39 0.44 B
0.71 0.61 0.54 0.52 0.54 C
0.98 0.82 0.92 0.98 0.97 D
0.05 0.03 0.15 0.01 0.11 A
0.33 0.29 0.33 0.47 0.27 B
0.72 0.52 0.61 0.71 0.68 C
0.78 0.86 0.91 1.0 0.76 D
0.01 0.17 0.14 0.15 0.2 A
0.44 0.36 0.32 0.32 0.35 B
0.67 0.65 0.57 0.58 0.52 C
0.87 0.92 0.8 0.83 0.77 D
0.01 0.11 0.14 0.12 0.07 A
0.33 0.43 0.43 0.45 0.38 B
0.57 0.54 0.75 0.7 0.64 C
0.9 0.94 0.83 0.96 0.77 D
0.29 0.29 0.42 0.36 0.27
0.56 0.67 0.71 0.66 0.7
输出：
B
C
*/

#include <iostream>
#include <vector>
#include <math.h>
#include <map>
#include <algorithm>
#include <fstream>

using namespace std;


  struct sample {
      char label;
      double distance;
  };


  bool cmp(sample a, sample b)
  {
      return a.distance < b.distance;
  }


  void readTrainData(vector<vector<double> > &trainData, vector<char> &trainLabel, int L, int M)
  {
      ifstream inFile;  //打开训练数据txt文件
      inFile.open("train.txt");
          if(!inFile.is_open())
              {
                  cout << "can not read file tran" << endl;
              }
      for (int i = 0; i < M; ++i) {
          // 读入训练数据特征
          vector<double> lineData;
          double tmpData;

          for (int j = 0; j < L; ++j) {
                 inFile>>tmpData;
                  //cout<<tmpData<<" ";//测试
                 lineData.push_back(tmpData);
          }
          trainData.push_back(lineData);          
          // 读入训练数据标注
          char label;
           inFile>>label;
         // cin >> label;
           //cout<<label<<"---"<<endl;//测试
          trainLabel.push_back(label);
       }
       inFile.close();             //关闭文件输入流
    }


  void readTestData(vector<vector<double> > &testData,vector<char> &testLabel, int L, int N)
  {

      ifstream inFile;  //打开测试数据txt文件
      inFile.open("test.txt");
          if(!inFile.is_open())
              {
                  cout << "can not read file test" << endl;
              }
         //读测试数据
      for (int i = 0; i < N; ++i) {
          vector<double> lineData;
          double tmpData;
          char tlabel;
          for (int j = 0; j < L; ++j) {
              inFile>>tmpData;
             // cout<<tmpData<<endl;//测试
              lineData.push_back(tmpData);
          }
          testData.push_back(lineData);
          //读取测试标注

           inFile>>tlabel;
         // cin >> label;
         // cout<<tlabel<<endl;//测试
          testLabel.push_back(tlabel);
      }
       inFile.close();             //关闭文件输入流
  }


  double calcDistance(vector<double> data1, vector<double> data2)
  {
      int length = data1.size();
      double distance = 0.0;
      for (int i = 0; i < length; ++i)
          distance += (data1[i] - data2[i]) * (data1[i] - data2[i]);
      return sqrt(distance);
  }


  void KNN(vector<vector<double> > trainData, vector<vector<double> > testData,
           vector<char> trainLabel, vector<char>testLabel,int k, int M, int N)
  {
      for (int i = 0; i < N; ++i) {
          // 计算每一个测试样本与所有训练样本的距离，并排序。
          vector<sample> distances;
          for (int j = 0; j < M; ++j) {
              sample tmpDistance;
              tmpDistance.distance = calcDistance(testData[i], trainData[j]);
              tmpDistance.label = trainLabel[j];
              distances.push_back(tmpDistance);
          }
          sort(distances.begin(), distances.end(), cmp);//sort排序

          // 选择前k个样本的距离，确定前k个点所在类别出现频率。
          map<char, int> labelMap;
          for ( j = 0; j < k; ++j) {
              if (!labelMap[distances[j].label])
                  labelMap[distances[j].label] = 0;
                  labelMap[distances[j].label] += 1;
          }

          // 找到labelMap中值最大的类别
          int maxVal = 0;
          char resLabel;
          for (map<char, int>::iterator it = labelMap.begin(); it != labelMap.end(); ++it)
              if (it->second > maxVal) {
                  maxVal = it->second;
                  resLabel = it->first;
              }
          // 输出类别。
          cout << resLabel << endl;//测试
  }
  }
  int main()
  {
      int k=3;
      int L=5;//特征向量长度
      int M=16;//训练数据行数
      int N=8;//测试行数


      vector<vector<double> > trainData;
      vector<vector<double> > testData;
      vector<char> trainLabel;
      vector<char> testLabel;

      readTrainData(trainData, trainLabel, L, M);
      readTestData(testData,testLabel, L, N);

      KNN(trainData, testData, trainLabel,testLabel, k, M, N);

      return 0;
  }