package exception_handler;

import android.app.Activity;
import android.widget.Toast;

public class LogInException {
	public LogInException() {
		
	}
	
	public void sendToast(Activity a) {
		Toast.makeText(a.getBaseContext(), "Password is incorrect",
				Toast.LENGTH_SHORT).show();
		return;
	}
}
