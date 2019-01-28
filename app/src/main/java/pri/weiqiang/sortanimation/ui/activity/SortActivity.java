package pri.weiqiang.sortanimation.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import pri.weiqiang.sortanimation.R;
import pri.weiqiang.sortanimation.ui.fragment.SortFragment;

public class SortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        if (savedInstanceState == null) {
            SortFragment fragment = new SortFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment)
                    .commit();
        }
    }
}
