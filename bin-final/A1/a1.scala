object Test
{
	def main(args:Array[String])
	{
		println("Enter Count : ");
		var count=Console.readInt;
		val startTimeMillis = System.currentTimeMillis();
		var list=new Array[Int](count);
		for(a<-0 to (count-1))
		{
			//println("Enter Element "+(a+1)+": ");
			val r = new scala.util.Random
			var b = r.nextInt(1000)
			list(a)=b;	
		}
		scala.util.Sorting.quickSort(list);
		
		for(a<-0 to (count-1))
		{
			println("Element "+(a+1)+":"+(list(a)));
		}
		println("Enter element to be found :");
		var num=Console.readInt;

		var j=BinarySearch(list,num);

		if(j==(-1))
		{
			println("Element Not Found");
		}
		else
		{
			println("Element found at location "+(j+1));
		}
		val endTimeMillis = System.currentTimeMillis()
		val durationSeconds = (endTimeMillis - startTimeMillis) / 1000	
		println("The execuation time is : "+durationSeconds);

	}

	def BinarySearch(list:Array[Int],num:Int):Int=
	{
		var left=0;
		var right=list.length-1;
	
		while(left<=right)
		{
			val mid=left+(right-left)/2;

			if(list(mid)==num)
			{
				return mid;
			}
			if(list(mid)>num)
			{
				right=mid-1;
			}
			else
			{
				left=mid+1;
			}
		}
		return (-1);
		
	}

}
