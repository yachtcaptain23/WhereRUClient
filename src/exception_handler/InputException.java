package exception_handler;

import android.app.Activity;
import android.widget.Toast;

public class InputException {
	public InputException() {
		
	}
	
	public void sendToast(Activity a) {
		Toast.makeText(a.getBaseContext(), "Account name must not be null",
				Toast.LENGTH_SHORT).show();
		return;
	}
}
