#include<iostream>
#include <time.h>
#include<omp.h>
using namespace std;
class strat
{
	public:
		
		int party(int arr[],int low,int high)
		{
			int temp,i,j,key;
			key=arr[low];
			i=low+1;
			j=high;
			while(1)
			{
				while(i<high && key>=arr[i])
				{
					i++;
				}
				while(key<arr[j])
				{
					j--;
				}
				if(i<j)
				{
					temp=arr[i];
					arr[i]=arr[j];
					arr[j]=temp;
				}
				else
				{
					temp=arr[low];
					arr[low]=arr[j];
					arr[j]=temp;
					return j;
				}
			}
		}
		
		void quicky(int arr[],int low,int high)
		{
			int j;
			if(low<high)
			{
				j=party(arr,low,high);
				//omp_set_nested(1);
				cout<<"Pivot Element with "<<j<<"th index was found by thread->"<<omp_get_thread_num()<<"\n";
				#pragma omp parallel sections
				{
				
					#pragma omp section
					{
						//k=k+1;
						//cout << "Thread id " << omp_get_thread_num() << endl;
						quicky(arr,low,j-1);
					}
					#pragma omp section
					{
						//k=k+1;
						//cout << "Thread id " << omp_get_thread_num() << endl;
						quicky(arr,j+1,high);
					}
				}
			}
		}
};

int main()
{
    clock_t start, end;
    double cpu_time_used;

    start = clock();
    strat a;

    int arr[100];
    int n,i;

    cout<<"Enter the value of n\n";
    cin>>n;
    //cout<<"Enter the "<<n<<" number of elements \n";

    for(i=0;i<n;i++)
    {
	cout<<"Enter "<<(i+1)<<"th Element:";
        cin>>arr[i];
    }

    a.quicky(arr, 0, n - 1);

    cout<<"Elements of array after sorting \n";
    for(i=0;i<n;i++)
    {
        cout<<arr[i]<<"\t";
    }
    end = clock();
    cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
    cout<<"The Cpu time used is : "<<cpu_time_used;
    cout<<"\n";
}
