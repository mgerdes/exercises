import java.util.*;

class h {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int numOfTestCases = input.nextInt();

		int speedOfHourHand = 30;
		int speedOfMinuteHand = 360;
		int speedOfSecondHand = 360 * 60;

		for (int i = 0; i < numOfTestCases; i++) {
			int deltaTheta = input.nextInt();
			String modifier = input.next();
			int h_0 = input.nextInt();

			double deltaTime = 0;
			double thetaHour_0 = 0;

			double thetaHour = 0;
			double thetaMinute = 0;
			double thetaSecond = 0;

			if (modifier.charAt(0) == 'a') {
				thetaHour_0 = 30 * h_0;
				deltaTime = (deltaTheta + thetaHour_0) / (speedOfMinuteHand - speedOfHourHand);	

				thetaHour = (thetaHour_0 + speedOfHourHand * deltaTime) % 360;
				thetaMinute = (speedOfMinuteHand * deltaTime) % 360;
				thetaSecond = (speedOfSecondHand * deltaTime) % 360;
			} else {
				int newTime = 12 - h_0;
				if (newTime == 0) newTime = 12;
				thetaHour_0 = 30 * newTime;
				deltaTime = (deltaTheta - thetaHour_0) / (speedOfHourHand - speedOfMinuteHand);
				
				thetaHour = 360 - (thetaHour_0 + speedOfHourHand * deltaTime) % 360;
				thetaMinute = 360 - (speedOfMinuteHand * deltaTime) % 360;
				thetaSecond = 360 - (speedOfSecondHand * deltaTime) % 360;
			}

			int hour = (int)(thetaHour / 30);
			int minute = (int)(thetaMinute / 6);
			int second = (int)Math.round(thetaSecond / 6);

			if (hour == 0) hour = 12;
			if (minute == 60) minute = 0;
			if (second == 60) second = 0;

			//System.out.println(thetaHour + ", " + thetaMinute + ", " + thetaSecond);
			System.out.printf("Case %d: %d:%02d:%02d\n", i+1, hour, minute, second);
		}
	}
}
