package files;

import io.restassured.path.json.JsonPath;

public class ComplexJson {
	public static void main(String[] args) {

		JsonPath js = new JsonPath(payload.coursePrice());

		int count = js.getInt("courses.size()");
		System.out.println("Print No.of course return by API" + " " + "is" + " " + count);
		System.out.println(js.getString("dashboard.purchaseAmount"));
		System.out.println(js.getString("courses[0].title"));
		
		for(int i=0;i<count;i++)
		{
			String coursesTitle=js.getString("courses["+i+"].title");
			int coursesPrice=js.getInt("courses["+i+"].price");
			System.out.println(coursesTitle);
			System.out.println(coursesPrice);

		}
 
	}
}