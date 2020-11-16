import org.testng.Assert;

import Files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {

		JsonPath jsp = new JsonPath(Payloads.coursePrice());
		int count = jsp.getInt("courses.size()");
		System.out.println("The no. of Courses :" + count);

		int purchaseAmount = jsp.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount :" + purchaseAmount);

		String firstCourseTitle = jsp.get("courses[0].title");

		System.out.println("First Course Title :" + firstCourseTitle);

		// Print All course titles and their respective Prices

		for (int i = 0; i < count; i++) {

			System.out.println("Course Title :" + jsp.get("courses[" + i + "].title").toString() + " & its price :"
					+ jsp.get("courses[" + i + "].price").toString());
		}

		// Print no of copies sold by RPA Course

		for (int i = 0; i < count; i++) {

			if (jsp.get("courses[" + i + "].title").toString().equalsIgnoreCase("RPA")) {

				System.out.println(
						"Print no of copies sold by RPA Course : " + jsp.get("courses[" + i + "].copies").toString());

				break;
			}

		}

		// Verify if Sum of all Course prices matches with Purchase Amount

		int sumOfPrice = 0;

		for (int i = 0; i < count; i++) {
			sumOfPrice = sumOfPrice + jsp.getInt("courses[" + i + "].price")*jsp.getInt("courses[" + i + "].copies");

		}
		
		System.out.println("Sum of Prices : "+ sumOfPrice);
		
		Assert.assertEquals(sumOfPrice, purchaseAmount);

	}

}
